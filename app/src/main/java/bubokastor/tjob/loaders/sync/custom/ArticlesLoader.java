package bubokastor.tjob.loaders.sync.custom;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import bubokastor.tjob.api.ApiFactory;
import bubokastor.tjob.api.ArticleService;
import bubokastor.tjob.api.response.ArticleResponse;
import bubokastor.tjob.api.response.RequestResult;
import bubokastor.tjob.api.response.Response;
import bubokastor.tjob.content.Article;
import retrofit2.Call;

public class ArticlesLoader extends BaseLoader {

    public ArticlesLoader(Context context){
        super(context);
    }
    @Override
    protected Response apiCall() throws IOException {
        ArticleService service = ApiFactory.getArticleService();
        Call<List<Article>> call = service.listArticle();
        List<Article> airports = call.execute().body();
        return new ArticleResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(airports);
    }
}
