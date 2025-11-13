package com.example.retrofit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

    private FavoriteDB dbHelper;

    public FavoriteDAO(Context context) {
        dbHelper = new FavoriteDB(context);
    }

    public void addFavorite(Amiibo amiibo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", amiibo.getId());
        values.put("name", amiibo.getName());
        values.put("image", amiibo.getImage());

        db.insertWithOnConflict(FavoriteDB.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<Amiibo> getFavorites() {
        List<Amiibo> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + FavoriteDB.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            int idxId = cursor.getColumnIndexOrThrow("id");
            int idxName = cursor.getColumnIndexOrThrow("name");
            int idxImage = cursor.getColumnIndexOrThrow("image");

            do {
                Amiibo a = new Amiibo();
                a.setId(cursor.getString(idxId));
                a.setName(cursor.getString(idxName));
                a.setImage(cursor.getString(idxImage));

                list.add(a);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public void updateFavorite(Amiibo amiibo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", amiibo.getName());
        values.put("image", amiibo.getImage());

        db.update(FavoriteDB.TABLE_NAME, values, "id=?", new String[]{amiibo.getId()});
    }

    public void deleteFavorite(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(FavoriteDB.TABLE_NAME, "id=?", new String[]{id});
    }
}
