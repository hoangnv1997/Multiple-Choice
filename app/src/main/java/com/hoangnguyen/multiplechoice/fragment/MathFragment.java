package com.hoangnguyen.multiplechoice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.activity.MainActivity;
import com.hoangnguyen.multiplechoice.activity.ScreenSlidePagerActivity;
import com.hoangnguyen.multiplechoice.adapter.ExampleAdapter;
import com.hoangnguyen.multiplechoice.model.Example;

import java.util.ArrayList;

public class MathFragment extends Fragment {

    private ExampleAdapter exampleAdapter;
    private GridView gvExam;
    private ArrayList<Example> exampleArrayList = new ArrayList<Example>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_math, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Toán");

        gvExam = view.findViewById(R.id.gv_exam);

        exampleArrayList.add(new Example("Đề số 1"));
        exampleArrayList.add(new Example("Đề số 2"));

        exampleAdapter = new ExampleAdapter(getActivity(), exampleArrayList);
        gvExam.setAdapter(exampleAdapter);
//
//        gvExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), ScreenSlidePagerActivity.class);
//                intent.putExtra("num_exam",position+1 );
//                intent.putExtra("subject", "chsong");
//                intent.putExtra("test", "yes");
//                startActivity(intent);
//            }
//        });
        return view;
    }
}
