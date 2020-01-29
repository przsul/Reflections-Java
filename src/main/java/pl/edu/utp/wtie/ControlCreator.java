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

	private Map<VFieldControl, List<Validator>> controlsValidators = new LinkedHashMap<>();
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

	public Map<VFieldControl, List<Validator>> getControlsValidators() {
		return controlsValidators;
	}

	public Map<Control, Field> getMap() {
		return map;
	}

	private List<Validator> reflectValidator(Field classField, Annotation[] annotations) {		
		List<Object> objects = new ArrayList<>();
		List<Validator> validators = new ArrayList<>();
		
		for(Annotation annotation : annotations) {
			try {
				Class<?> reflectValidator = Class.forName("pl.edu.utp.wtie.validation."
						+ annotation.annotationType().getSimpleName() + "Validator");
				Constructor<?> reflectConstructor = reflectValidator.getConstructor(Field.class);
				objects.add(reflectConstructor.newInstance(classField));
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}					
		}
		
		objects.forEach(object -> validators.add((Validator)object));
		
		return validators;
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

			Annotation[] annotations = classField.getAnnotations();

			// Create text input without validation
			if (annotations.length == 0) {
				fieldControl = new FieldControl(5, classField, object, pd);
				nodes.add(fieldControl);
				map.putAll(fieldControl.getMap());

			// Create text input with validation
			} else {
				vFieldControl = new VFieldControl(5, classField, object, pd);
				
				List<Validator> v = reflectValidator(classField, annotations);	
				controlsValidators.put(vFieldControl, v);
				vFieldControl.registerValidator(v);
				
				nodes.add(vFieldControl);
				map.putAll(vFieldControl.getMap());
			}
		});
	}
}
