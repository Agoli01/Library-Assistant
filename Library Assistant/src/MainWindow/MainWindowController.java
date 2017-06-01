package MainWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import database.AddBookModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class MainWindowController implements Initializable{

	@FXML
	private Text bookName,bookAuthor,bookAvail;
	@FXML
	private Text memberName,memberNum;
	@FXML
	private TextField bookIdInput,memberIdInput,bookId;
	@FXML
	private ListView<String> issueDataList;
	
	private boolean isReadyForSubmission = false,isBook=false,isMember=false;
	
	@FXML
	public void loadAddMember(ActionEvent event) {
		
		loadWindow("/addMember/AddMember.fxml", "Add Member");
	}
	
	@FXML
	public void loadAddBook(ActionEvent event) {
		loadWindow("/addBook/AddBook.fxml", "Add Book");
	}
	
	@FXML
	public void loadMemberTable(ActionEvent event) {
		loadWindow("/listMember/ListMember.fxml", "Member list");
	}

	@FXML
	public void loadBookTable(ActionEvent event) {
		loadWindow("/listBook/bookList.fxml", "Book list ");
	}
	
	@FXML
	private void loadBookInfo(ActionEvent event) throws SQLException {
		
		boolean flag = false;
		isBook = false;
		AddBookModel addBookModel = new AddBookModel();
		String id = bookIdInput.getText();
		
		String qu = "SELECT * FROM Book WHERE id = '"+id+"' ";
		ResultSet rs = null;
		rs = addBookModel.execQuery(qu);
		
		try {
			while(rs.next()){
				
				String bName = rs.getString("title");
				
				String bAuthor = rs.getString("author");
				boolean bAvail = rs.getBoolean("isAvail");
				
				bookName.setText(bName);
				bookAuthor.setText(bAuthor);
				String status = bAvail ? "Available":"Not Available";
				bookAvail.setText(status);
				flag = true;
				isBook = true;
			}
			if(!flag){
				bookName.setText("No Such Book Available !!!");
				bookAuthor.setText("You Choose from Available one's");
				bookAvail.setText("");
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		addBookModel.closeDatabase();
	}
	@FXML
	private void loadMemberInfo(ActionEvent event) throws SQLException {
		
		boolean flag = false;
		isMember = false;
		AddBookModel addBookModel = new AddBookModel();
		String id = memberIdInput.getText();
		
		String qu = "SELECT * FROM Member WHERE id = '"+id+"' ";
		ResultSet rs = null;
		rs = addBookModel.execQuery(qu);
		
		try {
			while(rs.next()){
				
				String mName = rs.getString("name");
				String mNumber = rs.getString("mobile");
				
				memberName.setText(mName);
				memberNum.setText(mNumber);
				flag = true;
				isMember = true;
			}
			if(!flag){
				memberName.setText("No Such Member Available !!!");
				memberNum.setText("Please Enter the Vaild Member ID");
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		addBookModel.closeDatabase();
	}
	
	@FXML
	private void loadMemberInfo2(ActionEvent event) throws SQLException{
		
		ObservableList<String> issueData = FXCollections.observableArrayList();
		isReadyForSubmission = false;
		AddBookModel getBook = new AddBookModel();
		String id = bookId.getText();
		String qu = "SELECT * FROM Issue Where bookId = '"+ id + "'";
		ResultSet rs = getBook.execQuery(qu);
		
		try {
			while(rs.next())
			{
				String mbook = id;
				String mid = rs.getString("memberId");
				int mRenew =rs.getInt("renew_count");
				
				issueData.add("Issue Date and Time : "+ rs.getString("issueTime"));
				issueData.add("Renew Count : "+mRenew);
				
				issueData.add("\t\t\t\t\tBook Information ");
				qu = "SELECT * FROM Book WHERE id ='"+id+"'";
				ResultSet rb = getBook.execQuery(qu);
				while(rb.next())
				{
					issueData.add("Book Name : "+rb.getString("title"));
					issueData.add("Book ID : "+rb.getString("id"));
					issueData.add("Book Author : "+rb.getString("author"));
					issueData.add("Book Publisher : "+rb.getString("publisher"));
				}
				
				qu = "SELECT * FROM Member WHERE id ='"+mid+"'";
				rb = getBook.execQuery(qu);
				
				issueData.add("\t\t\t\t\tMember Information ");
				while(rb.next())
				{
					issueData.add("Member Name : "+rb.getString("name"));
					issueData.add("Mobile : "+rb.getString("mobile"));
					issueData.add("Email: "+rb.getString("email"));
				}
				isReadyForSubmission = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		issueDataList.getItems().setAll(issueData);
		getBook.closeDatabase();
	}
	
	public void loadWindow(String loc , String title)
	{
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle(title);
			stage.setScene(new Scene(parent));
			stage.show();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	@FXML
	private void loadIssueOperation(ActionEvent event) throws SQLException
	{
		String memberId = memberIdInput.getText();
		String bookId = bookIdInput.getText();
		if(!isBook || !isMember){
			
			Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setTitle("Error");
			alert1.setHeaderText("Please Enter a Vaild Book ID and Member ID ");
			alert1.showAndWait();
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Issue Operation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure want to issue the book "+ bookName.getText() + "\n To " + memberName.getText() +"?");
		
		Optional<ButtonType> response = alert.showAndWait();
		if(response.get() == ButtonType.OK){
			
			String str = "INSERT INTO Issue(bookId,memberId) VALUES ( " +"'"+bookId + "'"+","+"'"+memberId+"')";
			String str2 = "UPDATE Book SET isAvail = 0  WHERE id = '"+bookId +"'";
		
			AddBookModel issue = new AddBookModel();
			
			if(issue.execAction(str) && issue.execAction(str2))
			{
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("Success");
				alert1.setHeaderText("Book Issue Complete");
				loadBookInfo(new ActionEvent());
				alert1.showAndWait();
			}
			else{
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("Failed");
				alert1.setHeaderText("Issue Operation Failed");
				alert1.setContentText("Book already issued to someone else.");
				alert1.showAndWait();
			}
		}
		else{
			Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
			alert1.setTitle("Cancelled");
			alert1.setHeaderText(null);
			alert1.setContentText("Issue Operation Cancelled");
			alert1.showAndWait();
		}
	}
	
	@FXML
	private void loadSubmission(ActionEvent se) throws SQLException
	{
		loadMemberInfo2(new ActionEvent());
		if(!isReadyForSubmission)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Failed");
			alert.setHeaderText("Please Select A Book To Submit");
			alert.setContentText("");
			alert.showAndWait();
			return;
		}
			
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Submit Operation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure want to Submit this Book ?");
		
		Optional<ButtonType> response = alert.showAndWait();
		if(response.get() == ButtonType.OK){
			
			String id = bookId.getText();
			String qu = "DELETE FROM Issue where bookId ='"+id+"'";
			String action = "UPDATE Book SET isAvail = 1 WHERE id ='"+id+"'";
		
			AddBookModel submit = new AddBookModel();
			if(submit.execAction(qu) && submit.execAction(action))
			{
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("Success");
				alert1.setHeaderText("Book Has Been Submitted");
				loadMemberInfo2(new ActionEvent());
				alert1.show();
			}
			else
			{	
				Alert alert2 = new Alert(Alert.AlertType.ERROR);
				alert2.setTitle("Failed");
				alert2.setHeaderText("Submission Has Been Failed");
				alert2.showAndWait();
			}	
			
		}else{
			Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
			alert1.setTitle("Cancelled");
			alert1.setHeaderText(null);
			alert1.setContentText("Submit Operation Cancelled");
			alert1.showAndWait();
		}	
	}
	@FXML
	private void loadRenew(ActionEvent e) throws SQLException
	{
		loadMemberInfo2(new ActionEvent());
		if(!isReadyForSubmission)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Failed");
			alert.setHeaderText("Please Select A Book To Submit");
			alert.setContentText("");
			alert.showAndWait();
			return;
		}
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Renew Operation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure want to Renew this Book ?");
		
		Optional<ButtonType> response = alert.showAndWait();
		if(response.get() == ButtonType.OK){
			
			String ac = "UPDATE Issue SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE bookId ='"+bookId.getText()+"'";
			AddBookModel renew = new AddBookModel();
			if(renew.execAction(ac))
			{
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("Success");
				alert1.setHeaderText("Book Has Been Renewed");
				loadMemberInfo2(new ActionEvent());
				alert1.show();
			}else
			{	
				Alert alert2 = new Alert(Alert.AlertType.ERROR);
				alert2.setTitle("Failed");
				alert2.setHeaderText("Renew Has Been Failed");
				alert2.showAndWait();
			}	
		}
		else{
			Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
			alert1.setTitle("Cancelled");
			alert1.setHeaderText(null);
			alert1.setContentText("Submit Operation Cancelled");
			alert1.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
