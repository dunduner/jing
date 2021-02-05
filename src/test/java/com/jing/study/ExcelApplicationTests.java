package com.jing.study;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.jing.study.entity.DemoData;
import com.jing.study.excel.DemoDataListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("我有的!");
    }

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照com.alibaba.easyexcel.test.demo.write.DemoData
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
//        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH_mm_ss");
        String format = simpleDateFormat.format(new Date());
        String fileName = "C:/excle/" + "simpleWrite" + format + ".xlsx";
        System.out.println(fileName);
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("sheet1").doWrite(data());
    }

    /**
     * 根据参数只导出指定列
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 根据自己或者排除自己需要的列
     * <p>
     * 3. 直接写即可
     *
     * @since 2.1.1
     */
    @Test
    public void excludeOrIncludeWrite() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH_mm_ss");
        String format = simpleDateFormat.format(new Date());
        String fileName = "C:/excle/" + "simpleWrite" + format + ".xlsx";
        System.out.println(fileName);

        // 根据用户传入字段 假设我们要忽略 date
//        Set<String> excludeColumnFiledNames = new HashSet<String>();
//        excludeColumnFiledNames.add("date");
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        EasyExcel.write(fileName, DemoData.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("模板007")
//                .doWrite(data());

        // 根据用户传入字段 假设我们只要导出 date
        Set<String> includeColumnFiledNames = new HashSet<String>();
        includeColumnFiledNames.add("date");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).includeColumnFiledNames(includeColumnFiledNames).sheet("模板")
                .doWrite(data());


    }


    /**
     * 读
     *
     * @return
     */
    @Test
    public void simpleRead() {
        String fileName = "C:/excle/demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        //写法1
//        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        // 写法2：
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(1).build();//读第2个sheet
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    public List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
