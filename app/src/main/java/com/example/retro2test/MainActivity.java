package com.example.retro2test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        //getComments();
        //createPost();
        //updatePost();
        deletePost();


    }

    private void getPosts(){
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code : " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                for (Post post : posts){
                    String content = " ";
                    content += "ID :" + post.getId() + "\n" ;
                    content += "User ID : " + post.getUserId() + "\n";
                    content += "Title :" + post.getTitle() + "\n";
                    content += "Text :" + post.getText() + "\n\n";

                    tvResult.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code : " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments){
                    String content = " ";
                    content +="Post Id : " + comment.getPostId() + "\n";
                    content += "ID :" + comment.getId() + "\n";
                    content += "Name : " + comment.getName() + "\n";
                    content += "Email : " + comment.getEmail() + "\n";
                    content += "Text : " + comment.getText() + "\n\n";

                    tvResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void createPost(){
        Post post = new Post(23,"New Title","New Text");

        Map<String,String> fields = new HashMap<>();
        fields.put("userId","23");
        fields.put("title","new title");


        Call<Post> call =jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code : " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = " ";
                content += "Code :" + response.code() + "\n";
                content += "ID : " + postResponse.getId() + "\n";
                content += "User ID : " + postResponse.getUserId() + "\n";
                content += "Title : " + postResponse.getTitle() + "\n";
                content += "Text : " + postResponse.getText() + "\n\n";

                tvResult.setText(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost(){
        Post post = new Post(12,null,"upadted text");

        Call<Post> call = jsonPlaceHolderApi.patchPost(5,post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code : " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = " ";
                content += "Code :" + response.code() + "\n";
                content += "ID : " + postResponse.getId() + "\n";
                content += "User ID : " + postResponse.getUserId() + "\n";
                content += "Title : " + postResponse.getTitle() + "\n";
                content += "Text : " + postResponse.getText() + "\n\n";

                tvResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void deletePost(){
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                tvResult.setText("code : " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }



}