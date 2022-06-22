package com.example.studentservice.controller;

import com.example.studentservice.model.Student;
import com.example.studentservice.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class StudentControllerTest {

    @MockBean
    StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    List<Student> studentList;
    Student student;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "Vil", "Carlos", LocalDate.of(1960, 7, 5));
        studentList = new ArrayList<>();
        studentList.add(new Student(1L, "Vil", "Carlos", LocalDate.of(1960, 7, 5)));
        studentList.add(new Student(2L, "Diaz", "Carolina", LocalDate.of(1990, 1, 15)));
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void findAll() throws Exception {
        when(studentService.findAll()).thenReturn(studentList);
        mockMvc.perform(get("/students/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("[0].surname").value("Vil"));
    }

    @Test
    void findById() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(student);
        mockMvc.perform(get("/students/findById/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("Carlos"));
    }

    @Test
    void addStudent() throws Exception {
        Student crearStudent = new Student(1L, "Vil", "Carlos", LocalDate.of(1960, 7, 5));
        when(studentService.addStudent(any())).thenReturn(crearStudent);
        mockMvc.perform(post("/students/addStudent").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearStudent)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("Carlos"));
    }

    @Test
    void deleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);
        mockMvc.perform(delete("/students/deleteStudent/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void updateStudent() throws Exception {
        Student student1 = new Student(2L, "Diaz", "Carolina", LocalDate.of(1990, 1, 15));
        when(studentService.updateStudent(any())).thenReturn(student1);
        mockMvc.perform(put("/students/update").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("Carolina"));
    }

    @Test
    void calcularEdadPromedio() throws Exception {
        int promedio1 = 46;
        when(studentService.calcularEdadPromedio()).thenReturn(promedio1);
        mockMvc.perform(get("/students/calcularEdadPromedio").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("46"))
                .andExpect(status().is(200));
    }

    @Test
    void edadMaxima() throws Exception {
        int edadMaxima = 61;
        when(studentService.edadMaxima()).thenReturn(edadMaxima);
        mockMvc.perform(get("/students/edadMaxima").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("61"))
                .andExpect(status().is(200));
    }

    @Test
    void edadMinima() throws Exception {
        int edadMinima = 32;
        when(studentService.edadMinima()).thenReturn(edadMinima);
        mockMvc.perform(get("/students/edadMinima").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("32"))
                .andExpect(status().is(200));

    }

    @Test
    void mostrarDatos() throws Exception {
        String student1 = "1- Carlos Vil, 2- Carolina Diaz";
        when(studentService.mostrarDatos()).thenReturn(student1);
        mockMvc.perform(get("/students/mostrarDatos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1- Carlos Vil, 2- Carolina Diaz"))
                .andExpect(status().is(200));
    }

    @Test
    public void listarMayores() throws Exception {
        List<Student> s1 = studentList.stream().filter(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears() >= 18)
                .collect(Collectors.toList());
        when(studentService.listarMayores()).thenReturn(s1);
        mockMvc.perform(get("/students/listarMayores").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("[0].surname").value("Vil"))
                .andExpect(status().is(200));
    }

    @Test
    public void listarMenores() throws Exception {
        List<Student> s1 = studentList.stream().filter(s -> Period.between(s.getBirthday(), LocalDate.now()).getYears() < 18)
                .collect(Collectors.toList());
        when(studentService.listarMenores()).thenReturn(s1);
        mockMvc.perform(get("/students/listarMenores").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void promedioEdadMayores() throws Exception {
        Double average = 46.5;
        when(studentService.promedioEdadMayores()).thenReturn(average);
        mockMvc.perform(get("/students/promedioEdadMayores").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("46.5"))
                .andExpect(status().is(200));
    }

    @Test
    void promedioEdadMenores() throws Exception {
        Double average = 10.0;
        when(studentService.promedioEdadMenores()).thenReturn(average);
        mockMvc.perform(get("/students/promedioEdadMenores").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("10.0"))
                .andExpect(status().is(200));
    }
}