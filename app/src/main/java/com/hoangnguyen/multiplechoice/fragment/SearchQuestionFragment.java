package com.hoangnguyen.multiplechoice.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.activity.MainActivity;
import com.hoangnguyen.multiplechoice.adapter.QuestionAdapter;
import com.hoangnguyen.multiplechoice.model.Question;
import com.hoangnguyen.multiplechoice.utilities.QuestionDAO;

import java.util.ArrayList;

public class SearchQuestionFragment extends Fragment {

    private ListView lvQuestion;
    private QuestionDAO questionDAO;
    private QuestionAdapter questionAdapter;
    private EditText edtSearch;
    private ImageView imgSearch;
    private String subject = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_question, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Tìm kiếm");

        lvQuestion = view.findViewById(R.id.lv_question);
        edtSearch = view.findViewById(R.id.edt_search_question);
        questionDAO = new QuestionDAO(getActivity());
        imgSearch = view.findViewById(R.id.imv_subject);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listCursor(questionDAO.getQuestionSearchAll(edtSearch.getText().toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        return view;
    }

    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_math:
                        subject = "math";
                        imgSearch.setImageResource(R.drawable.ic_math);
                        listCursor(questionDAO.getQuestionBySubject(subject));
                        setEventEdt(subject);
                        break;
                    case R.id.action_physics:
                        subject = "chsong";
                        imgSearch.setImageResource(R.drawable.ic_physics);
                        listCursor(questionDAO.getQuestionBySubject(subject));
                        setEventEdt(subject);
                        break;
                    case R.id.action_chemistry:
                        subject = "chemistry";
                        imgSearch.setImageResource(R.drawable.ic_chemistry);
                        listCursor(questionDAO.getQuestionBySubject(subject));
                        setEventEdt(subject);
                        break;
                    case R.id.action_biology:
                        subject = "biology";
                        imgSearch.setImageResource(R.drawable.ic_biology);
                        listCursor(questionDAO.getQuestionBySubject(subject));
                        setEventEdt(subject);
                        break;
                    case R.id.action_all:
                        imgSearch.setImageResource(R.drawable.ic_search);
                        listCursor(questionDAO.getAllQuestion());
                        edtSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                listCursor(questionDAO.getQuestionSearchAll(edtSearch.getText().toString()));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        break;
                }
                return false;
            }
        });
        popupMenu.inflate(R.menu.menu_question);
        popupMenu.show();
    }

    private void listCursor(Cursor cursor) {
        questionAdapter = new QuestionAdapter(getActivity(), cursor, true);
        lvQuestion.setAdapter(questionAdapter);
        questionAdapter.notifyDataSetChanged();
    }


    private void setEventEdt(final String subject) {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listCursor(questionDAO.getQuestion(subject, edtSearch.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
