package com.lemarkis.todolist.Entities;

import java.util.Date;
import java.util.List;

/**
 * Created by Jean on 19/01/2017.
 */

public class ToDoEntity {
    public Long _id;
    public String title;
    public String desc;
    public Date deadline;
    public Date creation;
    public List<CheckEntity> checkList;

    public ToDoEntity() {}

    public void addToList(String item) {
        checkList.add(new CheckEntity(_id, item, false));
    }

    public void addToList(String item, Boolean done) {
        checkList.add(new CheckEntity(_id, item, done));
    }

    public String getBrief() {
        return desc.substring(0, 23).concat("...");
    }

    public int getState() {
        long diff = deadline.getTime() - creation.getTime();
        return (int)(diff / deadline.getTime() * 100);
    }
}
