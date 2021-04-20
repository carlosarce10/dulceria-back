package mx.edu.utez.sad.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.entity.StockEntity;
import mx.edu.utez.sad.service.ProductService;
import mx.edu.utez.sad.service.StockService;

@RestController
@Validated
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@Autowired
	private ProductService productService;

	private String username = "";
	private String host = "";
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/list")
	public ResponseEntity<?> stocksList() {
		return ResponseEntity.ok(stockService.getAll());
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/list/expired")
	public ResponseEntity<?> stocksListExpiredStock() {
		return ResponseEntity.ok(stockService.findByExpiredStock());
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/list/notExpired")
	public ResponseEntity<?> stocksListNotExpiredStock() {
		return ResponseEntity.ok(stockService.findByNotExpiredStock());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> saveStock(@RequestBody @Valid StockEntity stock, HttpServletRequest request) {
		ProductEntity product = productService.findById(stock.getProduct().getId());
		stock.setProduct(product);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		stock.setHost(host);
		stock.setUserB(username);
		return ResponseEntity.ok(stockService.save(stock));
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id) {
		return ResponseEntity.ok(stockService.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id,
			HttpServletRequest request) {
		StockEntity stock = stockService.findById(id);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		stock.setHost(host);
		stock.setUserB(username);
		stockService.save(stock);
		return ResponseEntity.ok(stockService.delete(id));
	}
}
