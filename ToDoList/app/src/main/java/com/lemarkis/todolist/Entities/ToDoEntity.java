package com.lemarkis.todolist.Entities;

import java.util.Date;

/**
 * Created by Jean on 24/01/2017.
 */

public class ToDoEntity {
    public Long _id;
    public String   title;
    public String   desc;
    public Long   deadline;
    private Long   creation;

    public ToDoEntity() {
        creation = new Date().getTime();
    }

    public ToDoEntity(Date pCreation) {
        creation = pCreation.getTime();
    }

    public Long getCreation() {
        return creation;
    }
}
