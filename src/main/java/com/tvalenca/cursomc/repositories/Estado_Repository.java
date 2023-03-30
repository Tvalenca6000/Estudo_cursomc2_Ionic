package com.tvalenca.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tvalenca.cursomc.domain.Estado;

@Repository
public interface Estado_Repository extends JpaRepository<Estado, Integer> {
    
    @Transactional(readOnly = true)
    public List<Estado> findAllByOrderByNome();
    
}
