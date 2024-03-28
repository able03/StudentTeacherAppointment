package com.example.studentteacherappointment.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentteacherappointment.CustomToast;
import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity
{
    private Spinner sp_role, sp_gender;
    private TextInputLayout lay_fName, lay_mName, lay_lName, lay_uName, lay_pass;
    private TextInputEditText et_fName, et_mName, et_lName, et_uName, et_pass;
    private MaterialButton btn_register;
    private TextView tv_login_here;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initValues();
        setAllSpinner();
        setListeners();
    }

    private void initValues()
    {
        sp_role = findViewById(R.id.spRoleRegister);
        sp_gender = findViewById(R.id.spGenderRegister);

        lay_fName = findViewById(R.id.layFNameRegister);
        lay_mName = findViewById(R.id.layMNameRegister);
        lay_lName = findViewById(R.id.layLNameRegister);
        lay_uName = findViewById(R.id.layUNameRegister);
        lay_pass = findViewById(R.id.layPassRegister);

        et_fName = findViewById(R.id.etFNameRegister);
        et_mName = findViewById(R.id.etMNameRegister);
        et_lName = findViewById(R.id.etLNameRegister);
        et_uName = findViewById(R.id.etUNameRegister);
        et_pass = findViewById(R.id.etPassRegister);

        btn_register = findViewById(R.id.btnRegister);

        tv_login_here = findViewById(R.id.tvLoginHere);

        dbHelper = new DBHelper(this);
    }

    private void setAllSpinner()
    {
        ArrayAdapter<CharSequence> rolesAdapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_dropdown_item);
        rolesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_role.setAdapter(rolesAdapter);

        ArrayAdapter<CharSequence> gendersAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        gendersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(gendersAdapter);
    }

    private void setListeners()
    {
        //used lambda here

        btn_register.setOnClickListener(register -> {
            registerProcess();
        });


        tv_login_here.setOnClickListener(loginHere -> {
            finish();
        });
    }


    private void registerProcess()
    {
        String role = sp_role.getSelectedItem().toString();
        String fname = et_fName.getText().toString().trim();
        String mname = et_mName.getText().toString().trim();
        String lname = et_lName.getText().toString().trim();
        String gender = sp_gender.getSelectedItem().toString();
        String uname = et_uName.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();

        CustomToast toast = new CustomToast();

        if(dbHelper.addData(role, fname, mname, lname, gender, uname, pass))
        {
            toast.myToast(RegisterActivity.this, R.drawable.ic_success, "Registration", "Success");
            finish();
        }
        else
        {
            toast.myToast(RegisterActivity.this, R.drawable.ic_failed, "Registration", "Failed");
        }
    }

//    private void confirmAction()
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Register").setMessage("Confirm action?");
//
//        builder.setPositiveButton("yes", (dialog, which) -> {
//            CustomToast customToast = new CustomToast();
//            customToast.myToast(RegisterActivity.this, R.drawable.ic_success, "Registration", "Success");
//        });
//
//        builder.setNegativeButton("cancel", (dialog, which) -> {
//            CustomToast customToast = new CustomToast();
//            customToast.myToast(RegisterActivity.this, R.drawable.ic_failed, "Registration", "Failed");
//        });
//
//        builder.show();
//    }




}