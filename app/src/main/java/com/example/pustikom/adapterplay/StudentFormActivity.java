package com.example.pustikom.adapterplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;

/**
 * Created by eka on 04/11/16.
 */

public class StudentFormActivity extends AppCompatActivity {
    private AppCompatSpinner genderSpinner;
    private int actionMode;
    private Student student;
    private static final String ADD_MODE="Add Student";
    private static final String EDIT_MODE="Edit Student";
    private EditText nimText, nameText, mailText, phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        genderSpinner = (AppCompatSpinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this,R.array.gender_array,R.layout.support_simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        Intent intent = getIntent();

        //register Views
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.floatingSaveButton);
        FloatingActionButton cancelButton = (FloatingActionButton) findViewById(R.id.floatingCancelButton);
        nimText = (EditText) findViewById(R.id.edit_nim);
        nameText = (EditText) findViewById(R.id.edit_nama);
        genderText = (EditText) findViewById(R.id.edit_gender);
        mailText = (EditText) findViewById(R.id.edit_email);
        phoneText = (EditText) findViewById(R.id.edit_phone);

        //setup listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        @Override
        protected void onStart() {
            super.onStart();
            displayDatabaseInfo();

    private void displayDatabaseInfo() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StudentContract._ID,
                StudentContract.COLUMN_NIM,
                StudentContract.COLUMN_NAME,
                StudentContract.COLUMN_GENDER,
                StudentContract.COLUMN_MAIL,
                StudentContract.COLUMN_PHONE };

        // Perform a query on the provider using the ContentResolver.
        Cursor cursor = getContentResolver().query(
                StudentContract.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null);                  // The sort order for the returned rows

        TextView displayView = (TextView) findViewById(R.id.text_view_student);

        try {
            // Create a header in the Text View that looks like this:
            displayView.setText("The student table contains " + cursor.getCount() + " student.\n\n");
            displayView.append(StudentContract._ID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
                    StudentContract.COLUMN_NIM + "TEXT NOT NULL" +
                    StudentContract.COLUMN_NAME + "TEXT NOT NULL" +
                    StudentContract.COLUMN_GENDER + "INTEGER" +
                    StudentContract.COLUMN_MAIL + "TEXT" +
                    StudentContract.COLUMN_PHONE + "TEXT" );

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_NAME);
            int nimColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_NIM);
            int genderColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_GENDER);
            int mailColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_MAIL);
            int phoneColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(nimColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                String currentMail = cursor.getString(mailColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentNim + " - " +
                        currentGender + " - " +
                        currentMail + " - " +
                        currentPhone));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }


        actionMode = intent.getIntExtra("mode",0);
        switch (actionMode){
            case 0:
                setTitle("Add Student");
                break;
            case 1:
                setTitle("Edit Student");
                //Todo: case edit preload all edit text with passed data
                student = (Student) intent.getSerializableExtra("Student");
                nimText.setText(student.getNoreg());
                nameText.setText(student.getName());
                mailText.setText(student.getMail());
                phoneText.setText(student.getPhone());
                genderSpinner.setSelection(student.getGender());
                break;
        }

        //setup listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = nimText.getText().toString();
                String name = nameText.getText().toString();
                int genderId = genderSpinner.getSelectedItemPosition();
                String mail = mailText.getText().toString();
                String phone = phoneText.getText().toString();
                student = new Student(nim,name,genderId,mail,phone);
                if(validateStudent(student)) {
                    saveStudent(student,actionMode);
                    finish();
                }
            }
        });
    }

    /**
     * Todo: implement validation the criterias are
     * 1. NIM must be all numbers and 8 digits
     * 2. Name must not be empty
     * 3. Any other field are optionals
     * @param student
     * @return true if all field validated, false otherwise
     */
    private boolean validateStudent(Student student){
        //put your code here, set validated to false if input not conformed
        //use nameText.setError("Please input name"); or nimText.setError("NIM must be 8 character"); in case input invalidated
        //change isValidated to false for each error found
        boolean isValidated=true;

        if(student.getName().length()==0){
            nameText.setError("Please input name");
            isValidated=false;
        }

        if(student.getNoreg().length()!=10) {
            nimText.setError("NIM must be 10 character");
            isValidated=false;
        }
        return isValidated;
    }

    /**
     * Todo: implement save data
     * @param student
     */
    private void saveStudent(Student student,int mode){
        if(mode==0){
            //add current student to global StudentList
            Student.getStudentList().add(student.getId(),student);
        } else{
            Student.getStudentList().set(student.getId(),student);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        //TODO: Only load menu when in edit Mode
        if(actionMode==1)
            inflater.inflate(R.menu.edit_student_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.deleteStudentItem:
                //Todo: Implement action for delete student
                int id=student.getId();
                Student.getStudentList().remove(id);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
