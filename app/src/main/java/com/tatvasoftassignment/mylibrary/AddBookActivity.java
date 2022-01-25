package com.tatvasoftassignment.mylibrary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddBookActivity extends AppCompatActivity {

    private Button btnAddBook;
    private TextView txtDate;
    private Spinner mSpinner;
    private EditText etBookName, etAuthorName;

    private RadioButton rFiction;
    private CheckBox cChild, cAdult, cSixtyPlus;
    String bookGenre, type, age = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        memoryAllocation();
        setLaunchingDate();
        setSpinner();
        DataHelper db = new DataHelper(AddBookActivity.this);
        btnAddBook.setOnClickListener(view -> {

            if (isValid()) {

                if (rFiction.isChecked()) {
                    type = "Fiction";
                } else {
                    type = "Non-fiction";
                }
                if (cChild.isChecked()) {
                    age = "child" + "\n";
                } else {
                    age += "";
                }
                if (cAdult.isChecked()) {
                    age += "Adult" + "\n";
                } else {
                    age += "";
                }
                if (cSixtyPlus.isChecked()) {
                    age += "senior citizen" + "\n";
                } else {
                    age += "";
                }

                db.insertData(etBookName.getText().toString(), etAuthorName.getText().toString(), bookGenre, type, txtDate.getText().toString(), age);
                Toast.makeText(getApplicationContext(), "Book added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }


        });
    }

    private Boolean isValid() {
        boolean valid = true;

        if (etBookName.getText().toString().length() == 0) {
            Toast.makeText(this, "Enter Book Name", Toast.LENGTH_LONG).show();
            valid = false;
        } else if (etAuthorName.getText().toString().length() == 0) {
            Toast.makeText(this, "Enter Author Name", Toast.LENGTH_LONG).show();
            valid = false;
        } else if (txtDate.getText().toString().length() == 0) {

            Toast.makeText(this, "select Date", Toast.LENGTH_LONG).show();
            valid = false;
        } else if (!cChild.isChecked() && !cAdult.isChecked() && !cSixtyPlus.isChecked()) {
            Toast.makeText(this, "select age group", Toast.LENGTH_LONG).show();
            valid = false;
        }


        return valid;
    }

    private void memoryAllocation() {
        txtDate = findViewById(R.id.txtDate);
        mSpinner = findViewById(R.id.mSpinner);
        btnAddBook = findViewById(R.id.btnAddBook);
        etBookName = findViewById(R.id.etBookName);
        etAuthorName = findViewById(R.id.etAuthorName);
        rFiction = findViewById(R.id.rFiction);
        cChild = findViewById(R.id.cChild);
        cAdult = findViewById(R.id.cAdult);
        cSixtyPlus = findViewById(R.id.cSixtyPulse);


    }

    private void setSpinner() {

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.genreArray, android.R.layout.simple_spinner_item);

        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapterSpinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bookGenre = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setLaunchingDate() {
        txtDate.setOnClickListener(v -> {
            int date, set_month, set_year;
            GregorianCalendar gc = new GregorianCalendar();
            date = gc.get(Calendar.DAY_OF_MONTH);
            set_month = gc.get(Calendar.MONTH);
            set_year = gc.get(Calendar.YEAR);
            DatePickerDialog dpd = new DatePickerDialog(this, (datePicker, year, month, day) -> {
                String launchDate = day + "/" + (month + 1) + "/" + year;
                txtDate.setText(launchDate);
            }, set_year, set_month, date);
            dpd.show();
        });

    }


}