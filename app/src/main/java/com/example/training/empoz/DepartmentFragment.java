package com.example.training.empoz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.training.empoz.model.Department;
import com.example.training.empoz.adapters.Employee;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class DepartmentFragment extends Fragment {
    LayoutInflater inflate;
    View view;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ListView listview;
    ArrayList<String> dept=new ArrayList<>();
    ImageButton Adddept;
    EditText txtdept;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        myRef =database.getReference("Department");
        view=inflater.inflate(R.layout.fragment_department, container, false);
        inflate  = getLayoutInflater();
        listview = view.findViewById(R.id.department);

        Adddept  = view.findViewById(R.id.fab_add);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,dept);
        listview.setAdapter(adapter);
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
                //emp.remove(value.name);
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Adddept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setTitle("Employee");
                builder.setPositiveButton("ok!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"ok clicked",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();

            }
        });
        return view;
    }

}




