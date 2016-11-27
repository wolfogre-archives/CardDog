package com.wolfogre;

import com.wolfogre.service.AnalysisStudentGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by wolfogre on 10/16/16.
 */
@Component
public class Main implements CommandLineRunner {

    private final AnalysisStudentGroup analysisStudentGroup;

    @Autowired
    public Main(AnalysisStudentGroup analysisStudentGroup) {
        this.analysisStudentGroup = analysisStudentGroup;
    }

    @Override
    public void run(String... strings) throws Exception {
        analysisStudentGroup.run(strings);
    }
}
