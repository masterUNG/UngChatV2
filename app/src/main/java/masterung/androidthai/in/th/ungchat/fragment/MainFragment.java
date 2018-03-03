package masterung.androidthai.in.th.ungchat.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import masterung.androidthai.in.th.ungchat.R;
import masterung.androidthai.in.th.ungchat.ServiceActivity;
import masterung.androidthai.in.th.ungchat.utiliti.MyAlert;

/**
 * Created by masterung on 4/3/2018 AD.
 */

public class MainFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        Login Controller
        loginController();

    }   // Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText passwordlEditText = getView().findViewById(R.id.edtPassword);

                String emailString = emailEditText.getText().toString().trim();
                String passwordlString = passwordlEditText.getText().toString().trim();

                final MyAlert myAlert = new MyAlert(getActivity());

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Check Email and Password");
                progressDialog.setMessage("Please Wait Few Minus...");
                progressDialog.show();

                if (emailString.isEmpty() || passwordlString.isEmpty()) {
                    myAlert.myDialog(getString(R.string.have_space),
                            getString(R.string.message_have_space));
                    progressDialog.dismiss();
                } else {

                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword(emailString, passwordlString)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(),
                                                "Welcome", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(), ServiceActivity.class));
                                        getActivity().finish();
                                    } else {
                                        myAlert.myDialog("Cannot Login",
                                                task.getException().getMessage());
                                    }
                                    progressDialog.dismiss();
                                }
                            });

                }

            }
        });
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null).commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}
