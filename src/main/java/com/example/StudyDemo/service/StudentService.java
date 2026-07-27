package com.example.StudyDemo.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.StudyDemo.entity.Student;
import com.example.StudyDemo.mapper.StudentMapper;

@Service
public class StudentService {
    StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    /**
     * @Cacheable:
     *             - value="students": 代表快取空間/前綴名稱。
     *             - key: 組合 studentNo 與 studentName 當作 Redis 的 Key。
     *             - condition: 當傳入條件都不為空時才快取，避免空條件快取全表資料。
     */
    @Cacheable(value = "students", key = "'search:' + (#studentNo ?: '') + ':' + (#studentName ?: '')", condition = "#studentNo != null || #studentName != null", unless = "#result.isEmpty()")
    public List<Student> searchStudent(String studentNo, String studentName) {
        System.out.println(">>> 真正執行 MyBatis 查詢 DB <<<"); // 用來觀察是否有走快取
        Student student = new Student();
        student.setStudentNo(studentNo);
        student.setStudentName(studentName);
        return studentMapper.search(student);
    }

    /**
     * @CachePut:
     *            - value="students": 代表快取空間/前綴名稱。
     *            - key: 組合 studentNo 與 studentName 當作 Redis 的 Key。
     *            - condition: 當傳入條件都不為空時才快取，避免空條件快取全表資料。
     */
    @CachePut(value = "students", key = "'search:' + (#studentNo ?: '') + ':' + (#studentName ?: '')", condition = "#studentNo != null || #studentName != null")
    public int update(Student student) {
        return studentMapper.update(student);
    }

    public int create(Student student) {
        // TODO Auto-generated method stub
        return studentMapper.create(student);
    }
}
