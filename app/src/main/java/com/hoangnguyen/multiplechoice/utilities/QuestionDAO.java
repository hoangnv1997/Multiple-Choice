package com.hoangnguyen.multiplechoice.utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoangnguyen.multiplechoice.model.Question;

import java.util.ArrayList;

public class QuestionDAO {

    private DBHelper dbHelper;

    public QuestionDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Question> getQuestion(int num_exam, String subject) {

        ArrayList<Question> questionArrayList = new ArrayList<Question>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM question WHERE num_exam='" + num_exam +
                "' AND subject='" + subject + "'", null);
        cursor.moveToFirst();

        do {
            Question question;
            question = new Question(cursor.getInt(0), cursor.getString(1)
                    , cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getString(6), cursor.getInt(7)
                    , cursor.getString(8), "");
            questionArrayList.add(question);
        } while (cursor.moveToNext());

        return questionArrayList;
    }

    //Lấy ds câu hỏi theo từ trong câu hỏi
    public Cursor getQuestion(String subject, String keySearch) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM question WHERE question LIKE '%" + keySearch + "%'" +
                "AND subject ='" + subject + "'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Lấy ds câu hỏi theo môn học
//    public Cursor getQuestionBySubject(String keySearch) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM cauhoivatly WHERE subject LIKE '%" + keySearch + "%'", null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }
    public Cursor getQuestionSearchAll(String keySearch) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM question WHERE question LIKE '%" + keySearch + "%'"
                , null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getQuestionBySubject(String subject) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM question WHERE subject ='" + subject + "'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllQuestion() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM question ", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
