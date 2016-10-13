package bubokastor.tjob.Items;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import bubokastor.tjob.repository.Database;

import static bubokastor.tjob.ArticleListActivity.context;

public class ArticleContent {
    public final List<Article> ITEMS = new ArrayList<>();
    public static final Map<String, Article> ITEM_MAP = new HashMap<>();

    private static Database mDatabase;
    private static SQLiteDatabase mSqLiteDatabase;

    private Context context;

    private boolean mIsUsedDB = false;
    public ArticleContent(Context context){
        this.context = context;
        initDB();
    }



    private void initDB() {
        mDatabase = new Database(context, "Database1.db", null, 1);
        mSqLiteDatabase = mDatabase.getWritableDatabase();
        mIsUsedDB = true;
        Cursor cursor = mSqLiteDatabase.query(Database.DATABASE_TABLE, new String[] {
                Database.ID_COLUMN,
                Database.NAME_COLUMN,
                Database.TIME_COLUMN,
                Database.AUTHOR_COLUMN,
                Database.DESCRIPTION_COLUMN,
                Database.COUNT_LIKE_COLUMN,
                Database.IS_LIKE_ME_COLUMN,
                Database.IMG_URL_COLUMN},
                null, null,
                null, null, null) ;

        while (cursor.moveToNext()){
            Article art = new Article(
                    cursor.getString(cursor.getColumnIndex(Database.ID_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(Database.NAME_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(Database.TIME_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(Database.AUTHOR_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(Database.DESCRIPTION_COLUMN)),
                    cursor.getInt(cursor.getColumnIndex(Database.COUNT_LIKE_COLUMN)),
                    cursor.getInt(cursor.getColumnIndex(Database.IS_LIKE_ME_COLUMN) ) > 0,
                    cursor.getString(cursor.getColumnIndex(Database.IMG_URL_COLUMN))
                    );
            ITEMS.add(art);
            ITEM_MAP.put(art.id, art);
        }
        cursor.close();

    }

    public void addItem(Article item) {
        if (!ITEM_MAP.containsKey(item.id)) {
            if (mIsUsedDB) {
                ContentValues values = new ContentValues();
                values.put(Database.ID_COLUMN, item.id);
                values.put(Database.NAME_COLUMN, item.name);
                values.put(Database.TIME_COLUMN, item.time);
                values.put(Database.AUTHOR_COLUMN, item.author);
                values.put(Database.DESCRIPTION_COLUMN, item.description);
                values.put(Database.COUNT_LIKE_COLUMN, item.count_like);
                values.put(Database.IS_LIKE_ME_COLUMN, (item.is_like_me ? "1" : "0"));
                values.put(Database.IMG_URL_COLUMN, item.img_url);
                mSqLiteDatabase.insert(Database.DATABASE_TABLE, null, values);
            }
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        }
    }

    public static class Article{
        public final String id;

        public final String name;
        //public final Date time;
        public final String time;
        public final String author;
        public final String description;
        public int count_like;
        public boolean is_like_me;
        public final String img_url;

        public Article(String id
                , String name
                , String time
                , String author
                , String description
                , int count_like
                , boolean is_like_me
                , String img_url) {
            this.id = id;
            this.name = name;
            this.time = time;
            this.author = author;
            this.description = description;
            this.count_like = count_like;
            this.is_like_me = is_like_me;
            this.img_url = img_url;
        }

        public ContentValues toContentValues(){
            ContentValues values = new ContentValues();
            values.put(Database.ID_COLUMN, this.id);
            values.put(Database.NAME_COLUMN, this.name);
            values.put(Database.TIME_COLUMN, this.time);
            values.put(Database.AUTHOR_COLUMN, this.author);
            values.put(Database.DESCRIPTION_COLUMN, this.description);
            values.put(Database.COUNT_LIKE_COLUMN, this.count_like);
            values.put(Database.IS_LIKE_ME_COLUMN, this.is_like_me);
            values.put(Database.IMG_URL_COLUMN, this.img_url);
            return  values;
        }
        public void update(String column, String[] value){
            mSqLiteDatabase.update(Database.DATABASE_TABLE, this.toContentValues(),
                    column,
                    value);
        }
    }
}
