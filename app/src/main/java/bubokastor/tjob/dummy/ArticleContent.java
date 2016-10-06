package bubokastor.tjob.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleContent {

    public static final String DEFAULT_URL = "https://www.google.ru/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    public static final List<Article> ITEMS = new ArrayList<>();

    public static final Map<String, Article> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createArticleItem(i));
        }
    }

    private static void addItem(Article item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Article createArticleItem(int position) {
        return new Article(String.valueOf(position), "Item " + position, makeDetails(position),DEFAULT_URL);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class Article{
        public final String id;
        public final String content;
        public final String details;
        public final String urlImage;

        public Article(String id, String content, String details, String urlImage) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.urlImage = urlImage;
        }

        @Override
        public String toString(){ return content; }

        public String getUrlImage(){ return  urlImage; }
    }
}
