package masterung.androidthai.in.th.ungchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ServiceActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String displayNameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

       // Log.d("4MarchV1", "DistplayName ==> " + firebaseUser.getDisplayName());

//        Check Status
        if (firebaseUser == null) {
            startActivity(new Intent(ServiceActivity.this, MainActivity.class));
            finish();
        } else {
            displayNameString = firebaseUser.getDisplayName();
            Log.d("4MarchV1", "DistplayName ==> " + firebaseUser.getDisplayName());

            //        Create Toolbar
            createToolbar();

        }




    }   // Main Method

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Service");
        getSupportActionBar().setSubtitle(displayNameString + " login");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_service, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemExit) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}   // Main Class
