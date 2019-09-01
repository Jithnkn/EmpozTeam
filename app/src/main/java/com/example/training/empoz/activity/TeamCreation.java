package com.example.training.empoz.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.training.empoz.R;
import com.example.training.empoz.adapters.EmployeeDepartment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TeamCreation extends AppCompatActivity {
    private ListView teamCreateList,newTeam;
    Inflater inflater;
    private ArrayList<String> addEmployee=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_creation);
        teamCreateList= findViewById(R.id.teamCreateList);
        newTeam= findViewById(R.id.newTeam);
        databaseReference=firebaseDatabase.getReference("User");
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplication(),android.R.layout.simple_list_item_1,addEmployee);
        teamCreateList.setAdapter(adapter);
        final   String[] department=new String[1];
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                EmployeeDepartment employeeDepartment = dataSnapshot.getValue(EmployeeDepartment.class);
                final String uid=firebaseAuth.getCurrentUser().getUid();

                databaseReference.child(uid).child("department").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        department[0]= (String) dataSnapshot.getValue();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                employeeDepartment.department=department[0];
                    addEmployee.add(employeeDepartment.name);
                    adapter.notifyDataSetChanged();

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
    }
}
