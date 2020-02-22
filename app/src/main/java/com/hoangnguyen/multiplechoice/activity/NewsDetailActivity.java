package com.hoangnguyen.multiplechoice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hoangnguyen.multiplechoice.R;

public class NewsDetailActivity extends AppCompatActivity {
    private WebView webViewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        webViewNews = findViewById(R.id.web_view_news);
        Intent intent = getIntent();
        intent.getStringExtra("link");

        webViewNews.loadUrl(intent.getStringExtra("link"));
        webViewNews.setWebViewClient(new WebViewClient());
    }
}
