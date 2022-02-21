package com.sintad.exam.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,length = 20)
    private String username;
    @Column(length = 200)
    private String password;
    private Boolean enabled;
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "usuarios_roles",joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name ="role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","role_id"})})
    private List<Role> roles;
}
