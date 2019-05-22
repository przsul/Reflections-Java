package pl.edu.utp.wtie.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

import pl.edu.utp.wtie.annotations.Password;

public class PasswordValidator implements Validator {
	
	private boolean valid;
	private Field field;
	private Annotation annotation;
	private Password password;
	
	public PasswordValidator(Field field) {
		try {
			this.field = field;
			annotation = field.getAnnotation(Password.class);
			password = (Password)annotation;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void validate(String value) {
		if(annotation != null)
			if(Pattern.compile(password.pattern()).matcher(value).matches())
				this.valid = true;
			else
				this.valid = false;
	}

	@Override
	public String getMessage() {
		return password.message();
	}

	@Override
	public boolean isValid() {
		return this.valid;
	}
}
