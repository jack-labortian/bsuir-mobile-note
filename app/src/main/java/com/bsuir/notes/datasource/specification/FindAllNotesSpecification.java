package com.bsuir.notes.datasource.specification;

/**
 * @author Eugene Novik
 */
public class FindAllNotesSpecification implements SqlSpecification{
    @Override
    public String toSqlClauses() {
        return "select * from notes order by title";
    }
}
