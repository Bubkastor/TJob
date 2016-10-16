package bubokastor.tjob.loaders;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

public class LocalArticlesLoader extends CursorLoader {

    public LocalArticlesLoader(Context context, Uri uri,
            String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
}
