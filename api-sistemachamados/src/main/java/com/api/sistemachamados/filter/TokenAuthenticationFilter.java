package com.api.sistemachamados.filter;

import com.api.sistemachamados.security.UsuarioContext;
import com.api.sistemachamados.security.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    static final String[] PERMIT_ALL_PATTERNS = new String[] {"login.auth","login.swagger", "login.docs"};

    private final TokenService tokenService;

    public TokenAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { ;
        if(checkIfNeedFilter(request)){
            String tokenFromHeader = getTokenFromHeader(request);
            boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
            if(tokenValid) {
                this.authenticateFromToken(tokenFromHeader);
            }

        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }

    private void authenticateFromToken(String token) {
        UsuarioContext user = tokenService.getUserContextFromJwtToken(token);

        if(user != null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    private boolean checkIfNeedFilter(HttpServletRequest request) {
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String pattern : PERMIT_ALL_PATTERNS) {
            matchers.add(new AntPathRequestMatcher(pattern));
        }
        RequestMatcher ignoreRequestMatcher = new OrRequestMatcher(matchers);
        return !(ignoreRequestMatcher.matches((HttpServletRequest) request));
    }

}
