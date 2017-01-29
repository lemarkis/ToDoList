package com.lemarkis.todolist.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lemarkis.todolist.Models.CheckModel;
import com.lemarkis.todolist.R;

import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jean on 25/01/2017.
 */

public class CreateCheckAdapter extends ArrayAdapter<CheckModel> {
    private Context ctx;
    private Vector<CheckModel> list = new Vector<>();

    public CreateCheckAdapter(Context context, List<CheckModel> items) {
        super(context, 0, items);
        list =  (Vector<CheckModel>) items;
        ctx = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CreateViewHolder holder;
        if (view != null) {
                holder = (CreateViewHolder) view.getTag();
        }
        else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_create_check, parent, false);
            holder = new CreateViewHolder(view);
            view.setTag(holder);
        }

        final CheckModel item = getItem(position);
        holder.name.setText(item.text);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText itemList = new EditText(ctx);
                itemList.setText(item.text);
                AlertDialog dialog = new AlertDialog.Builder(ctx)
                        .setTitle("Corriger un élément")
                        .setMessage("On a fait une erreur ? :)")
                        .setView(itemList)
                        .setPositiveButton("Corriger", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                item.text = String.valueOf(itemList.getText());
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Annuler", null)
                        .setNeutralButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.remove(item);
                                notifyDataSetChanged();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(item);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    static class CreateViewHolder {
        @BindView(R.id.checklist_del)
        ImageButton del;
        @BindView(R.id.checklist_name)
        TextView name;

        public CreateViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
