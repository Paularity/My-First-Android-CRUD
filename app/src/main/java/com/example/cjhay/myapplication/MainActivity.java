package com.example.cjhay.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtID,edtName,edtEmail;
    Button btnAdd,btnUpdate,btnDelete;
    ListView listPersons;

    List<Person> data = new ArrayList();
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        listPersons = (ListView) findViewById(R.id.list);
        edtID = (EditText) findViewById(R.id.edtID);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        //Load Data
        refreshData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String id = edtID.getText().toString();
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
                if(!id.equals("") && !name.equals("") && !email.equals("")) {
                    Person person = new Person(Integer.parseInt(
                            edtID.getText().toString())
                            , edtName.getText().toString()
                            , edtEmail.getText().toString());
                    db.addPerson(person);
                    refreshData();
                }
                else
                {
                    toastMessage("Please fill in the blank/s");
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtID.getText().toString();
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                if(!id.equals("") && !name.equals("") && !email.equals("")) {
                    Person person = new Person(Integer.parseInt(
                            edtID.getText().toString())
                            , edtName.getText().toString()
                            , edtEmail.getText().toString());
                    db.updatePerson(person);
                    refreshData();
                }
                else
                {
                    toastMessage("Please fill in the blank/s");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtID.getText().toString();
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                if(!id.equals("") && !name.equals("") && !email.equals("")) {
                Person person = new Person(Integer.parseInt(
                        edtID.getText().toString())
                        , edtName.getText().toString()
                        , edtEmail.getText().toString());
                db.deletePerson(person);
                refreshData();
            }else
                {
                    toastMessage("Please fill in the blank/s");
                }
            }
        });



    }

    private void refreshData()
    {
        data = db.getAllPerson();
        PersonAdapter adapter = new PersonAdapter(MainActivity.this,data,edtID,edtName,edtEmail);
        listPersons.setAdapter(adapter);
    }
    public void toastMessage(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
