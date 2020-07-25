package com.atguigu.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu on 2020/7/14 0014
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEaseExcelWrite {


    @Test
    public void testExcelWrite() {

        String fileNmae = "E:\\sutdent.xlsx";
        EasyExcel.write(fileNmae, DataDemo.class).sheet("学生信息").doWrite(getData());
    }

    private static List<DataDemo> getData() {
        List<DataDemo> list = new ArrayList<DataDemo>();
        for (int i = 0; i < 10; i++) {
            DataDemo dataDemo = new DataDemo();
            dataDemo.setSNo(i);
            dataDemo.setSName("Liuy" + i);
            list.add(dataDemo);

        }
        return list;
    }

    /*public static void main(String[] args) {
        String fileNmae = "E:\\sutdent.xlsx";
        EasyExcel.write(fileNmae, DataDemo.class).sheet("学生信息").doWrite(getData());
    }*/
    public static void main(String[] args) {
        String fileNmae = "E:\\sutdent.xlsx";
        EasyExcel.read(fileNmae,DataDemo.class,new ExcelListener()).sheet().doRead();
    }
}
