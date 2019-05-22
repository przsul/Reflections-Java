package pl.edu.utp.wtie.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import pl.edu.utp.wtie.annotations.PESEL;

public class PESELValidator implements Validator {

	private boolean valid;
	private Field field;
	private Annotation annotation;
	private PESEL pesel;

	public PESELValidator(Field field) {
		try {
			this.field = field;
			annotation = field.getAnnotation(PESEL.class);
			pesel = (PESEL) annotation;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	private boolean isNumeric(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void validate(String PESEL) {
		char[] digits = PESEL.toCharArray();
		
		if (annotation != null) {
			if(!isNumeric(PESEL)) {
				this.valid = false;
				return;
			}
			
			if(PESEL.length() != 11) {
				this.valid = false;
				return;
			}

			int sum = 9 * Character.digit(digits[0], 10) +
 					  7 * Character.digit(digits[1], 10) +
 					  3 * Character.digit(digits[2], 10) +
 					  1 * Character.digit(digits[3], 10) +
 					  9 * Character.digit(digits[4], 10) +
 					  7 * Character.digit(digits[5], 10) +
 					  3 * Character.digit(digits[6], 10) +
 					  1 * Character.digit(digits[7], 10) +
 					  9 * Character.digit(digits[8], 10) +
 					  7 * Character.digit(digits[9], 10);
			
			sum %= 10;
			
			if(sum == Character.digit(digits[10], 10))
				this.valid = true;
				
		}
	}

	@Override
	public String getMessage() {
		return pesel.message();
	}

	@Override
	public boolean isValid() {
		return valid;
	}
}
