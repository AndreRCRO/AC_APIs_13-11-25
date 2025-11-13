package com.example.retrofit;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<Amiibo> list;
    private Context context;
    private FavoriteDAO dao;
    private Runnable refreshCallback;

    public FavoriteAdapter(Context context, List<Amiibo> list, FavoriteDAO dao, Runnable refreshCallback) {
        this.context = context;
        this.list = list;
        this.dao = dao;
        this.refreshCallback = refreshCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_amiibo_fav, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Amiibo a = list.get(position);

        holder.txtName.setText(a.getName());
        Glide.with(context).load(a.getImage()).into(holder.imgAmiibo);

        holder.btnDelete.setOnClickListener(v -> {
            dao.deleteFavorite(a.getId());
            refreshCallback.run();
            Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
        });

        holder.btnEdit.setOnClickListener(v -> showEditDialog(a));
    }

    private void showEditDialog(Amiibo a) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_amiibo, null);

        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtImage = view.findViewById(R.id.edtImage);

        edtName.setText(a.getName());
        edtImage.setText(a.getImage());

        new AlertDialog.Builder(context)
                .setTitle("Editar Amiibo")
                .setView(view)
                .setPositiveButton("Guardar", (d,w) -> {
                    a.setName(edtName.getText().toString());
                    a.setImage(edtImage.getText().toString());
                    dao.updateFavorite(a);
                    refreshCallback.run();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAmiibo;
        TextView txtName, btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAmiibo = itemView.findViewById(R.id.imgAmiibo);
            txtName = itemView.findViewById(R.id.txtName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
