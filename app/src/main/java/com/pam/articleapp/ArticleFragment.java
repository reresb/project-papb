package com.pam.articleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragment extends Fragment {
    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    List<ArticleModel> articleModelList;
    AppDatabase db;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("article");
    FloatingActionButton btAddArticle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    articleModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ArticleModel article = dataSnapshot.getValue(ArticleModel.class);
                        articleModelList.add(article);
                    }
                    articleAdapter = new ArticleAdapter(articleModelList, getContext());
                    recyclerView.setAdapter(articleAdapter);
                } else {
                    fetchData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btAddArticle = view.findViewById(R.id.btAddArticle);
        btAddArticle.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new AddArticleFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        return view;
    }

    void fetchData() {
        String url = "https://scarlet-gabie-79.tiiny.io/";

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            ArticleModel article = new ArticleModel(
                                    0,
                                    obj.getString("title"),
                                    obj.getString("content")
                            );

                            DatabaseReference newRef = dbRef.push();
                            String firebaseKey = newRef.getKey();
                            article.setId(firebaseKey.hashCode());
                            newRef.setValue(article);
                        }

                        Toast.makeText(getContext(), "Fetch Article Successfully", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Failed to load articles", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(request);
    }
}
