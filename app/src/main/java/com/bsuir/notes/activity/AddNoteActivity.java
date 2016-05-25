package com.bsuir.notes.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bsuir.notes.Constants;
import com.bsuir.notes.R;
import com.bsuir.notes.datasource.model.Note;
import com.bsuir.notes.datasource.repository.INoteRepository;
import com.bsuir.notes.datasource.repository.NoteRepositoryImpl;


public class AddNoteActivity extends ActionBarActivity {

    private EditText title;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.COLOR_STRING)));

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView yourTextView = (TextView) findViewById(titleId);
        Typeface face = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");

        yourTextView.setTextSize(30);
        yourTextView.setTypeface(face);

        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);

        title.setTypeface(face);
        content.setTypeface(face);

        Button start_remembering = (Button) findViewById(R.id.start_remembering);
        start_remembering.setTypeface(face);
        start_remembering.setTextSize(30);

        start_remembering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    INoteRepository nds = new NoteRepositoryImpl(getApplicationContext());
                    if (!title.getText().toString().trim().isEmpty() && !content.getText().toString().trim().isEmpty()) {

                        Note note = new Note(title.getText().toString(), content.getText().toString());
                        nds.addNote(note);
                        Toast.makeText(getApplicationContext(), "Note Added.", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(AddNoteActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please write something.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
