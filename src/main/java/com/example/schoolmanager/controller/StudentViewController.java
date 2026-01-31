package com.example.schoolmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentViewController {

    @Autowired
    private StudentService service;

    // 1. Danh sách sinh viên
    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", service.getAll());
        return "students";
    }

    // 2. Chi tiết sinh viên
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("student", service.getStudentById(id));
        return "student-detail";
    }

    // 3. Tìm kiếm sinh viên
    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("students", service.searchByName(keyword));
        return "students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        service.addStudent(student);
        return "redirect:/students";
    }

    // HIỂN THỊ FORM SỬA
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("student", service.getStudentById(id));
        return "student-edit";
    }

    // XỬ LÝ SUBMIT FORM SỬA
    @PostMapping("/edit")
    public String update(@ModelAttribute Student student) {
        service.save(student);
        return "redirect:/students";
    }
}
