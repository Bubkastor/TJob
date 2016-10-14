package bubokastor.tjob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bubokastor.tjob.Items.ArticleContent;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticleListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private ServerRequestTask mTask;
    private ArticleContent mContent;
    public  Context mContext;
    public static SimpleItemRecyclerViewAdapter sAdapter;

    @BindView(R.id.article_list) View mRecyclerView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);

        mContext = this;
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());

        assert mRecyclerView != null;
        mContent = new ArticleContent(mContext);
        mTask = new ServerRequestTask(mContent);
        mTask.execute();
        sAdapter = new SimpleItemRecyclerViewAdapter(mContent.ITEMS);
        setupRecyclerView((RecyclerView) mRecyclerView);
        if (findViewById(R.id.article_detail_container) != null) {
            mTwoPane = true;
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(sAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<ArticleContent.Article> mValues;

        public SimpleItemRecyclerViewAdapter(List<ArticleContent.Article> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).name);

            if(holder.mItem.is_like_me) {
                holder.mLikeView.setVisibility(View.VISIBLE);
            }
            else{
                holder.mLikeView.setVisibility(View.INVISIBLE);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ArticleDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        ArticleDetailFragment fragment = new ArticleDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.article_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ArticleDetailActivity.class);
                        intent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.name) TextView mIdView;
            @BindView(R.id.like) ImageView mLikeView;
            final View mView;

            ArticleContent.Article mItem;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mView = view;
            }

        }
    }
}
