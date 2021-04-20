package mx.edu.utez.sad.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_book")
public class LogBookEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "table_execute")
	private String tableExecute;
	private String action;

	@Column(name = "previous_log", columnDefinition = "TEXT")
	private String previousLog;
	@Column(name = "next_log", columnDefinition = "TEXT")
	private String nextLog;
	private String user;
	private String date;
	private String host;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableExecute() {
		return tableExecute;
	}

	public void setTableExecute(String tableExecute) {
		this.tableExecute = tableExecute;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPreviousLog() {
		return previousLog;
	}

	public void setPreviousLog(String previousLog) {
		this.previousLog = previousLog;
	}

	public String getNextLog() {
		return nextLog;
	}

	public void setNextLog(String nextLog) {
		this.nextLog = nextLog;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
