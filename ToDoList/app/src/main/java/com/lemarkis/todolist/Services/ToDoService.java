package com.lemarkis.todolist.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lemarkis.todolist.Entities.ToDoEntity;
import com.lemarkis.todolist.Helpers.DbHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by lemarq_j on 22/01/2017.
 */

public class ToDoService {
    static ToDoService sInstance;
    private SQLiteDatabase db;

    public static synchronized ToDoService getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new ToDoService(ctx);
        }
        return sInstance;
    }

    private ToDoService(Context ctx) {
        db = DbHelper.getInstance(ctx).getWritableDatabase();
    }

    public ToDoEntity getById(long id) {
        return cupboard().withDatabase(db).get(ToDoEntity.class, id);
    }
}
