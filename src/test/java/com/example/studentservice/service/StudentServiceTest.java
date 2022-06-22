package com.example.studentservice.service;

import com.example.studentservice.exceptions.NoIdException;
import com.example.studentservice.model.Student;
import com.example.studentservice.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    List<Student> studentList;
    Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "Vil", "Carlos", LocalDate.of(1960, 7, 5));
        studentList = new ArrayList<>();
        studentList.add(new Student(1L, "Vil", "Carlos", LocalDate.of(1960, 7, 5)));
        studentList.add(new Student(2L, "Diaz", "Carolina", LocalDate.of(1990, 1, 15)));

    }

    @Test
    void findAll() {
        when(studentRepository.findAll()).thenReturn(studentList);

        assertFalse(studentService.findAll().isEmpty());
    }

    @Test
    void addStudent() {
        Student crearStudent = new Student(1L, "Vil", "Carlos", LocalDate.of(1960, 7, 5));
        when(studentRepository.save(student)).thenReturn(crearStudent);

        assertNotNull(studentService.addStudent(crearStudent));
    }

    @Test
    void deleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        doNothing().when(studentRepository).delete(student);
        studentRepository.delete(student);

        assertTrue(studentRepository.findById(student.getId()).isEmpty());
    }

    @Test
    void findById() throws NoIdException {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        assertNotNull(studentService.findById(1L));
    }

    @Test
    void update() throws NoIdException {
        when(studentRepository.save(student)).thenReturn(student);
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        Student student1 = studentService.updateStudent(student);

        assertEquals(student1.getId(), student.getId());
        assertEquals(student1.getBirthday(), student.getBirthday());
        assertEquals(student1.getName(), student.getName());
        assertEquals(student1.getSurname(), student.getSurname());

    }

    @Test
    void calcularEdadPromedio() {
        when(studentRepository.findAll()).thenReturn(studentList);
        int promedio = (int) studentList.stream()
                .mapToInt(student -> Period.between(student.getBirthday(), LocalDate.now()).getYears()).average().orElseThrow();
        int promedio1 = 46;

        assertEquals(studentService.calcularEdadPromedio(), promedio);
        assertEquals(studentService.calcularEdadPromedio(), promedio1);
    }

    @Test
    void edadMaxima() {
        when(studentRepository.findAll()).thenReturn(studentList);
        int edadMaxima = 61;

        assertEquals(studentService.edadMaxima(), edadMaxima);
        assertTrue(studentService.edadMaxima() > 6);
    }

    @Test
    void edadMinima() {
        when(studentRepository.findAll()).thenReturn(studentList);
        int edadMinima = 32;

        assertEquals(studentService.edadMinima(), edadMinima);
    }

    @Test
    void mostrarDatos() {
        when(studentRepository.findAll()).thenReturn(studentList);

        String student1 = "1- Carlos Vil, 2- Carolina Diaz";

        assertEquals(studentService.mostrarDatos(), student1);
    }

    @Test
    public void listarMayores() {
        when(studentRepository.findAll()).thenReturn(studentList);
        List<Student> s1 = studentList.stream().filter(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears() >= 18)
                .collect(Collectors.toList());
       assertEquals(studentService.listarMayores(), s1);
    }

    @Test
    public void listarMenores() {
        studentList.add(new Student(3L, "Andrea", "Picolo", LocalDate.of(2008, 7, 5)));
        studentList.add(new Student(4L, "Snape", "Severus", LocalDate.of(2015, 1, 15)));
        when(studentRepository.findAll()).thenReturn(studentList);

        List<Student> s1 = studentList.stream().filter(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears() < 18)
                .collect(Collectors.toList());
        assertEquals(studentService.listarMenores(), s1);
        assertNotNull(studentService.listarMenores());
    }

    @Test
    void promedioEdadMayores() {
        when(studentRepository.findAll()).thenReturn(studentList);
        Double average = 46.5;

        assertNotNull(studentService.promedioEdadMayores());
        assertEquals(studentService.promedioEdadMayores(), average);
    }

    @Test
    void promedioEdadMenores() {
        studentList.add(new Student(3L, "Andrea", "Picolo", LocalDate.of(2008, 7, 5)));
        studentList.add(new Student(4L, "Snape", "Severus", LocalDate.of(2015, 1, 15)));
        when(studentRepository.findAll()).thenReturn(studentList);
        Double average = 10.0;

        assertNotNull(studentService.promedioEdadMenores());
        assertEquals(studentService.promedioEdadMenores(), average);
    }
}