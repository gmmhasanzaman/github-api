package com.example.githubapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.githubapi.R;
import com.example.githubapi.model.GitHubUser;
import com.example.githubapi.rest.APIClient;
import com.example.githubapi.rest.GitHubUserEndPoints;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private ImageView avatarIV;
    private TextView usernameTV, followersTV, followingTv, loginTV, emailTV;
    Button ownRepoBtn;
    private String username;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        avatarIV = findViewById(R.id.avatarIV);
        usernameTV = findViewById(R.id.usernameTV);
        followersTV = findViewById(R.id.followersTV);
        followingTv = findViewById(R.id.followingTV);
        loginTV = findViewById(R.id.loginTV);
        emailTV = findViewById(R.id.emailTV);

        ownRepoBtn = findViewById(R.id.ownRepoBtn);

        extras = getIntent().getExtras();
        username = extras.getString("username");

        ownRepoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOwnRepos();
            }
        });

        loadData();
    }

    public void loadData(){

        GitHubUserEndPoints apiService = APIClient.getClient().create(GitHubUserEndPoints.class);
        Call<GitHubUser> gitHubUserCall = apiService.getUser(username);
        gitHubUserCall.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {

                GitHubUser gitHubUser = response.body();

                if (response.code() == 200){

                    String avatarUrl = response.body().getAvatarUrl();
                    Picasso.get().load(avatarUrl).into(avatarIV);
                    usernameTV.setText(gitHubUser.getName());
                    followersTV.setText(String.valueOf(gitHubUser.getFollowers()));
                    followingTv.setText(String.valueOf(gitHubUser.getFollowing()));
                    loginTV.setText(response.body().getLogin());
                    emailTV.setText(response.body().getEmail());

                }
                else {
                    Toast.makeText(UserActivity.this, "ErrorCode - "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {

                Toast.makeText(UserActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void loadOwnRepos(){
        Intent intent = new Intent(UserActivity.this,RepoActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}
