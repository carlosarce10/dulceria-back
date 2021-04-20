package mx.edu.utez.sad.controller;

import javax.servlet.http.HttpServletRequest;
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

import mx.edu.utez.sad.entity.CashboxEntity;
import mx.edu.utez.sad.service.CashboxService;
import mx.edu.utez.sad.service.UserService;

@RestController
@Validated
@RequestMapping("/cashbox")
public class CashboxController {

	@Autowired
	private CashboxService cbs;

	@Autowired
	private UserService us;

	private String username = "";
	private String host = "";
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/list")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(cbs.findAll());
	}

	@PreAuthorize("hasRole('CASHIER')")
	@PostMapping("/openBox")
	public ResponseEntity<?> save(@RequestBody CashboxEntity cashbox, HttpServletRequest request) {
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		cashbox.setHost(host);
		cashbox.setUserB(username);
		return ResponseEntity.ok(cbs.save(cashbox));
	}

	@PreAuthorize("hasRole('CASHIER')")
	@PostMapping("/open/box")
	public ResponseEntity<?> saveTemp(@RequestBody CashboxEntity cashbox, HttpServletRequest request) {
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		cashbox.setHost(host);
		cashbox.setUserB(username);
		cashbox.setUser(us.findByUsername(username));
		return ResponseEntity.ok(cbs.save(cashbox));
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id)  {
		return ResponseEntity.ok(cbs.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findByCashier/{id}")
	public ResponseEntity<?> findByUser(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id)  {
		return ResponseEntity.ok(cbs.findByUser(id));
	}

	@PreAuthorize("hasRole('CASHIER')")
	@GetMapping("/closeBox/{id}")
	public ResponseEntity<?> closeBox(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id,
			HttpServletRequest request)  {
		CashboxEntity cashbox = cbs.findById(id);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		cashbox.setHost(host);
		cashbox.setUserB(username);
		return ResponseEntity.ok(cbs.closeCashBox(cashbox));
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/makeWithdrawal/{id}/{quantity}")
	public ResponseEntity<?> makeWithdrawal(
			@PathVariable("id") @Positive(message = "El identificador no debe ser negativo") Long id,
			@PathVariable("quantity") @Positive(message = "La cantidad de retiro no debe ser negativa") double quantity,
			HttpServletRequest request)  {
		CashboxEntity cash = cbs.findById(id);
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		cash.setHost(host);
		cash.setUserB(username);
		return ResponseEntity.ok(cbs.makeWithdrawal(cash, quantity));
	}

}