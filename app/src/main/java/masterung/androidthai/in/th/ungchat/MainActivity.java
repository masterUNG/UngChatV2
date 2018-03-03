package masterung.androidthai.in.th.ungchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import masterung.androidthai.in.th.ungchat.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentMainFragment, new MainFragment()).commit();
        }

    }
}
