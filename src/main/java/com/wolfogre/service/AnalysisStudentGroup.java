package com.wolfogre.service;

import com.wolfogre.dao.TerminalRepository;
import com.wolfogre.dao.TransactionRepository;
import com.wolfogre.domain.TerminalEntity;
import com.wolfogre.domain.TransactionEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wolfogre on 11/20/16.
 */
@Service
public class AnalysisStudentGroup {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final TerminalRepository terminalRepository;

    private final TransactionRepository transactionRepository;

    private final FriendshipService friendshipService;

    @Autowired
    public AnalysisStudentGroup(TerminalRepository terminalRepository, TransactionRepository transactionRepository, FriendshipService friendshipService) {
        this.terminalRepository = terminalRepository;
        this.transactionRepository = transactionRepository;
        this.friendshipService = friendshipService;
    }

    public void run(String... strings) throws Exception {
        //应该被考虑的商家
        int[] businessList = {
                22,//良友食堂
                23,//可缘坊
                24,//招待食堂
                27,//尔美一楼食堂
                28,//尔美二楼食堂
                30,//尔美教授食堂
                31,//能量小站1
                33,//山明食堂
                34,//得福食堂
                35,//留香食堂
                36,//山明西点
                39,//水秀食堂
                40,//得月食堂
                41,//能量小站2
                54,//益新食堂
                67,//嘉定中快餐厅
                75,//嘉定益众餐厅
                79,//嘉定饺子店
                81,//嘉定益众早餐
                82,//延长一食堂早班
                83,//延长一食堂梅陇
                84,//延长第二食堂
                86,//延长三食堂一楼卤味
                87,//延长三食堂一楼面条
                88,//延长三食堂二楼中式
                89,//延长三食堂三楼
                91,//延长五食堂一楼午晚餐
                92,//延长五食堂二楼
                93,//延长五食堂三楼
                96,//吾馨食堂
                97,//多功能餐厅服务点2
                98,//吾悦食堂
                149,//延长三食堂二楼沙县小吃
                153,//宝山D楼超市
                163,//嘉定东门超市
                178,//全日制服务点2
                224,//多功能餐厅服务点3
                230,//中快食堂
                233,//东部二楼食堂
                234,//东部三楼食堂
                235,//东部面包房
                238,//延长申江
                239,//延长面包房新
                240,//延长清真新
                242,//Hub cafe
                243,//Hub cafe new
                294,//宝山西门教育超市
                295,//宝山图书馆教育超市
                298,//宝山新世纪教育超市
                299,//宝山南区教育超市
                300,//宝山教学D楼教育超市
                301,//延长广中路教育超市
                302,//延长西门教育超市
                303,//延长西部教育超市
                304,//嘉定东门教育超市
                315,//新闸路C楼食堂
                321,//菜管家
                343,//宝山咖啡
        };

        HashMap<String, Integer> terminalBelong = new HashMap<>();
        List<TerminalEntity> terminalEntityList = terminalRepository.findAll();
        for(TerminalEntity terminalEntity : terminalEntityList)
            terminalBelong.put(terminalEntity.getTermId(), terminalEntity.getBusinessId());

        HashMap<String, HashMap<String, Integer>> record = new HashMap<>();
        HashMap<Integer, LinkedList<TransactionEntity>> queues = new HashMap<>();
        for(int it : businessList) {
            queues.put(it, new LinkedList<>());
        }

        //String date = "20160401";
        String date = "20160402";
        List<TransactionEntity> transactionEntityList = transactionRepository.findByTransdateOrderByTranstimeAsc(date);
        logger.info("transactionEntityList.size " + transactionEntityList.size());

        Time preTime = null;
        int minuteCount = 0;
        StopWatch stopWatch = new StopWatch();
        int ioCount = 0;

        for(TransactionEntity transactionEntity : transactionEntityList) {
            if(!"3160".equals(transactionEntity.getTranscode())) {
                continue;
            }

            LinkedList<TransactionEntity> queue = queues.get(terminalBelong.get(transactionEntity.getTermid()));
            if(queue == null)
                continue;

            ++minuteCount;
            if(preTime == null || transactionEntity.getTranstime().getMinutes() != preTime.getMinutes()) {
                preTime = transactionEntity.getTranstime();
                logger.info("Now is " + transactionEntity.getTranstime() + ", minuteCount is " + minuteCount + ", io tims is " + stopWatch.getTotalTimeMillis() / (ioCount == 0 ? 1 : ioCount));
                logger.info("Yixin queue length is " + queues.get(terminalBelong.get(transactionEntity.getTermid())).size());
                minuteCount = 0;
            }

            queue.add(transactionEntity);
            while(transactionEntity.getTranstime().getTime() - queue.element().getTranstime().getTime() > 10 * 60 * 1000) //去掉十分钟之前的记录
                queue.remove();
            Iterator<TransactionEntity> iterator = queue.descendingIterator();
            iterator.next(); //去掉刚刚入队的记录
            while(iterator.hasNext()) {
                TransactionEntity it = iterator.next();
                if(it.getStuempno().equals(transactionEntity.getStuempno()))
                    break;
                stopWatch.start();
                ++ioCount;
                addFriendship(transactionEntity.getStuempno(), it.getStuempno(), 1);
                stopWatch.stop();
            }
        }


//        for (int month = 4; month <= 6; ++month)
//            for (int day = 1; day <= 31; ++day) {//之后删掉不存在的日期
//                String date = "20160" + month + (day < 10 ? ("0" + day) : day);
//                if (date.equals("20160431") || date.equals("20160631"))
//                    continue;
//                List<TransactionEntity> transactionEntityList = transactionRepository.findByTransdateOrderByTranstimeAsc(date);
//                for(TransactionEntity transactionEntity : transactionEntityList) {
//                    if(!"3160".equals(transactionEntity.getTranscode()))
//                        continue;
//                    LinkedList<TransactionEntity> queue = queues.get(terminalBelong.get(transactionEntity.getTermid()));
//                    if(queue == null)
//                        continue;
//                    queue.add(transactionEntity);
//                    while(transactionEntity.getTranstime().getTime() - queue.element().getTranstime().getTime() > 10 * 60 * 1000) //去掉十分钟之前的记录
//                        queue.remove();
//                    Iterator<TransactionEntity> iterator = queue.descendingIterator();
//                    iterator.next(); //去掉刚刚入队的记录
//                    if(iterator.hasNext()) {
//                        TransactionEntity it = iterator.next();
//                        if(it.getStuempno().equals(transactionEntity.getStuempno()))
//                            break;
//                        addFriendship(transactionEntity.getStuempno(), it.getStuempno(), 1);
//                    }
//                }
//                logger.info("Finish " + date);
//            }

    }

    private void addFriendship(String first, String second, int value) {
        Integer oldValue = friendshipService.get(first, second);
        if(oldValue == null)
            friendshipService.set(first, second, value);
        else
            friendshipService.set(first, second, oldValue + value);
    }
}
