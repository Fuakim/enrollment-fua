package com.fuakim.repo;

import com.fuakim.model.Enrollment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface IEnrollmentRepo extends IGenericRepo<Enrollment, Integer> {

}
