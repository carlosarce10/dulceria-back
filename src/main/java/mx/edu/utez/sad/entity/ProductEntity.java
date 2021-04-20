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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El nombre del producto no puede ser nulo o estar vacío")
	@Size(min = 3, max = 40, message = "El nombre del producto tiene que tener entre 3 y 40 caracteres")
	@Column(nullable = false, length = 40)
	private String name;

	@Positive(message = "El precio de menudeo debe ser positivo")
	@Column(name = "retail_price", nullable = false)
	private double retailPrice;

	@Positive(message = "El precio de mayoreo debe ser positivo")
	@Column(name = "wholesale_price", nullable = false)
	private double wholesalePrice;

	@NotBlank(message = "El contenido neto del producto no puede ser nulo o vacío")
	@Size(min = 3, max = 20, message = "El contenido neto del producto tiene que tener entre 3 y 20 caracteres")
	@Column(name = "net_content", nullable = false, length = 20)
	private String netContent;

	private String image;

	private boolean status;

	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private BrandEntity brand;

	@NotNull(message = "El producto debe contener una categoría")
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private CategoryEntity category;

	private String userB;

	private String host;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public double getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public String getNetContent() {
		return netContent;
	}

	public void setNetContent(String netContent) {
		this.netContent = netContent;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public BrandEntity getBrand() {
		return brand;
	}

	public void setBrand(BrandEntity brand) {
		this.brand = brand;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
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
