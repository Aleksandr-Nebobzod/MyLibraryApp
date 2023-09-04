package org.nebobrod.mylibrary;

import static java.util.Comparator.comparing;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
	/** This is an identifier for getting string surely*/
	public static final String BOOK_ID_KEY = "bookId";
	private static final String ALL_BOOKS_KEY = "all_books";
	private static final String ALREADY_READ_BOOKS = "already_read_books";
	private static final String CURRENTLY_READING_BOOKS = "currently_reading_book";
	private static final String WANT_TO_READ_BOOKS = "want_to_read_books";
	private static final String FAVOURITE_BOOKS = "favourites_book";
	private static final String TAG = "Utils";

	private static Utils instance;

	private SharedPreferences sharedPreferences;

	private Utils(Context context) {

		sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

		if (null == getAllBooks()){
			initData();
		}

		SharedPreferences.Editor editor = sharedPreferences.edit();
		Gson gson = new Gson();

		if (null == getAlreadyReadBooks()){
			editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
			editor.commit();
		}
		if (null == getCurrentlyReadingBooks()){
			editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
			editor.commit();
		}

		if (null == getWantToReadBooks()){
			editor.putString(WANT_TO_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
			editor.commit();
		}

		if (null == getFavouriteBooks()){
			editor.putString(FAVOURITE_BOOKS, gson.toJson(new ArrayList<Book>()));
			editor.commit();
		}
	}

	private void initData(ArrayList<Book>... incomingBooks) {
		ArrayList<Book> books = new ArrayList<>();

		books.add(new Book(1, "Anathem", "Neal Stephenson", 1008, "https://m.media-amazon.com/images/I/41fwJ0p6yeL.jpg",
				"Major themes include the many-worlds interpretation of quantum ...",
				"Since childhood, Raz has lived behind the walls of a 3,400-year-old monastery, a sanctuary for scientists, philosophers, and mathematicians. There, he and his cohorts are sealed off from the illiterate, irrational, unpredictable \"saecular\" world, an endless landscape of casinos and megastores that is plagued by recurring cycles of booms and busts, dark ages and renaissances, world wars and climate change. Until the day that a higher power, driven by fear, decides it is only these cloistered scholars who have the abilities to avert an impending catastrophe. And, one by one, Raz and his friends, mentors, and teachers are summoned forth without warning into the unknown."));
		books.add(new Book(2, "City of Ember", "Jeanne DuPrau", 288,"https://m.media-amazon.com/images/I/91iBP6ITGkL._AC_UF1000,1000_QL80_.jpg",
				"“A realistic post-apocalyptic world. DuPrau’s book leaves Doon and Lina on the verge of undiscovered country and readers wanting more.” —USA Today",
				"Many hundreds of years ago, the city of Ember was created by the Builders to contain everything needed for human survival. It worked - but now the storerooms are almost out of food, crops are blighted, corruption is spreading through the city and worst of all - the lights are failing. Soon Ember could be engulfed by darkness-But when two children, Lina and Doon, discover fragments of an ancient parchment, they begin to wonder if there could be a way out of Ember. Can they decipher the words from long ago and find a new future for everyone? Will the people of Ember listen to them?"));
		books.add(new Book(3, "The People of Sparks", "Jeanne DuPrau", 354,"https://m.media-amazon.com/images/I/51drXel0L-L.jpg",
				"The people of the small village of Sparks seem willing to help them... at first... but life on the surface has it's dark side too. ",
				"Before long the villagers of Sparks become more reluctant to share their precious resources with the strange, new underground people. Lina and Doon watch in horror as the differences between the two groups grow into resentment, anger and hate. Somehow they must help overcome the distrust and bring the people of Ember and Sparks together."));
		books.add(new Book(4, "The Diamond of Darkhold", "Jeanne DuPrau", 306,"https://m.media-amazon.com/images/I/51uU6P6ZbTL.jpg",
				"A modern-day classic. This highly acclaimed adventure series about two friends desperate to save their doomed city has captivated kids and teachers alike for almost fifteen years and has sold over 3.5  MILLION copies!",
				"Lina and Doon escaped the dying city of Ember and led their people to the town of Sparks. But they soon discover that winter is harsh aboveground. When Doon finds a book with torn pages that hints at a mysterious device from the Builders, it doesn’t take much for him to convince Lina to join him for one last adventure in the city of Ember. But what—and who—will they find when they return?"));
		books.add(new Book(5, "The Prophet of Yonwood", "Jeanne DuPrau", 306, "https://m.media-amazon.com/images/I/51oAPYWw+UL.jpg",
				"A prequel to the modern-day classic The City of Ember.",
				"Nickie will grow up to be one of the first citizens of the city of Ember. But for now, she’s an eleven-year-old girl whose father was sent away on some mysterious government project. \n" +
						" \n" +
						"So when the opportunity to move presents itself, Nickie seizes it. But her new town of Yonwood, North Carolina, isn’t what she’d anticipated. It’s a place full of suspicion and mistrust, where one person’s visions of fire and destruction have turned the town’s citizens against each other. Nickie explores the oddities around her—her great-grandfather’s peculiar journals, a reclusive neighbor who studies the heavens, a strange boy who is fascinated with snakes—all while keeping an eye out for ways to help the world. Or is it already too late to avoid a devastating war?"));

		SharedPreferences.Editor editor = sharedPreferences.edit();
		Gson gson = new Gson();
		if(null == incomingBooks) {
			editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
		} else {
			editor.putString(ALL_BOOKS_KEY, gson.toJson(incomingBooks));
		}
		editor.commit();
	}

	public static Utils getInstance(Context context) {
		if (null == instance) {
			instance = new Utils(context);
		}
		return instance;
	}

	/**
	 *
	 * @param s "123", null, ""
	 * @return casted number or 0
	 */
	public static int intFromString(String s) {
		int num=0;
		try
		{
			if(s != null)
				num = Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			num =  0;
		}
		return num;
	}

	public  ArrayList<Book> getAllBooks() {
		Gson gson = new Gson();
		//TypeToken typeToken;
		Type type = new TypeToken<ArrayList<Book>>() {}.getType();
		ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);

		return books;
	}

	public  ArrayList<Book> getAlreadyReadBooks() {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Book>>() {}.getType();
		ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS, null), type);

		return books;
	}

	public  ArrayList<Book> getWantToReadBooks() {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Book>>() {}.getType();
		ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS, null), type);

		return books;
	}

	public  ArrayList<Book> getCurrentlyReadingBooks() {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Book>>() {}.getType();
		ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS, null), type);

		return books;
	}

	public  ArrayList<Book> getFavouriteBooks() {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Book>>() {}.getType();
		ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVOURITE_BOOKS, null), type);

		return books;
	}

	/**
	 * looks for the required Book in its collection
	 * @param bookId integer id
	 * @return the Book object
	 */
	public Book getBookById(int bookId){
		// TODO: 25.08.2023 здесь было getFavourite... 
		ArrayList<Book> books = getAllBooks();
		if (null != books){
			for (Book b: books){
				if (b.getId() == bookId){
					return b;
				}
			}
		}
		return null;
	}

	public boolean updateBookInAllBook(Book book) {
		ArrayList<Book> books = getAllBooks();
		ArrayList<Book> oldBook = new ArrayList<>();
		boolean result = false;
		if (null != books) {
			// looking for the book in array
			for (Book b : books) {
				if (b.getId() == book.getId()) {
					oldBook.add(b);
					removeFromAllBooks(b);
					books.remove(b);
					break;
				}
			}
			// for non duplicating id
			if (null == getBookById(book.getId())){
				if (addToAllBook(book)) {
					result = true;
				} else {
					// book not added so there is no need to
					Log.d(TAG, "updateBookInAllBook: UNSUCCESFULL EDIT");
					if (!addToAllBook(oldBook.get(0))){
						Log.d(TAG, "updateBookInAllBook: & bad restoring");
						Log.w(TAG, "updateBookInAllBook: some data is lost");
					}
				}
			}
		}
		return result;
	}

	public int getNextBookId(){
		ArrayList<Book> books = getAllBooks();
		int index;
		index = books.stream().max(comparing(Book::getId)).get().getId();
		if (index != 0){
			return index+1;
		}
		return 0;
	}

	public boolean addToAllBook(Book book){
		ArrayList<Book> books = getAllBooks();
		if (null!= books){
			if (books.add(book)){
				Gson gson = new Gson();
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.remove(ALL_BOOKS_KEY); // но не! -- editor.clear();
				editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
				editor.commit();
				return true;
			}
		}
		return false;
	}

	public boolean addToAlreadyRead(Book book){
		ArrayList<Book> books = getAlreadyReadBooks();
		if (null!= books){
			if (books.add(book)){
				Gson gson = new Gson();
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.remove(ALREADY_READ_BOOKS); // но не! -- editor.clear();
				editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
				editor.commit();
				return true;
			}
		}
		return false;
	}

	public boolean addToCurrentlyReading (Book book){
		ArrayList<Book> books = getCurrentlyReadingBooks();
		if (null!= books){
			if (books.add(book)){
				Gson gson = new Gson();
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.remove(CURRENTLY_READING_BOOKS); // но не! -- editor.clear();
				editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
				editor.commit();
				return true;
			}
		}
		return false;
	}

	public boolean addToWantToRead (Book book){
		ArrayList<Book> books = getWantToReadBooks();
		if (null!= books){
			if (books.add(book)){
				Gson gson = new Gson();
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.remove(WANT_TO_READ_BOOKS); // но не! -- editor.clear();
				editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
				editor.commit();
				return true;
			}
		}
		return false;
	}

	public boolean addToFavourite (Book book){
		ArrayList<Book> books = getFavouriteBooks();
		if (null!= books){
			if (books.add(book)){
				Gson gson = new Gson();
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.remove(FAVOURITE_BOOKS); // но не! -- editor.clear();
				editor.putString(FAVOURITE_BOOKS, gson.toJson(books));
				editor.commit();
				return true;
			}
		}
		return false;
	}

	/**
	 * removers
	 * @param book
	 * @return success or not
	 */
	public boolean removeFromAllBooks (Book book){
		ArrayList<Book> books = getAllBooks();
		if(null != books){
			for (Book b: books){
				if(b.getId() == book.getId()){
					if (books.remove(b)){
						Gson gson = new Gson();
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.remove(ALL_BOOKS_KEY);
						editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
						editor.commit();
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean removeFromAlreadyRead (Book book){
		ArrayList<Book> books = getAlreadyReadBooks();
		if(null != books){
			for (Book b: books){
				if(b.getId() == book.getId()){
					if (books.remove(b)){
						Gson gson = new Gson();
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.remove(ALREADY_READ_BOOKS);
						editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
						editor.commit();
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean removeFromCurrentlyReading  (Book book){
		ArrayList<Book> books = getCurrentlyReadingBooks();
		if(null != books){
			for (Book b: books){
				if(b.getId() == book.getId()){
					if (books.remove(b)){
						Gson gson = new Gson();
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.remove(CURRENTLY_READING_BOOKS);
						editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
						editor.commit();
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean removeFromAWantToRead (Book book){
		ArrayList<Book> books = getWantToReadBooks();
		if(null != books){
			for (Book b: books){
				if(b.getId() == book.getId()){
					if (books.remove(b)){
						Gson gson = new Gson();
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.remove(WANT_TO_READ_BOOKS);
						editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
						editor.commit();
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean removeFromFavourite (Book book){
		ArrayList<Book> books = getFavouriteBooks();
		if(null != books){
			for (Book b: books){
				if(b.getId() == book.getId()){
					if (books.remove(b)){
						Gson gson = new Gson();
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.remove(FAVOURITE_BOOKS);
						editor.putString(FAVOURITE_BOOKS, gson.toJson(books));
						editor.commit();
						return true;
					}
				}
			}
		}
		return false;
	}

}
// todo   230904 comment for git