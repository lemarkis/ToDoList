package com.lemarkis.todolist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lemarkis.todolist.Entities.ToDoEntity;
import com.lemarkis.todolist.R;

import java.util.List;

/**
 * Created by lemarq_j on 21/01/2017.
 */

public class ToDoAdapter extends ArrayAdapter<ToDoEntity> {
    public ToDoAdapter(Context context, List<ToDoEntity> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.preview_main, parent, false);
        }

        HomeViewHolder viewHolder = (HomeViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new HomeViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.preview_titleText);
            viewHolder.brief = (TextView) convertView.findViewById(R.id.preview_briefText);
            viewHolder.deadline = (TextView) convertView.findViewById(R.id.preview_dateText);
            viewHolder.state = (ProgressBar) convertView.findViewById(R.id.preview_progress);
        }

        ToDoEntity item = getItem(position);
        viewHolder.title.setText(item.title);
        viewHolder.brief.setText(item.getBrief());
        viewHolder.deadline.setText(item.deadline.toString());
        viewHolder.state.setProgress(item.getState());

        return convertView;
    }

    private class HomeViewHolder {
        TextView title;
        TextView brief;
        TextView deadline;
        ProgressBar state;
    }
}
