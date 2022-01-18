package com.example.clubolympus.data;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.clubolympus.R;
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

public class MemberCursorAdapter extends CursorAdapter {

    public MemberCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.member_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idTextView = view.findViewById(R.id.idTextView);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView surnameTextView = view.findViewById(R.id.surnameTextView);
        TextView sexTextView = view.findViewById(R.id.sexTextView);
        TextView sportsGroupTextView = view.findViewById(R.id.sportsGroupTextView);

        String id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_ID)));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_NAME));
        String surname = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_SURNAME));
        String sex = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_SEX));
        String sportsGroup = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_SPORTS_GROUP));

        idTextView.setText(id);
        nameTextView.setText(name);
        surnameTextView.setText(surname);
        sexTextView.setText(sex);
        sportsGroupTextView.setText(sportsGroup);
    }
}
