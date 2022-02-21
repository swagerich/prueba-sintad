package com.sintad.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sintad.exam.models.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

}
