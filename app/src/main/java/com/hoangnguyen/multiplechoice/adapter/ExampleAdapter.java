package com.hoangnguyen.multiplechoice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.model.Example;

import java.util.ArrayList;

public class ExampleAdapter extends ArrayAdapter<Example> {
    public ExampleAdapter(@NonNull Context context, ArrayList<Example> exampleArrayList) {
        super(context, 0, exampleArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);

        }

        TextView tvName = convertView.findViewById(R.id.tv_num);
        ImageView imageView = convertView.findViewById(R.id.img_book);

        Example example = getItem(position);
        if (example!=null){
            tvName.setText(example.getName());
            imageView.setImageResource(R.drawable.ic_book);
        }
        return convertView;
    }
}
