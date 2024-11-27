package com.pam.articleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailArticleFragment extends Fragment {
    TextView tvTitle, tvContent;
    Button btnBack;
    ImageView ivImage;

    public DetailArticleFragment() {}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_article, container, false);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvContent = view.findViewById(R.id.tvContent);
        btnBack = view.findViewById(R.id.btnBack);
        ivImage = view.findViewById(R.id.imageView3);

        if (getArguments() != null) {
            String title = getArguments().getString("article_title");
            String content = getArguments().getString("article_content");
            int id = getArguments().getInt("article_id");

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
        }

        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
