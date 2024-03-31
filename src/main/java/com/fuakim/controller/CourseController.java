package com.fuakim.controller;

import com.fuakim.dto.CourseDTO;
import com.fuakim.dto.GenericResponse;
import com.fuakim.model.Course;
import com.fuakim.service.ICourseService;
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
@RequestMapping("/courses")
@Tag(name = "Courses")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService service;
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> readAll() throws Exception{
       List<CourseDTO> list = service.readAll().stream().map(this::convertToDto).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<CourseDTO>> readById(@PathVariable("id") Integer id) throws Exception{
        Course obj = service.readById(id);
        return ResponseEntity.ok(new GenericResponse<CourseDTO>().ok(Arrays.asList(convertToDto(obj))));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<CourseDTO>> save(@Valid @RequestBody CourseDTO dto) throws Exception{
        Course obj = service.save(convertToEntity(dto));
        return ResponseEntity.created(URI.create("/courses/" + obj.getId())).body(new GenericResponse<CourseDTO>().created(Arrays.asList( convertToDto(obj))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@Valid @RequestBody CourseDTO dto, @PathVariable("id") Integer id) throws Exception{
        Course obj = service.update(convertToEntity(dto), id);

        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CourseDTO convertToDto(Course obj){
        return modelMapper.map(obj, CourseDTO.class);
    }

    private Course convertToEntity(CourseDTO dto){
        return modelMapper.map(dto, Course.class);
    }


}
