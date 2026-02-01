package com.example.schoolmanager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.respository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Student addStudent(Student student) {
        return repository.save(student);
    }

    public void deleteStudent(UUID id) {
        repository.deleteById(id);
    }

    public List<Student> searchByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getStudentById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Student student) {
        repository.save(student);
    }
}
