import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

// to show alertbox
public class AlertBox {
	
	public static void display(String title, String message) {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		//set title
		window.setTitle(title);
		window.setMinWidth(350);
		
		// set message to be printed
		Label label = new Label();
		label.setText(message);
		
		// OK button and setting in alertbox
		Button btClose = new Button("OK");
		btClose.setPrefWidth(75);
		
		// action of OK button
		btClose.setOnAction(e -> window.close());
		btClose.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				window.close();
				e.consume();
			}
		});

		// vbox to hold components in vertical form
		VBox vbox = new VBox(30);
		vbox.getChildren().addAll(label, btClose);
		vbox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(vbox, 350, 150);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
