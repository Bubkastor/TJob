package bubokastor.tjob.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bubok on 10.10.2016.
 */

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "Article";
    public static final String ID_COLUMN  = "id";
    public static final String NAME_COLUMN = "name";
    public static final String TIME_COLUMN = "time_article";
    public static final String AUTHOR_COLUMN = "author";
    public static final String DESCRIPTION_COLUMN = "descrp";
    public static final String COUNT_LIKE_COLUMN = "count_like";
    public static final String IS_LIKE_ME_COLUMN = "is_like_me";
    public static final String IMG_URL_COLUMN = "img_url";

    public static final String DATABASE_CREATE_SCRIPT =
            "create table " + DATABASE_TABLE + "( " + ID_COLUMN + " integer primary key," +
                    NAME_COLUMN + " text not null," +
                    TIME_COLUMN + " text," +
                    AUTHOR_COLUMN + " text," +
                    DESCRIPTION_COLUMN + " text," +
                    COUNT_LIKE_COLUMN +" integer," +
                    IS_LIKE_ME_COLUMN +" boolean," +
                    IMG_URL_COLUMN +" text);";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
