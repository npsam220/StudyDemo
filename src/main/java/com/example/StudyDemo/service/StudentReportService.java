package com.example.StudyDemo.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.StudyDemo.entity.Student;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class StudentReportService {

    private final StudentService studentService;

    public StudentReportService(StudentService studentService) {
        this.studentService = studentService;
    }

    public byte[] exportPdf(String studentNo, String studentName) {
        try {
            List<Student> students = studentService.searchStudent(studentNo, studentName);

            InputStream jrxml = getClass()
                    .getResourceAsStream("/reports/StudentReport.jrxml");

            if (jrxml == null) {
                throw new IllegalStateException(
                        "找不到報表檔：/reports/StudentReport.jrxml");
            }

            JasperReport report = JasperCompileManager.compileReport(jrxml);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report,
                    parameters,
                    dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("學生報表產生失敗", e);
        }
    }
}