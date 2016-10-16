package bubokastor.tjob.api;

import java.util.List;

import bubokastor.tjob.content.Article;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ArticleService {

    @GET("/api/JSON")
    Call<List<Article>> listArticle();

    @POST("/api/JSON/{id}")
    Call<Article> sendArticle(@Path("id") String id, @Body Article article);

}
