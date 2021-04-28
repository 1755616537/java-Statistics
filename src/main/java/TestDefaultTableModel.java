import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
public class TestDefaultTableModel
{
    static DefaultTableModel model=new DefaultTableModel();
    JTable table=new JTable(model);
    ArrayList<TableColumn> hiddenColumns = new ArrayList<TableColumn>();
    public void init()
    {
        model.setColumnIdentifiers(Main.title);
        JScrollPane JScrollPane =new JScrollPane(table);
        table.getModel().addTableModelListener(new TableModelListener()
        {
            public void tableChanged(TableModelEvent e)
            {
                if (e.getType() == TableModelEvent.UPDATE){
                    int col = e.getColumn();
                    int row = e.getFirstRow();
                    int larow =e.getLastRow();

                    String newvalue = table.getValueAt(e.getLastRow(),e.getColumn()).toString();
                    Map<String,String> map =Count.list.get(e.getLastRow());
                    map.put(Main.title[e.getColumn()],newvalue);
                    Count.list.set(e.getLastRow(),map);
                    new Count().SetVal();
                }
            }
        });
        Main.mainForm.add(JScrollPane);
        JMenuBar menuBar = new JMenuBar();
        Main.mainForm.setJMenuBar(menuBar);
        JMenu tableMenu = new JMenu("管理");
        menuBar.add(tableMenu);
        JMenuItem addRowItem = new JMenuItem("增加行");
        addRowItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                String[] newCells = new String[Main.COLUMN_COUNT];
                for (int i = 0; i < newCells.length; i++)
                {
                    newCells[i] = "新单元格值 " + model.getRowCount() + " " + i;
                }
                model.addRow(newCells);
            }
        });
        tableMenu.add(addRowItem);
        JMenuItem removeRowsItem = new JMenuItem("删除选中行");
        removeRowsItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                int[] selected = table.getSelectedRows();
                for (int i = selected.length - 1; i >= 0; i--)
                {
                    model.removeRow(selected[i]);
                }
            }
        });
        tableMenu.add(removeRowsItem);
    }

    public static void Runval(){
        model.setRowCount(0);
        for (int i = 0; i < Count.list.size(); i++) {
            model.addRow(new String[]{
                    Count.GetName(i),
                    Count.Get性别(i),
                    Count.Get学号(i),
                    Count.Get专业(i),
                    Count.Get班级(i),
                    Count.Get科目(i),
                    String.valueOf(Count.GetCount(i)),
            });
        }
    }
}