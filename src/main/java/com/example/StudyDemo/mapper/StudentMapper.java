package com.example.StudyDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.StudyDemo.entity.Student;

@Mapper
public interface StudentMapper {
    List<Student> search(Student student);

    int update(Student student);

    int create(Student student);
}
