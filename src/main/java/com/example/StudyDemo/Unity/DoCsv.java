package com.example.StudyDemo.Unity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

public class DoCsv {

    public void exportCsv(
            String[] titleName,
            String[] fieldName,
            String fileName,
            List<HashMap<String, Object>> dataList,
            HttpServletResponse response) throws IOException {

        String downloadFileName = URLEncoder.encode(fileName + ".csv", StandardCharsets.UTF_8);

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename*=UTF-8''" + downloadFileName);

        try (PrintWriter writer = response.getWriter()) {

            writer.write("\uFEFF");

            writer.println(toCsvLine(titleName));

            for (HashMap<String, Object> data : dataList) {
                String[] values = new String[fieldName.length];

                for (int i = 0; i < fieldName.length; i++) {
                    Object value = data.get(fieldName[i]);
                    values[i] = value == null ? "" : value.toString();
                }

                writer.println(toCsvLine(values));
            }

            writer.flush();
        }
    }

    private String toCsvLine(String[] values) {
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                line.append(",");
            }

            line.append(escapeCsv(values[i]));
        }

        return line.toString();
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        String escaped = value.replace("\"", "\"\"");

        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }

        return escaped;
    }
}
