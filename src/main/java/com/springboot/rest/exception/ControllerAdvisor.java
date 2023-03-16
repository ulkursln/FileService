package com.springboot.rest.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

	

	@ExceptionHandler(DeleteFromDBException.class)
	public ResponseEntity<String> deleteFromDBException(DeleteFromDBException deleteFromDBException) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(deleteFromDBException.getMessage());
	}

	@ExceptionHandler(EmptyFileException.class)
	public ResponseEntity<String> emptyFileException(EmptyFileException emptyFileException) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(emptyFileException.getMessage());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> entityNotFoundException(EntityNotFoundException entityNotFoundException) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityNotFoundException.getMessage());
	}

	@ExceptionHandler(FileFormatException.class)
	public ResponseEntity<String> fileFormatException(FileFormatException fileFormatException) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(fileFormatException.getMessage());

	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<String> fileNotFoundException(FileNotFoundException fileNotFoundException) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fileNotFoundException.getMessage());

	}

	@ExceptionHandler(FileStorageSecurityException.class)
	public ResponseEntity<String> fileStorageSecurityException(FileStorageSecurityException fileStorageSecurityException) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(fileStorageSecurityException.getMessage());

	}

	@ExceptionHandler(SaveToDBException.class)
	public ResponseEntity<String> saveToDBException(SaveToDBException saveToDBException) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(saveToDBException.getMessage());
	}

	@ExceptionHandler(StorageException.class)
	public ResponseEntity<String> storageException(StorageException storageException) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(storageException.getMessage());
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<String> handleDatabaseException(DataAccessException dataAccessException) {
		//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dataAccessException.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
		

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}
}