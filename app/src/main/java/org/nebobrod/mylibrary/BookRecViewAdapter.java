package org.nebobrod.mylibrary;


import static org.nebobrod.mylibrary.Utils.BOOK_ID_KEY;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder> {
	private static final String TAG = "BookRecViewAdapter";

	private ArrayList<Book> books = new ArrayList<>();
	private Context mContext;
	private String parentActivity;

	public BookRecViewAdapter(Context mContext, String parentActivity) {
		this.mContext = mContext;
		this.parentActivity = parentActivity;
		Log.d(TAG, mContext.getClass().getName() + " vs. " + parentActivity);
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Log.d(TAG, "onBindViewHolder: Called");
		holder.txtName.setText(books.get(position).getName());
		Glide.with(mContext)
				.asBitmap()
				.load(books.get(position).getImgUrl())
				.into(holder.imgBook);

		holder.parent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Toast.makeText(mContext, books.get(position).getName()+" Selected", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(mContext, BookActivity.class);
				intent.putExtra(BOOK_ID_KEY, books.get(position).getId());
				mContext.startActivity(intent);
			}
		});
		
		holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				//Toast.makeText(mContext, "Editing", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(mContext, EditBookActivity.class);
				intent.putExtra(BOOK_ID_KEY, books.get(position).getId());
				mContext.startActivity(intent);
				return true;
			}
		});

		holder.txtAuthor.setText(books.get(position).getAuthor());
		holder.txtDescription.setText(books.get(position).getShortDesc());

		if(books.get(position).isExpanded()){
			TransitionManager.beginDelayedTransition(holder.parent);
			holder.expandedRelLayout.setVisibility(View.VISIBLE);
			holder.imgDown.setVisibility(View.GONE);
			holder.btnDelete.setVisibility(View.VISIBLE);
			if(parentActivity.equals("allBooks")){
				holder.btnDelete.setVisibility(View.GONE);
			} else if (parentActivity.equals("alreadyRead")) {
				//holder.btnDelete.setVisibility(View.VISIBLE);
				holder.btnDelete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setMessage(books.get(position).getName() + "is going to be deleted");
						builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (Utils.getInstance(mContext).removeFromAlreadyRead(books.get(position))){
									Toast.makeText(mContext, "Book has been removed", Toast.LENGTH_SHORT).show();
									notifyDataSetChanged();
								} else {
									Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
								}
							}
						});
						builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(mContext, "Deleting is rejected", Toast.LENGTH_SHORT).show();
							}
						});

						builder.create().show();
					}
				});
			} else if (parentActivity.equals("wantToRead")) {
				holder.btnDelete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setMessage(books.get(position).getName() + "is going to be deleted");
						builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (Utils.getInstance(mContext).removeFromAWantToRead(books.get(position))){
									Toast.makeText(mContext, "Book has been removed", Toast.LENGTH_SHORT).show();
									notifyDataSetChanged();
								} else {
									Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
								}
							}
						});
						builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(mContext, "Deleting is rejected", Toast.LENGTH_SHORT).show();
							}
						});

						builder.create().show();
					}
				});
			} else if (parentActivity.equals("currentlyReading")) {
				holder.btnDelete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setMessage(books.get(position).getName() + "is going to be deleted");
						builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (Utils.getInstance(mContext).removeFromCurrentlyReading(books.get(position))){
									Toast.makeText(mContext, "Book has been removed", Toast.LENGTH_SHORT).show();
									notifyDataSetChanged();
								} else {
									Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
								}
							}
						});
						builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(mContext, "Deleting is rejected", Toast.LENGTH_SHORT).show();
							}
						});

						builder.create().show();
					}
				});
			} else { // favBooks
				holder.btnDelete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setMessage(books.get(position).getName() + "is going to be deleted");
						builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (Utils.getInstance(mContext).removeFromFavourite(books.get(position))){
									Toast.makeText(mContext, "Book has been removed", Toast.LENGTH_SHORT).show();
									notifyDataSetChanged();
								} else {
									Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
								}
							}
						});
						builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(mContext, "Deleting is rejected", Toast.LENGTH_SHORT).show();
							}
						});

						builder.create().show();
					}
				});
			}
		} else {
			TransitionManager.beginDelayedTransition(holder.parent);
			holder.expandedRelLayout.setVisibility(View.GONE);
			holder.imgDown.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int getItemCount() {
		return books.size();
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
		notifyDataSetChanged();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{
		private CardView parent;
		private ImageView imgBook;
		private TextView txtName;

		private ImageView imgUp, imgDown;
		private RelativeLayout expandedRelLayout;
		private TextView txtAuthor, txtDescription;

		private TextView btnDelete;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			parent = itemView.findViewById(R.id.parent);
			imgBook = itemView.findViewById(R.id.imgView);
			txtName = itemView.findViewById(R.id.txtBookName); // вот после определения переменных делаем extends наш адаптер ⇧, а потом [Ctrl]+[i]

			imgDown = itemView.findViewById(R.id.btnArrowDown);
			imgUp = itemView.findViewById(R.id.btnArrowUp);
			expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);
			txtAuthor = itemView.findViewById(R.id.txtAuthor);
			txtDescription = itemView.findViewById(R.id.txtShortDesc);

			btnDelete = itemView.findViewById(R.id.btnDelete);

			imgDown.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Book book = books.get(getAdapterPosition());
					book.setExpanded(!book.isExpanded());
					notifyItemChanged(getAdapterPosition());
				}
			});

			imgUp.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Book book = books.get(getAdapterPosition()); // the same code
					book.setExpanded(!book.isExpanded());		// 'cos of this inversion
					notifyItemChanged(getAdapterPosition());
				}
			});

		}
	}
}
