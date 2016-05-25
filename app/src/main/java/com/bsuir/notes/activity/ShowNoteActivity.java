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
import android.widget.EditText;
import android.widget.TextView;

import com.bsuir.notes.Constants;
import com.bsuir.notes.R;
import com.bsuir.notes.datasource.model.Note;
import com.bsuir.notes.datasource.repository.INoteRepository;
import com.bsuir.notes.datasource.repository.NoteRepositoryImpl;


public class ShowNoteActivity extends ActionBarActivity {

    private EditText titleEdit;
    private EditText contentEdit;
    private Typeface typeFace;
    private Note note;
    private TextView titleTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note);
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setTitle("Note");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.COLOR_STRING)));

        this.typeFace = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView yourTextView = (TextView) findViewById(titleId);
        yourTextView.setTextSize(30);
        Typeface face = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        yourTextView.setTypeface(face);

        Intent intent = getIntent();


        long id = intent.getLongExtra("id", 1);
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        this.note = new Note(id, title, content);

        titleTextView = (TextView) findViewById(R.id.title_answercard);
        contentTextView = (TextView) findViewById(R.id.content_answercard);

        titleTextView.setTypeface(face);
        contentTextView.setTypeface(face);

        titleTextView.setText(note.getTitle());
        contentTextView.setText(note.getContent());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.answer_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            View localView = getLayoutInflater().inflate(R.layout.edite_note, null);

            localBuilder.setView(localView).setPositiveButton("Save Note", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

                    String newValueTitle = titleEdit.getText().toString();
                    String newValueContent = contentEdit.getText().toString();

                    note.setTitle(newValueTitle);
                    note.setContent(newValueContent);

                    titleTextView.setText(newValueTitle);
                    contentTextView.setText(newValueContent);

                    INoteRepository nds = new NoteRepositoryImpl(ShowNoteActivity.this);
                    nds.updateNote(note);

                }
            });

            localBuilder.setView(localView).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                }
            });


            titleEdit = (EditText) localView.findViewById(R.id.title_edit);
            contentEdit = (EditText) localView.findViewById(R.id.content_edite);

            titleEdit.setText(note.getTitle());
            titleEdit.setTypeface(typeFace);

            contentEdit.setText(note.getContent());
            contentEdit.setTypeface(typeFace);

            localBuilder.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
