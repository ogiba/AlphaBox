package pl.alphabox.scenes.share.fragments.users.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.alphabox.models.User;
import pl.alphabox.R;

/**
 * Created by ogiba on 12.07.2017.
 */

public class ShareUsersAdapter extends BaseAdapter {

    final private Context context;
    private ArrayList<User> items;
    private OnUserClickListener userClickListener;

    public ShareUsersAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public User getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_available_user, parent, false);
            viewHolder = new ViewHolder(convertView, context, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final User user = items.get(position);

        viewHolder.emailLabel.setText(user.email);
        viewHolder.setOnUserSelectListener(userClickListener);
        viewHolder.setSelected(user.isSelected());

        return convertView;
    }

    public void setOnUserSelectListener(OnUserClickListener callback) {
        this.userClickListener = callback;
    }

    public void setItems(ArrayList<User> items) {
        this.items.clear();
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    public void addItem(User item) {
        this.items.add(item);

        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
    }

    public ArrayList<User> getItems() {
        return items;
    }

    private class ViewHolder implements View.OnClickListener {
        public TextView emailLabel;
        public ImageView checkImage;

        private Context context;
        private int position;
        private OnUserClickListener userClickListener;

        ViewHolder(@NonNull View sourceView, @NonNull Context context, int position) {
            this.context = context;
            this.position = position;
            this.emailLabel = (TextView) sourceView.findViewById(R.id.tv_user_email);
            this.checkImage = (ImageView) sourceView.findViewById(R.id.iv_select);

            sourceView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (userClickListener == null)
                return;

            userClickListener.onUserSelected(v, position);
        }

        public void setOnUserSelectListener(OnUserClickListener callback) {
            this.userClickListener = callback;
        }

        public void setSelected(boolean isSelected) {
            if (context == null)
                return;

            if (isSelected)
                checkImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_black_24dp));
            else
                checkImage.setImageDrawable(null);
        }
    }
}
