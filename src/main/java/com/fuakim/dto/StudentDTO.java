package com.fuakim.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class StudentDTO {

    private Integer id;

    @NotNull(message = "name no puede ser nulo")
    @NotBlank(message = "name no puede estar en blanco")
    @Size(max = 30,message = "El nombre debe tener maximo 30 caracteres")
    private String name;

    @NotNull(message = "lastName no puede ser nulo")
    @NotBlank(message = "lastName no puede estar en blanco")
    @Size(max = 30,message = "El apellido debe tener maximo 30 caracteres")
    private String lastName;

    @NotNull(message = "dni no puede ser nulo")
    @NotBlank(message = "dni no puede estar en blanco")
    @Size(max = 12,message = "El nombre del curso debe tener maximo 30 caracteres")
    private String dni;

    @NotNull(message = "age no puede ser nulo")
    private Integer age;
}
