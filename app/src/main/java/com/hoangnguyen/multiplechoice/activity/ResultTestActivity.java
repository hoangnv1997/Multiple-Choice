package com.hoangnguyen.multiplechoice.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.model.Question;

import java.util.ArrayList;

public class ResultTestActivity extends AppCompatActivity {

    private ArrayList<Question> questionArrayList = new ArrayList<Question>();
    private int numTrue, numFalse, numNoAns, totalQues;
    private TextView tvTrue, tvFalse, tvNoAns;
    private Button btnTestAgain, btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test);
        init();

        Intent intent = getIntent();
        questionArrayList = (ArrayList<Question>) intent.getExtras().getSerializable("list_ques");
        totalQues = questionArrayList.size();

        checkResult();
        tvTrue.setText(numTrue + "/" + totalQues);
        tvFalse.setText(numFalse + "/" + totalQues);
        tvNoAns.setText((numNoAns + "/" + totalQues));

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultTestActivity.this);
                builder.setIcon(R.drawable.ic_notification);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        btnTestAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshList();
                finish();
                Intent intent1 = new Intent(ResultTestActivity.this, ScreenSlidePagerActivity.class);
                intent1.putExtra("list_ques", questionArrayList);
                intent1.putExtra("test", "no");
                startActivity(intent1);
            }
        });

    }
    private void refreshList(){
        for (int i=0; i<questionArrayList.size(); i++){
            questionArrayList.get(i).setTraLoi("");
        }
    }

    private void init() {
        tvTrue = findViewById(R.id.tv_true);
        tvFalse = findViewById(R.id.tv_false);
        tvNoAns = findViewById(R.id.tv_no_ans);


        btnTestAgain = findViewById(R.id.btn_test_again);
        btnCancel = findViewById(R.id.btn_cancel);

    }

    private void checkResult() {
        for (int i = 0; i < questionArrayList.size(); i++) {
            if (questionArrayList.get(i).getTraLoi().equals("") == true) {
                numNoAns++;
            } else if (questionArrayList.get(i).getResult().equals(questionArrayList.get(i).getTraLoi()) == true) {
                numTrue++;
            } else {
                numFalse++;
            }
        }
    }
}
