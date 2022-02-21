package com.sintad.exam.configuration;

import com.sintad.exam.models.Usuario;
import com.sintad.exam.service.IUsuarioService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlusToken  implements TokenEnhancer {

    private final IUsuarioService usuarioService;

    public PlusToken(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {

        Usuario usuario = usuarioService.findByUsername(oAuth2Authentication.getName());
        Map<String,Object> plus = new HashMap<>();
        plus.put("Information ","Hello, Welcome!".concat(oAuth2Authentication.getName()));
        plus.put("nombre",usuario.getNombre());
        plus.put("apellido", usuario.getApellido());
        plus.put("email",usuario.getEmail());
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(plus);
        return accessToken;
    }
}
