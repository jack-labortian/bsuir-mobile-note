package com.bsuir.notes.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bsuir.notes.Constants;
import com.bsuir.notes.R;
import com.bsuir.notes.adapter.NotesAdapter;
import com.bsuir.notes.datasource.model.Note;
import com.bsuir.notes.datasource.repository.INoteRepository;
import com.bsuir.notes.datasource.repository.NoteRepositoryImpl;
import com.bsuir.notes.datasource.specification.FindAllNotesSpecification;
import com.bsuir.notes.datasource.specification.FindNotesByTitleSpecification;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private List<Note> items;
    private NotesAdapter notesAdapter;
    private INoteRepository repository;

    public List<Note> getDataForListView() {
        items = repository.query(new FindAllNotesSpecification());
        return this.items;
    }

    @Override
    public boolean onContextItemSelected(MenuItem paramMenuItem) {
        switch (paramMenuItem.getItemId()) {
            case R.id.delete_item:
                int i = (int) ((AdapterView.AdapterContextMenuInfo) paramMenuItem.getMenuInfo()).id;
                Note note = this.items.get(i);
                repository.removeNote(note);
                this.items.remove(i);
                this.notesAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(paramMenuItem);


        }
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setTitle("Notes");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.COLOR_STRING)));

        repository = new NoteRepositoryImpl(getApplicationContext());


        TextView localTextView = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", "android"));
        localTextView.setTextSize(30.0F);
        Typeface face = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        localTextView.setTypeface(face);

        TextView tips = (TextView) findViewById(R.id.tips);
        tips.setTypeface(face);
        SpannableString localSpannableString = new SpannableString(Constants.ABOUT_INFO);
        tips.setText(localSpannableString);
        List<Note> items = MainActivity.this.getDataForListView();
        if (items.size() != 0) {
            tips.setVisibility(View.GONE);
        }

        this.notesAdapter = new NotesAdapter(items, this);
        ListView notesListView = ((ListView) findViewById(R.id.notes_lv));
        notesListView.addFooterView(new View(this), null, false);
        notesListView.addHeaderView(new View(this), null, false);
        notesListView.setAdapter(this.notesAdapter);

        registerForContextMenu(notesListView);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
                Intent localIntent = new Intent(MainActivity.this, ShowNoteActivity.class);
                localIntent.putExtra("id", MainActivity.this.items.get(paramAnonymousInt - 1).id);
                localIntent.putExtra("title", MainActivity.this.items.get(paramAnonymousInt - 1).title);
                localIntent.putExtra("content", MainActivity.this.items.get(paramAnonymousInt - 1).content);
                localIntent.putExtra("from", "app");
                MainActivity.this.startActivity(localIntent);
            }
        });
        notesListView.setLongClickable(true);
    }

    @Override
    public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo) {
        super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
        getMenuInflater().inflate(R.menu.context, paramContextMenu);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes, menu);

        final MenuItem search = menu.findItem(R.id.search_notes);

        SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                List<Note> resultItems = repository.query(new FindNotesByTitleSpecification(query));

                MainActivity.this.items.clear();
                MainActivity.this.items.addAll(resultItems);

                MainActivity.this.notesAdapter.notifyDataSetChanged();
                return true;
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        int i = paramMenuItem.getItemId();
        if (i == R.id.newNotes) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        }
        if (i == R.id.about) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            View localView = getLayoutInflater().inflate(R.layout.about, null);
            TextView localTextView1 = (TextView) localView.findViewById(R.id.title);
            Typeface localTypeface = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
            localTextView1.setTypeface(localTypeface);
            TextView localTextView2 = (TextView) localView.findViewById(R.id.content);
            SpannableString localSpannableString = new SpannableString(Constants.ABOUT_INFO);
            Linkify.addLinks(localSpannableString, 15);
            localTextView2.setTypeface(localTypeface);
            localTextView2.setText(localSpannableString);
            localBuilder.setView(localView).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                }
            });
            localBuilder.show();
            return true;
        }
        return super.onOptionsItemSelected(paramMenuItem);
    }

}