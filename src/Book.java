
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.RadioButton;

public class Book {
	private final SimpleStringProperty bookName ;
	private final SimpleStringProperty genre;
	private final SimpleStringProperty author;
	private final SimpleStringProperty year;
	private final SimpleStringProperty rented;
	private RadioButton rbRent;
	
	public Book(String bookName, String genre, String author, String year, String rented) {
		this.bookName = new SimpleStringProperty(bookName);
		this.genre = new SimpleStringProperty(genre);
		this.author = new SimpleStringProperty(author);
		this.year = new SimpleStringProperty(year);
		this.rented = new SimpleStringProperty(rented);
        this.rbRent = new RadioButton();
	}

	public String getBookName() {
		return bookName.get();
	}

	public void setBookName(String bookName) {
		this.bookName.set(bookName);
	}

	public String getGenre() {
		return genre.get();
	}

	public void setGenre(String genre) {
		this.genre.set(genre);
	}

	public String getAuthor() {
		return author.get();
	}

	public void setAuthor(String author) {
		this.author.set(author);
	}

	public String getYear() {
		return year.get();
	}

	public void setYear(String year) {
		this.year.set(year);
	}
	
	public String getRented() {
		return rented.get();
	}

	public void setRented(String rented) {
		this.rented.set(rented);
	}

	public RadioButton getRbRent() {
		return rbRent;
	}

	public void setRdButton(RadioButton rbRent) {
		this.rbRent = rbRent;
	}
	
}
