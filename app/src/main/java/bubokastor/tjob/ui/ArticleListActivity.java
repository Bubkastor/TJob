package bubokastor.tjob.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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

import bubokastor.tjob.R;
import bubokastor.tjob.content.Article;
import bubokastor.tjob.database.table.ArticlesTable;
import bubokastor.tjob.loaders.async.cursor.ArticlesLoader;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticleListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private boolean mTwoPane;
    public static SimpleItemRecyclerViewAdapter sAdapter;

    public static List<Article> sArticles;


    @BindView(R.id.article_list) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());

        if (findViewById(R.id.article_detail_container) != null) {
            mTwoPane = true;
        }
        getLoaderManager().initLoader(R.id.articles_loader, Bundle.EMPTY, this);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(sAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case R.id.articles_loader:
                return new ArticlesLoader(this);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        if (id == R.id.articles_loader) {
            if (data != null && data.moveToFirst()) {
                List<Article> articles = ArticlesTable.listFromCursor(data);
                sArticles = articles;
                sAdapter = new SimpleItemRecyclerViewAdapter(articles);
                assert  mRecyclerView != null;
                setupRecyclerView(mRecyclerView);
            }
        }
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Article> mValues;

        public SimpleItemRecyclerViewAdapter(List<Article> items) {
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
            holder.mIdView.setText(mValues.get(position).getName());

            if(holder.mItem.isLikeMe()) {
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
                        arguments.putString(ArticleDetailFragment.ARG_ITEM_ID, holder.mItem.getId());

                        ArticleDetailFragment fragment = new ArticleDetailFragment();
                        fragment.setArguments(arguments);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.article_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ArticleDetailActivity.class);
                        intent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, holder.mItem.getId());

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

            Article mItem;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mView = view;
            }

        }
    }
}
