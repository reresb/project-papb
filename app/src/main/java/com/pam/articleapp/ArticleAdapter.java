package com.pam.articleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    List<ArticleModel> articleList;
    Context context;

    public ArticleAdapter(List<ArticleModel> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivImage;
        Button btnRead;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.articleTitle);
            ivImage = itemView.findViewById(R.id.articleImage);
            btnRead = itemView.findViewById(R.id.btnArticle);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.article_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleModel article = articleList.get(position);

        holder.tvTitle.setText(article.getTitle());
        switch (article.getId()) {
            case 1:
                holder.ivImage.setImageResource(R.drawable.g1);
                break;
            case 2:
                holder.ivImage.setImageResource(R.drawable.g2);
                break;
            case 3:
                holder.ivImage.setImageResource(R.drawable.g3);
                break;
            default:
                holder.ivImage.setImageResource(R.color.white);
                break;
        }

        holder.btnRead.setOnClickListener(v -> {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            DetailArticleFragment fragment = new DetailArticleFragment();

            Bundle bundle = new Bundle();
            bundle.putString("article_title", article.getTitle());
            bundle.putString("article_content", article.getContent());
            bundle.putInt("article_id", article.getId());
            fragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
