package pl.alphabox.Scenes.Share;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.alphabox.R;

public class ShareActivity extends AppCompatActivity implements IShareView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
    }
}
