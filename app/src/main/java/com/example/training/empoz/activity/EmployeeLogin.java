package com.example.training.empoz.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.training.empoz.R;
import com.example.training.empoz.UserRegistration;
import com.example.training.empoz.utils.Constants;
import com.example.training.empoz.utils.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EmployeeLogin extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    EditText etUsername, etPassword;
    TextView tvRegister;
    Button btnLogin;
    DatabaseReference rootRef;
    FirebaseDatabase database;
    private ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    String email,password,department,user_name;
    public static final String Username = "nameKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername =(EditText) findViewById(R.id.input_email);
        etPassword =(EditText) findViewById(R.id.input_password);
        btnLogin =(Button) findViewById(R.id.btn_login);
        tvRegister =(TextView) findViewById(R.id.link_signup);

        btnLogin.setOnClickListener(this);
        //tvRegister.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), UserRegistration.class);
                startActivity(register);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.btn_login:
                onLoginButtonClicked();
                break;




        }
    }

    private void onLoginButtonClicked() {
        email = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Logging in..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            final String username = firebaseAuth.getCurrentUser().getUid();
                            // Toast.makeText(getApplicationContext(),username+"depart work",Toast.LENGTH_LONG).show();

                            database = FirebaseDatabase.getInstance();
                            rootRef = database.getReference("Users");
                            rootRef.child(username).child("department").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    department = (String) dataSnapshot.getValue();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            rootRef.child(username).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    user_name = (String) dataSnapshot.getValue();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            rootRef.child(username).child("position").addListenerForSingleValueEvent(new ValueEventListener() {
                                String depart;

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    depart = (String) dataSnapshot.getValue();
                                    String emp = "Employee";
                                    String dep = "Department";
                                    String org = "Organization";

                                    //Toast.makeText(getApplicationContext(),depart,Toast.LENGTH_LONG).show();
                                    if (TextUtils.equals(depart, emp)) {
                                        PreferenceManager.putToPreference(EmployeeLogin.this, Constants.USERNAME, username);
                                        PreferenceManager.putToPreference(EmployeeLogin.this, Constants.DEPARTMENT, department);
                                        PreferenceManager.putToPreference(EmployeeLogin.this, Constants.USER_NAME, user_name);

                                        Intent intent = new Intent(getApplicationContext(), EmployeHome.class);
                                        startActivity(intent);

                                    } else if (TextUtils.equals(depart, dep)) {
                                        PreferenceManager.putToPreference(EmployeeLogin.this, Constants.DEPARTMENT, department);
                                        PreferenceManager.putToPreference(EmployeeLogin.this, Constants.USER_NAME, user_name);

                                        Intent intent = new Intent(getApplicationContext(), DepartmentHome.class);
                                        startActivity(intent);
                                    } else if (TextUtils.equals(depart, org)) {
                                        PreferenceManager.putToPreference(EmployeeLogin.this, Constants.USER_NAME, user_name);
                                        Intent intent = new Intent(getApplicationContext(), OrganizationHome.class);
                                        startActivity(intent);
                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });


                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
