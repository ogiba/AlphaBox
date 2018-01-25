package pl.alphabox.scenes.profile;

import pl.alphabox.R;
import pl.alphabox.utils.BaseToolbarActivity;

public class ProfileActivity extends BaseToolbarActivity<IProfilePresenter>
        implements IProfileView {

    @Override
    protected int provideLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected IProfilePresenter providePresenter() {
        return new ProfilePresenter(this);
    }
}
