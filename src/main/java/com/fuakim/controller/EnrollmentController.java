package com.fuakim.controller;

import com.fuakim.dto.EnrollmentDTO;
import com.fuakim.dto.GenericResponse;
import com.fuakim.model.Enrollment;
import com.fuakim.service.IEnrollmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollments")
@Tag(name = "Enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final IEnrollmentService service;
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> readAll() throws Exception{
        List<EnrollmentDTO> list = service.readAll().stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<EnrollmentDTO>> readById(@PathVariable("id") Integer id) throws Exception{
        Enrollment obj = service.readById(id);
        return ResponseEntity.ok(new GenericResponse<>(200, "success", Arrays.asList( convertToDto(obj))) );
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> save(@Valid @RequestBody EnrollmentDTO dto) throws Exception{
        Enrollment obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> update(@Valid @RequestBody EnrollmentDTO dto, @PathVariable("id") Integer id) throws Exception{
        Enrollment obj = service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/studentsByCourse")
    public ResponseEntity<Map<String, List<String>>> getStudentsByCourse(){
        return new ResponseEntity<>(service.getStudentsByCourse(), HttpStatus.OK);
    }

    private EnrollmentDTO convertToDto(Enrollment obj){
        return modelMapper.map(obj, EnrollmentDTO.class);
    }

    private Enrollment convertToEntity(EnrollmentDTO dto){
        return modelMapper.map(dto, Enrollment.class);
    }

}
