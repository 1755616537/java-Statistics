import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

class Main  {
    static JFrame mainForm = new JFrame("窗口");
    public static JButton buttonBrowseSource = new JButton("加载文件");
    public static JButton buttonStatistics = new JButton("统计");
    public static JButton buttonPreserve = new JButton("保存");

    public static String[] title=new String[]{"姓名", "性别", "学号", "专业", "班级", "科目", "分数"};
    static int COLUMN_COUNT;

    public static void main(String[] args){
        new Main().Run();
    }

    public void Run(){
        COLUMN_COUNT=Main.title.length;
        Container container = mainForm.getContentPane();
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainForm.setLocationRelativeTo(null);
        mainForm.setResizable(false);
//        mainForm.setLayout(null);
        mainForm.setVisible(true);
        buttonBrowseSource.addActionListener(new BrowseAction());
        container.add(buttonBrowseSource,BorderLayout.NORTH);
        new TestDefaultTableModel().init();
        buttonStatistics.addActionListener(new BrowseAction());
        container.add(buttonStatistics,BorderLayout.BEFORE_LINE_BEGINS);
        buttonPreserve.addActionListener(new BrowseAction());
        container.add(buttonPreserve,BorderLayout.SOUTH);

        Count.Run("");
        TestDefaultTableModel.Runval();

        mainForm.pack();
    }
}
