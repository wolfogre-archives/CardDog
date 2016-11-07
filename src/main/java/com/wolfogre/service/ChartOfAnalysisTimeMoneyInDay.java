package com.wolfogre.service;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by wolfogre on 10/16/16.
 */
@Service
public class ChartOfAnalysisTimeMoneyInDay{

    private final Logger logger = Logger.getLogger(this.getClass());

    private DefaultCategoryDataset createDataset( ) throws FileNotFoundException {
        logger.info("Begin loading data");
        Scanner scanner = new Scanner(new FileInputStream("output/AnalysisTimeMoneyInDay.txt"));
        Scanner poorScanner = new Scanner(new FileInputStream("output/AnalysisTimeMoneyInDayOfPoor.txt"));
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        scanner.nextLine();
        poorScanner.nextLine();

        for(int i = 1; i <= 86400; /*++i*/) {
            int range = 0;
            String time = null;
            double sum = 0;
            double poorSum = 0;
            int count = 0;
            int poorCount = 0;
            while(++range <= 5 * 60) {
                ++i;
                if(i % 1000 == 0)
                    logger.info("Begin loading data to " + time);
                if(time == null)
                    time = scanner.next();
                else
                    scanner.next();//For time
                poorScanner.next();//For time
                sum += scanner.nextDouble();
                count += scanner.nextInt();
                scanner.nextDouble();//For ave
                poorSum += poorScanner.nextDouble();
                poorCount += poorScanner.nextInt();
                poorScanner.nextDouble();//For ave
            }


            //dataset.addValue(sum, "总消费", time);
            //dataset.addValue(count, "消费计数", time);
            dataset.addValue(sum / count, "平均消费", time);
            dataset.addValue(poorSum / (poorCount == 0 ? 1 : poorCount), "贫困生平均消费", time);
        }
        logger.info("End of loading data");
        return dataset;
    }

    public void run(String... strings) throws Exception {
        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "学生消费","一天时间",
                "",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);
        CategoryAxis categoryAxis = ((CategoryPlot)lineChartObject.getPlot()).getDomainAxis();
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        categoryAxis.setLowerMargin(0.0);
        categoryAxis.setUpperMargin(0.0);
        LineAndShapeRenderer lineAndShapeRenderer = (LineAndShapeRenderer)((CategoryPlot)lineChartObject.getPlot()).getRenderer();
        lineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(5));
        lineAndShapeRenderer.setSeriesStroke(1, new BasicStroke(5));
        int width = 1000 * 5; /* Width of the image */
        int height = 1000; /* Height of the image */
        File lineChart = new File("output/ChartOfAnalysisTimeMoneyInDay.jpeg");
        logger.info("Begin creating chart");
        ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
        logger.info("End of creating chart");
    }
}
