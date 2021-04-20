package mx.edu.utez.sad.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import mx.edu.utez.sad.configuration.CustomUserDetailsService;
public class JwtTokenFilter extends OncePerRequestFilter{

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	private Logger loggerFilter = LoggerFactory.getLogger(JwtTokenFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String header = request.getHeader("Authorization");
			String token = null;
			
			if(header != null && header.startsWith("Bearer")) {
				token = header.replace("Bearer ", "");
			}
			
			if(token != null && jwtProvider.verifyToken(token)) {
				String username = jwtProvider.getUsernameFromToken(token);
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			
		} catch (Exception e) {
			loggerFilter.error("An error has ocurred in the request filter.");
		}
		
		filterChain.doFilter(request, response);
	}
	
	
}
