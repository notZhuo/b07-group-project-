package com.example.b07courseaddition;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.b07courseaddition.databinding.FragmentSecondBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Layout;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.b07courseaddition.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    Spinner s1;
    List<String> items1;

    private Button first_to_second;

    Spinner s2;
    List<String> items2;

    //    public ListView listView;
    ArrayList<String> listView = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> list = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        super.onCreate(savedInstanceState);

        com.example.b07courseaddition.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        final String[] sq = new String[1];
        final String[] pq = new String[1];

//        s1 = findViewById(R.id.dropdownmenu);
        s2 = findViewById(R.id.dropdownmenuq);

        items1 = new ArrayList<>();
        items2 = new ArrayList<>();

        // The Course Offering schedule
        items1.add("Fall");
        items1.add("Winter");
        items1.add("Summer");

        // The Course Pre-req (to be defined)
        items2.add("--select--");
        items2.add("CSCA08");
        items2.add("CSCA48");
        items2.add("CSCB07");
        items2.add("CSCB09");
        items2.add("CSCB58");
        items2.add("CSCB36");
        items2.add("CSCB63");
        items2.add("MATA22");
        items2.add("MATA31");
        items2.add("CSCA67");
        items2.add("MATA37");
        items2.add("MATB41");
        items2.add("STAB52");
        items2.add("MATB24");

        s2.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items2));

//        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = items1.get(i);
//                sq[0] = item;
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        CheckBox c4 = findViewById(R.id.checkBox4);
        CheckBox c2 = findViewById(R.id.checkBox2);
        CheckBox c3 = findViewById(R.id.checkBox3);

        final String[] d = {""};


        final String[] k = {""};


        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = items2.get(i);
                if(!Objects.equals(item, "--select--")){
                if(!Objects.equals(k[0], "")) k[0] = k[0] + ",";
                k[0] = k[0] + item;
                Toast.makeText(MainActivity.this, "" + item + "is added to the course pre-req.", Toast.LENGTH_SHORT).show();}
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Firebase thing by member 1
        final EditText edit_course_name = findViewById(R.id.editTextTextPersonName3);
        final EditText edit_course_code = findViewById(R.id.editTextTextPersonName2);
//        final View edit_course_offering = findViewById(R.id.dropdownmenu);
//        final View edit_course_pre_req = findViewById(R.id.dropdownmenuq);

        listView.add(edit_course_code.getText().toString());

        Button btn = findViewById(R.id.button_first);

        DAOCourseAddition dao = new DAOCourseAddition();

        btn.setOnClickListener(v->
        {
            if(c2.isChecked()){
                if(!Objects.equals(d[0], "")) d[0] = d[0] + ",";
                d[0] = d[0] + "Fall";
            }
            if(c3.isChecked()){
                if(!Objects.equals(d[0], "")) d[0] = d[0] + ",";
                d[0] = d[0] + "Winter";
            }
            if(c4.isChecked()){
                if(!Objects.equals(d[0], "")) d[0] = d[0] + ",";
                d[0] = d[0] + "Summer";
            }
            Course_details emp = new Course_details(edit_course_name.getText().toString(), edit_course_code.getText().toString(), d[0], k[0]);
            dao.add(emp).addOnSuccessListener(suc->
            {
                Toast.makeText(this, edit_course_code.getText().toString() + " is added!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->
            {
                Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
//        items2.add("hola" + edit_course_code.getText().toString());
        first_to_second = findViewById(R.id.move);
//        first_to_second.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                first_to_second.setVisibility(View.GONE);
////                Fragment fragment = new SecondFragment();
////                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                fragmentTransaction.replace(R.id.container, fragment).commit();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    int year_setter = 0;
    int session_setter = 0;
    int year = 0;
    public void init() {
        year_setter = 0;
        TableLayout stk = (TableLayout) findViewById(R.id.tableLayout);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Sessions ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Courses ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < 25; i++) {
            if(year_setter == 0){
                year++;
            }
            else if(year_setter == 2){
                year_setter = 0;
            }
            year_setter++;
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            String current_session = "";
            if(session_setter == 0) current_session = current_session + "Fall ";
            else if(session_setter == 1) current_session = current_session + "Summer ";
            else if(session_setter == 2) current_session = current_session + "Winter ";
            current_session = current_session + year;
            t1v.setText(current_session);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("Product " + i);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            stk.addView(tbrow);
        }
    }
}