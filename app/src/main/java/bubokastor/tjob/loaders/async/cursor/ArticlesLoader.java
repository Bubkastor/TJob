package bubokastor.tjob.loaders.async.cursor;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

import bubokastor.tjob.api.ApiFactory;
import bubokastor.tjob.api.ArticleService;
import bubokastor.tjob.api.RetrofitCallback;
import bubokastor.tjob.content.Article;
import bubokastor.tjob.database.table.ArticlesTable;
import retrofit2.Call;
import retrofit2.Response;

public class ArticlesLoader extends BaseLoader {
    private final ArticleService mArticleService;

    public ArticlesLoader(Context context) {
        super(context);
        mArticleService = ApiFactory.getArticleService();
    }

    @Override
    protected void onForceLoad() {
        Call<List<Article>> call = mArticleService.listArticle();
        call.enqueue(new RetrofitCallback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful()) {
                    ArticlesTable.clear(getContext());
                    ArticlesTable.save(getContext(), response.body());
                    Cursor cursor = getContext().getContentResolver().query(ArticlesTable.URI,
                            null, null, null, null);
                    deliverResult(cursor);
                } else {
                    deliverResult(null);
                }
            }
        });
    }

}
