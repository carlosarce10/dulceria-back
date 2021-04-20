package mx.edu.utez.sad.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.sad.entity.PackageEntity;
import mx.edu.utez.sad.service.PackageService;

@RestController
@Validated
@RequestMapping("/package")
public class PackageController {

	@Autowired
	private PackageService ps;

	private String username = "";
	private String host = "";
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/list/true")
	public ResponseEntity<?> findByStatusTrue() {
		return ResponseEntity.ok(ps.findByStatus(true));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list/false")
	public ResponseEntity<?> findByStatusFalse() {
		return ResponseEntity.ok(ps.findByStatus(false));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody @Valid PackageEntity packagee, HttpServletRequest request) {
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		packagee.setUserB(username);
		packagee.setHost(host);
		packagee.setStatus(true);
		return ResponseEntity.ok(ps.save(packagee));
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo") Long id) {
		return ResponseEntity.ok(ps.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/del/{id}")
	public ResponseEntity<?> deleteById(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo") Long id,
			HttpServletRequest request) {
		PackageEntity packagee = ps.findById(id);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		packagee.setUserB(username);
		packagee.setHost(host);
		packagee.setStatus(false);
		return ResponseEntity.ok(ps.save(packagee));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/put/{id}")
	public ResponseEntity<?> changeStatusTrue(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo") Long id,
			HttpServletRequest request) {
		PackageEntity packagee = ps.findById(id);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		packagee.setUserB(username);
		packagee.setHost(host);
		packagee.setStatus(true);

		return ResponseEntity.ok(ps.save(packagee));
	}

}
