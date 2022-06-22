package com.example.studentservice.service;

import com.example.studentservice.exceptions.NoIdException;
import com.example.studentservice.model.Student;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) throws NoIdException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NoIdException("No existe estudiante con " + id));
        studentRepository.delete(student);
    }

    public Student findById(Long id) throws NoIdException {
        return studentRepository.findById(id).orElseThrow(() -> new NoIdException("No existe estudiante con " + id));
    }

    @Transactional
    public Student updateStudent(Student student) throws NoIdException {
        Student student1 = studentRepository.findById(student.getId()).orElseThrow(() -> new NoIdException("No existe estudiante con " + student.getId()));
        student1.setName(student.getName());
        student1.setSurname(student.getSurname());
        student1.setBirthday(student.getBirthday());
        return studentRepository.save(student1);
    }

    //calcular la edad promedio de los students
    public int calcularEdadPromedio() {
        List<Student> studentList = studentRepository.findAll();
        return (int) studentList.stream()
                .mapToInt(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears()).average().orElseThrow();
    }

    //Mostrar el student con más edad
    public int edadMaxima() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .mapToInt(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears())
                .max().orElseThrow();
    }

    //y con menos edad
    public int edadMinima(){
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .mapToInt(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears())
                .min().orElseThrow();
    }

    //Mostrar solo el id, surname y name de los students. Por ejemplo: 1 - ,Lovelace, Ada.
    public String mostrarDatos() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream().map(s -> s.getId() + ("- ") + s.getName() + (" ") + s.getSurname())
                .collect(Collectors.joining(", "));

    }

    //Listar students mayores de edad.
    public List<Student> listarMayores() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .filter(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears() >= 18)
                .collect(Collectors.toList());
    }

    //y los menores de edad .
    public List<Student> listarMenores() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .filter(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears() < 18)
                .collect(Collectors.toList());
    }

    //Mostrar la edad promedio de los mayores de edad.
    public Double promedioEdadMayores() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .filter(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears() >= 18)
                .mapToDouble(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears())
                .average().orElseThrow();
    }

    //Mostrar la edad promedio de los menores de edad.
    public Double promedioEdadMenores() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .filter(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears() < 18)
                .mapToDouble(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears())
                .average().orElseThrow();
    }
}
