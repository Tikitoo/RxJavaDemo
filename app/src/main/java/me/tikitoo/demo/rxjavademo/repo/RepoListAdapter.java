package me.tikitoo.demo.rxjavademo.repo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.tikitoo.demo.rxjavademo.R;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.repo_name)
        TextView mRepoName;
        @Bind(R.id.repo_description)
        TextView mRepoDescription;
        @Bind(R.id.repo_url)
        TextView mRepoUrl;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Repo repo) {
            mRepoName.setText(repo.getName());
            mRepoDescription.setText(repo.getDescription());
            mRepoUrl.setText(repo.getUrl());
        }
    }

//  =======================================================================================

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_repo, parent, false);
        return new RepoViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bind(mRepos.get(position));

    }


    public RepoListAdapter() {
        mRepos = new ArrayList<>();
    }

    private List<Repo> mRepos;

    public void setRepos(List<Repo> repos) {
        this.mRepos = repos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRepos == null ? 0 : mRepos.size();
    }


}
