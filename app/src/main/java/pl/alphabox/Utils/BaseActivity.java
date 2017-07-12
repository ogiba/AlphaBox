package pl.alphabox.Utils;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by ogiba on 12.07.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayout());
        ButterKnife.bind(this);
        setupPresenter();
    }

    @LayoutRes
    protected abstract int provideLayout();

    protected abstract void setupPresenter();
}
