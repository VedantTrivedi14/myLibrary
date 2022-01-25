package com.tatvasoftassignment.mylibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> implements Filterable {
    ArrayList<Books> bookListFull;
    ArrayList<Books> bookList;
    Context ctx;


    public RecyclerViewAdapter(ArrayList<Books> bookList, Context ctx) {
        this.bookList = bookList;
        this.ctx = ctx;
        bookListFull = new ArrayList<>(bookList);
    }

    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Books> filterArrayList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterArrayList.addAll(bookListFull);
            } else {
                for (Books item : bookListFull) {
                    if (item.getBookNames().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterArrayList.add(item);
                    }
                    if (item.getBookAuthorNames().equals(constraint.toString().toLowerCase()) && !item.getBookNames().equals(item.getBookAuthorNames())) {
                        filterArrayList.add(item);
                    }
                    if (item.getBookGenre().toLowerCase().equals(constraint.toString().toLowerCase())) {
                        filterArrayList.add(item);
                    }
                    if (item.getBookType().toLowerCase().equals(constraint.toString().toLowerCase())) {
                        filterArrayList.add(item);
                    }
                }

            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterArrayList;
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bookList.clear();
            bookList.addAll((ArrayList<Books>) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater recyclerInflater = LayoutInflater.from(ctx);
        @SuppressLint("InflateParams") View recyclerView = recyclerInflater.inflate(R.layout.booklist_view_adapter, null);
        return new RecyclerViewHolder(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        Books book_temp = bookList.get(position);
        holder.t1.setText(book_temp.getBookNames());
        holder.t2.setText(book_temp.getBookAuthorNames());
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx, BookDetailsActivity.class);

            int id = book_temp.getId();
            i.putExtra("id", id);
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    public void sortDataByBookName() {
        Collections.sort(bookList, (o1, o2) -> o1.getBookNames().compareToIgnoreCase(o2.getBookNames()));
        notifyDataSetChanged();
    }


    public void sortDataByBookLaunchDate() {
        Collections.sort(bookList, (o1, o2) -> o1.getBookLaunchDate().compareTo(o2.getBookLaunchDate()));
        notifyDataSetChanged();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.txtBookName);
            t2 = itemView.findViewById(R.id.txtAutherName);

        }

    }
}