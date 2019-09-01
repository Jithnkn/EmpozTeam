package com.example.training.empoz.fragment;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.training.empoz.activity.EmployeeProfile;
import com.example.training.empoz.adapters.Employee;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.training.empoz.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;


public class EmployeFragment extends Fragment {


    LayoutInflater inflate;
    View view;
    TextView textView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ListView listview;
    SearchView searchView;
    private ArrayList<String> emp=new ArrayList<>();
   FirebaseAuth firebaseAuth;



    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view=inflater.inflate(R.layout.fragment_employe, container, false);
        inflate = getLayoutInflater();
        database = FirebaseDatabase.getInstance();
   firebaseAuth=FirebaseAuth.getInstance();
        listview = view.findViewById(R.id.emplist);
        searchView= view.findViewById(R.id.idsearch);

        myRef = database.getReference("Users");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, emp);
        listview.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                              @Override
                                              public boolean onQueryTextSubmit(String query) {
                                                  return false;
                                              }

                                              @Override
                                              public boolean onQueryTextChange(String newText) {
                                                  String text = newText;
                                                  adapter.getFilter().filter(newText);
                                                  return false;
                                              }
                                          });




        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {

                final Employee value = dataSnapshot.getValue(Employee.class);
                DatabaseReference databaseReference=database.getReference("Users/"+firebaseAuth.getCurrentUser().getUid());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                   Employee employee=dataSnapshot.getValue(Employee.class);


                   if(!employee.getName().equalsIgnoreCase(value.name)) {
                       if (employee.getPosition().equalsIgnoreCase("department")) {
                           if (employee.getDepartment().equalsIgnoreCase(value.department)) {
                               emp.add(value.name);
                               adapter.notifyDataSetChanged();
                           }
                       }
                       else
                       {
                        emp.add(value.name);
                           adapter.notifyDataSetChanged();
                       }
                   }
                 }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


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

        final String[] detailis=new String[4];

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //Toast.makeText(getContext(),""+itemValue,Toast.LENGTH_SHORT).show();


   Intent intent=new Intent(getContext(),EmployeeProfile.class);


                        final String  itemValue= (String) listview.getItemAtPosition(position);
                        intent.putExtra("name",itemValue);
                        startActivity(intent);



            }
        });







        return view;
    }


}
