package pl.alphabox.Scenes.Share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pl.alphabox.Models.UserModel;
import pl.alphabox.R;

/**
 * Created by ogiba on 12.07.2017.
 */

public class ShareUsersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserModel> items;

    public ShareUsersAdapter(Context context) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            View sourceView = LayoutInflater.from(context).inflate(R.layout.listview_item_available_user, parent);
            viewHolder = new ViewHolder(sourceView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.emailLabel.setText(items.get(position).email);

        return convertView;
    }

    public void setItems(ArrayList<UserModel> items) {
        this.items.clear();
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    private class ViewHolder {
        public TextView emailLabel;

        ViewHolder(View sourceView) {
            this.emailLabel = (TextView) sourceView.findViewById(R.id.tv_user_email);
        }
    }
}
