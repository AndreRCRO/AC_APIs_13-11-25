package com.example.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AmiiboAdapter extends RecyclerView.Adapter<AmiiboAdapter.ViewHolder> {

    private List<Amiibo> amiiboList;
    private Context context;
    private OnAmiiboClick listener;

    public AmiiboAdapter(Context context, List<Amiibo> amiiboList, OnAmiiboClick listener) {
        this.context = context;
        this.amiiboList = amiiboList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_amiibo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Amiibo amiibo = amiiboList.get(position);

        holder.txtName.setText(amiibo.getName());
        Glide.with(context).load(amiibo.getImage()).into(holder.imgAmiibo);

        holder.itemView.setOnClickListener(v -> listener.onClick(amiibo));
    }

    @Override
    public int getItemCount() {
        return amiiboList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAmiibo;
        TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAmiibo = itemView.findViewById(R.id.imgAmiibo);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
