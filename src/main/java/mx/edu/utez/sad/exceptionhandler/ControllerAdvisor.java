package mx.edu.utez.sad.exceptionhandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import mx.edu.utez.sad.model.Response;
import mx.edu.utez.sad.time.Time;

import org.springframework.web.HttpRequestMethodNotSupportedException;

@RestControllerAdvice
public class ControllerAdvisor {

	private Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

	private Response result;

	private String errorMsg = "";
	private static final String SEPARADOR = "-";
	private static final String CODIGOERR = "Código de error: ";
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> exception() {
		result = new Response(Time.getTime(), true, "Error en el sistema, intentelo más tarde.", "SEJ-00");
		errorMsg = "Excepción general.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<Response> arithmeticException() {
		result = new Response(Time.getTime(), true, "Error de tipo aritmético.", "SEJ-01");
		errorMsg = "Error de tipo aritmético.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<Response> indexOutOfBoundsException() {
		result = new Response(Time.getTime(), true, "Elemento no aceptado.", "SEJ-02");
		errorMsg = "Elemento fuera de rango.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ClassCastException.class)
	public ResponseEntity<Response> classCastException() {
		result = new Response(Time.getTime(), true, "Error de código.", "SEJ-03");
		errorMsg = "Error al castear un objeto.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NegativeArraySizeException.class)
	public ResponseEntity<Response> negativeArraySizeException() {
		result = new Response(Time.getTime(), true, "Error de código.", "SEJ-04");
		errorMsg = "Tamaño de arreglo no permitido.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Response> nullPointerException() {
		result = new Response(Time.getTime(), true, "La información enviada no es la esperada.", "SEJ-05");
		errorMsg = "El elemento al que se intenta acceder se encuentra como nulo.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Response> numberFormatException() {
		result = new Response(Time.getTime(), true, "", "SEJ-06");
		errorMsg = "La información enviada no es la esperada.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<Response> stringIndexOutOfBoundsException() {
		result = new Response(Time.getTime(), true, "Error al procesar la información.", "SEJ-07");
		errorMsg = "Elemento fuera de rango para una cadena.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EmptyStackException.class)
	public ResponseEntity<Response> emptyStackException() {
		result = new Response(Time.getTime(), true, "Error al procesar la información.", "SEJ-08");
		errorMsg = "La pila a la que se intenta acceder está vacía.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<Response> securityException() {
		result = new Response(Time.getTime(), true, "Ocurrió una violación de seguridad.", "SEJ-09");
		errorMsg = "Ocurrió una violación de seguridad.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Response> illegalStateException() {
		result = new Response(Time.getTime(), true,
				"El sistema no se encuentra en un estado apropiado para la operación solicitada.", "SEJ-10");
		errorMsg = "El sistema no se encuentra en un estado apropiado para la operación solicitada.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ClassNotFoundException.class)
	public ResponseEntity<Response> classNotFoundException() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-12");
		errorMsg = "La clase a la que se intenta acceder no se encuentra en la ruta especificada.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoClassDefFoundError.class)
	public ResponseEntity<Response> noClassDefFoundError() {
		result = new Response(Time.getTime(), true, "Error de tipo interno", "SEJ-13");
		errorMsg = "La clase a la que intenta acceder el método no se encuentra en la ruta especificada.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AssertionError.class)
	public ResponseEntity<Response> assertionError() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-14");
		errorMsg = "Error de afirmación.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalAccessException.class)
	public ResponseEntity<Response> illegalAccessException() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-15");
		errorMsg = "No se pudo acceder al método y/o campo solicitado.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OutOfMemoryError.class)
	public ResponseEntity<Response> outOfMemoryError() {
		result = new Response(Time.getTime(), true, "No hay suficiente espacio para continuar con el proceso.",
				"SEJ-16");
		errorMsg = "No hay suficiente espacio para continuar con el proceso.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InstantiationException.class)
	public ResponseEntity<Response> instantiationException() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-17");
		errorMsg = "Error al intentar instanciar una clase.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InterruptedException.class)
	public ResponseEntity<Response> interruptedException() {
		result = new Response(Time.getTime(), true, "El proceso ha sido interrumpido.", "SEJ-18");
		errorMsg = "Hilo interrumpido.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ArrayStoreException.class)
	public ResponseEntity<Response> arrayStoreException() {
		result = new Response(Time.getTime(), true, "Error al procesar la información.", "SEJ-19");
		errorMsg = "El objeto que se intenta almacenar no es el que espera el arreglo.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Response> illegalArgumentException() {
		result = new Response(Time.getTime(), true, "La información enviada no es la esperada.", "SEJ-20");
		errorMsg = "El valor del argumento es inválido.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ExceptionInInitializerError.class)
	public ResponseEntity<Response> exceptionInInitializerError() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-21");
		errorMsg = "Error en un inicializador estático.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(StackOverflowError.class)
	public ResponseEntity<Response> stackOverflowError() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-22");
		errorMsg = "Demasiadas llamadas recursivas al método.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Response> noSuchElementException() {
		result = new Response(Time.getTime(), true, "El elemento solicitado no se encontró.", "SEJ-23");
		errorMsg = "El elemento solicitado no se encontró.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Response> constraintViolationException() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-24");
		errorMsg = "Violación de restricciones.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoSuchMethodException.class)
	public ResponseEntity<Response> noSuchMethodException() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-25");
		errorMsg = "Método no encontrado.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<Response> iOException() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-26");
		errorMsg = "Error en una operación de entrada/salida.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<Response> fileNotFoundException() {
		result = new Response(Time.getTime(), true, "Error de tipo interno.", "SEJ-27");
		errorMsg = "Error al intentar abrir el archivo especificado.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<Response> sQLException() {
		result = new Response(Time.getTime(), true, "Error en la base de datos.", "SEJ-28");
		errorMsg = "Error en la base de datos.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Response> sQLIntegrityConstraintViolationException() {
		result = new Response(Time.getTime(), true, "Error en la base de datos.", "SEJ-29");
		errorMsg = "Errores con primary key, foreign key y/o unique key.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CannotGetJdbcConnectionException.class)
	public ResponseEntity<Response> cannotGetJdbcConnectionException() {
		result = new Response(Time.getTime(), true, "Error en la base de datos.", "SES-01");
		errorMsg = "Error de conexión con la base de datos.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> methodargumentnotvalidexception() {
		result = new Response(Time.getTime(), true, "La información enviada no es la esperada.", "SES-02");
		errorMsg = "Ocurrió un error con la validación de datos.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Response> httpRequestMethodNotSupportedException() {
		result = new Response(Time.getTime(), true, "Error de envío.", "SES-03");
		errorMsg = "El método de solicitud no es compatible";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Response> methodArgumentTypeMismatchException() {
		result = new Response(Time.getTime(), true, "La información enviada no es la esperada.", "SES-04");
		errorMsg = "El dato enviado no coincide con el tipo de dato esperado";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Response> accessDeniedException() {
		result = new Response(Time.getTime(), true, "Acceso denegado", "SES-05");
		errorMsg = "Acceso denegado";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Response> dataIntegrityViolationException() {
		result = new Response(Time.getTime(), true,
				"Error en la base de datos. Verifique que los datos no estén repetidos", "SES-06");
		errorMsg = "Error en la base de datos.";
		logger.error(CODIGOERR + result.getCode() + SEPARADOR + errorMsg);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
