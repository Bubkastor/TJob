package bubokastor.tjob;

import android.animation.ObjectAnimator;
import android.app.Activity;
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

import org.w3c.dom.Text;

import bubokastor.tjob.Items.ArticleContent;
import bubokastor.tjob.repository.Database;

public class ArticleDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private ArticleContent.Article mItem;
    private boolean isShow = false;

    public ArticleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = ArticleContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.article_detail, container, false);
        if (mItem != null) {
            final TextView description = (TextView) rootView.findViewById(R.id.article_detail);
            description.setText(mItem.description);
            ((TextView) rootView.findViewById(R.id.date)).setText(mItem.time);
            ((TextView) rootView.findViewById(R.id.author)).setText(mItem.author);
            ((TextView) rootView.findViewById(R.id.count_like)).setText(Integer.toString(mItem.count_like));
            Picasso.with(ArticleListActivity.context)
                    .load(mItem.img_url)
                    .into((ImageView) rootView.findViewById(R.id.image_view));
            final Button buttonShow = (Button) rootView.findViewById(R.id.button_show);
            final ObjectAnimator animationShow = ObjectAnimator.ofInt(
                    description,
                    "maxLines",
                    25);

            animationShow.setDuration(500);
            final ObjectAnimator animationHide = ObjectAnimator.ofInt(
                    description,
                    "maxLines",
                    5);

            animationHide.setDuration(500);

            buttonShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isShow) {
                        animationShow.start();
                        description.setEllipsize(null);
                        buttonShow.setText("Скрыть");
                    }
                    else{
                        animationHide.start();
                        description.setEllipsize(TextUtils.TruncateAt.END);
                        buttonShow.setText("Показать");
                    }
                    isShow = !isShow;
                }
            });
            final ImageButton likeButton = (ImageButton) rootView.findViewById(R.id.like_button);
            //TODO Убрать дублирование
            if(mItem.is_like_me)
                likeButton.setImageResource(R.drawable.icon_dislike);
            else
                likeButton.setImageResource(R.drawable.icon_like);
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean is_like_me = !mItem.is_like_me;
                    int count_like = mItem.count_like;
                    if(is_like_me) {
                        likeButton.setImageResource(R.drawable.icon_dislike);

                        count_like++;
                    }
                    else{
                        likeButton.setImageResource(R.drawable.icon_like);
                        count_like--;
                }
                    ((TextView) rootView.findViewById(R.id.count_like)).setText(Integer.toString(count_like));
                    Log.d("ASSERT",Integer.toString(mItem.count_like));
                    ArticleListActivity.adapter.notifyDataSetChanged();
                    String reques = Database.COUNT_LIKE_COLUMN + " = ? OR " + Database.IS_LIKE_ME_COLUMN + " = ?";
                    mItem.update(reques, new String[]{Integer.toString(count_like), Boolean.toString(is_like_me) } );
                    mItem.is_like_me = is_like_me;
                    mItem.count_like = count_like;
                }
            });
        }
        return rootView;
    }
}
