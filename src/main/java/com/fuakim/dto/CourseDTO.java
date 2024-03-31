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
public class CourseDTO {

    private Integer id;

    @NotNull(message = "name no puede ser nulo")
    @NotBlank(message = "name no puede estar en blanco")
    @Size(max = 30,message = "El nombre debe tener maximo 30 caracteres")
    private String name;

    @NotNull(message = "code no puede ser nulo")
    @NotBlank(message = "code no puede estar en blanco")
    @Size(max = 3,message = "El código debe tener maximo 3 caracteres")
    private String code;

    @NotNull(message = "enabled no puede ser nulo")
    private boolean enabled;
}
