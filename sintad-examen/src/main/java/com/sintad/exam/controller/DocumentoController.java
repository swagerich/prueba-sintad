package com.sintad.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sintad.exam.exception.NotFoundExpcetion;
import com.sintad.exam.models.Documento;
import com.sintad.exam.repository.DocumentoRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DocumentoController {


    private DocumentoRepository documentoRepo;

    public DocumentoController(DocumentoRepository documentoRepo) {
        this.documentoRepo = documentoRepo;
    }


    @GetMapping("/documentos")
    public List<Documento> getAllTDocumentos() {
        return documentoRepo.findAll();
    }

    @GetMapping("/documento/{id}")
    public Documento getTDocumentoById(@PathVariable int id) {
        Optional<Documento> tDocument = documentoRepo.findById(id);
        if (tDocument.isPresent()) {
            return tDocument.get();
        } else {
            throw new NotFoundExpcetion("Documentos por id no encontrado" + id);
        }

    }

    @Secured({"ROLE_ADMIN", "ROLE_USERNAME"})
    @PostMapping("/documento")
    public Object createTDocumento(@Valid @RequestBody Documento document, BindingResult result) {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        return documentoRepo.save(document);

    }

    @Secured("ROLE_USERNAME")
    @PutMapping("/documento/{id}")
    public Documento updateTDocumento(@PathVariable int id, @RequestBody Documento updocument) {

        return documentoRepo.findById(id).map(documento -> {
            documento.setCodigo(updocument.getCodigo());
            documento.setNombre(updocument.getNombre());
            documento.setDescripcion(updocument.getDescripcion());
            documento.setEstado(updocument.getEstado());
            return documentoRepo.save(documento);
        }).orElseThrow(() -> new NotFoundExpcetion("Documento no encontrado por id" + id));

    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/documento/{id}")
    public String deleteTDocumento(@PathVariable int id) {
        return documentoRepo.findById(id).map(documento -> {
            documentoRepo.delete(documento);
            return " Eliminado Documento con exito";
        }).orElseThrow(() -> new NotFoundExpcetion("No fue encontrado Documento por id"));
    }

    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El Campo" + " " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
