package com.fuakim.service;

import com.fuakim.model.Enrollment;

import java.util.List;
import java.util.Map;

public interface IEnrollmentService extends ICRUD<Enrollment, Integer>{


    public Map<String, List<String>> getStudentsByCourse();

}
