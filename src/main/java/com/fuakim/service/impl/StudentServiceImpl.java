package com.fuakim.service.impl;

import com.fuakim.model.Student;
import com.fuakim.repo.IGenericRepo;
import com.fuakim.repo.IStudentRepo;
import com.fuakim.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, Integer> implements IStudentService {

    private final IStudentRepo repo;

    @Override
    protected IGenericRepo<Student, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Student> findAllOrderByAge() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "age"));
    }

}
