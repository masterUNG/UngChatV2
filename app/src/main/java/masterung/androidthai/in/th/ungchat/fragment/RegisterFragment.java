package masterung.androidthai.in.th.ungchat.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import masterung.androidthai.in.th.ungchat.MainActivity;
import masterung.androidthai.in.th.ungchat.R;
import masterung.androidthai.in.th.ungchat.ServiceActivity;
import masterung.androidthai.in.th.ungchat.utiliti.MyAlert;

/**
 * Created by masterung on 4/3/2018 AD.
 */

public class RegisterFragment extends Fragment {

    //    Explicit
    private ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();


    }   // Main Method

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_register, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSave) {
            registerToFirebase();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerToFirebase() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Upload Value To Firebase");
        progressDialog.setMessage("Please Wait Few Minus...");
        progressDialog.show();

        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText emailEditText = getView().findViewById(R.id.edtEmail);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

        final String nameString = nameEditText.getText().toString().trim();
        String emailString = emailEditText.getText().toString().trim();
        String passowrdString = passwordEditText.getText().toString().trim();

        final MyAlert myAlert = new MyAlert(getActivity());

        if (nameString.isEmpty() || emailString.isEmpty() || passowrdString.isEmpty()) {
            myAlert.myDialog(getString(R.string.have_space), getString(R.string.message_have_space));
            progressDialog.dismiss();
        } else {

            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(emailString, passowrdString)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest
                                        .Builder().setDisplayName(nameString).build();
                                firebaseUser.updateProfile(userProfileChangeRequest)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("4MarchV1", "Update Profile OK");
                                            }
                                        });

                                Toast.makeText(getActivity(),
                                        "Success Register", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                myAlert.myDialog("Cannon Register",
                                        task.getException().getMessage());
                            }
                            progressDialog.dismiss();
                        }
                    });


        }   // if

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.register));

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
