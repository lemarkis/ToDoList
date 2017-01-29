package com.lemarkis.todolist.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lemarkis.todolist.Entities.CheckEntity;
import com.lemarkis.todolist.Helpers.DbHelper;
import com.lemarkis.todolist.Models.CheckModel;
import com.lemarkis.todolist.Models.ToDoModel;

import java.util.Vector;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Jean on 24/01/2017.
 */

public class CheckService {
    static CheckService sInstance;
    private SQLiteDatabase db;

    public static synchronized CheckService getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new CheckService(ctx);
        }
        return sInstance;
    }

    public CheckService(Context ctx) {
        db = DbHelper.getInstance(ctx).getWritableDatabase();
    }

    public CheckEntity getById(long id) {
        return cupboard().withDatabase(db).get(CheckEntity.class, id);
    }

    public Vector<CheckModel> getFor(Long id) {
        Vector<CheckModel> CheckList = new Vector<>();
        QueryResultIterable<CheckEntity> itr = null;
        try {
            itr = cupboard().withDatabase(db).query(CheckEntity.class).query();
            for (CheckEntity entity : itr) {
                if (entity.fk_todoId == id) {
                    CheckModel model = EntityToModel(entity);
                    CheckList.add(model);
                }
            }
        } finally {
            itr.close();
        }
        return CheckList;
    }

    public Long Put(CheckEntity entity) {
        return cupboard().withDatabase(db).put(entity);
    }

    public void Replace(Long todoId, Vector<CheckModel> models) {
        DeleteFor(todoId);
        for (CheckModel model : models) {
            model.fk_todoId = todoId;
            CheckEntity entity = ModelToEntity(model);
            Put(entity);
        }
    }

    public void DeleteFor(Long id) {
        cupboard().withDatabase(db).delete(CheckEntity.class, "fk_todoId = ?", id.toString());
    }

    private CheckModel EntityToModel(CheckEntity entity) {
        CheckModel model = new CheckModel();
        model._id = entity._id;
        model.fk_todoId = entity.fk_todoId;
        model.text = entity.text;
        model.done = entity.done != 0;

        return model;
    }

    private CheckEntity ModelToEntity(CheckModel model) {
        CheckEntity entity = new CheckEntity();
        if (model.fk_todoId == null) {
            return null;
        }
        entity._id = model._id;
        entity.fk_todoId = model.fk_todoId;
        entity.text = model.text;
        entity.done = model.done ? 1 : 0;

        return entity;
    }
}
