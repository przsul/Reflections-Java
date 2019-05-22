package application;

import pl.edu.utp.wtie.annotations.PESEL;
import pl.edu.utp.wtie.annotations.Password;
import pl.edu.utp.wtie.annotations.Regexp;

public class Person {
	
	@PESEL(message="Type your PESEL.")
	private String PESEL;
	
	private String name;
	
	private String lastName;
	
	private Boolean male;
	
	private Boolean female;
	
	@Password
	private String password;
	
	@Regexp(pattern="^\\S+@\\S+\\.\\S+$", message="Type email address.")
	private String email;
	
	@Regexp(pattern="\\d{2}-\\d{3}", message="Type postal code.")
	private String postalCode;
	
	private String text;
	
	public Person() {}
	
	public String getPESEL() {
		return PESEL;
	}
	
	public void setPESEL(String PESEL) {
		this.PESEL = PESEL;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public Boolean getMale() {
		return male;
	}

	public void setMale(Boolean male) {
		this.male = male;
	}

	public Boolean getFemale() {
		return female;
	}

	public void setFemale(Boolean female) {
		this.female = female;
	}
}
