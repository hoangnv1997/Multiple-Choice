package com.hoangnguyen.multiplechoice.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.adapter.CheckAnswerAdapter;
import com.hoangnguyen.multiplechoice.commom.ZoomOutPageTransformer;
import com.hoangnguyen.multiplechoice.fragment.ScreenSlidePageFragment;
import com.hoangnguyen.multiplechoice.model.Question;
import com.hoangnguyen.multiplechoice.utilities.QuestionDAO;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 15;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    private QuestionDAO questionDAO;
    private ArrayList<Question> questionArrayList;

    private TextView tvCheckAns, tvTimer, tvScore;
    private CountDown timer;

    private String subject;
    private int num_exam;
    int checkAns = 0;
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide_pager);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        final Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        num_exam = intent.getIntExtra("num_exam", 0);

        test = intent.getStringExtra("test");

        questionDAO = new QuestionDAO(this);
        questionArrayList = new ArrayList<>();

        if (test.equals("yes") == true) {
            questionArrayList = questionDAO.getQuestion(num_exam, subject);
        } else {
            questionArrayList = (ArrayList<Question>) intent.getExtras().getSerializable("list_ques");
        }


        tvCheckAns = findViewById(R.id.tvKiemTra);
        tvTimer = findViewById(R.id.tvTimer);
        tvScore = findViewById(R.id.tvScore);

        timer = new CountDown((15 * 60 * 1000) + 1000, 1000);

        tvCheckAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });


        timer.start();

        tvScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent(ScreenSlidePagerActivity.this, ResultTestActivity.class);
                intent1.putExtra("list_ques", questionArrayList);
                startActivity(intent1);
            }
        });
    }

    public ArrayList<Question> getData() {

        return questionArrayList;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ScreenSlidePagerActivity.this);
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

//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position, checkAns);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void checkAnswer() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_check_answer);


        CheckAnswerAdapter checkAnswerAdapter = new CheckAnswerAdapter(this, questionArrayList);
        GridView gvListQues = dialog.findViewById(R.id.gv_list_question);
        gvListQues.setAdapter(checkAnswerAdapter);

        gvListQues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });

        Button btnCancel, btnFinish;
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnFinish = dialog.findViewById(R.id.btn_finish);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result();
                timer.cancel();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public void result() {
        checkAns = 1;

        if (mPager.getCurrentItem() >= 5) {
            mPager.setCurrentItem(mPager.getCurrentItem() - 4);
        } else if (mPager.getCurrentItem() < 5) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 4);
        }
        tvScore.setVisibility(View.VISIBLE);
        tvCheckAns.setVisibility(View.GONE);
    }

    public class CountDown extends CountDownTimer {


        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");
            Toast.makeText(ScreenSlidePagerActivity.this, "Hết thời gian làm bài.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ScreenSlidePagerActivity.this, ResultTestActivity.class);
            intent.putExtra("list_ques", questionArrayList);
            startActivity(intent);
            //SetText cho textview hiện thị thời gian.
        }

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
    }

}