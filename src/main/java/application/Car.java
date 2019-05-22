package application;

import pl.edu.utp.wtie.annotations.Regexp;

public class Car {
	
	@Regexp(pattern="\\d{2}-\\d{3}", message="Type car brand.")
	private String brand = "Volkswagen";
	
	@Regexp(pattern="\\d{2}-\\d{3}", message="Type car brand.")
	private String model = "Passat";
	
	private int yearOfProduction = 2006;
	
	private float mileage = 6.55f;
	
	private boolean diesel = true;
	
	private boolean petrol = false;
	
	//@Regexp(pattern="\\d{2}-\\d{3}", message="Type car brand.")
	private String text = "This is very popular car.";
	
	private char segment = 'D';
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}

	public int getYearOfProduction() {
		return yearOfProduction;
	}
	
	public void setYearOfProduction(int yearOfProduction) {
		this.yearOfProduction = yearOfProduction;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public boolean isDiesel() {
		return diesel;
	}

	public void setDiesel(boolean diesel) {
		this.diesel = diesel;
	}

	public float getMileage() {
		return mileage;
	}

	public void setMileage(float mileage) {
		this.mileage = mileage;
	}

	public char getSegment() {
		return segment;
	}

	public void setSegment(char segment) {
		this.segment = segment;
	}

	public boolean isPetrol() {
		return petrol;
	}

	public void setPetrol(boolean petrol) {
		this.petrol = petrol;
	}
}
