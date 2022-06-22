package com.example.studentservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "Vil", "Carlos", LocalDate.of(1960, 7, 5));
    }

    @Test
    void getId() {
        assertEquals(1,student.getId());
    }

    @Test
    void getSurname() {
        assertEquals("Vil", student.getSurname());
    }

    @Test
    void getName() {
         assertEquals("Carlos", student.getName());
    }

    @Test
    void getBirthday() {
        assertEquals(LocalDate.of(1960, 7, 5), student.getBirthday());
    }

    @Test
    void setId() {
        Student student = new Student();
        student.setId(4L);
        assertEquals(4L,student.getId());
    }

    @Test
    void setSurname() {
        Student student = new Student();
        student.setSurname("Vil");
        assertEquals("Vil", student.getSurname());
    }

    @Test
    void setName() {
        Student student = new Student();
        student.setName("Carlos");
        assertEquals("Carlos",student.getName());
    }

    @Test
    void setBirthday() {
        Student student = new Student();
        student.setBirthday(LocalDate.of(1960, 7, 5));
        assertEquals(LocalDate.of(1960, 7, 5), student.getBirthday());
    }

    @Test
    void ToString(){
        assertEquals("Student(id=1, surname=Vil, name=Carlos, birthday=1960-07-05)", student.toString());
    }
}