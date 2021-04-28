import java.awt.Font;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class JFreeChartTest
{
    public void Run(DefaultCategoryDataset data) {
        JFrame JFrame = new JFrame("成绩结构体");
        JFrame.setContentPane(createPanel(data));
        JFrame.pack();
        JFrame.setVisible(true);
    }

    public JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart=ChartFactory.createBarChart("成绩结构体", "分数发布",
                "分数", dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setTitle(new TextTitle("成绩结构体",new Font("宋体",Font.BOLD+Font.ITALIC,20)));
        CategoryPlot plot=(CategoryPlot)chart.getPlot();
        CategoryAxis categoryAxis=plot.getDomainAxis();
        categoryAxis.setLabelFont(new Font("微软雅黑",Font.BOLD,12));
        return chart;
    }

    public JPanel createPanel(DefaultCategoryDataset data) {
        JFreeChart chart =createChart(data);
        return new ChartPanel(chart);
    }

}