package com.fuakim.service;

import com.fuakim.model.Student;
import com.fuakim.repo.IStudentRepo;
import com.fuakim.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {

    @MockBean
    private StudentServiceImpl service;

    private IStudentRepo repo;

    private Student STUDENT_1;
    private Student STUDENT_2;
    private Student STUDENT_3;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        this.service = new StudentServiceImpl(repo);

        STUDENT_1 = new Student(1, "Cristhian", "Fallas", "117780576", 23);
        STUDENT_2 = new Student(2, "Alberto", "Zuniga", "114140953", 53);
        STUDENT_3 = new Student(3, "Maria", "Valverde", "110500789", 34);

        List<Student> categories = List.of(STUDENT_1, STUDENT_2, STUDENT_3);

        Mockito.when(repo.findAll()).thenReturn(categories);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(STUDENT_1));
        Mockito.when(repo.save(any())).thenReturn(STUDENT_1);
    }

    @Test
    void readAllTest() throws Exception{
       List<Student> response = service.readAll();

        assertEquals(response.size(), 3);
    }

    @Test
    void readByIdTest() throws Exception{
        final int ID = 1;
        Student response = service.readById(ID);

        assertNotNull(response);
    }

    @Test
    void save() throws Exception{
        Student response = service.save(STUDENT_1);

        assertNotNull(response);
    }

    @Test
    void update() throws Exception{
        Student response = service.update(STUDENT_1, STUDENT_1.getId());

        assertNotNull(response);
    }

    @Test
    void delete() throws Exception{
        final int ID = 1;

        repo.deleteById(ID);
        repo.deleteById(ID);

        verify(repo, times(2)).deleteById(ID);
    }
}
