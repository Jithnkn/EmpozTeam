package com.example.training.empoz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.training.empoz.R;
import com.example.training.empoz.adapters.Employee;
import com.example.training.empoz.fragment.EmployeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeProfile extends AppCompatActivity {
    TextView ename, department, email;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
     Employee employee;
  String a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        ename = (TextView) findViewById(R.id.name);
        department = (TextView) findViewById(R.id.department);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        final Intent intent = getIntent();
        final String name1 = intent.getStringExtra("name");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 employee = dataSnapshot.getValue(Employee.class);
                if(employee.name.equalsIgnoreCase(name1))
                {
                      a=employee.name;
                      b=employee.department;
                    if(a.equalsIgnoreCase(name1))
                    {

                        ename.setText(a);
                        department.setText(b);



                    }
                      stopService(intent);


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });
//        String name = employee.name;
//        if (name.equalsIgnoreCase(name1)) {
//            ename.setText(employee.name);
//            email.setText(employee.email);
//            department.setText(employee.department);



    }

    public void onBackPressed() {
        super.onBackPressed();
    }

}




// if(detailis[3].equalsIgnoreCase(employee.name))
//                        {
//                            //detailis[1]=employee.department;
//
//                            //detailis[2]=employee.email;
//                            Bundle bundle=new Bundle();
//                            bundle.putString("name",detailis[3]);
//                           // bundle.putString("email",employee.email);
//                            //bundle.putString("department",employee.department);
//
//                            intent.putExtras(bundle);
//                        }

