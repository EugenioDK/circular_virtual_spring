package com.example.circular.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface IGenericService <T, ID extends Serializable>{

	public T find(ID id) throws Exception ;
    
    public Page<T> findAll(Specification<T> spec, Pageable pageable) throws Exception ;
    
    public T generate(T entity) throws Exception ; 
    
    public T amend(ID id, T entity) throws Exception;
     
    public String destroy(ID id) throws Exception ;
    

}
