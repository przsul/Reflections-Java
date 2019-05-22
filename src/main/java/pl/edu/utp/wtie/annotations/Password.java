package pl.edu.utp.wtie.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Password {
	public String pattern() default "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#@$!%*?&])[A-Za-z\\d#@$!%*?&]{8,}$";
	public String message() default "Password should contains\n"
			+ "- Minimum 8 characters\n"
			+ "- At least one uppercase letter\n"
			+ "- At least one lowercase letter\n"
			+ "- At least one digit\n"
			+ "- At least one special character (# @ $ ! % * ? &)";
}
