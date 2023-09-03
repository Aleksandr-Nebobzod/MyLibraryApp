package org.nebobrod.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class WantToReadActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_want_to_read);

		RecyclerView recyclerView = findViewById(R.id.booksRecView);

		/**
		 * for a recyclerView there is an adapter needed. but since we've made BookRecViewAdapter already let's reuse it
		 */
		BookRecViewAdapter adapter = new BookRecViewAdapter(this, "wantToRead");
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		adapter.setBooks(Utils.getInstance(this).getWantToReadBooks());
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}