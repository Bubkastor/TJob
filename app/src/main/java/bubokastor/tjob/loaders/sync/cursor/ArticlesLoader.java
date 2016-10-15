package bubokastor.tjob.loaders.sync.cursor;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.List;

import bubokastor.tjob.api.ApiFactory;
import bubokastor.tjob.api.ArticleService;
import bubokastor.tjob.content.Article;
import bubokastor.tjob.database.table.ArticlesTable;
import retrofit2.Call;

public class ArticlesLoader extends BaseLoader {
    public ArticlesLoader(Context context){
        super(context);
    }
    @Override
    protected Cursor apiCall() throws IOException {
        ArticleService service = ApiFactory.getArticleService();
        Call<List<Article>> call = service.listArticle();
        List<Article> airports = call.execute().body();
        ArticlesTable.save(getContext(), airports);
        return getContext().getContentResolver().query(ArticlesTable.URI,
                null, null, null, null);
    }
}
