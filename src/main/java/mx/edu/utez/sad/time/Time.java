package mx.edu.utez.sad.time;

import java.sql.Timestamp;

public class Time {
	
	public static Timestamp getTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	private Time() {
	}
	
	
}
