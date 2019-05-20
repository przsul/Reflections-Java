package pl.edu.utp.wtie.controllers;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import pl.edu.utp.wtie.App;
import pl.edu.utp.wtie.FieldControl;
import pl.edu.utp.wtie.InfoTextArea;

public class AppController {
	
	@FXML
	private VBox vBox;
	
	@FXML
	private Button saveButton;
	
	@FXML
	private Button createButton;
	
	@FXML
	private TextField classTextField;
    
    private Class<?> reflectClass;
	private Constructor<?> constructor;
	private Object object;
    private List<Field> fields;
    private Map<Control, Field> map = new LinkedHashMap<>();
    private FieldControl fieldControl;
    private InfoTextArea infoTextArea;
    
    @FXML
    private void saveObject() {
    	
    	infoTextArea.clear();
    	
    	map.forEach((input, field) -> {
    		try {
    			
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), reflectClass);
				Class<?>[] pTypes = pd.getWriteMethod().getParameterTypes();
				
				for(Class<?> pType : pTypes) {
					switch(pType.getName()) {
						case "boolean":
							pd.getWriteMethod().invoke(object, ((CheckBox)input).isSelected());
							break;
						case "java.lang.Boolean":
							pd.getWriteMethod().invoke(object, ((CheckBox)input).isSelected());
							break;
						case "byte":
							pd.getWriteMethod().invoke(object, Byte.parseByte(((TextInputControl)input).getText()));
							break;
						case "java.lang.Byte":
							pd.getWriteMethod().invoke(object, Byte.valueOf(((TextInputControl)input).getText()));
							break;
						case "short":
							pd.getWriteMethod().invoke(object, Short.parseShort(((TextInputControl)input).getText()));
							break;
						case "java.lang.Short":
							pd.getWriteMethod().invoke(object, Short.valueOf(((TextInputControl)input).getText()));
							break;							
						case "int":
							pd.getWriteMethod().invoke(object, Integer.parseInt(((TextInputControl)input).getText()));
							break;
						case "java.lang.Integer":
							pd.getWriteMethod().invoke(object, Integer.valueOf(((TextInputControl)input).getText()));
							break;
						case "long":
							pd.getWriteMethod().invoke(object, Long.parseLong(((TextInputControl)input).getText()));
							break;
						case "java.lang.Long":
							pd.getWriteMethod().invoke(object, Long.valueOf(((TextInputControl)input).getText()));
							break;
						case "float":
							pd.getWriteMethod().invoke(object, Float.parseFloat(((TextInputControl)input).getText()));
							break;
						case "java.lang.Float":
							pd.getWriteMethod().invoke(object, Float.valueOf(((TextInputControl)input).getText()));
							break;
						case "double":
							pd.getWriteMethod().invoke(object, Double.parseDouble(((TextInputControl)input).getText()));
							break;
						case "java.lang.Double":
							pd.getWriteMethod().invoke(object, Double.valueOf(((TextInputControl)input).getText()));
							break;
						case "char":
							pd.getWriteMethod().invoke(object, ((TextInputControl)input).getText().charAt(0));
							break;
						case "java.lang.Character":
							pd.getWriteMethod().invoke(object, ((TextInputControl)input).getText().charAt(0));
							break;
						default:
							pd.getWriteMethod().invoke(object, ((TextInputControl)input).getText());	
					}
				}
				
				infoTextArea.appendText(field.getName() + " = " + pd.getReadMethod().invoke(object).toString() + '\n');
				
			} catch (IntrospectionException | IllegalAccessException | InvocationTargetException | NumberFormatException e) {
				String tmp = infoTextArea.getText();
				infoTextArea.clear();
				infoTextArea.appendText("The property named " + field.getName() + " can't be changed!\n" + tmp);
			}
    	});
    }
	
	@FXML
	private void createObject() {
		try {
			reflectClass = Class.forName(classTextField.getText());
			constructor = reflectClass.getConstructor();
			object = constructor.newInstance();
			
			fields = Arrays.asList(reflectClass.getDeclaredFields());
			
			fields.forEach(field -> {
				PropertyDescriptor pd = null;
				
				try {
					pd = new PropertyDescriptor(field.getName(), reflectClass);
				} catch (IntrospectionException e) {
					e.printStackTrace();
				}
				
				fieldControl = new FieldControl(5, field, object, pd);
				
				vBox.getChildren().add(fieldControl);
			});
			
			this.infoTextArea = new InfoTextArea(5);
			vBox.getChildren().add(infoTextArea);
			
			this.map = fieldControl.getMap();
			
			createButton.setDisable(true);
			classTextField.setDisable(true);
			saveButton.setDisable(false);
			
			App.primaryStage.sizeToScene();
			App.primaryStage.setMinWidth(App.primaryStage.getWidth());
			App.primaryStage.setMaxHeight(App.primaryStage.getHeight());
			
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} 
	}
	
	@FXML
	void initialize() {}
}
