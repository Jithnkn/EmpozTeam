package com.example.training.empoz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.training.empoz.activity.EmployeeLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private EditText input_name;
    private EditText input_password;
    private EditText input_email;
    private EditText input_password_confirm;
    private Button register_button;
    private String item;
    private Spinner input_spinner;
    private TextView logintxtView;
    private ProgressDialog progressDialog;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        input_name=(EditText) findViewById(R.id.input_name);
        input_email=(EditText) findViewById(R.id.input_email);
        input_password=(EditText) findViewById(R.id.input_password);
        logintxtView=(TextView) findViewById(R.id.login_text);
        input_password_confirm=(EditText) findViewById(R.id.input_confirmpassword);
        input_spinner=(Spinner) findViewById(R.id.department_select);
        progressDialog = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mRef=FirebaseDatabase.getInstance().getReference().child("Users");
        register_button=(Button) findViewById(R.id.btn_signup);

        List <String> list = new ArrayList <String>();
        list.add("Select Any Option");
       // list.add("Organization");
        list.add("UI Design");
        list.add("Production");
        list.add("Human Resource Management");
        list.add("Accounting and Finance");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            input_spinner.setAdapter(dataAdapter);
            input_spinner.setOnItemSelectedListener(this);

            logintxtView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent= new Intent(getApplicationContext(),EmployeeLogin.class);
                    startActivity(intent);
                    return false;
                }
            });

            register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=input_name.getText().toString().trim();
                final String email=input_email.getText().toString().trim();
                final String password=input_password.getText().toString().trim();
                final String confirm_password=input_password_confirm.getText().toString().trim();
                final String spinner=item;


                if(!TextUtils.equals(password,confirm_password))
                {
                    Toast.makeText(getApplicationContext(),"Password And Confirm Password Must be Equal",Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(getApplicationContext(),"Invalid Email ",Toast.LENGTH_SHORT).show();
                }
                else if(spinner=="Select Any Option")
                {
                    Toast.makeText(getApplicationContext(),"Please Select any department ",Toast.LENGTH_SHORT).show();

                }

                else if (TextUtils.isEmpty(name))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Your Name",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(email))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Your Email",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(password))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(confirm_password))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Your Confirm Password",Toast.LENGTH_SHORT).show();
                    }
                    else
                {


//                       // Toast.makeText(getApplicationContext(),"Working.. working",Toast.LENGTH_SHORT).show();
                    progressDialog.setMessage("User Registering in Process :) ");
                    progressDialog.show();


                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    String username=mAuth.getCurrentUser().getUid();
                                    progressDialog.dismiss();

                                    DatabaseReference current_user_db=mRef.child(username);
                                    current_user_db.child("name").setValue(name);
                                    current_user_db.child("department").setValue(spinner);
                                    current_user_db.child("position").setValue("Employee");
                                    Toast.makeText(getApplicationContext(),"Register Successfully..Now Login",Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(getApplicationContext(),EmployeeLogin.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"User already Registered,\nPlease Login", Toast.LENGTH_LONG).show();
                                    Intent intent= new Intent(getApplicationContext(),EmployeeLogin.class);
                                    startActivity(intent);
                                }

                            }
                        });
                    }


                }


        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
