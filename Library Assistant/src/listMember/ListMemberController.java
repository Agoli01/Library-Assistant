package listMember;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.AddBookModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;


public class ListMemberController implements Initializable{
	@FXML
	private TableView<Member> tableView;
	@FXML
	private TableColumn<Member,String> idCol;
	@FXML
	private TableColumn<Member,String> nameCol;
	@FXML
	private TableColumn<Member,String> mCol;
	
	ObservableList<Member> list = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		initCol();
		loadData();
	}
	
	private void initCol() {
			
			idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
			mCol.setCellValueFactory(new PropertyValueFactory<>("email"));
			
		}
	
	private void loadData() {
		AddBookModel dispBook = new AddBookModel();
		String qu = "SELECT * from Member";
		ResultSet rs = dispBook.execQuery(qu);
		
		try {
			while(rs.next())
			{
				String id = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString(3);
				String mobile = rs.getString("mobile");
				
				list.add(new Member(id, name, mobile, email));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
		}
		tableView.getItems().setAll(list);
		dispBook.closeDatabase();
	}

	
	
	public static class Member{
		
		private final SimpleStringProperty id;
		private final SimpleStringProperty name;
		private final SimpleStringProperty email;
		private final SimpleStringProperty mobile;
		
		
		Member(String mid,String mname,String mmobile,String memail)
		{
			this.id = new SimpleStringProperty(mid);
			this.name = new SimpleStringProperty(mname);
			this.mobile = new SimpleStringProperty(mmobile);
			this.email = new SimpleStringProperty(memail);
			
		}

		public String getId() {
			return id.get();
		}

		public String getName() {
			return name.get();
		}

		public String getmobile() {
			return mobile.get();
		}

		public String getEmail() {
			return email.get();
		}

	}

}
