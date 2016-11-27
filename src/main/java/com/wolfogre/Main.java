package com.wolfogre;

import com.wolfogre.service.AnalysisStudentGroupOutputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by wolfogre on 10/16/16.
 */
@Component
public class Main implements CommandLineRunner {

    private final AnalysisStudentGroupOutputData analysisStudentGroupOutputData;

    @Autowired
    public Main(AnalysisStudentGroupOutputData analysisStudentGroupOutputData) {
        this.analysisStudentGroupOutputData = analysisStudentGroupOutputData;
    }

    @Override
    public void run(String... strings) throws Exception {
        analysisStudentGroupOutputData.run(strings);
    }
}
