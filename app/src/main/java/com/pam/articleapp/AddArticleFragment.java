package com.pam.articleapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddArticleFragment extends Fragment {
    private EditText etTitle, etContent;
    private Button btnSave;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_article, container, false);

        dbRef = FirebaseDatabase.getInstance().getReference("article");
        etTitle = view.findViewById(R.id.etTitle);
        etContent = view.findViewById(R.id.etContent);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> addArticle());
        return view;
    }

    private void addArticle() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(getContext(), "Fill all form", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference newRef = dbRef.push();
        String firebaseKey = newRef.getKey();

        ArticleModel article = new ArticleModel(
                firebaseKey.hashCode(),
                title,
                content
        );

        newRef.setValue(article)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "Article Added Successfully", Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new ArticleFragment());
                    transaction.commit();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to add Article", Toast.LENGTH_SHORT).show();

                    etTitle.setText("");
                    etContent.setText("");
                });
    }
}
