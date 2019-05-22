package pl.edu.utp.wtie.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

import pl.edu.utp.wtie.annotations.Regexp;

public class RegexpValidator implements Validator {
	
	private boolean valid;
	private Field field;
	private Annotation annotation;
	private Regexp regexp;
	
	public RegexpValidator(Field field) {
		try {
			this.field = field;
			annotation = field.getAnnotation(Regexp.class);
			regexp = (Regexp)annotation;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void validate(String value) {
		if(annotation != null)
			if(Pattern.compile(regexp.pattern()).matcher(value).matches())
				this.valid = true;
			else
				this.valid = false;
	}

	@Override
	public String getMessage() {
		return regexp.message();
	}

	@Override
	public boolean isValid() {
		return this.valid;
	}
}
