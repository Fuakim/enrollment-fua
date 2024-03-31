package com.fuakim.controller;

import com.fuakim.dto.GenericResponse;
import com.fuakim.dto.StudentDTO;
import com.fuakim.model.Student;
import com.fuakim.service.IStudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Students")
@RequiredArgsConstructor
public class StudentController {

    private final IStudentService service;
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> readAll() throws Exception{
        List<StudentDTO> list = service.readAll().stream().map(this::convertToDto).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> readById(@PathVariable("id") Integer id) throws Exception{
        Student obj = service.readById(id);

        return ResponseEntity.ok(convertToDto(obj));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<StudentDTO>> save(@Valid @RequestBody StudentDTO dto) throws Exception{
        Student obj = service.save(convertToEntity(dto));

        return ResponseEntity.created(URI.create("/students/" + obj.getId())).body(new GenericResponse<StudentDTO>().created( Arrays.asList( convertToDto(obj))) );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@Valid @RequestBody StudentDTO dto, @PathVariable("id") Integer id) throws Exception{
        Student obj = service.update(convertToEntity(dto), id);

        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/order")
    public ResponseEntity<List<StudentDTO>> findAllOrderByAge(){
        List<StudentDTO> list = service.findAllOrderByAge().stream().map(this::convertToDto).toList();

        return ResponseEntity.ok(list);
    }


    private StudentDTO convertToDto(Student obj){
        return modelMapper.map(obj, StudentDTO.class);
    }

    private Student convertToEntity(StudentDTO dto){
        return modelMapper.map(dto, Student.class);
    }


}
