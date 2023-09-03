package org.nebobrod.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class AlreadyReadBookActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_already_read_book);

		RecyclerView recyclerView = findViewById(R.id.booksRecView);

		/**
		 * for a recyclerView there is an adapter needed. but since we've made BookRecViewAdapter already let's reuse it
		 */
		BookRecViewAdapter adapter = new BookRecViewAdapter(this, "alreadyRead");
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		adapter.setBooks(Utils.getInstance(this).getAlreadyReadBooks());
	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();

		Intent intent = new Intent(this, MainActivity.class);
		//TODO интересно посмотреть, как этот стек живёт в реале
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}