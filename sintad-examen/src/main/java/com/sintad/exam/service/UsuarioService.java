package com.sintad.exam.service;

import com.sintad.exam.exception.NotFoundExpcetion;
import com.sintad.exam.models.Usuario;
import com.sintad.exam.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService,IUsuarioService {

    private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private  UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByUsername(username);

        if(usuario==null){
            logger.error("Ah ocurrido un error en el login: no existe el usuario'"+username+"' en el sistema!");
            throw  new UsernameNotFoundException("Error en el login");
        }

        List<GrantedAuthority> authorities= usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(authority -> logger.info("Role:" + authority.getAuthority()))
                .collect(Collectors.toList());
        return new User(usuario.getUsername(),usuario.getPassword(),usuario.getEnabled(),true,true,true,authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepo.findByUsername(username);
    }
}
