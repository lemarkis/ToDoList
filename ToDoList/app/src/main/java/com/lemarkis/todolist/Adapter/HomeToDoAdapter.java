package com.lemarkis.todolist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lemarkis.todolist.Models.ToDoModel;
import com.lemarkis.todolist.R;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lemarq_j on 21/01/2017.
 */

public class HomeToDoAdapter extends ArrayAdapter<ToDoModel> {
    public HomeToDoAdapter(Context context, List<ToDoModel> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (HomeViewHolder) convertView.getTag();
        }
        else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_content_main, parent, false);
            viewHolder = new HomeViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        ToDoModel item = getItem(position);
        viewHolder.title.setText(item.title);
        viewHolder.brief.setText(item.getBrief());
        viewHolder.deadline.setText(fmt.format(item.deadline));
        int state = item.getState();
        viewHolder.state.setProgress(state);


        return convertView;
    }

    class HomeViewHolder {
        @BindView(R.id.preview_titleText)
        TextView title;
        @BindView(R.id.preview_briefText)
        TextView brief;
        @BindView(R.id.preview_dateText)
        TextView deadline;
        @BindView(R.id.preview_progress)
        ProgressBar state;

        HomeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
