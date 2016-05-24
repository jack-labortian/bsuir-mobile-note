package com.bsuir.notes.datasource.specification;

/**
 * @author Eugene Novik
 */
public class FindNotesByTitleSpecification implements SqlSpecification {

    private String title;

    public FindNotesByTitleSpecification(String title) {
        this.title = title;
    }

    @Override
    public String toSqlClauses() {
        return "select * from notes where upper(title) like upper('%" + title + "%') order by title";
    }
}
