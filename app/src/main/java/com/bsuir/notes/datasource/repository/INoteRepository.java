package com.bsuir.notes.datasource.repository;

import com.bsuir.notes.datasource.model.Note;
import com.bsuir.notes.datasource.specification.SqlSpecification;

import java.util.List;

/**
 * @author Eugene Novik
 */
public interface INoteRepository {

    void addNote(Note note);

    void removeNote(Note note);

    void updateNote(Note note); // Think it as replace for set

    List<Note> query(SqlSpecification specification);
}
