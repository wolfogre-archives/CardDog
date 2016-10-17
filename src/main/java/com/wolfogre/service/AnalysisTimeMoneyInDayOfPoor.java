package com.wolfogre.service;

import com.wolfogre.dao.PoorRepository;
import com.wolfogre.dao.TransactionRepository;
import com.wolfogre.domain.PoorEntity;
import com.wolfogre.domain.TransactionEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by wolfogre on 10/17/16.
 */
@Service
public class AnalysisTimeMoneyInDayOfPoor {
    private final TransactionRepository transactionRepository;

    private final PoorRepository poorRepository;

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public AnalysisTimeMoneyInDayOfPoor(TransactionRepository transactionRepository, PoorRepository poorRepository) {
        this.transactionRepository = transactionRepository;
        this.poorRepository = poorRepository;
    }

    public void run(String... strings) throws Exception {
        HashSet<String> poor = new HashSet<>();
        List<PoorEntity> poorEntities = poorRepository.findAll();
        for(PoorEntity poorEntity : poorEntities)
            poor.add(poorEntity.getStuempid());


        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        HashMap<String, Double> sumRecord = new HashMap<>(86400);
        HashMap<String, Integer> countRecord = new HashMap<>(86400);
        for (int month = 4; month <= 6; ++month)
            for (int day = 1; day <= 31; ++day) {//之后删掉不存在的日期
                String date = "20160" + month + (day < 10 ? ("0" + day) : day);
                if (date.equals("20160431") || date.equals("20160631"))
                    continue;
                logger.info(date + "-loading");
                List<TransactionEntity> result = transactionRepository.findByTransdate(date);
                logger.info(date + "-analysing");
                for (TransactionEntity it : result) {
                    if(!poor.contains(it.getStuempno()))
                        continue;
                    double amount = Double.parseDouble(it.getCardbefbal()) - Double.parseDouble(it.getCardaftbal());
                    if (amount > 0) {
                        String time = timeFormat.format(it.getTranstime());
                        sumRecord.put(time, sumRecord.getOrDefault(time, 0.0) + amount);
                        countRecord.put(time, countRecord.getOrDefault(time, 0) + 1);
                    }
                }
                logger.info(date + "-done");
            }

        PrintStream output = new PrintStream(new FileOutputStream("output/AnalysisTimeMoneyInDayOfPoor.txt"));
        output.println(new Date().toString());
        Date time = new Date(0 + (24 - 8) * 60 * 60 * 1000); // 转化为本地时间的0点
        int count = 24 * 60 * 60;
        while (count-- > 0) {
            output.println(timeFormat.format(time)
                    + "\t" + sumRecord.getOrDefault(timeFormat.format(time), 0.0)
                    + "\t" + countRecord.getOrDefault(timeFormat.format(time), 0)
                    + "\t" + sumRecord.getOrDefault(timeFormat.format(time), 0.0) / countRecord.getOrDefault(timeFormat.format(time), 1));
            time.setTime(time.getTime() + 1000);
        }
        output.close();
    }
}
