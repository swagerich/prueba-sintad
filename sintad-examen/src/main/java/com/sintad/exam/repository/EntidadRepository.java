package com.sintad.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sintad.exam.models.Entidad;


public interface EntidadRepository extends JpaRepository<Entidad, Integer> {

	List<Entidad> findByDocumentoId(int documentoId);
	List<Entidad> findByContribuyenteId(int contribuyenteId);

}
