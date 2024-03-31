package com.fuakim.service.impl;

import com.fuakim.model.Course;
import com.fuakim.repo.ICourseRepo;
import com.fuakim.repo.IGenericRepo;
import com.fuakim.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDImpl<Course, Integer> implements ICourseService {

    private final ICourseRepo repo;

    @Override
    protected IGenericRepo<Course, Integer> getRepo() {
        return repo;
    }

}
