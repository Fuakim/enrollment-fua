package com.fuakim.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class EnrollmentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "enrollment", nullable = false, foreignKey = @ForeignKey(name = "FK_DETAIL_SALE"))
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "course", nullable = false, foreignKey = @ForeignKey(name = "FK_DETAIL_PRODUCT"))
    private Course course;

    @Column(nullable = false, length = 10)
    private String classroom;


}
