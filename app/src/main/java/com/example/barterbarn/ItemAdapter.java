package com.example.barterbarn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ItemAdapter extends Adapter<ItemAdapter.ItemViewHolder> implements Filterable {
    private final ArrayList<Item> filteredResults;
    private final ArrayList<Item> allResults;


    // Search algorithm
    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Item> sortedList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                sortedList.addAll(allResults);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : allResults) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        sortedList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = sortedList;

            return results;
        }

        // Connection notifier
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredResults.clear();
            filteredResults.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    private OnItemClickListener mListener;

    public ItemAdapter(ArrayList<Item> items) {
        this.filteredResults = items;
        this.allResults = new ArrayList<>(items);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view, mListener);
    }

    // Text binding
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {    // Assign value
        Item currentItem = filteredResults.get(position);
        holder.textView.setText(currentItem.getTitle());
        holder.locationCity.setText(currentItem.getLocation());
        String url = currentItem.getmImageUrl();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String sellerID =  Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("listings").child(currentItem.getUploadId());
                if (sellerID.equals(currentItem.getSellerId())){
                    dbReference.removeValue();
                }
            }
        });
        if (!sellerID.equals(currentItem.getSellerId())){
            holder.deleteBtn.setVisibility(View.INVISIBLE);
        }


        try {
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
            holder.imageView.setImageBitmap(myBitmap);
        } catch (IOException e) {
            Log.i("loadPicture", "Profile doesn't have picture");
        }
    }
    public Item getItemAt(int position){
        return filteredResults.get(position);
    }
    @Override
    public int getItemCount() {
        return filteredResults.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public ArrayList<Item> getFilteredResults() {
        return filteredResults;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ItemViewHolder extends ViewHolder {

        public TextView textView;
        public TextView locationCity;
        public ImageView imageView;
        public ImageButton deleteBtn;


        public ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) { // Link variable
            super(itemView);
            textView = itemView.findViewById(R.id.item_title);
            locationCity = itemView.findViewById(R.id.item_distance);
            imageView = itemView.findViewById(R.id.imageView2);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
