package com.example.nguyenhoanganh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.nguyenhoanganh.database.AppDatabase;

public class MainActivity extends AppCompatActivity {
    EditText edName, edEmail, edDescription;
    Spinner spinner;
    CheckBox cbPrivacy;
    Button btnSend;
    String option = "Gender";
    String[] listOption = {"Male", "Female", "LGBT"};
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getAppDatabase(this);

        initView();
        sendFeedBack();
    }

    private void initView() {
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edDescription = findViewById(R.id.edDescription);

        spinner = findViewById(R.id.spinner);
        setSpinner();

        cbPrivacy = findViewById(R.id.cbPrivacy);
        btnSend = findViewById(R.id.btnSend);
    }

    private void sendFeedBack() {
        FeedBack feedback = new FeedBack();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString().trim().toLowerCase();
                String email = edEmail.getText().toString().toLowerCase().trim();
                String description = edDescription.getText().toString().toLowerCase().trim();
                if (name.isEmpty() || email.isEmpty() || description.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Name or email or description or all of them is required", Toast.LENGTH_SHORT).show();
                } else {
                    if (!cbPrivacy.isChecked()) {
                        Toast.makeText(MainActivity.this,
                                "Check the checkbox", Toast.LENGTH_SHORT).show();
                    } else {
                        feedback.setName(name);
                        feedback.setEmail(email);
                        feedback.setDescription(description);
                        feedback.setOption(option);
                        db.feedbackDao().insertFeedback(feedback);
                        Toast.makeText(MainActivity.this,
                                "There're " + db.feedbackDao().getAllFeedback().size() + " feedbacks in database",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listOption);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option = listOption[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}