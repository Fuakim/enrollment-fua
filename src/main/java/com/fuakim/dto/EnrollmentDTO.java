package com.fuakim.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentDTO {


    private Integer id;

    @JsonIncludeProperties(value = {"id", "name"})
    @NotNull(message = "student no puede ser nulo")
    private StudentDTO student;

    @NotNull(message = "dateTime no puede ser nulo")
    private LocalDateTime dateTime;

    @NotNull(message = "enabled no puede ser nulo")
    private boolean enabled;

    @JsonManagedReference
    @NotNull(message = "details no puede ser nulo")
    private List<EnrollmentDetailDTO> details;
}
