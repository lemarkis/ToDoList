package com.lemarkis.todolist.Entities;

/**
 * Created by Jean on 24/01/2017.
 */

public class CheckEntity {
    public Long _id;
    public Long fk_todoId;
    public String   text;
    public Integer  done;

    public CheckEntity() {}

    public CheckEntity(Long pTodoId, String pText, Boolean pDone) {
        fk_todoId = pTodoId;
        text = pText;
        done = pDone ? 1 : 0 ;
    }
}
