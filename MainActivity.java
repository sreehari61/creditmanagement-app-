package com.creditmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Databasehelper mydb;
    Button btn,btnview;
    EditText name,email,credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new Databasehelper(this);

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        credits = (EditText)findViewById(R.id.number);

        btn = (Button) findViewById(R.id.btn);
        btnview = (Button) findViewById(R.id.btnview);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData();
            }
        });
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewdata();
            }
        });

    }

    public void AddData() {
//        int cre = Integer.parseInt(credits.getText().toString());
        boolean isInserted = mydb.insertData(name.getText().toString(),
                email.getText().toString(),
                credits.getText().toString());
        if(isInserted == true)
            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
    }

    public void viewdata()
    {
        startActivity(new Intent(this,Viewdata.class));
    }
}
