package com.fuakim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuakim.dto.StudentDTO;
import com.fuakim.exception.ModelNotFoundException;
import com.fuakim.model.Student;
import com.fuakim.service.IStudentService;
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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentService service;

    @MockBean(name = "defaultMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Student STUDENT_1 = new Student(1, "Cristhian", "Fallas", "117780576", 23);
    Student STUDENT_2 =new Student(2, "Alberto", "Zuniga", "114140953", 53);
    Student STUDENT_3 = new Student(3, "Maria", "Valverde", "110500789", 34);


    StudentDTO STUDENTDTO_1 = new StudentDTO(1, "Cristhian", "Fallas", "117780576", 23);
    StudentDTO STUDENTDTO_2 = new StudentDTO(2, "Alberto", "Mora", "114140983", 53);
    StudentDTO STUDENTDTO_3 = new StudentDTO(3, "Maria", "Valverde", "110500789", 34);


    @Test
    void readAllTest() throws Exception{
        List<Student> categories = List.of(STUDENT_1,STUDENT_2,STUDENT_3);

        Mockito.when(service.readAll()).thenReturn(categories);
        Mockito.when(modelMapper.map(STUDENT_1, StudentDTO.class)).thenReturn(STUDENTDTO_1);
        Mockito.when(modelMapper.map(STUDENT_2, StudentDTO.class)).thenReturn(STUDENTDTO_2);
        Mockito.when(modelMapper.map(STUDENT_3, StudentDTO.class)).thenReturn(STUDENTDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("Alberto")));
    }

    @Test
    void readByIdTest() throws Exception{
        final int ID = 1;

        Mockito.when(service.readById(any())).thenReturn(STUDENT_1);
        Mockito.when(modelMapper.map(STUDENT_1, StudentDTO.class)).thenReturn(STUDENTDTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/" + ID)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Cristhian")));
    }

    @Test
    void createTest() throws Exception{
        Mockito.when(service.save(any())).thenReturn(STUDENT_3);
        Mockito.when(modelMapper.map(STUDENT_3, StudentDTO.class)).thenReturn(STUDENTDTO_3);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(STUDENTDTO_3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(201)));
    }

    @Test
    void updateTest() throws Exception{
        final int ID = 2;
        Mockito.when(service.update(any(), any())).thenReturn(STUDENT_2);
        Mockito.when(modelMapper.map(STUDENT_2, StudentDTO.class)).thenReturn(STUDENTDTO_2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/students/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(STUDENTDTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alberto")));
    }

    @Test
    void updateErrorTest() throws Exception{
        final int ID = 99;
        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT VALID: " + ID));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/students/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(STUDENTDTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }

    @Test
    void deleteTest() throws Exception{
        final int ID = 1;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/" + ID)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteErrorTest() throws Exception{
        final int ID = 99;

        Mockito.doThrow(new ModelNotFoundException("ID NOT FOUND: " + ID)).when(service).delete(ID);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/" + ID)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ModelNotFoundException.class, result.getResolvedException()));
    }


    @Test
    void findAllOrderByAgeTest() throws Exception{
        List<Student> students = List.of(STUDENT_2, STUDENT_3 ,STUDENT_1);

        Mockito.when(service.findAllOrderByAge()).thenReturn(students);
        Mockito.when(modelMapper.map(STUDENT_1, StudentDTO.class)).thenReturn(STUDENTDTO_1);
        Mockito.when(modelMapper.map(STUDENT_2, StudentDTO.class)).thenReturn(STUDENTDTO_2);
        Mockito.when(modelMapper.map(STUDENT_3, StudentDTO.class)).thenReturn(STUDENTDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/order")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Cristhian")));
    }
}
