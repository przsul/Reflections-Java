package pl.edu.utp.wtie;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.Control;
import pl.edu.utp.wtie.validation.Validator;

public class ControlCreator {

	private Class<?> reflectClass;
	private Object object;
	private List<Field> classFields;

	private VFieldControl vFieldControl;
	private FieldControl fieldControl;

	private List<Validator> validators = new ArrayList<>();
	private List<Node> nodes = new ArrayList<>();
	private Map<Control, Field> map = new LinkedHashMap<>();

	public ControlCreator(Class<?> reflectClass, Object object, List<Field> classFields) {
		this.reflectClass = reflectClass;
		this.object = object;
		this.classFields = classFields;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public List<Validator> getValidators() {
		return validators;
	}

	public Map<Control, Field> getMap() {
		return map;
	}

	private Validator reflectValidator(Field classField, Annotation[] annotation) {		
		Object object = null;
		try {
			Class<?> reflectValidator = Class.forName("pl.edu.utp.wtie.validation."
					+ annotation[0].annotationType().getSimpleName() + "Validator");
			Constructor<?> reflectConstructor = reflectValidator.getConstructor(Field.class);
			object = reflectConstructor.newInstance(classField);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}		
		
		return (Validator)object;
	}
	
	public void createControls() {
		classFields.forEach(classField -> {

			// PropertyDescriptor for access to getters and setters
			PropertyDescriptor pd = null;
			try {
				pd = new PropertyDescriptor(classField.getName(), reflectClass);
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}

			Annotation[] annotation = classField.getAnnotations();

			// Create text input without validation
			if (annotation.length == 0) {
				fieldControl = new FieldControl(5, classField, object, pd);
				nodes.add(fieldControl);
				map.putAll(fieldControl.getMap());

			// Create text input with validation
			} else {
				vFieldControl = new VFieldControl(5, classField, object, pd);

				Validator v = reflectValidator(classField, annotation);	
				validators.add(v);
				vFieldControl.registerValidator(v);
				
				nodes.add(vFieldControl);
				map.putAll(vFieldControl.getMap());
			}
		});
	}
}
