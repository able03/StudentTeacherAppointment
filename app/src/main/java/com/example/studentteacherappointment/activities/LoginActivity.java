package com.example.studentteacherappointment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity
{
    private Spinner sp_role;
    private TextInputLayout lay_uName, lay_pass;
    private TextInputEditText et_uName, et_pass;
    private MaterialButton btn_login;
    private TextView tv_register_here;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initValues();
        setSpinner();
        setListeners();
    }

    private void initValues()
    {
        sp_role = findViewById(R.id.spRoleLogin);

        lay_uName = findViewById(R.id.layUNameLogin);
        lay_pass = findViewById(R.id.layPassLogin);

        et_uName = findViewById(R.id.etUNameLogin);
        et_pass = findViewById(R.id.etPassLogin);

        btn_login = findViewById(R.id.btnLogin);

        tv_register_here = findViewById(R.id.tvLoginHere);

        dbHelper = new DBHelper(this);
    }

    private void setSpinner()
    {
        ArrayAdapter<CharSequence> rolesAdapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_dropdown_item);
        rolesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_role.setAdapter(rolesAdapter);
    }

    private void setListeners()
    {
        btn_login.setOnClickListener(login -> {
            loginProcess();
        });

        tv_register_here.setOnClickListener(registerHere -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginProcess()
    {
        String role = sp_role.getSelectedItem().toString();
        String uname = et_uName.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();

        Cursor cursor = dbHelper.checkLogin(role, uname, pass);
        cursor.moveToFirst();
        if(cursor.getCount() > 0)
        {

            String id = cursor.getString(0);
            if(role.equals("Student")){
                Toast.makeText(this, "Student: " + id, Toast.LENGTH_SHORT).show();
            }
            else if(role.equals("Teacher"))
            {
                Toast.makeText(this, "Student: " + id, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
        }
    }



}