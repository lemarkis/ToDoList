package com.lemarkis.todolist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lemarkis.todolist.Adapter.HomeToDoAdapter;
import com.lemarkis.todolist.Models.ToDoModel;
import com.lemarkis.todolist.R;
import com.lemarkis.todolist.Services.ToDoService;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_list)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Vector<ToDoModel> items = ToDoService.getInstance(getApplicationContext()).getAll();
        HomeToDoAdapter adapter = new HomeToDoAdapter(MainActivity.this, items);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoModel item = (ToDoModel) mListView.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                bundle.putLong("_id", item._id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                bundle.putLong("_id", 0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Vector<ToDoModel> items = ToDoService.getInstance(getApplicationContext()).getAll();
        HomeToDoAdapter adapter = new HomeToDoAdapter(MainActivity.this, items);
        mListView.setAdapter(adapter);
    }
}
