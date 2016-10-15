package bubokastor.tjob.api;

import java.util.List;

import bubokastor.tjob.content.Article;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticleService {

    @GET("/api/JSON")
    Call<List<Article>> listArticle();
}
