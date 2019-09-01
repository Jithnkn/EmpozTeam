package com.example.training.empoz.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import com.example.training.empoz.R;
import com.example.training.empoz.adapters.Employee;
import com.example.training.empoz.adapters.LeaveSender;
import com.example.training.empoz.utils.Constants;
import com.example.training.empoz.utils.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LeaveFragment extends Fragment {


    EditText  EditReason;
    DatePicker  datefrom,dateto;
    Button buttonCancel,buttonSubmit;
    TextView datefromview, datetoview;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    String uid,name;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_leave_tab, container, false);
        EditReason= view.findViewById(R.id.EditReason);

        datefrom= view.findViewById(R.id. datefrom );
        dateto= view.findViewById(R.id. dateto);
        datefromview =view.findViewById(R.id.textview1);
        datetoview =view.findViewById(R.id.textview2);
        DatePickerDialog DatePickerDialog;
        buttonCancel= view.findViewById(R.id.buttonCancel);
        buttonSubmit= view.findViewById(R.id.buttonSubmit);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        int day = datefrom.getDayOfMonth();
        int month = datefrom.getMonth();
        int year = datefrom.getYear();
        int day1 = dateto.getDayOfMonth();
        int month1 = dateto.getMonth();
        int year2 = dateto.getYear();
        databaseReference=firebaseDatabase.getReference("Leave");
        uid=firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference data=firebaseDatabase.getReference("Users/"+uid);
        String user= PreferenceManager.getFromPreference(getActivity(), Constants.USER_NAME);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
           Employee employee=dataSnapshot.getValue(Employee.class);
           name=employee.getName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditReason.setText("");

            }
        });
        datefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                android.app.DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datefromview.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                android.app.DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datetoview.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(EditReason.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Incorrect",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    data.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Employee employee=dataSnapshot.getValue(Employee.class);
                            name=employee.getName();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(getActivity(),"Leave Request Submitted",Toast.LENGTH_SHORT).show();
                    final String key = databaseReference.push().getKey();
                    LeaveSender leaveSender = new LeaveSender(EditReason.getText().toString(), datefromview.getText().toString(), datetoview.getText().toString(),name);
                    databaseReference.child(key).setValue(leaveSender);
                }
            }
            });
        EditReason.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(EditReason.getText().toString().isEmpty())
                {
                    EditReason.setError("Please fill");
                }

            }
        });


        return view;
   }



}
