package bubokastor.tjob.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import bubokastor.tjob.content.Article;
import bubokastor.tjob.database.SQLiteHelper;

public class ArticlesTable {

    public static final Uri URI = SQLiteHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull Article article) {
        context.getContentResolver().insert(URI, toContentValues(article));
    }

    public static void save(Context context, @NonNull List<Article> articles) {
        ContentValues[] values = new ContentValues[articles.size()];
        for (int i = 0; i < articles.size(); i++) {
            values[i] = toContentValues(articles.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static Article fromCursor(@NonNull Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(Columns.ID));
        String name = cursor.getString(cursor.getColumnIndex(Columns.NAME));
        String time = cursor.getString(cursor.getColumnIndex(Columns.TIME));
        String author = cursor.getString(cursor.getColumnIndex(Columns.AUTHOR));
        String description = cursor.getString(cursor.getColumnIndex(Columns.DESCRIPTION));
        int countLike = cursor.getInt(cursor.getColumnIndex(Columns.COUNT_LIKE));
        boolean isLikeMe = cursor.getInt(cursor.getColumnIndex(Columns.IS_LIKE_ME)) > 0;
        String imgUrl = cursor.getString(cursor.getColumnIndex(Columns.IMG_URL));
        return new Article(id, name, time, author, description, countLike,isLikeMe,imgUrl);
    }

    @NonNull
    public static List<Article> listFromCursor(@NonNull Cursor cursor) {
        List<Article> articles = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return articles;
        }
        try {
            do {
                articles.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return articles;
        } finally {
            cursor.close();
        }
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull Article article) {
        ContentValues values = new ContentValues();
        values.put(Columns.ID, article.getId());
        values.put(Columns.NAME, article.getName());
        values.put(Columns.TIME, article.getTime());
        values.put(Columns.AUTHOR, article.getAuthor());
        values.put(Columns.DESCRIPTION, article.getDescription());
        values.put(Columns.COUNT_LIKE, article.getCountLike());
        values.put(Columns.IS_LIKE_ME, article.isLikeMe());
        values.put(Columns.IMG_URL, article.getImgUrl());
        return values;
    }

    public interface Columns {
        String ID = "id";
        String NAME = "name";
        String TIME = "time";
        String AUTHOR = "author";
        String DESCRIPTION = "descr";
        String COUNT_LIKE = "count_like";
        String IS_LIKE_ME = "is_like_me";
        String IMG_URL = "img_url";

    }

    public interface Requests {

        String TABLE_NAME = ArticlesTable.class.getSimpleName();

        String CREATION_REQUEST = "create table if not exists " + TABLE_NAME + " (" +
                Columns.ID + " text(10) NOT NULL, " +
                Columns.NAME + " text not null, " +
                Columns.TIME + " text, " +
                Columns.AUTHOR + " text, " +
                Columns.DESCRIPTION + " text, " +
                Columns.COUNT_LIKE + " integer, " +
                Columns.IS_LIKE_ME + " boolean, " +
                Columns.IMG_URL + " text);";

        String DROP_REQUEST = "drop table if exists " + TABLE_NAME;
    }
}
