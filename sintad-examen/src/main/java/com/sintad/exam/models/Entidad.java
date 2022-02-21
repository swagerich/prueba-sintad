package com.sintad.exam.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_entidad")
public class Entidad implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;

    @Column(name = "nro_documento", length = 25)
    private int nroDocumento;

    @Column(name = "razon_social", length = 100)
    @NotEmpty
    private String razonSocial;

    @Column(name = "nombre_comercial", length = 100)
    @NotEmpty
    private String nombreComercial;

    @Column(length = 250)
    @NotEmpty
    private String direccion;

    @Column(length = 50)
    @NotEmpty
    private String telefono;

    private Boolean estado;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipodocumento_id")
    @JsonIgnoreProperties(value = {"entidad", "handler", "hibernateLazyInitializer"})
    private Documento documento;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipocontribuyente_id")
    @JsonIgnoreProperties(value = {"entidad", "handler", "hibernateLazyInitializer"})
    private Contribuyente contribuyente;


}
