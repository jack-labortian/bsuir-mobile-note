package com.bsuir.notes.datasource.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.bsuir.notes.datasource.model.Note;
import com.bsuir.notes.datasource.specification.SqlSpecification;
import com.bsuir.notes.datasource.util.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugene Novik
 */
public class NoteRepositoryImpl implements INoteRepository {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public NoteRepositoryImpl(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    @Override
    public void addNote(Note note) {
        this.open();
        ContentValues insertValues = new ContentValues();
        insertValues.put(SQLiteHelper.COLUMN_TITLE, note.getTitle());
        insertValues.put(SQLiteHelper.COLUMN_CONTENT, note.getContent());
        long val = database.insert(SQLiteHelper.TABLE_NOTES, null, insertValues);
        note.setId(val);
        this.close();
    }

    @Override
    public void removeNote(Note note) {
        this.open();
        database.delete(SQLiteHelper.TABLE_NOTES, SQLiteHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
        this.close();
    }

    @Override
    public void updateNote(Note note) {
        this.open();

        ContentValues updateValues = new ContentValues();
        updateValues.put(SQLiteHelper.COLUMN_TITLE, note.getTitle());
        updateValues.put(SQLiteHelper.COLUMN_CONTENT, note.getContent());

        database.update(SQLiteHelper.TABLE_NOTES, updateValues, SQLiteHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});

        this.close();
    }

    @Override
    public List<Note> query(SqlSpecification specification) {
        this.open();
        List<Note> items = new ArrayList<>();
        Cursor cursor = database.rawQuery(specification.toSqlClauses(), null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                long id = cursor.getLong(cursor.getColumnIndex(SQLiteHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_CONTENT));
                items.add(new Note(id, title, content));
                cursor.moveToNext();
            }
        }
        this.close();
        return items;
    }

}
