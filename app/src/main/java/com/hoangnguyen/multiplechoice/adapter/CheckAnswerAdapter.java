package com.hoangnguyen.multiplechoice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.model.Question;

import java.util.ArrayList;

public class CheckAnswerAdapter extends BaseAdapter {

    Context context;
    ArrayList listData;
    LayoutInflater inflater;

    public CheckAnswerAdapter(Context context, ArrayList listData) {

        this.listData = listData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvNumAns, tvYourAns;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Question question = (Question) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gridview_list_answer, null);

            viewHolder.tvNumAns = convertView.findViewById(R.id.tv_num_answer);
            viewHolder.tvYourAns = convertView.findViewById(R.id.tv_answer);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int i = position+1;
        viewHolder.tvNumAns.setText("Câu "+i+":");
        viewHolder.tvYourAns.setText(question.getTraLoi());
        return convertView;
    }
}
