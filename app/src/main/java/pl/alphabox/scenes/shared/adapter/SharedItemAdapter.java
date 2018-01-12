package pl.alphabox.scenes.shared.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import pl.alphabox.R;
import pl.alphabox.models.UserFile;
import pl.alphabox.scenes.shared.viewholder.SharedItemViewHolder;

/**
 * Created by robertogiba on 12.01.2018.
 */

public class SharedItemAdapter extends BaseAdapter {

    private ArrayList<UserFile> items;

    public SharedItemAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public UserFile getItem(int position) {
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

    public void setItems(ArrayList<UserFile> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(UserFile item) {
        this.addItem(item);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.items.clear();
        notifyDataSetChanged();
    }
}
