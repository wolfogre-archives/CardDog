package com.wolfogre;

import com.wolfogre.dao.EatRepository;
import com.wolfogre.dao.TransactionRepository;
import com.wolfogre.domain.TransactionEntity;
import com.wolfogre.service.AnalysisTimeMoneyInDay;
import com.wolfogre.service.ChartOfAnalysisTimeMoneyInDay;
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
    private final ChartOfAnalysisTimeMoneyInDay chartOfAnalysisTimeMoneyInDay;

    @Autowired
    public Main(AnalysisTimeMoneyInDay analysisTimeMoneyInDay, ChartOfAnalysisTimeMoneyInDay chartOfAnalysisTimeMoneyInDay) {
        this.analysisTimeMoneyInDay = analysisTimeMoneyInDay;
        this.chartOfAnalysisTimeMoneyInDay = chartOfAnalysisTimeMoneyInDay;
    }


    @Override
    public void run(String... strings) throws Exception {
        //analysisTimeMoneyInDay.run(strings);
        chartOfAnalysisTimeMoneyInDay.run(strings);
        //Thrown when code that is dependent on a keyboard, display, or mouse is called in an environment that does not support a keyboard, display, or mouse.
    }
}
