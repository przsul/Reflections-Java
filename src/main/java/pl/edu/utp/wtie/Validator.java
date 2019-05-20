package pl.edu.utp.wtie;

public interface Validator {
	void validate(String value);
	String getMessage();
	boolean isValid();
}
