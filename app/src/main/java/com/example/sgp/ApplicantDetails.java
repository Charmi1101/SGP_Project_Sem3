package com.example.sgp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ApplicantDetails extends AppCompatActivity {
    boolean ret=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicantdetails);

        final EditText name_e=findViewById(R.id.name_create);

        final EditText dob_e=findViewById(R.id.date_create);
        final EditText res_area_e=findViewById(R.id.res_area_create);
        final EditText pref_area_e=findViewById(R.id.pref_area_create);



        Button button=(Button)findViewById(R.id.button_create);


        String TAG ="TAG";


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name=name_e.getText().toString();
                String dob=dob_e.getText().toString();
                String resArea=res_area_e.getText().toString();
                String prefArea=pref_area_e.getText().toString();
                String mobileNumber = getIntent().getStringExtra("PhoneNumber");

                Map<String,Object> data=new HashMap<>();
                data.put("name",Name);
                data.put("dob",dob);
                data.put("Residing Area",resArea);
                data.put("Preferred Area",prefArea);

                boolean C1 = SaveToFirebase(data, "DATA", mobileNumber);
                boolean C2 = SaveToFirebase(data, "buyer", mobileNumber);
                boolean C3 = SaveToFirebase(data, "seller", mobileNumber);
                if(C1 && C2 && C3){
                    finish();
                    startActivity(new Intent(ApplicantDetails.this, Dashboard.class));
                }
                /*if(passwordCreate.compareTo(confirm_password)==0) {
                    boolean C1 = SaveToFirebase(data, "DATA", mobileCreate);
                    boolean C2 = SaveToFirebase(data, "buyer", mobileCreate);
                    boolean C3 = SaveToFirebase(data, "seller", mobileCreate);
                    if(C1 && C2 && C3){
                        startActivity(new Intent(ApplicantDetails.this, Dashboard.class));
                    }
                    else{
                        Toast.makeText(ApplicantDetails.this, "Confirm password doesn't match", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ApplicantDetails.this, ApplicantDetails.class));
                    }
                }*/


            }
        });

    }

    //Method to save data to firebase
    protected boolean SaveToFirebase(final Map<String,Object> data, String destination, String mobileCreate){
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        DocumentReference dref=fb.collection(destination).document(mobileCreate);
        dref.set(data)
                .addOnSuccessListener(ApplicantDetails.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ApplicantDetails.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                        ret =false;

                    }
                })
                .addOnFailureListener(ApplicantDetails.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ApplicantDetails.this,"Account Creation Fail:"+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
        return ret;
    }
}