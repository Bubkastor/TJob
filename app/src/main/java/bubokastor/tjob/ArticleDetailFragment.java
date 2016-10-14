package bubokastor.tjob;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bubokastor.tjob.Items.ArticleContent;
import bubokastor.tjob.repository.Database;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private ArticleContent.Article mItem;
    private boolean mIsShow = false;
    private Context mContext;

    private ObjectAnimator mAnimationShow;
    private ObjectAnimator mAnimationHide;

    public ArticleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = ArticleContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
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
        boolean is_like_me = !mItem.is_like_me;
        int count_like = mItem.count_like;
        if(is_like_me) {
            button.setImageResource(R.drawable.icon_dislike);

            count_like++;
        }
        else{
            button.setImageResource(R.drawable.icon_like);
            count_like--;
        }
        mCountLike.setText(Integer.toString(count_like));
        Log.d("ASSERT",Integer.toString(mItem.count_like));
        ArticleListActivity.sAdapter.notifyDataSetChanged();
        String request = Database.COUNT_LIKE_COLUMN + " = ? OR " + Database.IS_LIKE_ME_COLUMN + " = ?";
        mItem.update(request, new String[]{Integer.toString(count_like), Boolean.toString(is_like_me) } );
        mItem.is_like_me = is_like_me;
        mItem.count_like = count_like;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.article_detail, container, false);
        ButterKnife.bind(this, rootView);
        initAnimation();
        if (mItem != null) {
            mDescription.setText(mItem.description);
            mTime.setText(mItem.time);
            mAuthor.setText(mItem.author);
            mCountLike.setText(Integer.toString(mItem.count_like));
            Picasso.with(mContext)
                    .load(mItem.img_url)
                    .into(mImage);

            if(mItem.is_like_me)
                mButtonLike.setImageResource(R.drawable.icon_dislike);
            else
                mButtonLike.setImageResource(R.drawable.icon_like);
        }
        return rootView;
    }
}
