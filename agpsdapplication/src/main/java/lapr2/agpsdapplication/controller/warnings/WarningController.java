package lapr2.agpsdapplication.controller.warnings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WarningController implements Initializable {

	@FXML
	private ImageView imageView;

	@FXML
	private Label lblTitle;

	@FXML
	private Text textContent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// This method does not need functional implementation in this case.
	}

	public void setContent(String title, String content, String imagePath) {
		lblTitle.setText(title);
		textContent.setText(content);
		imageView.setImage(new Image(imagePath));
	}

	public void onClose(ActionEvent actionEvent) {
		Node source = (Node) actionEvent.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		stage.close();
	}
}
