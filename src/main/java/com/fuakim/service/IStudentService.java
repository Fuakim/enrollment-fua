package com.fuakim.service;

import com.fuakim.model.Student;

import java.util.List;

public interface IStudentService extends ICRUD<Student, Integer>{

    List<Student> findAllOrderByAge();
}
