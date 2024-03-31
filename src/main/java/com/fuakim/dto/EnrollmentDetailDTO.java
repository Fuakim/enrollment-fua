package com.fuakim.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentDetailDTO {


    private Integer id;

    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private EnrollmentDTO enrollment;

    @JsonIncludeProperties(value = {"id", "name"})
    @NotNull(message = "course no puede ser nulo")
    private CourseDTO course;

    @NotNull(message = "classroom no puede ser nulo")
    @NotBlank(message = "classroom no puede estar en blanco")
    @Size(max = 10,message = "classroom debe tener maximo 10 caracteres")
    private String classroom;
}
