package com.example.b07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class admin_main extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        final Button addCourseButton = findViewById(R.id.addCourseButton);
        final Button modifyCourseButton = findViewById(R.id.modifyCourseButton);
        final Button deleteCourseButton = findViewById(R.id.deleteCourseButton);
        final Button logoutButton = findViewById(R.id.logoutButton);
        final Button go_to_all_courses = findViewById(R.id.button7);

        textView = findViewById(R.id.textViewName2);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        textView.setText(username);

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_main.this, Course_addition_admin.class);
                intent.putExtra("username", username);
                startActivity(intent);
//                startActivity(new Intent(admin_main.this, Course_addition_admin.class));
            }
        });

        modifyCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_main.this, MainActivity2.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_main.this, MainActivity3.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_main.this, Login.class));
            }
        });

        go_to_all_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(admin_main.this, DisplayCourseDetails.class));
                Intent intent = new Intent(admin_main.this, DisplayCourseDetails.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}