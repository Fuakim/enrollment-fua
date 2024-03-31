package com.fuakim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuakim.dto.CourseDTO;
import com.fuakim.exception.ModelNotFoundException;
import com.fuakim.model.Course;
import com.fuakim.service.ICourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICourseService service;

    @MockBean(name = "defaultMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Course COURSE_1 = new Course(1, "Matematica","MAT",  true);
    Course COURSE_2 = new Course(2, "Lenguas","LEN",  true);
    Course COURSE_3 = new Course(3, "Ciencia", "CIE", true);

    CourseDTO COURSEDTO_1 = new CourseDTO(1, "Matematica", "MAT", true);
    CourseDTO COURSEDTO_2 = new CourseDTO(2, "Lenguas", "LEN", true);
    CourseDTO COURSEDTO_3 = new CourseDTO(3, "Ciencia", "CIE", true);


    @Test
    void readAllTest() throws Exception{
        List<Course> categories = List.of(COURSE_1,COURSE_2,COURSE_3);

        Mockito.when(service.readAll()).thenReturn(categories);
        Mockito.when(modelMapper.map(COURSE_1, CourseDTO.class)).thenReturn(COURSEDTO_1);
        Mockito.when(modelMapper.map(COURSE_2, CourseDTO.class)).thenReturn(COURSEDTO_2);
        Mockito.when(modelMapper.map(COURSE_3, CourseDTO.class)).thenReturn(COURSEDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/courses")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("Lenguas")));
    }

    @Test
    void readByIdTest() throws Exception{
        final int ID = 1;

        Mockito.when(service.readById(any())).thenReturn(COURSE_1);
        Mockito.when(modelMapper.map(COURSE_1, CourseDTO.class)).thenReturn(COURSEDTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/courses/" + ID)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name", is("Matematica")));

    }

    @Test
    void createTest() throws Exception{
        Mockito.when(service.save(any())).thenReturn(COURSE_3);
        Mockito.when(modelMapper.map(COURSE_3, CourseDTO.class)).thenReturn(COURSEDTO_3);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(COURSEDTO_3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(201)));
    }

    @Test
    void updateTest() throws Exception{
        final int ID = 2;
        Mockito.when(service.update(any(), any())).thenReturn(COURSE_2);
        Mockito.when(modelMapper.map(COURSE_2, CourseDTO.class)).thenReturn(COURSEDTO_2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/courses/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(COURSEDTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Lenguas")));
    }

    @Test
    void updateErrorTest() throws Exception{
        final int ID = 99;
        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT VALID: " + ID));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/courses/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(COURSEDTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }

    @Test
    void deleteTest() throws Exception{
        final int ID = 1;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/courses/" + ID)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteErrorTest() throws Exception{
        final int ID = 99;

        Mockito.doThrow(new ModelNotFoundException("ID NOT FOUND: " + ID)).when(service).delete(ID);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/courses/" + ID)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }
}
