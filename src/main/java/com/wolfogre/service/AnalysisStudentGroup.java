package com.wolfogre.service;

import com.wolfogre.dao.TerminalRepository;
import com.wolfogre.domain.TerminalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wolfogre on 11/20/16.
 */
@Service
public class AnalysisStudentGroup {

    private final TerminalRepository terminalRepository;

    private final FriendshipService friendshipService;

    @Autowired
    public AnalysisStudentGroup(TerminalRepository terminalRepository, FriendshipService friendshipService) {
        this.terminalRepository = terminalRepository;
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

        List<TerminalEntity> terminalEntityList = terminalRepository.findAll();
        //for(TerminalEntity terminalEntity : terminalEntityList)
        //    System.out.println(terminalEntity.getTermName());
        System.out.println(friendshipService.get("a", "b"));
        friendshipService.set("a", "b", 1);
        System.out.println(friendshipService.get("a", "b"));
        friendshipService.set("a", "c", 100);
        System.out.println(friendshipService.get("a", "c"));
        // TODO:狗日的三个都是 null
    }
}
