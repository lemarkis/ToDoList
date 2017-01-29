package com.lemarkis.todolist.Services;

import android.content.ContentValues;
import android.content.Context;

import com.lemarkis.todolist.Entities.ToDoEntity;
import com.lemarkis.todolist.Models.ToDoModel;
import com.lemarkis.todolist.Helpers.DbHelper;

import java.util.Date;
import java.util.Vector;

import nl.qbusict.cupboard.DatabaseCompartment;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by lemarq_j on 22/01/2017.
 */

public class ToDoService {
    static private ToDoService sInstance;
    private DatabaseCompartment db;
    private CheckService checkService;

    public static synchronized ToDoService getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new ToDoService(ctx);
        }

        return sInstance;
    }

    private ToDoService(Context ctx) {
        db = cupboard().withDatabase(DbHelper.getInstance(ctx).getWritableDatabase());
        checkService = CheckService.getInstance(ctx);
    }

    public ToDoModel getById(long id) {
        ToDoEntity entity = db.get(ToDoEntity.class, id);
        ToDoModel model = EntityToModel(entity);
        model.checkList = checkService.getFor(model._id);
        return model;
    }

    private ToDoEntity getEntityById(Long id) {
        return db.get(ToDoEntity.class, id);
    }

    public Long AddOrUpdate(ToDoModel model) {
        ToDoEntity entity = ModelToEntity(model);
        if (entity._id != null) {
            ContentValues values = new ContentValues();
            values.put("title", entity.title);
            values.put("desc", entity.desc);
            values.put("deadline", entity.deadline);
            values.put("creation", entity.getCreation());

            checkService.Replace(model._id ,model.checkList);

            return (db.update(ToDoEntity.class, values, "_id = ?", entity._id.toString())) == 0 ? 0L : entity._id;
        }

        Long id = db.put(entity);
        checkService.Replace(id, model.checkList);
        return id;
    }

    public Vector<ToDoModel> getAll() {
        Vector<ToDoModel> Todos = new Vector<>();
        QueryResultIterable<ToDoEntity> itr = null;
        boolean itrNotNull = true;
        try {
            itr = db.query(ToDoEntity.class).query();
            for (ToDoEntity entity : itr) {
                ToDoModel model = EntityToModel(entity);
                model.checkList = checkService.getFor(model._id);
                Todos.add(model);
            }
        } catch (NullPointerException e) {
            itrNotNull = false;
        } finally {
            if (itrNotNull) {
                itr.close();
            }
        }

        return Todos;
    }

    public void Delete(ToDoModel model) {
        if (model._id != null) {
            ToDoEntity entity = getEntityById(model._id);
            checkService.DeleteFor(model._id);
            db.delete(ToDoEntity.class, entity._id);
        }
    }



    private ToDoModel EntityToModel(ToDoEntity entity) {
        ToDoModel model;
        if (entity.getCreation() != null) {
                model = new ToDoModel(new Date(entity.getCreation()));
        }
        else {
            model = new ToDoModel();
        }
        model._id = entity._id;
        model.title = entity.title;
        model.desc = entity.desc;
        model.deadline = new Date(entity.deadline);

        return model;
    }

    private ToDoEntity ModelToEntity(ToDoModel model) {
        ToDoEntity entity;
        if (model.getCreation() != null) {
            entity = new ToDoEntity(model.getCreation());
        }
        else {
            entity = new ToDoEntity();
        }
        entity._id = model._id;
        entity.title = model.title;
        entity.desc = model.desc;
        if (model.deadline != null) {
            entity.deadline = model.deadline.getTime();
        }

        return entity;
    }
}
