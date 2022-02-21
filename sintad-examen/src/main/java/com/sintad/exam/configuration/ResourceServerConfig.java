package com.sintad.exam.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //CONFIGURE POR MEDIO DE OAUTH
    @Override
    public void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/documentos/**").permitAll()
               .antMatchers(HttpMethod.GET,"/api/contribuyentes/**").permitAll()
               .antMatchers(HttpMethod.GET,"/api/entidades/**").permitAll()
               .anyRequest().authenticated()
               .and().cors().configurationSource(corsConfigurationSource());
    }

    //CONFIGURAMOS LOS CORS PARA ANGULAR
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("Content-Type","Authorization"));
        //REGISTRAMOS LA CONFIGURACION
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    //CREAMOS UN FILTRO DE CORS  Y LO REGISTRAMOS PRIORIDAD ALTA
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
