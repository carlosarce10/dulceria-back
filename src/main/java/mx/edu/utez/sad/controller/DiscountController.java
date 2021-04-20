package mx.edu.utez.sad.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.sad.entity.BrandEntity;
import mx.edu.utez.sad.entity.CategoryEntity;
import mx.edu.utez.sad.entity.DiscountEntity;
import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.service.BrandService;
import mx.edu.utez.sad.service.CategoryService;
import mx.edu.utez.sad.service.DiscountService;
import mx.edu.utez.sad.service.ProductService;

@RestController
@Validated
@RequestMapping("/discount")
public class DiscountController {

	@Autowired
	private DiscountService ds;

	@Autowired
	private BrandService bs;

	@Autowired
	private CategoryService cs;

	@Autowired
	private ProductService ps;

	private String username = "";
	private String host = "";
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";

	@PreAuthorize("hasRole('CASHIER')")
	@GetMapping("/cashier/list")
	public ResponseEntity<?> getAllDiscountsCashier() {
		List<DiscountEntity> discounts = ds.findAll();

		Map<String, Object> map = new HashMap<>();

		List<DiscountEntity> dProductos = new ArrayList<>();
		List<DiscountEntity> dMarcas = new ArrayList<>();
		List<DiscountEntity> dCategorias = new ArrayList<>();

		for (DiscountEntity d : discounts) {
			if (d.getProduct() != null)
				dProductos.add(d);

			if (d.getBrand() != null)
				dMarcas.add(d);

			if (d.getCategory() != null)
				dCategorias.add(d);
		}

		map.put("products", dProductos);
		map.put("brands", dMarcas);
		map.put("categories", dCategorias);

		return ResponseEntity.ok(map);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(ds.findAll());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody @Valid DiscountEntity discount, HttpServletRequest request) {

		if (discount.getBrand() != null) {
			discount.setBrand(bs.findById(discount.getBrand().getId()));
		} else if (discount.getCategory() != null) {
			discount.setCategory(cs.findById(discount.getCategory().getId()));
		} else if (discount.getProduct() != null) {
			discount.setProduct(ps.findById(discount.getProduct().getId()));
		}

		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		discount.setHost(host);
		discount.setUserB(username);
		return ResponseEntity.ok(ds.save(discount));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/has/product/{id}")
	public ResponseEntity<?> productHasDiscount(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo.") Long id) {
		ProductEntity product = ps.findById(id);
		return ResponseEntity.ok(ds.productHasDiscount(product));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/has/category/{id}")
	public ResponseEntity<?> categoryHasDiscount(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo.") Long id) {
		CategoryEntity category = cs.findById(id);
		return ResponseEntity.ok(ds.categoryHasDiscount(category));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/has/brand/{id}")
	public ResponseEntity<?> brandHasDiscount(
			@PathVariable("id") @Positive(message = "El identificador debe ser positivo.") Long id) {
		BrandEntity brand = bs.findById(id);
		return ResponseEntity.ok(ds.brandHasDiscount(brand));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(
			@PathVariable("id") @Positive(message = "El identificador del descuento no puede ser nulo") Long id) {
		return ResponseEntity.ok(ds.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/del/{id}")
	public ResponseEntity<?> deleteById(
			@PathVariable("id") @Positive(message = "El identificador del descuento no puede ser nulo") Long id,
			HttpServletRequest request) {
		DiscountEntity discount = ds.findById(id);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		discount.setHost(host);
		discount.setUserB(username);
		ds.save(discount);
		return ResponseEntity.ok(ds.deleteById(id));
	}

}
