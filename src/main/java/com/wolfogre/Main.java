package com.wolfogre;

import com.wolfogre.dao.EatRepository;
import com.wolfogre.dao.TransactionRepository;
import com.wolfogre.domain.TransactionEntity;
import com.wolfogre.service.AnalysisTimeMoneyInDay;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by wolfogre on 10/16/16.
 */
@Component
public class Main implements CommandLineRunner {

    private final AnalysisTimeMoneyInDay analysisTimeMoneyInDay;

    @Autowired
    public Main(AnalysisTimeMoneyInDay analysisTimeMoneyInDay) {
        this.analysisTimeMoneyInDay = analysisTimeMoneyInDay;
    }


    @Override
    public void run(String... strings) throws Exception {
        //analysisTimeMoneyInDay.run(strings);
    }
}
