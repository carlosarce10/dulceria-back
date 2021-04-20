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

import mx.edu.utez.sad.entity.BrandEntity;
import mx.edu.utez.sad.entity.CategoryEntity;
import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.service.BrandService;
import mx.edu.utez.sad.service.CategoryService;
import mx.edu.utez.sad.service.ProductService;

@RestController
@Validated
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	private String username = "";
	private String host = "";
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/list/true")
	public ResponseEntity<?> findByStatusTrue() {
		return ResponseEntity.ok(productService.findByStatus(true));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list/false")
	public ResponseEntity<?> findByStatusFalse() {
		return ResponseEntity.ok(productService.findByStatus(false));
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody @Valid ProductEntity product, HttpServletRequest request) {
		BrandEntity brand = brandService.findById(product.getBrand().getId());
		product.setBrand(brand);
		CategoryEntity category = categoryService.findById(product.getCategory().getId());
		product.setCategory(category);

		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		product.setHost(host);
		product.setUserB(username);
		product.setStatus(true);
		return ResponseEntity.ok(productService.save(product));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/del/{id}")
	public ResponseEntity<?> deleteById(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id,
			HttpServletRequest request) {
		ProductEntity product = productService.findById(id);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		product.setHost(host);
		product.setUserB(username);
		product.setStatus(false);
		return ResponseEntity.ok(productService.save(product));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/put/{id}")
	public ResponseEntity<?> changeStatusTrue(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id,
			HttpServletRequest request) {
		ProductEntity product = productService.findById(id);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		product.setHost(host);
		product.setUserB(username);
		product.setStatus(true);
		return ResponseEntity.ok(productService.save(product));
	}

}