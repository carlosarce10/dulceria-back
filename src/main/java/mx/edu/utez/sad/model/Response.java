package mx.edu.utez.sad.model;

import java.sql.Timestamp;

public class Response {

	private Timestamp timestamp;
	private boolean errorExists = false;
	private String message;
	private String code;
	
	public Response() {
	}

	public Response(Timestamp timestamp, boolean error, String message, String code) {
		this.timestamp = timestamp;
		this.errorExists = error;
		this.message = message;
		this.code = code;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isErrorExists() {
		return errorExists;
	}

	public void setErrorExists(boolean errorExists) {
		this.errorExists = errorExists;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
