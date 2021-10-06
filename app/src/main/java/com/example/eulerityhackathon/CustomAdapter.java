package com.example.eulerityhackathon;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eulerityhackathon.databinding.RvItemBinding;

import java.util.ArrayList;
import java.util.List;

import static com.example.eulerityhackathon.MainActivity.TAG;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private List<ImageModel> list = new ArrayList<>();
    private Context context;

    public void setList(ArrayList<ImageModel> list) {
        Log.i(TAG, "setList: " + list.size());
        this.list = list;
    }

    public CustomAdapter(Context context) {
        Log.i(TAG, "CustomAdapter: construct ");
        this.context = context;
    }


    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        return new CustomViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.rv_item,
                        parent,
                        false
                ));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

     class CustomViewHolder extends  RecyclerView.ViewHolder {
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(TAG, "CustomViewHolder: ");

        }

        public void bind(ImageModel im) {
            RvItemBinding binding = RvItemBinding.bind(itemView);
            binding.ifv.setImageBitmap(im.getBitmap());
            binding.itemCl.setOnClickListener(view -> {
                Intent intent = new Intent(context, FilterActivity.class);
                intent.putExtra("bitmap", im.getBitmap());
                intent.putExtra("url", im.getUrl());
                context.startActivity(intent);
            });
            Log.i(TAG, "bind: ");
        }
    }
}
