package com.hoangnguyen.multiplechoice.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.hoangnguyen.multiplechoice.R;

public class QuestionAdapter extends CursorAdapter {

    public QuestionAdapter(Context context, Cursor cursor, boolean autoRequery){
        super(context, cursor, autoRequery);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_question, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvQues = view.findViewById(R.id.tv_question);
        tvQues.setText(cursor.getString(1));
    }
}
