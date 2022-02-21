package com.sintad.exam.service;

import com.sintad.exam.models.Usuario;

public interface IUsuarioService {
    Usuario findByUsername(String username);
}
