package com.example.githubapi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubapi.R;
import com.example.githubapi.adapter.RepoAdapter;
import com.example.githubapi.model.GitHubRepo;
import com.example.githubapi.rest.APIClient;
import com.example.githubapi.rest.GitHubRepoEndPoint;
import com.example.githubapi.rest.GitHubUserEndPoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoActivity extends AppCompatActivity {

    private String username;
    private TextView usernameTV, repoSizeTV;
    private RecyclerView recyclerView;
    private List<GitHubRepo> gitHubRepoList = new ArrayList<>();;
    private RepoAdapter adapter;
    //private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        usernameTV = findViewById(R.id.repoUsernameTV);
        repoSizeTV = findViewById(R.id.repoSizeTV);
        recyclerView = findViewById(R.id.repoRV);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            username = extras.getString("username");
            usernameTV.setText(username);
        }



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RepoAdapter(this,gitHubRepoList);
        recyclerView.setAdapter(adapter);

        loadRepositories();

    }

    public void  loadRepositories(){

        GitHubRepoEndPoint apiService = APIClient.getClient().create(GitHubRepoEndPoint.class);
        Call<List<GitHubRepo>> call = apiService.getRepo(username);
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                if (response.code() == 200){

                    gitHubRepoList.clear();
                    gitHubRepoList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    repoSizeTV.setText("Total: "+gitHubRepoList.size());

                }else {
                    Toast.makeText(RepoActivity.this, "Error code-"+response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {

                Toast.makeText(RepoActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
