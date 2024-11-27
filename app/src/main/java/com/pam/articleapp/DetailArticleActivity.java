package com.pam.articleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailArticleActivity extends AppCompatActivity {
    TextView tvTitle, tvContent;
    Button btnBack;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        ivImage = findViewById(R.id.imageView3);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String title = intent.getStringExtra("article_title");
        String content = intent.getStringExtra("article_content");
        int id = intent.getIntExtra("article_id", -1);

        tvTitle.setText(title);
        tvContent.setText(content);
        switch (id) {
            case 1:
                ivImage.setImageResource(R.drawable.g1);
                break;
            case 2:
                ivImage.setImageResource(R.drawable.g2);
                break;
            case 3:
                ivImage.setImageResource(R.drawable.g3);
                break;
            default:
                ivImage.setImageResource(R.drawable.ic_launcher_background);
                break;
        }
        btnBack.setOnClickListener(v -> finish());
    }
}
