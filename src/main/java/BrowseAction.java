import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BrowseAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String Command= e.getActionCommand();
        if (Command==null)return;

        if (Command.equals("加载文件")) {
            选择文件();
        }else if (Command.equals("统计")) {
            Count.RunVal();
        }else if (Command.equals("保存")) {
            new Count().SetVal2();
        }
    }

    private void 选择文件(){
        JFileChooser fcDlg = new JFileChooser();
        fcDlg.setDialogTitle("请选择文件...");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "文本文件(*.xlsx)", "xlsx");
        fcDlg.setFileFilter(filter);
        int returnVal = fcDlg.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Count.Run(fcDlg.getSelectedFile().getPath());
            TestDefaultTableModel.Runval();
        }
    }
}