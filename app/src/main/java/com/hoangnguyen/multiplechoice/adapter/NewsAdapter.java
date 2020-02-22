package com.hoangnguyen.multiplechoice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.model.News;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    private TextView tvTitle, tvPubDate;

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_listview_news, null);

        }

        News news = getItem(position);
        if (news!=null){
            tvTitle = convertView.findViewById(R.id.tv_title);
            tvPubDate = convertView.findViewById(R.id.tv_pub_date);

            tvTitle.setText(news.getTitle());
            tvPubDate.setText(news.getPubDate());
        }
        return convertView;
    }
}
