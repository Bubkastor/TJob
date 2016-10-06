package bubokastor.tjob.Items;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleContent {

    public final List<Article> ITEMS = new ArrayList<>();
    public final Map<String, Article> ITEM_MAP = new HashMap<>();

    public void addItem(Article item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class Article{
        public final String id;

        public final String name;
        public final Date time;
        public final String author;
        public final String description;
        public int count_like;
        public boolean is_like_me;
        public final String img_url;

        public Article(String id
                , String name
                , Date time
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
            this.img_url = img_url;
            this.count_like = count_like;
            this.is_like_me = is_like_me;
        }
    }
}
