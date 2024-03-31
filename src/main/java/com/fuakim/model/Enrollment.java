package com.fuakim.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NamedNativeQuery(
        name = "Enrollment.fn_enrollments",
        query = "select * from fn_enrollments()",
        resultSetMapping = "Procedure.ProcedureDTO"
)


////////////////////////////////////////////////////////
/*@NamedStoredProcedureQuery(
        name = "getFnEnrollments2", //un alias a la configuracion
        procedureName = "fn_enrollmentsParameter", //debe coincidir con lo definido en Repo
        resultClasses = IProcedureDTO.class,
        parameters = {
                @StoredProcedureParameter(name = "p_id_client", type = Integer.class, mode = ParameterMode.IN) //,
                //@StoredProcedureParameter(name = "ABC", type = void.class, mode = ParameterMode.REF_CURSOR),
                //@StoredProcedureParameter(name = "DEF", type = String.class, mode = ParameterMode.OUT),
        })
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Table(schema = "sistemas")
@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student", nullable = false, foreignKey = @ForeignKey(name = "FK_ENROLLMENT_STUDENT"))
    private Student student;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<EnrollmentDetail> details;


}
