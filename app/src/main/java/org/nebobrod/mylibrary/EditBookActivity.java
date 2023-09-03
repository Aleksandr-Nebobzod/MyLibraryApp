package org.nebobrod.mylibrary;

import static org.nebobrod.mylibrary.Utils.BOOK_ID_KEY;
import static org.nebobrod.mylibrary.Utils.intFromString;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class EditBookActivity extends AppCompatActivity {
	private static final String TAG = "EditBookActivity";

	private int editMode = 0; // means new
	private boolean isEdited = false;
	private Book book;
	private TextView txtId, bookNameText, authorText, pagesText;
	private EditText edtImageUrl, txtName, txtAuthor, txtPages, txtDescription, txtLongDesc, txtComment;
	private ImageView imgCover;
	private Button btnApplyUrl, btnCancel, btnSave, btnAddToWanted, btnAddToAlreadyRead, btnAddToFav;

	private TextInputLayout edtDiscoveredInYear;

	// Registers a photo picker activity launcher in single-select mode.
	ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
			registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
				// Callback is invoked after the user selects a media item or closes the
				// photo picker.
				if (uri != null) {
					Log.d("PhotoPicker", "Selected URI: " + uri);
					imgCover.setImageURI(uri);
					edtImageUrl.setText(uri.getPath().toString());
				} else {
					Log.d("PhotoPicker", "No media selected");
				}
			});


	/**
	 * allows to know if data was edited
	 */// TODO: 30.08.2023 thing about demolition of that:
	private TextWatcher textWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
			Log.d(TAG, "afterTextChanged: 1");
			if (formDiffersOfData()){
				isEdited = true;
				if (formCheckOk()) {
					btnSave.setEnabled(true);
				}
			} else {
				isEdited = false;
				btnSave.setEnabled(false);
			}
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			Log.d(TAG, "beforeTextChanged: 2");
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
//			Log.d(TAG, "onTextChanged: 3");
		}
	};

	private boolean formCheckOk() {
		if (txtName.getText().toString().equals("")){
			//	it's an ugly error notes and drawables beside
//			Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_question_red);
//			customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
//			txtName.setError("Error", customErrorDrawable);
			txtName.setError("Name can't be empty");
			return false;
		}	else {
			// TODO: 31.08.2023 minor  is it necessary to drop off the error? --
			// txtName.setError("");
		}
		return true;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_book);

		initViews();
		Intent intent = getIntent();

		// defining edit mode: 0-new, others-edit
		if (null != intent) {
			int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
			editMode = bookId;
			switch (bookId) {
				case -1:
					Toast.makeText(this, "no book", Toast.LENGTH_SHORT).show();
					break;
				case 0:
					book = new Book(0, "", "", 0, "", "", "");
					txtId.setText(Utils.getInstance(this).getNextBookId()+"");
					//setData(book);
					break;
				default:
					book = Utils.getInstance(this).getBookById(bookId);
					setData(book);
					break;
			}
		}

		imgCover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String mimeType = "image/*";
				pickMedia.launch(new PickVisualMediaRequest.Builder()
						.setMediaType(new ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType))
						.build());
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed ();
			}
		});

		txtName.addTextChangedListener(textWatcher);
		txtAuthor.addTextChangedListener(textWatcher);
		txtPages.addTextChangedListener(textWatcher);
		//edtDiscoveredInYear.getChildAt(0).add...
		edtImageUrl.addTextChangedListener(textWatcher);
		txtDescription.addTextChangedListener(textWatcher);
		txtLongDesc.addTextChangedListener(textWatcher);

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isEdited){
					if (formCheckOk()){
						book.setId(intFromString(txtId.getText().toString()));
						book.setName(txtName.getText().toString());
						book.setAuthor(txtAuthor.getText().toString());
						book.setPages(intFromString(txtPages.getText().toString()));
						book.setImgUrl(edtImageUrl.getText().toString());
						book.setShortDesc(txtDescription.getText().toString());
						book.setLongDesc(txtLongDesc.getText().toString());
						//recording to Gson
						switch (editMode){
							case 0:
								if (Utils.getInstance(EditBookActivity.this).addToAllBook(book)){
									Toast.makeText(EditBookActivity.this, book.getName() + " added", Toast.LENGTH_SHORT).show();
									btnCancel.performClick();
								} else {
									Toast.makeText(EditBookActivity.this, "not enough successful", Toast.LENGTH_SHORT).show();
								}
								break;
							default:
								if (Utils.getInstance(EditBookActivity.this).updateBookInAllBook(book)){
									Toast.makeText(EditBookActivity.this, book.getName() + " updated", Toast.LENGTH_SHORT).show();
									btnCancel.performClick();
								} else {
									Toast.makeText(EditBookActivity.this, "update not successful", Toast.LENGTH_SHORT).show();
								}
						}

					} else {
						Toast.makeText(EditBookActivity.this, "Get rid of mistakes", Toast.LENGTH_SHORT).show();
					}
				} else {
					btnCancel.performClick();
				}
			}
		});

	} //onCreate

	private boolean formDiffersOfData() {
		boolean result = false;
		if (null != book) {
			result |= (!book.getName().equals(txtName.getText().toString()));
			result |= (!book.getAuthor().equals(txtAuthor.getText().toString()));
			int temp = 0;
			try {
				temp = new Integer(txtPages.getText().toString()+"");
			}   catch(NumberFormatException nfe) {
				Log.d(TAG, "compareToBook: Not an Integer");
				temp = 0;
			}
			result |= (book.getPages() != temp);
			result |= (!book.getImgUrl().equals(edtImageUrl.getText().toString()));
			result |= (!book.getShortDesc().equals(txtDescription.getText().toString()));
			result |= (!book.getLongDesc().equals(txtLongDesc.getText().toString()));
			// TODO: 27.08.2023  this field "Comment"'sto be added onto Book:
			// 	result = |= (!book.getComment().equals(txtComment.getText().toString()));
		}
		return result;
	}


	private void setData(Book incomingBook) {
		txtId.setText(""+incomingBook.getId());
		edtImageUrl.setText(incomingBook.getImgUrl());
		txtName.setText(incomingBook.getName());
		txtAuthor.setText(incomingBook.getAuthor());
		txtPages.setText(String.valueOf(incomingBook.getPages()));
		txtDescription.setText(incomingBook.getShortDesc());
		txtLongDesc.setText(incomingBook.getLongDesc());
		Glide.with(this)
				.asBitmap().load(incomingBook.getImgUrl())
				.into(imgCover);
	}

	private void initViews() {
		{
			edtDiscoveredInYear = findViewById(R.id.discovered_in_year);

			imgCover = findViewById(R.id.imgCover);

			txtId = findViewById(R.id.txtId);
			edtImageUrl = findViewById(R.id.edtImageUrl);
			txtName = findViewById(R.id.txtName);
			txtAuthor = findViewById(R.id.txtAuthor);
			txtPages = findViewById(R.id.txtPages);
			txtDescription = findViewById(R.id.txtDescription);
			txtLongDesc = findViewById(R.id.txtLongDesc);
			txtComment = findViewById(R.id.txtComment);

			btnApplyUrl = findViewById(R.id.btnApplyUrl);
			btnCancel = findViewById(R.id.btnCancel);
			btnSave = findViewById(R.id.btnSave);
			btnAddToWanted = findViewById(R.id.btnAddToWanted);
			btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
			btnAddToFav = findViewById(R.id.btnAddToFav);
		}
	}


}
