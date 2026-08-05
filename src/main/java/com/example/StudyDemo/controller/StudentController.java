package com.example.StudyDemo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.StudyDemo.entity.Employee;
import com.example.StudyDemo.entity.Student;
import com.example.StudyDemo.service.StudentReportService;
import com.example.StudyDemo.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentReportService studentReportService;

    public StudentController(
            StudentService studentService,
            StudentReportService studentReportService) {
        this.studentService = studentService;
        this.studentReportService = studentReportService;
    }

    @GetMapping("/search")
    public List<Student> getStudent(@RequestParam String studentNo, @RequestParam String studentName) {
        return studentService.searchStudent(studentNo, studentName);
    }

    // 更新
    @PutMapping("/{id}")
    @Operation(summary = "学生を更新")
    public Map<String, Object> update(@PathVariable Integer id, @RequestBody Student student) {
        student.setStudentId(id);
        int result = studentService.update(student);
        Map<String, Object> map = new HashMap<>();

        if (result > 0) {

            map.put("success", true);
            map.put("message", "勇者方成功啦！");

        } else {

            map.put("success", false);
            map.put("message", "更新失敗");

        }

        return map;
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> exportReport(
            @RequestParam(required = false) String studentNo,
            @RequestParam(required = false) String studentName) {

        byte[] pdf = studentReportService.exportPdf(studentNo, studentName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=students.pdf")
                .body(pdf);
    }

    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody Student student) {
        // TODO: process POST request
        int result = studentService.create(student);
        Map<String, Object> map = new HashMap<>();
        if (result > 0) {
            map.put("success", true);
            map.put("message", "学生情報を作成しました");
        } else {
            map.put("success", false);
            map.put("message", "学生情報の作成に失敗しました");
        }
        return map;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "学生を削除")
    public Map<String, Object> delete(@PathVariable Integer id) {
        int result = studentService.delete(id);
        Map<String, Object> map = new HashMap<>();
        if (result > 0) {
            map.put("success", true);
            map.put("message", "学生情報を削除しました");
        } else {
            map.put("success", false);
            map.put("message", "学生情報の削除に失敗しました");
        }
        return map;
    }
}
