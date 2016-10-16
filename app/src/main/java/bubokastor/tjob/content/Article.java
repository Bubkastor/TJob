package bubokastor.tjob.content;

import com.google.gson.annotations.SerializedName;

public class Article{

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("time")
    private String mTime;

    @SerializedName("author")
    private String mAuthor;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("count_like")
    private int mCountLike;

    @SerializedName("is_like_me")
    private boolean mIsLikeMe;

    @SerializedName("img_url")
    private String mImgUrl;

    public  Article(){
    }

    public Article(String id, String name, String time, String author, String description,
            int countLike, boolean isLikeMe, String imgUrl) {
        mId = id;
        mName = name;
        mTime = time;
        mAuthor = author;
        mDescription = description;
        mCountLike = countLike;
        mIsLikeMe = isLikeMe;
        mImgUrl = imgUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getCountLike() {
        return mCountLike;
    }

    public void setCountLike(int countLike) {
        mCountLike = countLike;
    }

    public boolean isLikeMe() {
        return mIsLikeMe;
    }

    public void setLikeMe(boolean likeMe) {
        mIsLikeMe = likeMe;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = imgUrl;
    }


}
