package bubokastor.tjob.Items;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleContent {

    public static final String DEFAULT_URL = "https://www.google.ru/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    public static final List<Article> ITEMS = new ArrayList<>();

    public static final Map<String, Article> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createArticleItem(i));
        }
    }

    private static void addItem(Article item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Article createArticleItem(int position) {

        return new Article(String.valueOf(position)
                , "Name"
                , new Date()
                , "author"
                , "description"
                , 0
                , false
                ,DEFAULT_URL);
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

        public Article(String id, String name, Date time, String author, String description,  int count_like, boolean is_like_me, String img_url) {
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
