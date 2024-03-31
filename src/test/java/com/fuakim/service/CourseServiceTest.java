package com.fuakim.service;

import com.fuakim.model.Course;
import com.fuakim.repo.ICourseRepo;
import com.fuakim.service.impl.CourseServiceImpl;
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
public class CourseServiceTest {

    @MockBean
    private CourseServiceImpl service;

    @MockBean
    private ICourseRepo repo;

    private Course COURSE_1;
    private Course COURSE_2;
    private Course COURSE_3;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        this.service = new CourseServiceImpl(repo);

        COURSE_1 = new Course(1, "Matematica","MAT",  true);
        COURSE_2 = new Course(2, "Lenguas","LEN",  true);
        COURSE_3 = new Course(3, "Ciencia", "CIE", true);

        List<Course> categories = List.of(COURSE_1, COURSE_2, COURSE_3);

        Mockito.when(repo.findAll()).thenReturn(categories);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(COURSE_1));
        Mockito.when(repo.save(any())).thenReturn(COURSE_1);
    }

    @Test
    void readAllTest() throws Exception{
       List<Course> response = service.readAll();

        assertEquals(response.size(), 3);
    }

    @Test
    void readByIdTest() throws Exception{
        final int ID = 1;
        Course response = service.readById(ID);

        assertNotNull(response);
    }

    @Test
    void save() throws Exception{
        Course response = service.save(COURSE_1);

        assertNotNull(response);
    }

    @Test
    void update() throws Exception{
        Course response = service.update(COURSE_1, COURSE_1.getId());

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
