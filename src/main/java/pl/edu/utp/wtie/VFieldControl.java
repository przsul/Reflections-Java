package pl.edu.utp.wtie;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pl.edu.utp.wtie.validation.Validator;

public class VFieldControl extends HBox {
	
	private static Map<Control, Field> map = new LinkedHashMap<>();
	private FontAwesomeIconView icon;
	private TextField textField;
	private Label label;
	private Tooltip tooltip;
	private List<Validator> validators;
	
	public VFieldControl(double spacing, Field field, Object object, PropertyDescriptor pd) {
		
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
				label = new Label("<- " + field.getName());
				this.getChildren().addAll(textArea, label);
				
			} else if(field.getGenericType().getTypeName().equals("boolean") ||
					field.getGenericType().getTypeName().equals("java.lang.Boolean")) {
				CheckBox checkBox = new CheckBox();
				if(pd.getReadMethod().invoke(object) != null)
					checkBox.setSelected(Boolean.parseBoolean(pd.getReadMethod().invoke(object).toString()));
				map.put(checkBox, field);
				
				label = new Label("<- " + field.getName());
				this.getChildren().addAll(checkBox, label);
				
			} else {
				icon = new FontAwesomeIconView();
				icon.setSize("1.8em");
				icon.setGlyphName("TIMES_CIRCLE");
				icon.setVisible(false);
				
				tooltip = new Tooltip();
				
				textField = new TextField();
				
				textField.textProperty().addListener((observable, oldValue, newValue) -> {
					for(Validator validator : validators) {
						if(validator != null) {
							validator.validate(textField.getText());

							icon.setVisible(true);
							
							if(validator.isValid()) {
								icon.setGlyphName("CHECK_CIRCLE");
								icon.setGlyphStyle("-fx-fill:green");
							} else {
								icon.setGlyphName("TIMES_CIRCLE");
								icon.setGlyphStyle("-fx-fill:red");
								break;
							}
						}
					}
					
					if(newValue.equals(""))
						icon.setVisible(false);
				});				
				
				if(pd.getReadMethod().invoke(object) != null)
					textField.setText(pd.getReadMethod().invoke(object).toString());
				map.put(textField, field);
				
				HBox.setHgrow(textField, Priority.ALWAYS);
				label = new Label("<- " + field.getName());
				this.getChildren().addAll(icon, textField, label);
			}			
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Control, Field> getMap() {
		return VFieldControl.map;
	}
	
	public TextInputControl getTextField() {
		return textField;
	}
	
	public void registerValidator(List<Validator> validators) {
		this.validators = validators;
		String tmp = "";
		
		for(Validator validator : validators)
			tmp += validator.getMessage() + '\n';
		
		tooltip.setText(tmp);
		textField.setTooltip(tooltip);
	}	
}
