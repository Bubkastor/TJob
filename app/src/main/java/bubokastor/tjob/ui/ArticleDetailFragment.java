package bubokastor.tjob.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bubokastor.tjob.R;
import bubokastor.tjob.content.Article;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private String mId;
    private Article mItem;
    private boolean mIsShow = false;
    private Context mContext;

    private ObjectAnimator mAnimationShow;
    private ObjectAnimator mAnimationHide;

    CollapsingToolbarLayout mAppBarLayout;

    public ArticleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mId = (getArguments().getString(ARG_ITEM_ID));
            for (Article it : ArticleListActivity.sArticles) {
                if (it.getId().equals(mId)) {
                    mItem = it;
                    break;
                }
            }
            Activity activity = this.getActivity();
            mAppBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (mAppBarLayout != null) {
                mAppBarLayout.setTitle(mItem.getName());
            }
        }
    }

    private void initAnimation(){
        mAnimationShow = ObjectAnimator.ofInt(
                mDescription,
                "maxLines",
                25);

        mAnimationShow.setDuration(500);
        mAnimationHide = ObjectAnimator.ofInt(
                mDescription,
                "maxLines",
                5);

        mAnimationHide.setDuration(500);
    }

    @BindView(R.id.date) TextView mTime;
    @BindView(R.id.author) TextView mAuthor;
    @BindView(R.id.count_like) TextView mCountLike;
    @BindView(R.id.article_detail) TextView mDescription;
    @BindView(R.id.image_view) ImageView mImage;
    @BindView(R.id.button_like) ImageButton mButtonLike;

    @OnClick(R.id.button_show)
    public void onClickButtonShow(Button button){
        if(!mIsShow) {
            mAnimationShow.start();
            mDescription.setEllipsize(null);
            button.setText("Скрыть");
        }
        else{
            mAnimationHide.start();
            mDescription.setEllipsize(TextUtils.TruncateAt.END);
            button.setText("Показать");
        }
        mIsShow = !mIsShow;
    }

    @OnClick(R.id.button_like)
    public void onClickButtonLike(ImageButton button){
        boolean is_like_me = !mItem.isLikeMe();
        int count_like = mItem.getCountLike();
        if(is_like_me) {
            button.setImageResource(R.drawable.icon_dislike);
            count_like++;
        }
        else{
            button.setImageResource(R.drawable.icon_like);
            count_like--;
        }
        mCountLike.setText(Integer.toString(count_like));
        ArticleListActivity.sAdapter.notifyDataSetChanged();

        mItem.setLikeMe(is_like_me);
        mItem.setCountLike(count_like);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.article_detail, container, false);
        ButterKnife.bind(this, rootView);
        initAnimation();
        if (mItem != null) {
            mDescription.setText(mItem.getDescription());
            mTime.setText(mItem.getTime());
            mAuthor.setText(mItem.getAuthor());
            mCountLike.setText(Integer.toString(mItem.getCountLike()));
            Picasso.with(mContext)
                    .load(mItem.getImgUrl())
                    .into(mImage);

            if(mItem.isLikeMe())
                mButtonLike.setImageResource(R.drawable.icon_dislike);
            else
                mButtonLike.setImageResource(R.drawable.icon_like);
        }
        return rootView;
    }

}
