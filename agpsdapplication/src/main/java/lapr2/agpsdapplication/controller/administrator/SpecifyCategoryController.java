package lapr2.agpsdapplication.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.ResourceBundle;

public class SpecifyCategoryController implements Initializable, ChildrenController {

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtDescription;

	@FXML
	private Label lblWarnings;

	private CategoryManager categoryManager;

	private AdministratorPageController administratorPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.categoryManager = HomeServices.getInstance().getCompany().getDataManager().get(CategoryManager.class);
	}

	public void onConfirm(ActionEvent actionEvent) {
		if (!areFieldsValid()) return;

		String id = txtId.getText().trim();
		String description = txtDescription.getText().trim();

		Category category = categoryManager.createCategory(id, description);

		if (categoryManager.isSecure(category)) {
			categoryManager.add(category);

			Application.getSceneManager().loadWarningPopUp("Success", "The category was successfully added", "/images/warnings/success.png");

			administratorPageController.onHome(null);
		} else {
			WarningUtilities.showLabelWarning(lblWarnings, "The category is not valid", 2, 0.1);
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.administratorPageController = (AdministratorPageController) initializable;
	}

	private boolean areFieldsValid() {
		String id = txtId.getText().trim();
		String description = txtDescription.getText().trim();

		boolean valid = true;
		if (id.isEmpty()) {
			valid = false;
			WarningUtilities.showLabelWarning(lblWarnings, "The unique identifier is not valid", 2, 0.1);
			txtId.requestFocus();
		} else if (description.isEmpty()) {
			valid = false;
			WarningUtilities.showLabelWarning(lblWarnings, "The description is not valid", 2, 0.1);
			txtDescription.requestFocus();
		} else {
			try {
				Category category = new Category(id, description);
				if (!categoryManager.isValid(category)) {
					valid = false;
					WarningUtilities.showLabelWarning(lblWarnings, "This category already exists", 2, 0.1);
				}
			} catch (Exception e) {
				valid = false;
				WarningUtilities.showLabelWarning(lblWarnings, "Something went wrong\nPlease try another fields", 2, 0.1);
			}
		}

		return valid;
	}
}
