package mx.edu.utez.sad.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "cashbox")
public class CashboxEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "start_time", nullable = false)
	private String startTime;

	@Column(name = "end_time")
	private String endTime;

	@Positive(message = "El número de caja debe ser positivo")
	@Column(name = "cashbox_number", nullable = false)
	private int cashboxNumber;

	@PositiveOrZero(message = "El monto inicial debe ser mayor a cero")
	@Column(name = "initial_amount", nullable = false)
	private double initialAmount;

	@Column(name = "amount", nullable = false)
	private double amount;

	@Column(name = "withdrawal", nullable = false)
	private double withdrawal;

	@Column(name = "total_sales", nullable = false)
	private int totalSales;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	private String userB;

	private String host;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getWithdrawal() {
		return withdrawal;
	}

	public void setWithdrawal(double withdrawal) {
		this.withdrawal = withdrawal;
	}

	public int getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getCashboxNumber() {
		return cashboxNumber;
	}

	public void setCashboxNumber(int cashboxNumber) {
		this.cashboxNumber = cashboxNumber;
	}

	public double getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(double initialAmount) {
		this.initialAmount = initialAmount;
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
