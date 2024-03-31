package com.fuakim.service.impl;

import com.fuakim.model.Enrollment;
import com.fuakim.model.EnrollmentDetail;
import com.fuakim.repo.IEnrollmentRepo;
import com.fuakim.repo.IGenericRepo;
import com.fuakim.service.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl extends CRUDImpl<Enrollment, Integer> implements IEnrollmentService {

    private final IEnrollmentRepo repo;

    @Override
    protected IGenericRepo<Enrollment, Integer> getRepo() {
        return repo;
    }

    @Override
    public Map<String, List<String>> getStudentsByCourse() {
        Stream<Enrollment> enrollmentStream = repo.findAll().stream();

        Stream<List<EnrollmentDetail>> lsStream = enrollmentStream.map(Enrollment::getDetails);

        Stream<EnrollmentDetail> streamDetail = lsStream.flatMap(Collection::stream);
        Map<String, List<String>> byCourse = streamDetail
                .collect(
                        groupingBy(
                                enrollmentDetail -> enrollmentDetail.getCourse().getName(),
                                mapping(enrollmentDetail -> enrollmentDetail.getEnrollment().getStudent().getName(), toList())
                                )
                )
        ;

        return byCourse;
    }
}