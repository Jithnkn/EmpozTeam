package com.example.training.empoz.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.training.empoz.R;
import com.example.training.empoz.model.Department;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class DepartmentFragment extends Fragment {
    LayoutInflater inflate;
    View view;
    TextView textView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ListView listview;
    final Context context=getContext();
    ArrayList<String> dept=new ArrayList<>();
    ImageButton adddept;
    EditText txtdept;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        myRef =database.getReference("Department");
        view=inflater.inflate(R.layout.fragment_department, container, false);
        inflate  = getLayoutInflater();
        listview = view.findViewById(R.id.department);
        adddept=(ImageButton)view.findViewById(R.id.fab_add);

        adddept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.alertdialog, null);

                txtdept=(EditText) alertLayout.findViewById(R.id.et_dept);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Add Department:");

                alert.setView(alertLayout);

                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String userkey=myRef.push().getKey();
                        final Department department=new Department(txtdept.getText().toString());
                        myRef.child(userkey).setValue(department);
                      Toast.makeText(getContext(), "Department Added Successfully "  , Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }



        });
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,dept);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String depart= (String) listview.getItemAtPosition(position);

            }
        });


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Department value = dataSnapshot.getValue(Department.class);
                dept.add(value.name);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Department value = dataSnapshot.getValue(Department.class);
                // //emp.remove(value.name);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return view;


    }




}







