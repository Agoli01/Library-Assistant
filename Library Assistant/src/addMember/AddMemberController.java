package addMember;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.AddBookModel;
import javafx.event.ActionEvent;

public class AddMemberController implements Initializable{
	@FXML
	private AnchorPane rootPane;
	@FXML
	private TextField name;
	@FXML
	private TextField id;
	@FXML
	private TextField mobile;
	@FXML
	private TextField email;
	@FXML
	private Button cancelButton;
	@FXML
	private Button saveButton;

	AddBookModel addBookModel;
	
	
	@FXML
	public void cancel(ActionEvent event)
	{
		
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void addMember(ActionEvent event) {
		
		String mname = name.getText();
		String mid = id.getText();
		String mmobile = mobile.getText();
		String memail = email.getText();
		
		if(mname.isEmpty()||mid.isEmpty()||mmobile.isEmpty()||memail.isEmpty()){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("please fill all the fields.");
			alert.showAndWait();
			return;
		}
		else{
			try {
				 if(addBookModel.isMemberAdded(mid, mname, mmobile, memail)){
					 Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Member Added Sucessfully");
						alert.showAndWait();
						id.setText("");name.setText("");mobile.setText("");email.setText("");
						addBookModel.closeDatabase();
				 }
				 else{
					 Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText(null);
						alert.setContentText("user id alerady exist");
						alert.showAndWait();
				 }
				
			} 
			catch (SQLException e) {				
				System.out.println("Error");
				e.printStackTrace();
			}

				
		}
			
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addBookModel = new AddBookModel();
		
	}
}
