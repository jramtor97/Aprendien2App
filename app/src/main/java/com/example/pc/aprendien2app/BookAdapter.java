package com.example.pc.aprendien2app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<Book> data;

    public BookAdapter(ArrayList<Book> data) {
        this.data = data;
    }

    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_items, parent, false);
        return new BookViewHolder(row);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {

        Book x = data.get(position);

        holder.title.setText(x.getTitle());
        holder.image.setImageResource(x.getImage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BookViewHolder  extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView image;


        public BookViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.bookName);
            image = (ImageView)itemView.findViewById(R.id.bookImage);

        }
    }
}
