package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvApi, rvFavorites;
    AmiiboAdapter apiAdapter;
    FavoriteAdapter favAdapter;
    FavoriteDAO favoriteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvApi = findViewById(R.id.rvApi);
        rvFavorites = findViewById(R.id.rvFavorites);

        rvApi.setLayoutManager(new LinearLayoutManager(this));
        rvFavorites.setLayoutManager(new LinearLayoutManager(this));

        favoriteDAO = new FavoriteDAO(this);

        loadAmiibosFromAPI();
        loadFavorites();
    }

    private void loadAmiibosFromAPI() {
        AmiiboApi api = RetrofitClient.getRetrofitInstance().create(AmiiboApi.class);

        api.getAmiibos().enqueue(new Callback<AmiiboResponse>() {
            @Override
            public void onResponse(Call<AmiiboResponse> call, Response<AmiiboResponse> response) {
                if (response.isSuccessful()) {

                    List<Amiibo> lista = response.body().getAmiibo();

                    if (lista.size() > 20) lista = lista.subList(0, 20);

                    apiAdapter = new AmiiboAdapter(
                            MainActivity.this,
                            lista,
                            amiibo -> {
                                favoriteDAO.addFavorite(amiibo);
                                loadFavorites();
                                Toast.makeText(MainActivity.this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
                            }
                    );

                    rvApi.setAdapter(apiAdapter);
                }
            }

            @Override
            public void onFailure(Call<AmiiboResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFavorites() {
        List<Amiibo> favs = favoriteDAO.getFavorites();

        favAdapter = new FavoriteAdapter(
                this,
                favs,
                favoriteDAO,
                this::loadFavorites
        );

        rvFavorites.setAdapter(favAdapter);
    }
}
