package mx.edu.utez.sad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.sad.entity.LogBookEntity;
import mx.edu.utez.sad.service.LogBookService;

@RestController
@RequestMapping("/logBook")
public class LogBookController {

	@Autowired
	private LogBookService lbs;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(lbs.findAll());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody LogBookEntity logBook)  {
		return ResponseEntity.ok(lbs.save(logBook));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(lbs.findById(id));
	}

}