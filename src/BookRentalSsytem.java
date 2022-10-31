import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BookRentalSsytem extends Application {
	
	@SuppressWarnings("unused")
	private Scene scene1, scene2, scene3;
	
	// for main scene
	//random username with format UserXXX where X is randomly generated number
	private String user = "User" + Integer.toString(ThreadLocalRandom.current().nextInt(0, 9 + 1)) 
	+ Integer.toString(ThreadLocalRandom.current().nextInt(0, 9 + 1)) + Integer.toString(ThreadLocalRandom.current().nextInt(0, 9 + 1));
	//hold randomly generated username
    String temp = user;
	//button for member registration
	private Button btRegisterMember = new Button("Register Member");
	//set initial registerMember to true when starting of app
	private boolean registerDisabled = false;
	//button for book renting
	private Button btRentBook = new Button("Rent Book");
	//button for exiting
	private Button btExit = new Button("Exit");
	
	// for member registration scene
	//label for username
	private Label lbUsername = new Label("Username\t\t\t: ");
	//textfield for username
	private TextField tfUsername;
	//label for Username error checking
    private Label chkUsername;
	//label for email
	private Label lbEmail = new Label("E-mail\t\t\t\t: ");
	//textfield for email
	private TextField tfEmail;
	//label for Email error checking
	private Label chkEmail;
	//label for contact
	private Label lbContact = new Label("Contact No.\t\t:");
	//textfield for contact
	private TextField tfContact;
	//label for contact error checking
	private Label chkContact;
	//label for Ps
	private Label lbPs = new Label("Password\t\t\t\t: ");
	//textfield for Ps
	private TextField tfPs;
	//passwordfield for Ps
	private PasswordField pfPs;
	//checkbox to reveal Ps
	private CheckBox cbRevealPs;
	//label for Ps error checking
    private Label chkPs;
	//button for registration
	private Button btRegister = new Button("Register");
	//button for back
	private Button btBack = new Button("Main Page");
	
	// for book rental scene
	//label for filter
	private Label lbFilter = new Label("Search: ");
	//textfield for filter
	private TextField tfFilter;
	//arraylist to hold books
	ArrayList<Book> books = new ArrayList<>();
	//togglegroup for radiobuttons
	ToggleGroup group = new ToggleGroup();
	//tableview for books
	private TableView<Book> table;
	//button for clear filter
	private Button btClear = new Button("Clear Filter");
	//button for renting book
	private Button btRent = new Button("Rent");
	//save the chosen if not renting
	private int chosen = 0;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		
		// for file reading
     	//define file name
		String fileName = "Books.txt";
		File fileIn = new File(fileName);
		//display error message if file does not exist
		if (!fileIn.exists())
		{
			AlertBox.display("Error", "File " + fileName + " is not found !");
			//setScene(primaryStage, 0);
		}
		//read from Books.txt
		Scanner input = new Scanner(fileIn);
		//seperate inputs by using ", "
		input.useDelimiter(", ");
		while (input.hasNext())
		{
			//read details from Books.txt
			String bookName = input.next();
			String genre = input.next();
			String author = input.next();
			String year = input.next();
			
			books.add(new Book(bookName, genre, author, year, "No"));
			
			//skip to next line(book)
			input.nextLine();
		}
		input.close();
		
		//show the first scene
		try {
			setScene(primaryStage, 0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		primaryStage.show();
		
	}
	
	// for switching scene
	@SuppressWarnings("unchecked")
	public void setScene(Stage primaryStage, int index) throws FileNotFoundException {
		
		switch(index) {
		case 0:
			//Scene1 for main Pane
			BorderPane mainPane = new BorderPane();
			Text text = new Text("Book Rental System");
			text.setFont(Font.font("Courier", FontPosture.ITALIC, 20));
			mainPane.setTop(new CustomPane(text));
			//for image in mainPane
			mainPane.setCenter(getHbox1());
			//for buttons in mainPane
			mainPane.setBottom(getGPane1(primaryStage));
			
			Scene scene1 = new Scene(mainPane, 850, 380);
			primaryStage.setTitle("Welcome, " + user);
			primaryStage.setScene(scene1);
			break;
		case 1:
			//Scene2 for member registration
			BorderPane memberPane = new BorderPane();
			Text text2 = new Text("MEMBERSHIP REGISTRATION");
			text2.setFont(Font.font("Courier",FontWeight.BOLD,15));
			memberPane.setPadding(new Insets(10, 10, 10, 10));
			memberPane.setTop(new CustomPane(text2));
			//for keying in details
			memberPane.setCenter(getGPane2());
			//for buttons
			memberPane.setBottom(getHBox2(primaryStage));
			
			scene2 = new Scene(memberPane, 850, 380);
			primaryStage.setTitle("Register as Member");
			primaryStage.setScene(scene2);
			break;
		case 2:
			//reset tableview
			table = new TableView<>();
			//columns for tableview
			//bookName column
			TableColumn<Book, String> bookNameColumn = new TableColumn<>("BOOK");
			bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
			//genre column
			TableColumn<Book, String> genreColumn = new TableColumn<>("GENRE");
			genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
			//author column
			TableColumn<Book, String> authorColumn = new TableColumn<>("AUTHOR");
			authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
			//year column
			TableColumn<Book, String> yearColumn = new TableColumn<>("YEAR OF PUBLISH");
			yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
			//rented column
			TableColumn<Book, String> rentedColumn = new TableColumn<>("RENTED");
			rentedColumn.setCellValueFactory(new PropertyValueFactory<>("rented"));
			//choose column
			@SuppressWarnings("rawtypes")
			TableColumn chooseColumn = new TableColumn("CHOOSE");
			chooseColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("rbRent"));
			
			//setup for items to be shown in table
			//wrap the observablelist in a filteredlist (initially display all data).
	        FilteredList<Book> filteredData = new FilteredList<>(getBooks(), b -> true);
	        //show the filterlist in tableview
			table.setItems(filteredData);
			table.getColumns().addAll(bookNameColumn, genreColumn, authorColumn, yearColumn, rentedColumn, chooseColumn);
			
			//Scene3 for book rental
			BorderPane rentPane = new BorderPane();
			Text text3 = new Text("BOOK RENTAL");
			text3.setFont(Font.font("Courier",FontWeight.BOLD,15));
			rentPane.setPadding(new Insets(10, 10, 10, 10));
			rentPane.setTop(new CustomPane(text3));
			rentPane.setCenter(getVBox3(primaryStage, filteredData, table));
			rentPane.setBottom(getHBox3(primaryStage));
			
			scene3 = new Scene(rentPane, 850, 380);
			primaryStage.setTitle("Welcome to Rent Book, " + user);
			primaryStage.setScene(scene3);
			break;
		default:
			break;
		}
		
	}
	
	// for components in first scene
	//for image
	private HBox getHbox1() {
		
		HBox hbox = new HBox();
		//fetching image
		final ImageView icon = new ImageView("reading books.png");
		hbox.getChildren().add(icon);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	
	}
	//for buttons
	private GridPane getGPane1(Stage primaryStage) {
	
		GridPane gPane = new GridPane();
		gPane.setPadding(new Insets(10, 10, 10, 10));
		gPane.setHgap(30);
		gPane.setVgap(10);
		
		// settings for buttons
		//set button for member registration enabled initially
		btRegisterMember.setDisable(registerDisabled);
		btRegisterMember.setPrefWidth(169);
		btRentBook.setPrefWidth(169);
		btExit.setPrefWidth(169);
		
		gPane.add(btRegisterMember, 11, 0);
		gPane.add(btRentBook, 11, 1);
		gPane.add(btExit, 11, 2);
		
		// for btRegisterMember action
		btRegisterMember.setOnAction(e -> {
			try {
				setScene(primaryStage, 1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btRegisterMember.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					setScene(primaryStage, 1);
					e.consume();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		
		// for btRentBook action
		btRentBook.setOnAction(e -> {
			try {
				setScene(primaryStage, 2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btRentBook.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					setScene(primaryStage, 2);
					e.consume();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		
		// for btExit action
		btExit.setOnAction(e -> {
			BooleanProperty exit = ChoiceBox.display("Exiting Book Rental System ...", "Are you sure to exit ?");
			if (exit.get())
			{	
				AlertBox.display("Thank you", "See you again !");
				System.exit(0);
			}
				
		});
		
		return gPane;
	}
	
	// for components in second scene
	private GridPane getGPane2() {
		
		GridPane gridPane = new GridPane();
	    gridPane.setPadding(new Insets(10, 10, 10, 10));
	    gridPane.setHgap(10); 
	    gridPane.setVgap(5);
	    
	    // pref width settings for labels
	    lbUsername.setPrefWidth(140);
	    lbEmail.setPrefWidth(140);
	    lbContact.setPrefWidth(140);
	    lbPs.setPrefWidth(140);
	    
	    // settings for textfields and checkbox
	    tfUsername = new TextField();
	    tfEmail = new TextField();
	    tfContact = new TextField();
	    tfPs = new TextField();
	    pfPs = new PasswordField();
	    cbRevealPs = new CheckBox("Show password");
	    
	    // prompt text settings for textfields
	    tfUsername.setPromptText("E.g. User123");
	    tfEmail.setPromptText("E.g. user123@gmail.com");
	    tfContact.setPromptText("E.g. 0123456789");
	    pfPs.setPromptText("E.g. Must be at least 5 characters");
	    
	    // pref width settings for textfields and passwordfields
	    tfUsername.setPrefWidth(300);
	    tfEmail.setPrefWidth(300);
	    tfContact.setPrefWidth(300);
	    tfPs.setPrefWidth(300);
	    pfPs.setPrefWidth(300);
	    
	    // settings for error checking
	    chkUsername = new Label();
	    chkEmail = new Label();
	    chkContact = new Label();
	    chkPs = new Label();
	    
	    // settings for reveal ps and confirmPs
	    //bind ps textfied and passwordfield tgt
	    tfPs.textProperty().bindBidirectional(pfPs.textProperty());
		//disable revealPs if ps is not entered
		cbRevealPs.disableProperty().bind(Bindings.isEmpty(pfPs.textProperty()));
		//show ps if cbRevealPs is selected
		tfPs.visibleProperty().bind(cbRevealPs.selectedProperty());
		//show encoded Ps if cbRevealPs is not selected
		pfPs.visibleProperty().bind(cbRevealPs.selectedProperty().not());
	    
		// adding components into gridPane
	    gridPane.add(lbUsername, 0, 0);
	    gridPane.add(tfUsername, 1, 0);
	    gridPane.add(chkUsername, 1, 1);
	    gridPane.add(lbEmail, 0, 2);
	    gridPane.add(tfEmail, 1, 2);
	    gridPane.add(chkEmail, 1, 3);
	    gridPane.add(lbContact, 0, 4);
	    gridPane.add(tfContact, 1, 4);
	    gridPane.add(chkContact, 1, 5);
	    gridPane.add(lbPs, 0, 6);
	    gridPane.add(tfPs, 1, 6);
	    gridPane.add(pfPs, 1, 6);
	    gridPane.add(cbRevealPs, 2, 6);
	    gridPane.add(chkPs, 1, 7);
	    
	    // adding listener to username
	    tfUsername.setOnKeyReleased(e -> {
			String str = readUsername();
			chkUsername.setText(str);
			chkUsername.setTextFill(Color.RED);
			//save the username
			user = tfUsername.getText();
		});
	    
	    // adding listener to email
	    tfEmail.setOnKeyReleased(e -> {
			String str = readEmail();
			chkEmail.setText(str);
			chkEmail.setTextFill(Color.RED);
		});
	    
	    // adding listener to contact
	    tfContact.setOnKeyPressed(e -> {
	    	// Idk why here has a delay
	    	String str = readContact();
	    	chkContact.setText(str);
	    	chkContact.setTextFill(Color.RED);
	    });
	    
	    // adding listener to password
	    pfPs.setOnKeyReleased(e -> {
	    	String str = readPs();
			chkPs.setText(str);
			chkPs.setTextFill(Color.RED);
		});
	    
	    // adding listener to reveal password checkbox
	    cbRevealPs.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				cbRevealPs.fire();
				if (cbRevealPs.isSelected())
					cbRevealPs.setSelected(false);
				else
					cbRevealPs.setSelected(true);
				e.consume();
			}
		});
	    
	    return gridPane;
		
	}
	private HBox getHBox2(Stage primaryStage) {
		HBox hbox = new HBox(665);
		
	    hbox.setAlignment(Pos.BOTTOM_RIGHT);
	    hbox.setPadding(new Insets(10,10,10,10));
	    
	    
	    // settings for buttons
	    btBack.setPrefWidth(100);
	    btRegister.setPrefWidth(100);
	    btRegister.disableProperty().bind(Bindings.isEmpty(tfUsername.textProperty()).or(Bindings.isEmpty(tfEmail.textProperty()))
	    		.or(Bindings.isEmpty(tfContact.textProperty())).or(Bindings.isEmpty(pfPs.textProperty()))
				.or(Bindings.isNotNull(chkUsername.textProperty())).or(Bindings.isNotNull(chkEmail.textProperty()))
				.or(Bindings.isNotNull(chkContact.textProperty())).or(Bindings.isNotNull(chkPs.textProperty())));
	    
	    // adding buttons to hbox
	    hbox.getChildren().add(btBack);
	    hbox.getChildren().add(btRegister);
		
	    // action for back button
	    btBack.setOnAction(e -> {
	    	try {
	    		user = temp;
				setScene(primaryStage, 0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    btBack.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					user = temp;
					setScene(primaryStage, 0);
					e.consume();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
	    
	    // actions for register button
	    btRegister.setOnAction(e -> {
	    	try {
	    		AlertBox.display("Congrats", "You have successfully registered as " + user + " !");
		    	registerDisabled = true;
				setScene(primaryStage, 2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    btRegister.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					AlertBox.display("Congrats", "You have successfully registered as " + user + " !");
			    	registerDisabled = true;
			    	setScene(primaryStage, 2);
					e.consume();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
	    
		return hbox;
	}
	 
	// for components in third scene
	private VBox getVBox3(Stage primaryStage, FilteredList<Book> filteredData, TableView<Book> table) {
		VBox vbox = new VBox();
		
		// creating gridPane in vbox
		GridPane gPane = new GridPane();
		gPane.setPadding(new Insets(10, 10, 10, 10));
		gPane.setHgap(10); 
		gPane.setVgap(5);
	    
		// pref width settings for label, textfield and button
	    tfFilter = new TextField();
	    tfFilter.setPromptText("Enter any keyword here");
	    tfFilter.setPrefWidth(300);
	    btClear.setPrefWidth(100);
	    
	    // adding components into gPane
	    gPane.add(lbFilter, 0, 0);
	    gPane.add(tfFilter, 1, 0);
	    gPane.add(btClear, 36, 0);
	    
	    // adding components into vbox
	    vbox.getChildren().add(gPane);
	    vbox.getChildren().add(table);
	    
	    //set listener to tfFilter
	    tfFilter.setOnKeyReleased(e -> {
	    	tfFilter.textProperty().addListener((observable, oldValue, newValue) -> {
		    	filteredData.setPredicate(book -> {
		    		//display all books if filter is empty
		    		if (newValue == null || newValue.isEmpty()) {
						return true;
					}
		    		//compare in lowercase
					String lowerCaseFilter = newValue.toLowerCase();
					
					//start comparison
					if (book.getBookName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
						return true; // Filter matches bookName.
					} else if (book.getGenre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches genre.
					}
					else if (book.getAuthor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches genre.
					}
					else if (book.getYear().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches genre.
					}
					else 
						return false; // Does not match.
		    	});
		    });
	    });
	    
	    // action for clear button
	    btClear.setOnAction(e -> {
	    	try {
	    		tfFilter.clear();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    btClear.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					tfFilter.clear();
					e.consume();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		
		return vbox;
	}
	private HBox getHBox3(Stage primaryStage) {
		HBox hbox = new HBox(665);
		
	    hbox.setAlignment(Pos.BOTTOM_RIGHT);
	    hbox.setPadding(new Insets(10,10,10,10));
	    
	    // settings for buttons
	    btBack.setPrefWidth(100);
	    btRent.setPrefWidth(100);
	    
	    //set all radiobuttons as a togglegroup
	    for (int i = 0; i < books.size(); i++)
	    	books.get(i).getRbRent().setToggleGroup(group);
	    
	    // set the first available book to be selected initially if no rent or chosen book previously
	    if (chosen != 0)
	    	group.selectToggle(books.get(chosen).getRbRent());
	    else
	    {
	    	int num = 0;
		    while (books.get(num).getRbRent().isDisabled())
		    	num++;
		    group.selectToggle(books.get(num).getRbRent());
	    }
	    
	    // adding components into hbox
	    hbox.getChildren().add(btBack);
	    hbox.getChildren().add(btRent);
		
	    // action for back button
	    btBack.setOnAction(e -> {
	    	try {
				setScene(primaryStage, 0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    btBack.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					setScene(primaryStage, 0);
					e.consume();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
	    
	    // action for rent button
	    btRent.setOnAction(e -> {
	    	try {
	    		// save the chosen bookName
	    		String chosenBook = "";
	    		for (int i = 0; i < books.size(); i++)
	    		{
	    			if (books.get(i).getRbRent().isSelected())
	    			{	
	    				chosenBook = books.get(i).getBookName();
	    				chosen = i;
	    			}
	    		}
	    		BooleanProperty choice = ChoiceBox.display("Confirm to rent ?", "\tAre you sure to rent the book: \n" + chosenBook + "\n\t\t\t\t?");
	    		if (choice.get())
	    		{	
	    			AlertBox.display("Congrats !", "You have successfully rented the book: \n" + chosenBook);
	    			//change book(s) to rented condition
		    		for (int i = 0; i < books.size(); i++)
		    		{
		    			if (books.get(i).getRbRent().isSelected())
		    			{
		    				// set book to unavailable to be rented
		    				books.get(i).setRented("Yes");
		    				books.get(i).getRbRent().setDisable(true);
		    				chosen = 0;
		    			}
		    		}
	    		}
	    		else
	    			group.selectToggle(books.get(chosen).getRbRent());
	    		//refresh the scene
				setScene(primaryStage, 2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    btRent.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					// save the chosen bookName
					String chosenBook = "";
		    		for (int i = 0; i < books.size(); i++)
		    		{
		    			if (books.get(i).getRbRent().isSelected())
		    			{	
		    				chosenBook = books.get(i).getBookName();
		    				chosen = i;
		    			}
		    		}
		    		BooleanProperty choice = ChoiceBox.display("Confirm to rent ?", "\tAre you sure to rent the book: \n" + chosenBook + "\n\t\t\t\t?");
		    		if (choice.get())
		    		{	
		    			AlertBox.display("Congrats !", "You have successfully rented the book: \n" + chosenBook);
		    			//change book(s) to rented condition
			    		for (int i = 0; i < books.size(); i++)
			    		{
			    			if (books.get(i).getRbRent().isSelected())
			    			{
			    				// set book to unavailable to be rented
			    				books.get(i).setRented("Yes");
			    				books.get(i).getRbRent().setDisable(true);
			    				chosen = 0;
			    			}
			    		}
		    		}
		    		else
		    			group.selectToggle(books.get(chosen).getRbRent());
		    		//refresh the scene
					setScene(primaryStage, 2);
					e.consume();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
	    
		return hbox;
	}
	
	
	// for backend
	//checking Username
	public String readUsername() {
		String message = null;
		String username = tfUsername.getText();
		if (username.length() == 0)
			message = "Required Field";
		else if (username.length() < 2)
		{
			message = "Username must be at least 2 characters";
		}
		return message;
	}
	
	//checking email
	public String readEmail() {
		String message = null;
		String email = tfEmail.getText();
		if (email.length() == 0)
			message = "Required Field";
		else
		{
			//make sure email ends with one of these suffix
			if ((email.endsWith("@gmail.com"))||(email.endsWith("@hotmail.com"))||(email.endsWith("@yahoo.au"))||(email.endsWith("@yahoo.com")))
			{
				if (email.length() <= 10)
					message = "Invalid E-mail input";
			}
			else
				message = "Invalid E-mail input";
		}
		return message;
	}
	
	//checking contact
	public String readContact() {
		//format of contact
		final String CONTACT_REGEX = "^[0][1][0-9]{8,9}$";
		String message = null;
		String contact = tfContact.getText();
		if (contact.length() == 0)
			message = "Required Field";
		else
		{
			//make sure contact is in correct format
			if(!contact.matches(CONTACT_REGEX))
				message = "Invalid contact, E.g. 0123456789";
		}
		return message;
	}
	
	//checking ps
	public String readPs() {
		String message = null;
		String ps = tfPs.getText();
		if (ps.length() == 0)
			message = "Required Field";
		//make sure password is at least 5 characters
		else if (ps.length() < 5)
		{
			message = "Password must be at least 5 characters";
		}
		return message;
	}
	
	//get books in observableList
	public ObservableList<Book> getBooks() {
		ObservableList<Book> obooks = FXCollections.observableArrayList();
		
		for (int i = 0; i < books.size(); i++)
			obooks.add(books.get(i));
		
		return obooks;
	}
	
}

// customize the title font style
class CustomPane extends StackPane {
	public CustomPane(Text text) {
		getChildren().add(text);
	    setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
	}
}
