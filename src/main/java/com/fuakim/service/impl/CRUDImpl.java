package com.fuakim.service.impl;

import com.fuakim.exception.ModelNotFoundException;
import com.fuakim.repo.IGenericRepo;
import com.fuakim.service.ICRUD;

import java.lang.reflect.Method;
import java.util.List;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T save(T t) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) throws Exception {
        Class<?> clazz = t.getClass();
        Method setIdMethod = clazz.getMethod("setId",id.getClass());
        setIdMethod.invoke(t, id);
        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND " + id));
        return getRepo().save(t);
    }

    @Override
    public List<T> readAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T readById(ID id) throws Exception {
        return getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND " + id));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND " + id));
        getRepo().deleteById(id);
    }
}
