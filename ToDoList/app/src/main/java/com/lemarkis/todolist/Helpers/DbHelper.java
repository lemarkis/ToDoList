package com.lemarkis.todolist.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lemarkis.todolist.Entities.CheckEntity;
import com.lemarkis.todolist.Entities.ToDoEntity;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by lemarq_j on 21/01/2017.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ToDoList.db";
    private static final int DB_VERSION = 1;

    private static DbHelper sInstance;

    public static synchronized DbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    static {
        cupboard().register(ToDoEntity.class);
        cupboard().register(CheckEntity.class);
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //@Override
    //public void onConfigure(SQLiteDatabase db) {
    //    super.onConfigure(db);
    //    db.setForeignKeyConstraintsEnabled(true);
    //}

    @Override
    public void onCreate(SQLiteDatabase db) {
      cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            cupboard().withDatabase(db).upgradeTables();
        }
    }
}
