package com.api.sistemachamados.security;

import com.api.sistemachamados.entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.secret}")
	private String secret;

	ObjectMapper objectMapper = new ObjectMapper();

	public UsuarioContext getUserFromContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((UsuarioContext)authentication.getPrincipal());
	}

	public String generateJwtToken(Usuario usuario) {
		JwtTokenData jwtTokenData = generateJwtTokenData(usuario);

		Map<String, Object> claims = objectMapper.convertValue(jwtTokenData, Map.class);

		Date now = new Date();
		Date exp = new Date(now.getTime() + Long.parseLong(expiration));

		SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

		return Jwts.builder()
				.setIssuer("HO_TOKEN")
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(secretKey).compact();
	}

	private JwtTokenData generateJwtTokenData(Usuario usuario) {
		JwtTokenData jwtTokenData = new JwtTokenData();

		UsuarioContext usuarioContext = new UsuarioContext();
		usuarioContext.setId(usuario.getId());
		usuarioContext.setEmail(usuario.getEmail());
		usuarioContext.setNome(usuario.getNome());
		List<String> roles = new ArrayList<>();
		usuario.getAuthorities().forEach((grantedAuthority) -> roles.add(grantedAuthority.getAuthority()));

		usuarioContext.setRoles(roles);

		jwtTokenData.setUsuario(usuarioContext);

		return jwtTokenData;
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Date getExpirationFromToken(String token) {
		return getAllClaimsFromJwtToken(token).getExpiration();
	}

	public UsuarioContext getUserContextFromJwtToken(String token) {
		Map<String, Object> usuarioObject = (Map<String, Object>) getAllClaimsFromJwtToken(token).get("usuario");

		UsuarioContext usuarioContext = new UsuarioContext();
		usuarioContext.setId(Long.parseLong(String.valueOf(usuarioObject.get("id"))));
		usuarioContext.setEmail(String.valueOf(usuarioObject.get("email")));
		usuarioContext.setNome(String.valueOf(usuarioObject.get("nome")));
		usuarioContext.setRoles((List<String>) usuarioObject.get("roles"));
//		UsuarioContext usuario = objectMapper.convertValue(usuarioObject, UsuarioContext.class);
		return usuarioContext;
	}

	private Claims getAllClaimsFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
	}



}
