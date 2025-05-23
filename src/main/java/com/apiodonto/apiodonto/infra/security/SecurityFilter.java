package com.apiodonto.apiodonto.infra.security;

import com.apiodonto.apiodonto.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Ignora o login e as rotas do Swagger para não exigir token
        if (requestURI.equals("/login") ||
                requestURI.equals("/auth/login") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.equals("/swagger-ui.html") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.equals("/v3/api-docs.yaml")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Recuperar token da requisição
            String tokenJWT = recuperarToken(request);

            // Validar token e recuperar o subject (usuário)
            String subject = tokenService.getSubject(tokenJWT);

            // Buscar o usuário no banco de dados com o login (subject)
            UserDetails usuario = repository.findByLogin(subject);

            if (usuario != null) {
                // Criar autenticação para o usuário e definir no contexto de segurança
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não encontrado");
                return;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        // Continua com o filtro
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        // Verifica se o cabeçalho Authorization foi enviado e se começa com "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token não enviado no cabeçalho Authorization");
        }

        // Retorna o token sem o prefixo "Bearer "
        return authorizationHeader.replace("Bearer ", "");
    }
}
