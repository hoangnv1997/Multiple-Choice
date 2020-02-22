package com.hoangnguyen.multiplechoice.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.activity.ScreenSlidePagerActivity;
import com.hoangnguyen.multiplechoice.model.Question;

import java.util.ArrayList;

public class ScreenSlidePageFragment extends Fragment {

    private ArrayList<Question> questionArrayList;
    private int pageNumber, checkAns;

    private TextView tvNum, tvQuestion;
    private RadioGroup radioGroup;
    private RadioButton radA, radB, radC, radD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        tvNum = rootView.findViewById(R.id.tvNum);
        tvQuestion = rootView.findViewById(R.id.tvQuestion);
        radioGroup = rootView.findViewById(R.id.radGroup);
        radA = rootView.findViewById(R.id.radA);
        radB = rootView.findViewById(R.id.radB);
        radC = rootView.findViewById(R.id.radC);
        radD = rootView.findViewById(R.id.radD);

        tvNum.setText("Câu " + (pageNumber + 1));
        tvQuestion.setText(questionArrayList.get(pageNumber).getQuestion());
        radA.setText(questionArrayList.get(pageNumber).getAns_a());
        radB.setText(questionArrayList.get(pageNumber).getAns_b());
        radC.setText(questionArrayList.get(pageNumber).getAns_c());
        radD.setText(questionArrayList.get(pageNumber).getAns_d());

        if (checkAns != 0) {
            radA.setClickable(false);
            radB.setClickable(false);
            radC.setClickable(false);
            radD.setClickable(false);
            getCheckAns(getItem(pageNumber).getResult());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getItem(pageNumber).choiceID = checkedId;
                getItem(pageNumber).setTraLoi(getChoiceFromID(checkedId));
            }
        });

        return rootView;
    }

    //Lấy giá trị (vị trí) radiogroup chuyển thành đáp án A/B/C/D
    private String getChoiceFromID(int ID) {
        if (ID == R.id.radA) {
            return "A";
        } else if (ID == R.id.radB) {
            return "B";
        } else if (ID == R.id.radC) {
            return "C";
        } else if (ID == R.id.radD) {
            return "D";
        } else return "";
    }

    private Question getItem(int position) {
        return questionArrayList.get(position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        questionArrayList = new ArrayList<Question>();
        ScreenSlidePagerActivity screenSlidePagerActivity = (ScreenSlidePagerActivity) getActivity();
        questionArrayList = screenSlidePagerActivity.getData();

        pageNumber = getArguments().getInt("page");
        checkAns = getArguments().getInt("checkAns");

        super.onCreate(savedInstanceState);
    }

    public static ScreenSlidePageFragment create(int pageNumber, int checkAns) {
        ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        args.putInt("checkAns", checkAns);
        screenSlidePageFragment.setArguments(args);
        return screenSlidePageFragment;
    }

    //Kiểm tra câu đúng, nếu câu đúng đổi background radiobutton tương ứng
    private void getCheckAns(String ans) {
        if (ans.equals("A") == true) {
            radA.setTextColor(Color.GREEN);
        } else if (ans.equals("B") == true) {
            radB.setTextColor(Color.GREEN);
        } else if (ans.equals("C") == true) {
            radC.setTextColor(Color.GREEN);
        } else if (ans.equals("D") == true) {
            radD.setTextColor(Color.GREEN);
        }
    }
}
