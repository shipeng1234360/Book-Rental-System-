import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChoiceBox {
	
	public static BooleanProperty display(String title, String message) {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		//set title
		window.setTitle(title);
		window.setMinWidth(350);
		
		// set message to be printed
		Label label = new Label();
		label.setText(message);
		
		// buttons and their settings for choicebox
		Button btYes = new Button("Yes");
		btYes.setPrefWidth(75);
		Button btNo = new Button("No");
		btNo.setPrefWidth(75);
		
		//boolean value to be returned
		BooleanProperty choice = new SimpleBooleanProperty();
		
		// action for YES button
		btYes.setOnAction(e -> {
			window.close();
			choice.set(true);
		});
		btYes.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				window.close();
				e.consume();
				choice.set(true);
			}
		});
		
		// action for No button
		btNo.setOnAction(e -> {
			window.close();
			choice.set(false);
		});
		btNo.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				window.close();
				e.consume();
				choice.set(false);
			}
		});
		
		// hbox to hold buttons in horizontal form
		HBox hbox = new HBox(20);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(btYes, btNo);
		
		// vbox to hold components in vertical form
		VBox vbox = new VBox(30);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(label, hbox);
		
		Scene scene = new Scene(vbox, 350, 150);
		window.setScene(scene);
		window.setResizable(false);
		window.showAndWait();
		
		return choice;
	}
}
