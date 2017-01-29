package com.lemarkis.todolist.Models;

/**
 * Created by lemarq_j on 21/01/2017.
 */

public class CheckModel {
    public Long _id;
    public Long fk_todoId;
    public String text;
    public Boolean done;

    public CheckModel() {}

    public CheckModel(Long pTodoId, String pText, Boolean pDone) {
        fk_todoId = pTodoId;
        text = pText;
        done = pDone;
    }
}
