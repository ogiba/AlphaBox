package pl.alphabox.scenes.shared.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import pl.alphabox.R;
import pl.alphabox.scenes.shared.viewholder.SharedItemViewHolder;

/**
 * Created by robertogiba on 12.01.2018.
 */

public class SharedItemAdapter extends BaseAdapter {

    private ArrayList<Object> items;

    public SharedItemAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        SharedItemViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_list_shared_item, viewGroup, false);
            viewHolder = new SharedItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SharedItemViewHolder) convertView.getTag();
        }

        viewHolder.setName(String.format("Item %s", position));

        return convertView;
    }

    public void setItems(ArrayList<Object> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
