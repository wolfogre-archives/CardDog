package com.wolfogre.service;

import com.wolfogre.dao.EatRepository;
import com.wolfogre.dao.TransactionRepository;
import com.wolfogre.domain.TransactionEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wolfogre on 10/16/16.
 */
@Service
public class AnalysisTimeMoneyInDay{

    private final TransactionRepository transactionRepository;

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public AnalysisTimeMoneyInDay(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void run(String... strings) throws Exception {
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
                    if (Double.parseDouble(it.getAmount()) > 0) {
                        String time = timeFormat.format(it.getTranstime());
                        sumRecord.put(time, sumRecord.getOrDefault(time, 0.0) + Double.parseDouble(it.getAmount()));
                        countRecord.put(time, countRecord.getOrDefault(time, 0) + 1);
                    }
                }
                logger.info(date + "-done");
            }

        PrintStream output = new PrintStream(new FileOutputStream("output/AnalysisTimeMoneyInDay.txt"));
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
