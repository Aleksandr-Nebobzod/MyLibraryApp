package org.nebobrod.mylibrary;

import static org.nebobrod.mylibrary.Utils.BOOK_ID_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

	private ImageView imgCover;
	private Button btnAddToCurrent,btnAddToWanted , btnAddToAlreadyRead, btnAddToFav;
	private TextView bookNameText, authorText, pagesText;
	private TextView txtName, txtAuthor, txtPages, txtDescription, txtLongDesc;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);

		initViews();

		Intent intent = getIntent();
		if (null!=intent){
			int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
			if (bookId != -1){
				Book incomingBook = Utils.getInstance(this).getBookById(bookId);
				if (null != incomingBook) {
					setData(incomingBook);

					handleAlreadyRead(incomingBook);
					handleWantToReadBooks(incomingBook);
					handleCurrentlyReadingBooks(incomingBook);
					handleFavoriteBooks(incomingBook);
				}
			}
		}
	}

	/**
	 * Analyse data and provide a relevant button enabling
	 * @param book the book for analysis
	 */
	private void handleFavoriteBooks(final Book book) {
		ArrayList<Book> favouriteBooks = Utils.getInstance(this).getFavouriteBooks(); // a kind of currently

		boolean bookExistsInList = false;
		for (Book b: favouriteBooks){
			if (b.getId() == book.getId()){
				bookExistsInList = true;
			}
		}
		if (bookExistsInList){
			btnAddToFav.setEnabled(false);
		} else {
			btnAddToFav.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (Utils.getInstance(BookActivity.this).addToFavourite(book)){
						Toast.makeText(BookActivity.this, book.getName() + " added", Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(BookActivity.this, FavoriteActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(BookActivity.this, "not enough successful", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	/**
	 * Analyse data and provide a relevant button enabling
	 * @param book the book for analysis
	 */
	private void handleCurrentlyReadingBooks(final Book book) {
		ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks(); // a kind of currently

		boolean bookExistsInList = false;
		for (Book b: currentlyReadingBooks){
			if (b.getId() == book.getId()){
				bookExistsInList = true;
			}
		}
		if (bookExistsInList){
			btnAddToCurrent.setEnabled(false);
		} else {
			btnAddToCurrent.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)){
						Toast.makeText(BookActivity.this, book.getName() + " added", Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(BookActivity.this, "not enough successful", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	/**
	 * Analyse data and provide a relevant button enabling
	 * @param book the book for analysis
	 */
	private void handleWantToReadBooks(final Book book) {
		ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

		boolean bookExistsInList = false;
		for (Book b: wantToReadBooks){
			if (b.getId() == book.getId()){
				bookExistsInList = true;
			}
		}
		if (bookExistsInList){
			btnAddToWanted.setEnabled(false);
		} else {
			btnAddToWanted.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (Utils.getInstance(BookActivity.this).addToWantToRead(book)){
						Toast.makeText(BookActivity.this, book.getName() + " added", Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(BookActivity.this, "not enough successful", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	/**
	 * Analyse data and provide a relevant button enabling
	 * @param book the book for analysis
	 */
	private void handleAlreadyRead(final Book book) {
		ArrayList<Book> alreadyReadBook = Utils.getInstance(this).getAlreadyReadBooks();

		boolean bookExistsInList = false;
		for (Book b: alreadyReadBook){
			if (b.getId() == book.getId()){
				bookExistsInList = true;
			}
		}
		if (bookExistsInList){
			btnAddToAlreadyRead.setEnabled(false);
		} else {
			btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
						Toast.makeText(BookActivity.this, book.getName() + " added", Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(BookActivity.this, "not enough successful", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	private void setData(Book book) {
		txtName.setText(book.getName());
		txtAuthor.setText(book.getAuthor());
		txtPages.setText(String.valueOf(book.getPages()));
		txtDescription.setText(book.getShortDesc());
		txtLongDesc.setText(book.getLongDesc());
//		imgCover.setImageURI(Uri.parse( book.getImgUrl()));
		Glide.with(this)
				.asBitmap().load(book.getImgUrl())
				.into(imgCover);
	}

	private void initViews() {
		imgCover = findViewById(R.id.imgCover);
		btnAddToCurrent = findViewById(R.id.btnAddToCurrent);
		btnAddToWanted = findViewById(R.id.btnAddToWanted);
		btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
		btnAddToFav = findViewById(R.id.btnAddToFav);

		bookNameText = findViewById(R.id.bookNameText);
		authorText= findViewById(R.id.authorText);
		pagesText= findViewById(R.id.pagesText);

		txtName = findViewById(R.id.txtName);
		txtAuthor = findViewById(R.id.txtAuthor);
		txtPages = findViewById(R.id.txtPages);
		txtDescription = findViewById(R.id.txtDescription);
		txtLongDesc = findViewById(R.id.txtLongDesc);

	}
}