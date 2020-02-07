package com.yws.plane.util;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * csv文件工具类
 */
public class CsvUtil {
    public static void main(String[] args) {
        importCsv();
    }

    public static String exportCsv(String fileName, HttpServletResponse response) {
        try {
            File file = File.createTempFile("xxx", ".csv");
            CsvWriter csvWriter = new CsvWriter(file.getCanonicalPath(), ',', StandardCharsets.UTF_8);
            csvWriter.writeRecord(new String[]{"title1", "title2"});
            csvWriter.writeRecord(new String[]{"value1", "value2"});
            csvWriter.close();

            response.setContentType("application/csv; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            InputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String importCsv() {
        String filePath = "123.csv";
        try {
            CsvReader csvReader = new CsvReader(filePath,',', Charset.forName("gb2312"));
            // 读表头
            csvReader.readHeaders();
            // 读内容
            while (csvReader.readRecord()) {
                // 读一整行
                System.out.println(csvReader.getRawRecord());
                // 读该行的某一列
                System.out.println(csvReader.get("Link"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
