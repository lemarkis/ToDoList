package com.lemarkis.todolist.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.lemarkis.todolist.Adapter.ViewCheckAdapter;
import com.lemarkis.todolist.Models.CheckModel;
import com.lemarkis.todolist.Models.ToDoModel;
import com.lemarkis.todolist.R;
import com.lemarkis.todolist.Services.ToDoService;

import java.text.DateFormat;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class ViewActivity extends AppCompatActivity {
    @BindView(R.id.view_title)
    TextView viewTitle;
    @BindView(R.id.view_desc)
    TextView viewDesc;
    @BindView(R.id.view_list)
    ListView viewList;
    @BindView(R.id.view_deadline)
    TextView viewDeadline;
    @BindView(R.id.view_creation)
    TextView viewCreation;

    ToDoModel toDoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Long ToDoId = intent.getLongExtra("_id", 0);

        if (ToDoId != 0) {
            DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            toDoModel = ToDoService.getInstance(ViewActivity.this).getById(ToDoId);
            viewTitle.setText(toDoModel.title);
            viewDesc.setText(toDoModel.desc);
            ViewCheckAdapter adapter = new ViewCheckAdapter(ViewActivity.this, toDoModel.checkList);
            viewList.setAdapter(adapter);
            viewDeadline.setText(fmt.format(toDoModel.deadline));
            viewCreation.setText(fmt.format(toDoModel.getCreation()));
        }
        else {
            finish();
        }
    }

    public void fillModel() {
        Vector<CheckModel> vec = new Vector<>();
        int count = viewList.getCount();
        for (int i = 0; i < count; i++) {
            CheckModel checkModel = (CheckModel) viewList.getItemAtPosition(i);
            vec.add(checkModel);
        }
        toDoModel.checkList = vec;
    }

    @OnItemClick(R.id.view_list)
    public void markDone(int position) {
        toDoModel.checkList.elementAt(position).done = !toDoModel.checkList.elementAt(position).done;
        ViewCheckAdapter adapter = new ViewCheckAdapter(ViewActivity.this, toDoModel.checkList);
        viewList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.view_close)
    public void close() {
        fillModel();
        ToDoService.getInstance(ViewActivity.this).AddOrUpdate(toDoModel);
        finish();
    }

    @OnClick(R.id.view_edit)
    public void edit() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(ViewActivity.this, CreateActivity.class);
        bundle.putLong("_id", toDoModel._id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.view_del)
    public void delete() {
        ToDoService.getInstance(ViewActivity.this).Delete(toDoModel);
        finish();
    }
}
