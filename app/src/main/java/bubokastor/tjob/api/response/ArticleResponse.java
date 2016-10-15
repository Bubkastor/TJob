package bubokastor.tjob.api.response;


import android.content.Context;
import android.util.Log;

import java.util.List;

import bubokastor.tjob.content.Article;

public class ArticleResponse extends Response {
    @Override
    public void save(Context context){
        List<Article> articles = getTypedAnswer();
        if(articles != null){
            Log.d("ArticleResponse", "SUCCESS");
        }
    }
}
