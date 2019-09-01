package com.example.training.empoz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.training.empoz.R;
import com.example.training.empoz.adapters.Employee;
import com.example.training.empoz.fragment.EmployeFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EmployeeList extends AppCompatActivity {
ListView listView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<String> emp=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        database = FirebaseDatabase.getInstance();

        listView = (ListView)findViewById(R.id.emplist);
        myRef = database.getReference("Users");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emp);
        listView.setAdapter(adapter);

        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {

                final Employee valuee = dataSnapshot.getValue(Employee.class);
                emp.add(valuee.name);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String  itemValue= (String) listView.getItemAtPosition(position);

            }
        });

    }
}
