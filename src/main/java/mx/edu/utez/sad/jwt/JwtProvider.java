package mx.edu.utez.sad.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import mx.edu.utez.sad.configuration.CustomUserDetails;

@Component
public class JwtProvider {

	@Value("${jwt.passwordSecret}")
	private String passwordSecret;

	@Value("${jwt.expiration}")
	private int expiration;
	
	private Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	private String errorMsg = "";
	private String separador = "-";
	private String codigo = "Código de error: ";
	
	public String generateToken(Authentication auth) {
		CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
		return Jwts.builder()
				.setSubject(customUserDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, this.passwordSecret).compact();
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(this.passwordSecret)
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	public Boolean verifyToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.passwordSecret).parseClaimsJws(token);
			return true;
		}catch (MalformedJwtException e) {
			errorMsg = "Token mal formado.";
			logger.error(codigo + "SET-01" + separador + errorMsg);
		}catch (UnsupportedJwtException e) {
			errorMsg = "Token no soportado.";
			logger.error(codigo + "SET-02" + separador + errorMsg);
		}catch (ExpiredJwtException e) {
			errorMsg = "Token expirado.";
			logger.error(codigo + "SET-03" + separador + errorMsg);
		}catch (IllegalArgumentException e) {
			errorMsg = "No hay token.";
			logger.error(codigo + "SET-04" + separador + errorMsg);
		}catch (SignatureException e) {
			errorMsg = "Firma errónea.";
			logger.error(codigo + "SET-05" + separador + errorMsg);
		}
		return false;
	}
	
}
