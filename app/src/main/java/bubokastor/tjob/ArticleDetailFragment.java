package bubokastor.tjob;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import bubokastor.tjob.Items.ArticleContent;

public class ArticleDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private ArticleContent.Article mItem;

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
        View rootView = inflater.inflate(R.layout.article_detail, container, false);
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.article_detail)).setText(mItem.description);
            Picasso.with(ArticleListActivity.context)
                    .load(mItem.img_url)
                    .into((ImageView) rootView.findViewById(R.id.image_view));
            final ImageButton likeButton = (ImageButton) rootView.findViewById(R.id.like_button);
            //TODO Убрать дублирование
            if(mItem.is_like_me)
                likeButton.setImageResource(R.drawable.icon_dislike);
            else
                likeButton.setImageResource(R.drawable.icon_like);
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItem.is_like_me = !mItem.is_like_me;
                    if(mItem.is_like_me)
                        likeButton.setImageResource(R.drawable.icon_dislike);
                    else
                        likeButton.setImageResource(R.drawable.icon_like);
                    ArticleListActivity.adapter.notifyDataSetChanged();
                }
            });
        }
        return rootView;
    }
}
