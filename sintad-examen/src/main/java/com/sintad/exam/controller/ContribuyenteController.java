package com.sintad.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sintad.exam.exception.NotFoundExpcetion;
import com.sintad.exam.models.Contribuyente;
import com.sintad.exam.repository.ContribuyenteRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ContribuyenteController {

    private ContribuyenteRepository contribuyenteRepo;

    public ContribuyenteController(ContribuyenteRepository contribuyenteRepo) {
        this.contribuyenteRepo = contribuyenteRepo;
    }


    @GetMapping("/contribuyentes")
    public List<Contribuyente> getAllContribuyents() {
        return contribuyenteRepo.findAll();
    }


    @GetMapping("/contribuyente/{id}")
    public Contribuyente getTContribuyenteById(@PathVariable int id) {
        Optional<Contribuyente> opTipo = contribuyenteRepo.findById(id);
        if (opTipo.isPresent()) {
            return opTipo.get();
        } else {
            throw new NotFoundExpcetion("Contribuyente no encontrado por el id" + id);
        }

    }

    @PostMapping("/contribuyente")
    public Object createTContribuyente(@Valid @RequestBody Contribuyente contribuyente, BindingResult result) {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        return contribuyenteRepo.save(contribuyente);
    }

    @PutMapping("/contribuyente/{id}")
    public Contribuyente updateTContribuyente(@PathVariable int id, @RequestBody Contribuyente upContribuyente) {
        return contribuyenteRepo.findById(id).map(contribuyente -> {
            contribuyente.setNombre(upContribuyente.getNombre());
            contribuyente.setEstado(upContribuyente.getEstado());
            return contribuyenteRepo.save(contribuyente);
        }).orElseThrow(() -> new NotFoundExpcetion("Contribuyente no encontrado por  id" + id));

    }

    @DeleteMapping("/contribuyente/{id}")
    public String deleteContribuyente(@PathVariable int id) {
        return contribuyenteRepo.findById(id).map(contribuyente -> {
            contribuyenteRepo.delete(contribuyente);
            return "Contribuyente Eliminado con Exito";
        }).orElseThrow(() -> new NotFoundExpcetion("Contribuyente no encontrado por id" + id));
    }


    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El Campo" + " " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}



