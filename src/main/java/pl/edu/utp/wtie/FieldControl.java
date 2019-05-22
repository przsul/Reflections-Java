package pl.edu.utp.wtie;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class FieldControl extends HBox {
	
	private static Map<Control, Field> map = new LinkedHashMap<>();
	
	public FieldControl(double spacing, Field field, Object object, PropertyDescriptor pd) {
		
		this.setSpacing(spacing);
		this.setAlignment(Pos.CENTER_LEFT);
		this.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		
		try {
			if(field.getName().equals("text")) {
				TextArea textArea = new TextArea();
				textArea.setPrefSize(200, 100);
				textArea.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
				textArea.setWrapText(true);
				if(pd.getReadMethod().invoke(object) != null)
					textArea.setText(pd.getReadMethod().invoke(object).toString());
				map.put(textArea, field);
				
				HBox.setHgrow(textArea, Priority.ALWAYS);
				Label label = new Label("<- " + field.getName());
				this.getChildren().addAll(textArea, label);
			} else if(field.getGenericType().getTypeName().equals("boolean") ||
					field.getGenericType().getTypeName().equals("java.lang.Boolean")) {
				CheckBox checkBox = new CheckBox();
				if(pd.getReadMethod().invoke(object) != null)
					checkBox.setSelected(Boolean.parseBoolean(pd.getReadMethod().invoke(object).toString()));
				map.put(checkBox, field);
				
				Label label = new Label("<- " + field.getName());
				this.getChildren().addAll(checkBox, label);
			} else {
				TextField textField = new TextField();
				if(pd.getReadMethod().invoke(object) != null)
					textField.setText(pd.getReadMethod().invoke(object).toString());
				map.put(textField, field);
				
				HBox.setHgrow(textField, Priority.ALWAYS);
				Label label = new Label("<- " + field.getName());
				this.getChildren().addAll(textField, label);
			}			
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Control, Field> getMap() {
		return FieldControl.map;
	}
}
