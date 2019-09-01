package com.example.training.empoz.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.training.empoz.fragment.LeaveFragment;
import com.example.training.empoz.fragment.LeaveListView;
import com.example.training.empoz.fragment.LeaveRequest;
import com.example.training.empoz.fragment.OrganizationPostsFragment;
import com.example.training.empoz.fragment.Post;
import com.example.training.empoz.R;
import com.example.training.empoz.fragment.DepartmentFragment;
import com.example.training.empoz.fragment.EmployeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrganizationHome extends AppCompatActivity  {
    LayoutInflater inflate;
    View view;
    TextView textView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ListView listview;
    FirebaseAuth firebaseAuth;
    ArrayList<String> emp=new ArrayList<>();


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.oraganization_home);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_organization_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.notification:
                AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
                alertbox.setMessage("No Posts Yet");
                alertbox.show();
                break;

            case R.id.action_about:
                Toast.makeText(this,"About clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_feedback:
                Toast.makeText(this,"Feedback clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logout:
                firebaseAuth.signOut();
                Toast.makeText(this,"Logout clicked",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this,EmployeeLogin.class);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

           switch (position)
           {
               case 0:EmployeFragment employeFragment=new EmployeFragment();
               return employeFragment;
               case 1:DepartmentFragment departmentFragment=new DepartmentFragment();
               return departmentFragment;
               case 2:OrganizationPostsFragment organizationPostsFragment=new OrganizationPostsFragment();
               return organizationPostsFragment;
               case 3:
                   LeaveListView leaveListView=new LeaveListView();
                   return leaveListView;
           }
           return null;
        }

        @Override
        public int getCount() {

            return 4;
        }
        //hggg
    }

}
