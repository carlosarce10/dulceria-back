package mx.edu.utez.sad.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.sad.entity.AuthenticationDto;
import mx.edu.utez.sad.entity.UserEntity;
import mx.edu.utez.sad.jwt.JwtDto;
import mx.edu.utez.sad.jwt.JwtProvider;
import mx.edu.utez.sad.model.Response;
import mx.edu.utez.sad.service.UserService;
import mx.edu.utez.sad.time.Time;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";
	
	private String separador = "-";
	private String codigo = "Código de error: ";
	
	@PostMapping("/login")
	public ResponseEntity<?> authentication(@Valid @RequestBody AuthenticationDto userAuth, 
			BindingResult bindingResult, HttpServletRequest request){
		
		String host = "";
		Response result;
		String errorMsg = "";
		
		if(bindingResult.hasErrors()) {
			result = new Response(Time.getTime(), true, "Datos incompletos.", "SEL-01");
			errorMsg = "Datos incompletos.";
			logger.error(codigo + result.getCode() + separador + errorMsg);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			UserEntity user = userService.findByUsername(userAuth.getUsername());
			if(user != null && user.isStatus()) {
				Authentication authentication = authenticationManager
						.authenticate(
							new UsernamePasswordAuthenticationToken(
									userAuth.getUsername(),
									userAuth.getPassword()
							)
						);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				String token = jwtProvider.generateToken(authentication);
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				host = request.getRemoteAddr();
				if(host.equals(LOCALIP)) {
					host = ORIGINALIP;
				}
				user.setHost(host);
				user.setUserB(username);
				user.setLastLogin(new Date());
				userService.save(user);
				
				JwtDto dto = new JwtDto(token, userDetails.getUsername(), userDetails.getAuthorities());
				
				return ResponseEntity.ok(dto);
			}else {
				result = new Response(Time.getTime(), true, "El usuario no existe o está inhabilitado.", "SEL-02");
				errorMsg = "El usuario no existe o está inhabilitado.";
				logger.error(codigo + result.getCode() + separador + errorMsg);
				return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
			}
			

		} catch (BadCredentialsException e) {
			result = new Response(Time.getTime(), true, "Usuario y/o contraseña incorrectos.", "SEL-03");
			errorMsg = "Usuario y/o contraseña incorrectos.";
			logger.error(codigo + result.getCode() + separador + errorMsg);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}
