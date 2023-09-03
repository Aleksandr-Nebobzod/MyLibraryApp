package org.nebobrod.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private TextView txtAppName, txtLiense;
	private Button btnAllBooks, btnCurrent, btnRead, btnWantRead, btnFavorites,  btnNewBook, btnAbout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Utils.getInstance(this); // это чтоб оживить класс для всех остальных активитиз

		initViews();

		btnFavorites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
				startActivity(intent);
			}
		});

		btnWantRead.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, WantToReadActivity.class);
				startActivity(intent);
			}
		});

		btnCurrent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CurrentlyReadingActivity.class);
				startActivity(intent);
			}
		});

		btnAllBooks.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
				startActivity(intent);
			}
		});

		btnRead.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AlreadyReadBookActivity.class);
				startActivity(intent);
			}
		});

		btnNewBook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, EditBookActivity.class);
				intent.putExtra("bookId", 0); // 0 means new book, so Edit will create new book
				startActivity(intent);
			}
		});

		btnAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);{
					builder.setTitle(R.string.app_name);
					builder.setMessage("The app is made by\n" +
							"Heaven_rover\n" +
							"with video and comments of\n"+
							"Meisam @ freeCodeCamp.org");
					builder.setNegativeButton(R.string.s_no, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
					builder.setPositiveButton(R.string.s_visit_site, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO: 25.08.2023 move to website
							Intent intent = new Intent(MainActivity.this, WebActivity.class);
							intent.putExtra("url", "https://vk.com/public216345855");
							startActivity(intent);
						}
					});

					builder.setCancelable(false);
					builder.create().show();
				}
			}
		});


	}

	private void initViews() {

		btnAllBooks = findViewById(R.id.btnAllBooks);
		btnCurrent = findViewById(R.id.btnCurrent);
		btnRead = findViewById(R.id.btnRead);
		btnWantRead = findViewById(R.id.btnWantRead);
		btnFavorites = findViewById(R.id.btnFavourites);
		btnNewBook = findViewById(R.id.btnNewBook);
		btnAbout = findViewById(R.id.btnAbout);
	}
}