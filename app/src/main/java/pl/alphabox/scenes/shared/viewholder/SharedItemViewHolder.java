package pl.alphabox.scenes.shared.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.alphabox.R;

/**
 * Created by robertogiba on 12.01.2018.
 */

public class SharedItemViewHolder {

    @BindView(R.id.tv_app_name)
    protected TextView appNameView;

    private final View itemView;

    public SharedItemViewHolder(View itemView) {
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public SharedItemViewHolder setName(String name) {
        this.appNameView.setText(name);
        return this;
    }
}
