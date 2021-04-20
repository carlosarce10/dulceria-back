package mx.edu.utez.sad.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.sad.entity.RoleEntity;
import mx.edu.utez.sad.entity.UserEntity;
import mx.edu.utez.sad.service.RoleService;
import mx.edu.utez.sad.service.UserService;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private String username2 = "";
	private String host = "";
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";
	private static final String ROLECASHIER = "ROLE_CASHIER";

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list/true")
	public ResponseEntity<?> findByStatusTrue() {
		RoleEntity role = roleService.findByName(ROLECASHIER);
		return ResponseEntity.ok(userService.findByStatusAndRole(true, role));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list/false")
	public ResponseEntity<?> findByStatusFalse() {
		RoleEntity role = roleService.findByName(ROLECASHIER);
		return ResponseEntity.ok(userService.findByStatusAndRole(false, role));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/exists/{username}")
	public ResponseEntity<?> existsByUsername(
			@PathVariable("username") @NotBlank(message = "El nombre de usuario no debe estar en blanco") @Size(min = 3, max = 255, message = "El nombre de usuario debe tener entre 3 y 255 caracteres") String username) {
		return ResponseEntity.ok(userService.findByUsername(username));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save/admin")
	public ResponseEntity<?> saveUser(@RequestBody @Valid UserEntity user, HttpServletRequest request) {
		user.setRole(roleService.findByName("ROLE_ADMIN"));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		user.setHost(host);
		user.setUserB(username2);
		user.setStatus(true);
		return ResponseEntity.ok(userService.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save/cashier")
	public ResponseEntity<?> saveCashier(@RequestBody @Valid UserEntity user, HttpServletRequest request) {
		user.setRole(roleService.findByName(ROLECASHIER));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		user.setHost(host);
		user.setUserB(username2);
		user.setStatus(true);
		return ResponseEntity.ok(userService.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/change/{username}/{id}")
	public ResponseEntity<?> changeUsername(
			@PathVariable("username") @NotBlank(message = "El nombre de usuario no debe ir vacío") @Size(min = 3, max = 255, message = "El nombre de usuario debe contener entre 3 y 255 caracteres") String username,
			@PathVariable("id") @Positive(message = "El identificador no puede ser nulo") Long id,
			HttpServletRequest request) {

		UserEntity user = userService.findById(id);
		user.setUsername(username);
		username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		user.setHost(host);
		user.setUserB(username2);
		return ResponseEntity.ok(userService.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/change/password")
	public ResponseEntity<?> changePassword(@RequestBody @Valid UserEntity user, HttpServletRequest request) {
		String newPassword = user.getPassword();
		user = userService.findById(user.getId());
		user.setPassword(passwordEncoder.encode(newPassword));
		username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		user.setHost(host);
		user.setUserB(username2);
		return ResponseEntity.ok(userService.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo") Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/del/{id}")
	public ResponseEntity<?> deleteById(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo") Long id,
			HttpServletRequest request) {
		UserEntity user = userService.findById(id);
		user.setStatus(false);
		username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		user.setHost(host);
		user.setUserB(username2);
		return ResponseEntity.ok(userService.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/put/{id}")
	public ResponseEntity<?> activateUser(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo") Long id,
			HttpServletRequest request) {
		UserEntity user = userService.findById(id);
		username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		user.setHost(host);
		user.setUserB(username2);
		user.setStatus(true);
		return ResponseEntity.ok(userService.save(user));
	}
}
