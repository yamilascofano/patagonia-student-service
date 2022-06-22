package com.example.studentservice.controller;

import com.example.studentservice.exceptions.NoIdException;
import com.example.studentservice.model.Student;
import com.example.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Student findById(@PathVariable Long id) throws NoIdException {
        return studentService.findById(id);
    }

    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable Long id) throws NoIdException {
        studentService.deleteStudent(id);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student) throws NoIdException {
        return studentService.updateStudent(student);
    }

    @GetMapping("/calcularEdadPromedio")
    public int calcularEdadPromedio() {
        return studentService.calcularEdadPromedio();
    }

    @GetMapping("/edadMaxima")
    public int edadMaxima() {
        return studentService.edadMaxima();
    }

    @GetMapping("/edadMinima")
    public int edadMinima() {
        return studentService.edadMinima();
    }

    @GetMapping("/mostrarDatos")
    public String mostrarDatos() {
        return studentService.mostrarDatos();
    }

    @GetMapping("listarMayores")
    public List<Student> listarMayores() {
        return studentService.listarMayores();
    }

    @GetMapping("listarMenores")
    public List<Student> listarMenores() {
        return studentService.listarMenores();
    }

    @GetMapping("/promedioEdadMayores")
    public Double promedioEdadMayores() {
        return studentService.promedioEdadMayores();
    }

    @GetMapping("/promedioEdadMenores")
    public Double promedioEdadMenores() {
        return studentService.promedioEdadMenores();
    }

}
