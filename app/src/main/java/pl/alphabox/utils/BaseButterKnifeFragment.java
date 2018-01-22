package pl.alphabox.utils;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by robertogiba on 22.01.2018.
 */

public abstract class BaseButterKnifeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Integer providedLayout = provideLayoutForFragment();

        if (providedLayout != 0) {
            View view = inflater.inflate(providedLayout, container, false);
            ButterKnife.bind(this, view);
            return view;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @LayoutRes
    @NonNull
    protected abstract Integer provideLayoutForFragment();
}
