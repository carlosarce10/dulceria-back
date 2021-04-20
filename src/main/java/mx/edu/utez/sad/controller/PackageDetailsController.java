package mx.edu.utez.sad.controller;

import java.util.List;

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

import mx.edu.utez.sad.entity.PackageDetailsEntity;
import mx.edu.utez.sad.entity.PackageEntity;
import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.service.PackageDetailsService;
import mx.edu.utez.sad.service.PackageService;
import mx.edu.utez.sad.service.ProductService;

@RestController
@Validated
@RequestMapping("/packageDetails")
public class PackageDetailsController {

	@Autowired
	private PackageDetailsService pds;

	@Autowired
	private ProductService pos;

	@Autowired
	private PackageService pas;

	private String username = "";
	private String host = "";
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(pds.findAll());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save/many/{idPackage}")
	public ResponseEntity<?> saveMany(@RequestBody @Valid List<PackageDetailsEntity> packagesDetails,
			@PathVariable("idPackage") @Positive(message = "El identificador del paquete no puede ser negativo") Long idPackage,
			HttpServletRequest request) {
		PackageEntity packagee = pas.findById(idPackage);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}

		for (PackageDetailsEntity packageDetails : packagesDetails) {
			ProductEntity product = pos.findById(packageDetails.getProduct().getId());
			packageDetails.setProduct(product);
			packageDetails.setPackagee(packagee);
			packageDetails.setHost(host);
			packageDetails.setUserB(username);
			pds.save(packageDetails);
		}

		return ResponseEntity.ok(pds.findByPackagee(packagee));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save/many/edit/{idPackage}")
	public ResponseEntity<?> saveManyEdit(@RequestBody @Valid List<PackageDetailsEntity> packagesDetails,
			@PathVariable("idPackage") @Positive(message = "El identificador del paquete no puede ser negativo") Long idPackage,
			HttpServletRequest request) {
		PackageEntity packagee = pas.findById(idPackage);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}

		List<PackageDetailsEntity> packs = pds.findByPackagee(packagee);
		for (PackageDetailsEntity pack : packs) {
			pack.setHost(host);
			pack.setUserB(username);
			pds.save(pack);
		}
		packagee.setHost(host);
		packagee.setUserB(username);
		pds.deleteByPackagee(packagee);

		for (PackageDetailsEntity packageDetails : packagesDetails) {
			packageDetails.setId(null);
			ProductEntity product = pos.findById(packageDetails.getProduct().getId());
			packageDetails.setProduct(product);
			packageDetails.setPackagee(packagee);
			packageDetails.setHost(host);
			packageDetails.setUserB(username);
			pds.save(packageDetails);
		}

		return ResponseEntity.ok(pds.findByPackagee(packagee));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody @Valid PackageDetailsEntity packageDetails, HttpServletRequest request) {
		PackageEntity packagee = pas.findById(packageDetails.getPackagee().getId());
		ProductEntity product = pos.findById(packageDetails.getProduct().getId());

		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		packageDetails.setHost(host);
		packageDetails.setUserB(username);
		packageDetails.setPackagee(packagee);
		packageDetails.setProduct(product);

		return ResponseEntity.ok(pds.save(packageDetails));
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findByPackage(
			@PathVariable("id") @Positive(message = "El identificador del paquete no puede ser negativo") Long id) {
		PackageEntity packagee = pas.findById(id);
		return ResponseEntity.ok(pds.findByPackagee(packagee));
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(
			@PathVariable("id") @Positive(message = "El identificador del detalle no puede ser negativo") Long id){
		return ResponseEntity.ok(pds.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/del/{id}")
	public ResponseEntity<?> deleteById(
			@PathVariable("id") @Positive(message = "El identificador del detalle no puede ser negativo") Long id,
			HttpServletRequest request) {
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		PackageDetailsEntity pack = pds.findById(id);
		pack.setHost(host);
		pack.setUserB(username);
		pds.save(pack);
		return ResponseEntity.ok(pds.deleteById(id));
	}

}