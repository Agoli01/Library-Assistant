package listBook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.AddBookModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;


public class bookListController implements Initializable {
	
	@FXML
	private AnchorPane rootPane;
	@FXML
	private TableView<Book> tableView;
	@FXML
	private TableColumn<Book,String> idCol;
	@FXML
	private TableColumn<Book,String> titleCol;
	@FXML
	private TableColumn<Book,String> authorCol;
	@FXML
	private TableColumn<Book,String> publisherCol;
	@FXML
	private TableColumn<Book,Boolean> availCol;
	
	ObservableList<Book> list = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initCol();
		loadData();
	}

	private void initCol() {
		
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		availCol.setCellValueFactory(new PropertyValueFactory<>("avail"));
	}

	private void loadData() {
		AddBookModel dispBook = new AddBookModel();
		String qu = "SELECT * from Book";
		ResultSet rs = dispBook.execQuery(qu);
		try {
			while(rs.next())
			{
				String id = rs.getString("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String publisher = rs.getString("publisher");
				Boolean avail = rs.getBoolean("isAvail");
				
				list.add(new Book(id, title, author, publisher, avail));
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
	
		
	public static class Book{
		private final SimpleStringProperty id;
		private final SimpleStringProperty title;
		private final SimpleStringProperty author;
		private final SimpleStringProperty publisher;
		private final SimpleBooleanProperty avail;
		
		Book(String id,String title,String author,String publisher,Boolean avail)
		{
			this.id = new SimpleStringProperty(id);
			this.title = new SimpleStringProperty(title);
			this.author = new SimpleStringProperty(author);
			this.publisher = new SimpleStringProperty(publisher);
			this.avail = new SimpleBooleanProperty(avail);
		}

		public String getId() {
			return id.get();
		}

		public String getTitle() {
			return title.get();
		}

		public String getAuthor() {
			return author.get();
		}

		public String getPublisher() {
			return publisher.get();
		}

		public Boolean getAvail() {
			return avail.get();
		}
	}
}
