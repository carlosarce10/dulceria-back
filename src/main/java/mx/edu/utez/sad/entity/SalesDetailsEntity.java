package mx.edu.utez.sad.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "sales_details")
public class SalesDetailsEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Positive(message = "La cantidad tiene que ser mayor a cero")
	@Column(nullable = false)
	private int quantity;
	
	@Positive(message = "El subtotal tiene que ser mayor a cero")
	@Column(nullable = false)
	private double subtotal;
	
	@PositiveOrZero(message = "El descuento no puede ser negativo")
	@Column(nullable = false)
	private double discount;
	
	@PositiveOrZero(message = "El total descontado no puede ser negativo")
	@Column(name = "discount_amount", nullable = false)
	private double discountAmount;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = true)
	private ProductEntity product;
	
	@ManyToOne
	@JoinColumn(name = "package_id", nullable = true)
	private PackageEntity packagee;
	
	@ManyToOne
	@JoinColumn(name = "sales_id", nullable = false)
	private SalesEntity sales;
	
	private String userB;

	private String host;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public SalesEntity getSales() {
		return sales;
	}

	public void setSales(SalesEntity sales) {
		this.sales = sales;
	}

	public PackageEntity getPackagee() {
		return packagee;
	}

	public void setPackagee(PackageEntity packagee) {
		this.packagee = packagee;
	}
	
	public String getUserB() {
		return userB;
	}

	public void setUserB(String userB) {
		this.userB = userB;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}