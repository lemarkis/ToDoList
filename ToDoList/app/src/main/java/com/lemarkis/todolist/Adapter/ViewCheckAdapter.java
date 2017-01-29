package com.lemarkis.todolist.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lemarkis.todolist.Models.CheckModel;
import com.lemarkis.todolist.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jean on 29/01/2017.
 */

public class ViewCheckAdapter extends ArrayAdapter<CheckModel> {
    public ViewCheckAdapter(Context context, List<CheckModel> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewViewHolder holder;
        if (view != null) {
            holder = (ViewViewHolder) view.getTag();
        }
        else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_view_list, parent, false);
            holder = new ViewViewHolder(view);
            view.setTag(holder);
        }

        int flags = holder.text.getPaintFlags();

        CheckModel item = getItem(position);
        holder.text.setText(item.text);
        holder.done.setChecked(item.done);

        return view;
    }

    class ViewViewHolder {
        @BindView(R.id.list_view_text)
        TextView text;
        @BindView(R.id.list_view_done)
        CheckBox done;

        ViewViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
