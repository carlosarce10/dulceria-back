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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "package_details")
public class PackageDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Positive(message = "La cantidad de productos en paquete no puede ser negativo o cero")
	@Column(name = "quantity_package", nullable = false)
	private int quantityPackage;

	@NotNull(message = "El producto no puede ser nulo")
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;

	@ManyToOne
	@JoinColumn(name = "packagee_id", nullable = false)
	private PackageEntity packagee;

	private String userB;

	private String host;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantityPackage() {
		return quantityPackage;
	}

	public void setQuantityPackage(int quantityPackage) {
		this.quantityPackage = quantityPackage;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
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