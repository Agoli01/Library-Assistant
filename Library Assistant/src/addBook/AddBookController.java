package addBook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.AddBookModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddBookController implements Initializable{

	
	@FXML
	private TextField title,id,author,publisher;
	
	@FXML
	private Button saveButton,cancelButton;
	
	@FXML
	private AnchorPane rootPane;
	
	AddBookModel addBookModel = null;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		addBookModel = new AddBookModel();
		//checkData();
		if(addBookModel.isDbConnected())
		{	//System.out.println("Connected");
		}
	}

	@FXML
	public void addBook(ActionEvent event)
	{
		String bookId = id.getText();
		String bookTitle = title.getText();
		String bookAuthor = author.getText();
		String bookPublisher = publisher.getText();
		
		if(bookId.isEmpty()||bookTitle.isEmpty()||bookAuthor.isEmpty()||bookPublisher.isEmpty()){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("please fill all the fields.");
			alert.showAndWait();
			return;
		}
		else{
			try {
				 if(addBookModel.isInserted(bookId, bookTitle, bookAuthor, bookPublisher)){
					 Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Book Added Sucessfully");
						alert.showAndWait();
						id.setText("");title.setText("");author.setText("");publisher.setText("");
						addBookModel.closeDatabase();
				 }
				 else{
					 Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText(null);
						alert.setContentText("Please enter unique Book ID");
						alert.showAndWait();
				 }
				
			} 
			catch (SQLException e) {				
				//System.out.println("Error");
				e.printStackTrace();
			}

				
		}
	}
	
	@FXML
	public void cancel(ActionEvent event)
	{
		
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}
	
	/*private void checkData()
	{
		String qu = "SELECT title from Book";
		ResultSet rs = addBookModel.execQuery(qu);
		try {
			while(rs.next())
			{
				String title = rs.getString(1);
				System.out.println(title);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
