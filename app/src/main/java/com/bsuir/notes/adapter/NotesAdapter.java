package com.bsuir.notes.adapter;


import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bsuir.notes.datasource.model.Note;
import com.bsuir.notes.R;

import java.util.List;

public class NotesAdapter extends BaseAdapter {
    private List<Note> items = null;
    private ActionBarActivity actionBar;

    public NotesAdapter(List<Note> items, ActionBarActivity actionBar) {
        this.items = items;
        this.actionBar = actionBar;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int paramInt) {
        return items.get(paramInt);
    }

    @Override
    public long getItemId(int paramInt) {
        return paramInt;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null) {
            LayoutInflater localLayoutInflater = (LayoutInflater) actionBar.getSystemService("layout_inflater");
            paramView = localLayoutInflater.inflate(R.layout.list_items, paramViewGroup, false);
            localLayoutInflater.inflate(R.layout.list_items, paramViewGroup, false);
        }
        Typeface localTypeface = Typeface.createFromAsset(actionBar.getAssets(), "Roboto-Thin.ttf");
        TextView localTextView1 = (TextView) paramView.findViewById(R.id.title);
        localTextView1.setTypeface(localTypeface);
        Note localNoteItems = this.items.get(paramInt);
        localTextView1.setText(localNoteItems.title);
        return paramView;
    }
}