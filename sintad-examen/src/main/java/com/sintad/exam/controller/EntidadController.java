package com.sintad.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.sintad.exam.models.Contribuyente;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sintad.exam.exception.NotFoundExpcetion;

import com.sintad.exam.models.Entidad;
import com.sintad.exam.repository.ContribuyenteRepository;
import com.sintad.exam.repository.DocumentoRepository;
import com.sintad.exam.repository.EntidadRepository;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class EntidadController {

	private ContribuyenteRepository contribuyenteRepo;
	private DocumentoRepository documentoRepo;
	private EntidadRepository entidadRepo;

	public EntidadController(ContribuyenteRepository contribuyenteRepo, DocumentoRepository documentoRepo,
			EntidadRepository entidadRepo) {
		this.contribuyenteRepo = contribuyenteRepo;
		this.documentoRepo = documentoRepo;
		this.entidadRepo = entidadRepo;
	}
	// LISTAMOS TODAS LAS ENTIDADES
	@GetMapping("/entidades")
	public List<Entidad> getAllEntidades() {
		return entidadRepo.findAll();
	}

	// LISTAMOS DOCUMENTOS CON LA ENTIDAD
	@Secured({"ROLE_ADMIN","ROLE_USERNAME"})
	@GetMapping("/documentos/{documentoId}/entidad")
	public Entidad getEntidadByDocumentoId(@PathVariable int documentoId) {

		if (!documentoRepo.existsById(documentoId)) {
			throw new NotFoundExpcetion("Documento not encontrado!");
		}

		List<Entidad> entidades = entidadRepo.findByDocumentoId(documentoId);
		if (entidades.size() > 0) {
			return entidades.get(0);
		} else {
			throw new NotFoundExpcetion("Entidad no encontrada!");
		}
	}

	// AGREGAMOS DOCUMENTOS , COntribuyentes CON ID E ENTIDADES
	@Secured("ROLE_ADMIN")
	@PostMapping("/documentos/{documentoId}/contribuyente/{contribuyenteId}/entidades")
	public Object createEntidad(@PathVariable int documentoId,@PathVariable int contribuyenteId , @Valid @RequestBody Entidad entidad, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		//code..
		return documentoRepo.findById(documentoId).map(r -> {
			entidad.setDocumento(r);
			return entidadRepo.save(entidad);
		}).orElseThrow(() -> new NotFoundExpcetion("Documento no encontrado!"));
	}

	// HACER UN UPDATE A LAS ENTIDADES
	@PutMapping("/entidades/{entidadId}")
	@Secured("ROLE_ADMIN")
	public Entidad updateEntidad(@PathVariable int entidadId, @RequestBody Entidad upEntidad) {
		return entidadRepo.findById(entidadId).map(entidad -> {
			entidad.setNroDocumento(upEntidad.getNroDocumento());
			entidad.setRazonSocial(upEntidad.getRazonSocial());
			entidad.setNombreComercial(upEntidad.getNombreComercial());
			entidad.setDireccion(upEntidad.getDireccion());
			entidad.setTelefono(upEntidad.getTelefono());
			entidad.setEstado(upEntidad.getEstado());
			entidad.setContribuyente(upEntidad.getContribuyente());
			return entidadRepo.save(entidad);
		}).orElseThrow(() -> new NotFoundExpcetion("Entidad no encontrado"));
	}
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/entidades/{entidadId}")
	public String deleteEntidad(@PathVariable int entidadId) {
		
		return entidadRepo.findById(entidadId).map(entidad ->{
			entidadRepo.delete(entidad);
			return "Entidad eliminada con exito";
		}).orElseThrow(()-> new NotFoundExpcetion("Entidad no encontrada!"));
	}
	/***
	 * =================================================================================================================================
	 * =================================================================================================================================
	 * =================================================================================================================================
	 * =================================================================================================================================
	 * ==================================================================================================================================
	*/

	@Secured({"ROLE_ADMIN","ROLE_USERNAME"})
	@GetMapping("/contribuyentes/{contribuyenteId}/entidad")
	public Entidad getEntidadByContribuyenteId(@PathVariable int contribuyenteId) {
		if (!contribuyenteRepo.existsById(contribuyenteId)) {
			throw new NotFoundExpcetion("Documento not encontrado!");
		}
		List<Entidad> entidades = entidadRepo.findByContribuyenteId(contribuyenteId);
		if (entidades.size() > 0) {
			return entidades.get(0);
		} else {
			throw new NotFoundExpcetion("Entidad no encontrada!");
		}
	}

	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err ->{
			errores.put(err.getField(),"El Campo" + " " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}

}
