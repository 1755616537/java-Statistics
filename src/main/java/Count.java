import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.util.*;

public class Count {

    static List<Map<String, String>> list=null;
    static String FilePath;

    public static List<Map<String, String>> Run(String filePath){
        if (filePath==""){
            File file = new File("");
            try {
                filePath = file.getCanonicalPath()+"/a.xlsx";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FilePath=filePath;
        try {
            list = importExcel(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            System.setOut(new PrintStream(new BufferedOutputStream(
//                    new FileOutputStream(filePath+"/print.txt")),true));
//            Run(list,最高,最低,总分,平均分);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        return list;
    }

    public static String GetName(int i){
        if (list==null)return "";
        return list.get(i).get("姓名");
    }

    public static String Get性别(int i){
        if (list==null)return "";
        return list.get(i).get("性别");
    }

    public static String Get学号(int i){
        if (list==null)return "";
        return list.get(i).get("学号");
    }

    public static String Get专业(int i){
        if (list==null)return "";
        return list.get(i).get("专业");
    }

    public static String Get班级(int i){
        if (list==null)return "";
        return list.get(i).get("班级");
    }

    public static String Get科目(int i){
        if (list==null)return "";
        return list.get(i).get("科目");
    }

    public static int GetCount(int i){
        if (list==null)return 0;
        String intval=list.get(i).get("分数");
        if (intval==null)return 0;
        return Integer.parseInt(intval);
    }

    public static XSSFWorkbook xls=null;

    public static List<Map<String, String>> importExcel(String file, int sheetIndex) throws IOException {
        FileInputStream in = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            in = new FileInputStream(file);
            xls = new XSSFWorkbook(in);
            XSSFSheet sheet = xls.getSheetAt(sheetIndex);
            XSSFRow titleRow = sheet.getRow(0);
            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                XSSFRow xssfRow = sheet.getRow(rowIndex);
                if (xssfRow == null) {
                    continue;
                }

                Map<String, String> map = new LinkedHashMap<String, String>();
                for (int cellIndex = 0; cellIndex < xssfRow.getPhysicalNumberOfCells(); cellIndex++) {
                    if (titleRow==null)continue;
                    titleRow.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
                    xssfRow.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
                    XSSFCell titleCell = titleRow.getCell(cellIndex);
                    XSSFCell xssfCell = xssfRow.getCell(cellIndex);
                    map.put(getString(titleCell),getString(xssfCell));
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert in != null;
            in.close();
        }
        return list;
    }

    public static List<Map<String, String>> importExcel(String file) throws IOException {
        return importExcel(file, 0);
    }

    public static String getString(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return "";
        }
        return String.valueOf(xssfCell.getStringCellValue());
    }

    public static void RunVal(){
        int 最高 = 0,最低 = 0,总分 = 0;
        int 差 = 0,普通 = 0,一般 = 0,优秀 = 0,异常优秀 = 0;
        Map<String, Integer> 个人总分 = new HashMap<String, Integer>();
        Map<String, Integer> 班级最高 = new HashMap<String, Integer>();
        Map<String, Integer> 班级最低 = new HashMap<String, Integer>();
        Map<String, Integer> 班级总分 = new HashMap<String, Integer>();
        Map<String, Integer> 班级总人数 = new HashMap<String, Integer>();
        Map<String, Integer> 班级合格率 = new HashMap<String, Integer>();
        Map<String, Integer> 班级科目合格率 = new HashMap<String, Integer>();
        for (int i = 0; i < (list != null ? list.size() : 0); i++) {
            int Count=GetCount(i);
            if (GetCount(最高)<Count)最高= i;
            if (GetCount(最低)>Count)最低= i;
            总分+=Count;
            if (Count>=0 && Count<=59)差++;
            if (Count>=60 && Count<=69)普通++;
            if (Count>=70 && Count<=79)一般++;
            if (Count>=80 && Count<=89)优秀++;
            if (Count>=90 && Count<=100)异常优秀++;

            if (GetCount(班级最高.get(Get班级(i))!=null?班级最高.get(Get班级(i)):0)<Count)班级最高.put(Get班级(i),i);
            if (GetCount(班级最低.get(Get班级(i))!=null?班级最低.get(Get班级(i)):0)>Count)班级最低.put(Get班级(i),i);
            个人总分=new Count().Set组合(个人总分,i,GetName(i)+Get学号(i),GetCount(i));
            班级总分=new Count().Set组合(班级总分,i,Get班级(i),GetCount(i));
            班级总人数=new Count().Set组合(班级总人数,i,Get班级(i),1);

            if (Count>=0 && Count<=59)班级合格率=new Count().Set组合(班级合格率,i,Get班级(i)+"差",1);
            if (Count>=60 && Count<=69)班级合格率=new Count().Set组合(班级合格率,i,Get班级(i)+"普通",1);
            if (Count>=70 && Count<=79)班级合格率=new Count().Set组合(班级合格率,i,Get班级(i)+"一般",1);
            if (Count>=80 && Count<=89)班级合格率=new Count().Set组合(班级合格率,i,Get班级(i)+"优秀",1);
            if (Count>=90 && Count<=100)班级合格率=new Count().Set组合(班级合格率,i,Get班级(i)+"异常优秀",1);

            if (Count>=0 && Count<=59)班级科目合格率=new Count().Set组合(班级科目合格率,i,Get班级(i)+Get科目(i)+"不合格",1);
            if (Count>=60 && Count<=79)班级科目合格率=new Count().Set组合(班级科目合格率,i,Get班级(i)+Get科目(i)+"合格",1);
            if (Count>=80 && Count<=100)班级科目合格率=new Count().Set组合(班级科目合格率,i,Get班级(i)+Get科目(i)+"优秀",1);
        }
        System.out.println(班级最高);
        System.out.println(班级最低);
        System.out.println(个人总分);
        System.out.println(班级总分);
        System.out.println(班级总人数);
        System.out.println(班级合格率);
        System.out.println(班级科目合格率);
        assert list != null;
        int 平均分 =总分/list.size();
        DefaultCategoryDataset data=new DefaultCategoryDataset();
        data.setValue(差,"差","0-59");
        data.setValue(普通,"普通","60-69");
        data.setValue(一般,"一般","70-79");
        data.setValue(优秀,"优秀","80-89");
        data.setValue(异常优秀,"异常优秀","90-100");
        new JFreeChartTest().Run(data);

        System.out.printf("最高=%s\n",GetCount(最高));
        System.out.printf("最低=%s\n",GetCount(最低));
        System.out.printf("总分=%s\n",总分);
        System.out.printf("平均分=%s\n",平均分);

        System.out.println("总体成绩测评===>>>");
        if (平均分>=0 && 平均分<=60) System.out.println("成绩一般般");
        if (平均分>=60 && 平均分<=80) System.out.println("成绩还行");
        if (平均分>=80 && 平均分<=90) System.out.println("成绩一般");
        if (平均分>=90 && 平均分<=99) System.out.println("成绩优秀还有待加强");
        if (平均分>=100) System.out.println("成绩优秀无敌");
    }

    public Map<String, Integer> Set组合(Map<String, Integer> map,int i,String key,Integer setval){
        Integer val=map.get(key);
        if (val==null)val=0;
        map.put(key,val+setval);
        return map;
    }

    public void Run(){
        FileInputStream in = null;
        try {
            in = new FileInputStream(FilePath);
            xls = new XSSFWorkbook(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            assert in != null;
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetVal(){
        new Count().Run();

        XSSFSheet xssfSheet = xls.getSheetAt(0);
        XSSFRow xssfRow;
        XSSFCell xssfCell;
        for (int i=-1;i<list.size();i++) {
            if (i==-1){
                xssfRow = xssfSheet.createRow(0);
                for (int j = 0; j < Main.title.length; j++) {
                    xssfCell = xssfRow.createCell(j);
                    xssfCell.setCellValue(Main.title[j]);
                }
                continue;
            }
            xssfRow = xssfSheet.createRow(i+1);
            Map<String,String> sub_list = list.get(i);
            for (int j=0;j<sub_list.size();j++) {
                xssfCell = xssfRow.createCell(j);
                xssfCell.setCellValue(sub_list.get(Main.title[j]));
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(FilePath);
            xls.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetVal2(){
        new Count().Run();

        XSSFSheet xssfSheet;
        try {
            xssfSheet = xls.getSheetAt(1);
        }catch (Exception e){
            xssfSheet = xls.createSheet();
        }
        XSSFRow xssfRow;
        XSSFCell xssfCell;
        for (int i=-1;i<list.size();i++) {
            if (i==-1){
                xssfRow = xssfSheet.createRow(0);
                for (int j = 0; j < Main.title.length; j++) {
                    xssfCell = xssfRow.createCell(j);
                    xssfCell.setCellValue(Main.title[j]);
                }
                continue;
            }
            xssfRow = xssfSheet.createRow(i+1);
            Map<String,String> sub_list = list.get(i);
            for (int j=0;j<sub_list.size();j++) {
                xssfCell = xssfRow.createCell(j);
                xssfCell.setCellValue(sub_list.get(Main.title[j]));
            }
        }

        XSSFUtils.testForComboChart(xssfSheet);

        FileOutputStream outputStream = null;
        try {
             outputStream = new FileOutputStream(FilePath);
            xls.write(outputStream);
            outputStream.flush();
        }catch (IOException e) {

        }finally {
            assert outputStream != null;
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SeriesData {
        public String name;

        public List<NameDouble> value;

        public SeriesData(java.util.List<NameDouble> value) {
            this.value = value;
        }

        public SeriesData(String name, List<NameDouble> value) {
            this.name = name;
            this.value = value;
        }

        public SeriesData() {
        }
    }
    public class NameDouble {

        public String name;
        public double value;

        public NameDouble(String name, double value) {
            this.name = name;
            this.value = value;
        }

        @SuppressWarnings("unused")
        public NameDouble() {
        }
    }

    public void updateChartExcelV(List<SeriesData> seriesDatas, XSSFWorkbook workbook, XSSFSheet sheet) {
        XSSFRow title = sheet.getRow(0);
        for (int i = 0; i < seriesDatas.size(); i++) {
            SeriesData data = seriesDatas.get(i);
            if (data.name != null && !data.name.isEmpty()) {
                // 系列名称，不能修改，修改后无法打开 excel
                //                title.getCell(i + 1).setCellValue(data.name);
            }
            int size = data.value.size();
            for (int j = 0; j < size; j++) {
                XSSFRow row = sheet.getRow(j + 1);
                if (row == null) {
                    row = sheet.createRow(j + 1);
                }
                NameDouble cellValu = data.value.get(j);
                XSSFCell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(cellValu.name);

                cell = row.getCell(i + 1);
                if (cell == null) {
                    cell = row.createCell(i + 1);
                }
                cell.setCellValue(cellValu.value);
            }
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum > size) {
                for (int idx = lastRowNum; idx > size; idx--) {
                    sheet.removeRow(sheet.getRow(idx));
                }
            }
        }
    }
}
