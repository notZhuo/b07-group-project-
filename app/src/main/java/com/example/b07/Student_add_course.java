package com.example.b07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class Student_add_course extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://cscb07-project-ba4c9-default-rtdb.firebaseio.com/").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_course);

        final EditText courseCode = findViewById(R.id.courseCode);
        final EditText courseSession = findViewById(R.id.courseSession);
        final Button addCourseButton = findViewById(R.id.addCourseButton);
        final Button backButton = findViewById(R.id.backButton);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String courseCodeText = courseCode.getText().toString();
                final String courseSessionText = courseSession.getText().toString();

                if(courseCodeText.isEmpty() || courseSessionText.isEmpty()){
                    Toast.makeText(Student_add_course.this, "Please fill in details", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Course_details").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(courseCodeText)){

                                final String courseSessions = snapshot.child(courseCodeText).child("Course Offerings").getValue(String.class);

                                List<String> sessions = Arrays.asList(courseSessions.split(","));

                                if(sessions.contains(courseSessionText)){
                                    final String courseNameText = snapshot.child(courseCodeText).child("Course Name").getValue(String.class);
                                    databaseReference.child("students").child(username).child("courses").child(courseCodeText).child("code").setValue(courseCodeText);
                                    databaseReference.child("students").child(username).child("courses").child(courseCodeText).child("session").setValue(courseSessionText);
                                    databaseReference.child("students").child(username).child("courses").child(courseCodeText).child("name").setValue(courseNameText);
                                    Toast.makeText(Student_add_course.this,"course added successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Student_add_course.this, "Cannot add the course. Course is not offered in this session", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Student_add_course.this, "Cannot add the course", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student_add_course.this, student_main.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}