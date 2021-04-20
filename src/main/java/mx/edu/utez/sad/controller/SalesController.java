package mx.edu.utez.sad.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.sad.entity.CashboxEntity;
import mx.edu.utez.sad.entity.PackageDetailsEntity;
import mx.edu.utez.sad.entity.PackageEntity;
import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.entity.SalesDetailsEntity;
import mx.edu.utez.sad.entity.SalesEntity;
import mx.edu.utez.sad.entity.StockEntity;
import mx.edu.utez.sad.service.CashboxService;
import mx.edu.utez.sad.service.PackageDetailsService;
import mx.edu.utez.sad.service.PackageService;
import mx.edu.utez.sad.service.ProductService;
import mx.edu.utez.sad.service.SalesDetailsService;
import mx.edu.utez.sad.service.SalesService;
import mx.edu.utez.sad.service.StockService;
import mx.edu.utez.sad.service.UserService;

@RestController
@Validated
@RequestMapping("/sales")
public class SalesController {

	@Autowired
	private SalesService salesService;

	@Autowired
	private SalesDetailsService salesDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private CashboxService cashboxService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PackageService packageService;

	@Autowired
	private StockService stockService;

	@Autowired
	private PackageDetailsService packageDetailsService;

	private String username = "";
	private String host = "";
	private static final String LOCALIP = "0:0:0:0:0:0:0:1";
	private static final String ORIGINALIP = "127.0.0.1";
	private static final String PRODUCTIDCONST = "productId";
	private static final String TOTALSALECONST = "totalSale";

	@PreAuthorize("hasRole('CASHIER')")
	@PostMapping("/verify/sale")
	public ResponseEntity<?> verifySale(@RequestBody @Valid List<SalesDetailsEntity> saleDetails) {
		Map<String, Object> verifyObj = new HashMap<>();

		verifyObj.put("verify", true);
		List<Map<String, Object>> products = new ArrayList<>();

		for (SalesDetailsEntity sd : saleDetails) {

			if (sd.getProduct() != null) {
				ProductEntity product = productService.findById(sd.getProduct().getId());

				boolean flag = true;
				for (Map<String, Object> p : products) {
					Long productId = (Long) p.get(PRODUCTIDCONST);
					if (productId != null && productId.equals(product.getId())) {
						int totalSale = (int) p.get(TOTALSALECONST);
						totalSale += sd.getQuantity();

						p.put(TOTALSALECONST, totalSale);

						flag = false;
						break;
					}
				}

				if (flag) {
					Map<String, Object> productMap = new HashMap<>();
					productMap.put(PRODUCTIDCONST, product.getId());
					productMap.put("productName", product.getName());
					productMap.put(TOTALSALECONST, sd.getQuantity());
					products.add(productMap);
				}

			} else if (sd.getPackagee() != null) {
				PackageEntity packagee = packageService.findById(sd.getPackagee().getId());
				List<PackageDetailsEntity> packageDetails = packageDetailsService.findByPackagee(packagee);

				for (PackageDetailsEntity pd : packageDetails) {
					ProductEntity product = pd.getProduct();

					boolean flag = true;
					for (Map<String, Object> p : products) {
						Long productId = (Long) p.get(PRODUCTIDCONST);
						if (productId != null && productId.equals(product.getId())) {
							int totalSale = (int) p.get(TOTALSALECONST);
							totalSale += pd.getQuantityPackage() * sd.getQuantity();

							p.put(TOTALSALECONST, totalSale);

							flag = false;
							break;
						}
					}

					if (flag) {
						Map<String, Object> productMap = new HashMap<>();
						productMap.put(PRODUCTIDCONST, product.getId());
						productMap.put("productName", product.getName());
						productMap.put(TOTALSALECONST, pd.getQuantityPackage() * sd.getQuantity());
						products.add(productMap);

					}

				}

			}
		}

		for (Map<String, Object> p : products) {
			ProductEntity product = productService.findById(((Long) p.get(PRODUCTIDCONST)));
			List<StockEntity> stocks = stockService.findByProductOrderByDateExpireAsc(product);

			int totalStock = 0;

			for (StockEntity s : stocks) {
				totalStock += s.getQuantityStock();
			}

			p.put("totalStock", totalStock);
			p.put("enough", totalStock >= ((int) p.get(TOTALSALECONST)));

			if (!((boolean) p.get("enough"))) {
				verifyObj.put("verify", false);
			}

		}

		verifyObj.put("products", products);
		return ResponseEntity.ok(verifyObj);
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/list")
	public ResponseEntity<?> salesList() {
		return ResponseEntity.ok(salesService.getAll());
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/list/today")
	public ResponseEntity<?> salesOfTheDay() {
		return ResponseEntity.ok(salesService.salesOfTheDay());
	}

	@PreAuthorize("hasRole('CASHIER')")
	@PostMapping("/save")
	public ResponseEntity<?> saveSales(@RequestBody @Valid SalesEntity sales, HttpServletRequest request) {
		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}
		sales.setDate(new Date(System.currentTimeMillis()));
		sales.setCashbox(cashboxService.findById(sales.getCashbox().getId()));

		sales.setUser(userService.findByUsername(username));
		sales.setHost(host);
		sales.setUserB(username);

		salesService.save(sales);

		double currentAmount = sales.getCashbox().getAmount();
		currentAmount += sales.getTotal();
		sales.getCashbox().setAmount(currentAmount);

		int currentTotalSales = sales.getCashbox().getTotalSales();
		currentTotalSales++;
		sales.getCashbox().setTotalSales(currentTotalSales);

		cashboxService.update(sales.getCashbox());
		return ResponseEntity.ok(sales);
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@PostMapping("/details/save/{idSale}")
	public ResponseEntity<?> saveSalesDetails(@RequestBody @Valid List<SalesDetailsEntity> salesDetails,
			@PathVariable("idSale") @Positive(message = "El identificador de la venta no puede ser nulo") Long id,
			HttpServletRequest request) {

		username = SecurityContextHolder.getContext().getAuthentication().getName();
		host = request.getRemoteAddr();
		if (host.equals(LOCALIP)) {
			host = ORIGINALIP;
		}

		SalesEntity sale = salesService.findById(id);

		for (SalesDetailsEntity sd : salesDetails) {
			sd.setSales(sale);
			sd.setHost(host);
			sd.setUserB(username);

			if (sd.getProduct() != null) {
				sd.setProduct(productService.findById(sd.getProduct().getId()));
				List<StockEntity> stocks = stockService.findByProductOrderByDateExpireAsc(sd.getProduct());
				int saleQuantity = sd.getQuantity();

				for (StockEntity s : stocks) {
					int currentQuantityStock = s.getQuantityStock();

					if (currentQuantityStock <= saleQuantity) {
						saleQuantity -= currentQuantityStock;

						s.setHost(host);
						s.setUserB(username);
						stockService.delete(s.getId());

					} else {
						currentQuantityStock -= saleQuantity;
						s.setQuantityStock(currentQuantityStock);
						stockService.save(s);
						saleQuantity = 0;
					}

					if (saleQuantity == 0) {
						break;
					}

				}

				salesDetailsService.save(sd);
			} else if (sd.getPackagee() != null) {
				sd.setPackagee(packageService.findById(sd.getPackagee().getId()));
				List<PackageDetailsEntity> packagesDetails = packageDetailsService.findByPackagee(sd.getPackagee());

				for (PackageDetailsEntity pd : packagesDetails) {
					int saleQuantity = sd.getQuantity();

					List<StockEntity> stocks = stockService.findByProductOrderByDateExpireAsc(pd.getProduct());
					int packageQuantity = pd.getQuantityPackage() * saleQuantity;

					for (StockEntity s : stocks) {
						int currentQuantityStock = s.getQuantityStock();

						if (currentQuantityStock <= packageQuantity) {
							packageQuantity -= currentQuantityStock;

							s.setHost(host);
							s.setUserB(username);
							stockService.delete(s.getId());

						} else {
							currentQuantityStock -= packageQuantity;
							s.setQuantityStock(currentQuantityStock);

							stockService.save(s);
							packageQuantity = 0;
						}

						if (packageQuantity == 0) {
							break;
						}

					}

					salesDetailsService.save(sd);
				}

			}

		}

		return ResponseEntity.ok(salesDetailsService.findBySale(sale));
	}

	@PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findById(
			@PathVariable("id") @Positive(message = "El identificador de la venta no puede ser nulo") Long id) {
		SalesEntity sale = salesService.findById(id);
		List<SalesDetailsEntity> salesDetails = salesDetailsService.findBySale(sale);

		Map<String, Object> map = new HashMap<>();
		map.put("sale", sale);
		map.put("saleDetails", salesDetails);

		return ResponseEntity.ok(map);
	}

	@PreAuthorize("hasRole('CASHIER')")
	@GetMapping("/list/cashbox/{id}")
	public ResponseEntity<?> findByCashbox(
			@PathVariable("id") @Positive(message = "El identificador de la venta no puede ser nulo") Long id) {
		CashboxEntity cashbox = cashboxService.findById(id);
		return ResponseEntity.ok(salesService.findByCashbox(cashbox));
	}

}
