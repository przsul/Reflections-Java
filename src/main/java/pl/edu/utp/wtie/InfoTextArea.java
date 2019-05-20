package pl.edu.utp.wtie;

import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class InfoTextArea extends HBox {
	
	private TextArea infoTextArea;
	
	public InfoTextArea(double spacing) {
		this.setSpacing(spacing);
		this.setAlignment(Pos.CENTER_LEFT);
		
		infoTextArea = new TextArea();
		infoTextArea.setPrefSize(200, 100);
		infoTextArea.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		infoTextArea.setWrapText(true);
		infoTextArea.setEditable(false);
		
		HBox.setHgrow(infoTextArea, Priority.ALWAYS);
		this.getChildren().add(infoTextArea);
	}
	
	public void clear() {
		this.infoTextArea.clear();
	}
	
	public void appendText(String text) {
		this.infoTextArea.appendText(text);
	}
	
	public String getText() {
		return this.infoTextArea.getText();
	}
}
