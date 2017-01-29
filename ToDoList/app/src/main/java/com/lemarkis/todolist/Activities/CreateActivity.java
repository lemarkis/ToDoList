package com.lemarkis.todolist.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lemarkis.todolist.Adapter.CreateCheckAdapter;
import com.lemarkis.todolist.Models.CheckModel;
import com.lemarkis.todolist.Models.ToDoModel;
import com.lemarkis.todolist.R;
import com.lemarkis.todolist.Services.ToDoService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateActivity extends AppCompatActivity {

    ToDoModel toDoModel;
    Calendar dateAndTime = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, month);
            dateAndTime.set(Calendar.DAY_OF_MONTH, day);
            viewDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(dateAndTime.getTime()));
        }
    };
    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hour);
            dateAndTime.set(Calendar.MINUTE, minute);
            viewTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(dateAndTime.getTime()));
        }
    };
    @BindView(R.id.create_title)
    EditText viewTitle;
    @BindView(R.id.create_description)
    EditText viewDesc;
    @BindView(R.id.create_check)
    ListView viewList;
    @BindView(R.id.create_date)
    Button viewDate;
    @BindView(R.id.create_time)
    Button viewTime;
    @BindView(R.id.create_creation)
    TextView viewCreation;
    CreateCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Long ToDoId = intent.getLongExtra("_id", 0);

        if (ToDoId != 0) {
            toDoModel = ToDoService.getInstance(CreateActivity.this).getById(ToDoId);
            viewTitle.setText(toDoModel.title);
            viewDesc.setText(toDoModel.desc);
            dateAndTime.setTime(toDoModel.deadline);
            adapter = new CreateCheckAdapter(CreateActivity.this, toDoModel.checkList);
        }
        else {
            toDoModel = new ToDoModel();
            adapter = new CreateCheckAdapter(CreateActivity.this, toDoModel.checkList);
        }
        viewDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(toDoModel.deadline));
        viewTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(toDoModel.deadline));
        viewCreation.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.DEFAULT).format(toDoModel.getCreation()));

        viewList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.creation_save_task:
                fillModel();
                ToDoService.getInstance(CreateActivity.this).AddOrUpdate(toDoModel);
                finish();
                break;

            case R.id.creation_del_task:
                fillModel();
                ToDoService.getInstance(CreateActivity.this).Delete(toDoModel);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void fillModel() {
        toDoModel.title = viewTitle.getText().toString();
        toDoModel.desc = viewDesc.getText().toString();
        //toDoModel.checkList.clear();
        Vector<CheckModel> vec = new Vector<>();
        int count = viewList.getCount();
        for (int i = 0; i < count; i++) {
            CheckModel checkModel = (CheckModel) viewList.getItemAtPosition(i);
            vec.add(checkModel);
        }
        toDoModel.checkList = vec;
        toDoModel.deadline = dateAndTime.getTime();
    }

    @OnClick(R.id.create_add_btn)
    public void addList() {
        final EditText itemList = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Nouvel élément")
                .setMessage("Ajouter l'élément à la liste")
                .setView(itemList)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item = String.valueOf(itemList.getText());
                        toDoModel.addToList(item);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Annuler", null)
                .create();
        dialog.show();
    }

    @OnClick(R.id.create_date)
    public void setDate() {
        new DatePickerDialog(CreateActivity.this, date,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @OnClick(R.id.create_time)
    public void setTime() {
        new TimePickerDialog(CreateActivity.this, time,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                true)
                .show();
    }
}
