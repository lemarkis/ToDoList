package com.lemarkis.todolist.Models;

import java.util.Date;
import java.util.Vector;

/**
 * Created by Jean on 19/01/2017.
 */

public class ToDoModel {
    public Long _id;
    public String title;
    public String desc;
    public Date deadline;
    private Date creation;
    public Vector<CheckModel> checkList;

    public ToDoModel() {
        creation = new Date();
        deadline = creation;
        checkList = new Vector<>();
    }

    public ToDoModel(Date pCreation) {
        creation = pCreation;
        deadline = new Date();
        checkList = new Vector<>();
    }

    public Date getCreation() {
        return creation;
    }

    public void addToList(CheckModel model) {
        checkList.add(model);
    }

    public void addToList(String item) {
        checkList.add(new CheckModel(_id, item, false));
    }

    public void addToList(String item, Boolean done) {
        checkList.add(new CheckModel(_id, item, done));
    }

    public String getBrief() {
        if (desc.length() < 47)
            return desc;
        return desc.substring(0, 47).concat("...");
    }

    public int getState() {
        final Date now = new Date();
        if (deadline.before(now))
            return 100;
        Long maxTime = deadline.getTime() - creation.getTime();
        Long elapsedTime = now.getTime() - creation.getTime();
        return maxTime != 0 ? (int)(elapsedTime * 100 / maxTime) : 100;
    }
}
