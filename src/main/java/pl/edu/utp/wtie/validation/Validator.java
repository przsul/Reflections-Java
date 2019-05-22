package pl.edu.utp.wtie.validation;

public interface Validator {
	void validate(String value);
	String getMessage();
	boolean isValid();
}
