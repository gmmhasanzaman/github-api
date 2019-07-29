package com.example.githubapi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubapi.R;
import com.example.githubapi.model.GitHubRepo;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private Context context;
    private List<GitHubRepo> gitHubRepos;

    public RepoAdapter(Context context, List<GitHubRepo> gitHubRepos) {
        this.context = context;
        this.gitHubRepos = gitHubRepos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.repo_layout,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.repoNameTV.setText(gitHubRepos.get(position).getName());
        holder.repoDesTV.setText(gitHubRepos.get(position).getDescription());
        holder.repoLangTV.setText(gitHubRepos.get(position).getLanguage());

    }

    @Override
    public int getItemCount() {
        //Log.e("List size = ",String.valueOf(gitHubRepos.size()));
        return gitHubRepos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView repoNameTV, repoDesTV, repoLangTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            repoNameTV = itemView.findViewById(R.id.repoNameId);
            repoDesTV = itemView.findViewById(R.id.repoDesId);
            repoLangTV = itemView.findViewById(R.id.repoLanguageId);
        }
    }
}
