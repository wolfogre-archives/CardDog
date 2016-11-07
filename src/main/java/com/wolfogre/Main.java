package com.wolfogre;

import com.wolfogre.service.AnalysisTimeMoneyInDay;
import com.wolfogre.service.AnalysisTimeMoneyInDayOfPoor;
import com.wolfogre.service.ChartOfAnalysisTimeMoneyInDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by wolfogre on 10/16/16.
 */
@Component
public class Main implements CommandLineRunner {

    private final AnalysisTimeMoneyInDay analysisTimeMoneyInDay;
    private final ChartOfAnalysisTimeMoneyInDay chartOfAnalysisTimeMoneyInDay;
    private final AnalysisTimeMoneyInDayOfPoor analysisTimeMoneyInDayOfPoor;

    @Autowired
    public Main(AnalysisTimeMoneyInDay analysisTimeMoneyInDay,
                ChartOfAnalysisTimeMoneyInDay chartOfAnalysisTimeMoneyInDay,
                AnalysisTimeMoneyInDayOfPoor analysisTimeMoneyInDayOfPoor) {
        this.analysisTimeMoneyInDay = analysisTimeMoneyInDay;
        this.chartOfAnalysisTimeMoneyInDay = chartOfAnalysisTimeMoneyInDay;
        this.analysisTimeMoneyInDayOfPoor = analysisTimeMoneyInDayOfPoor;
    }


    @Override
    public void run(String... strings) throws Exception {
        //analysisTimeMoneyInDay.run(strings);
        chartOfAnalysisTimeMoneyInDay.run(strings);
        //Thrown when code that is dependent on a keyboard, display, or mouse is called in an environment that does not support a keyboard, display, or mouse.
        //analysisTimeMoneyInDayOfPoor.run(strings);
    }
}
