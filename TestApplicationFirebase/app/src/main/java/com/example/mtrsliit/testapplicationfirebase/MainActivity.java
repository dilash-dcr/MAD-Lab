package com.example.mtrsliit.testapplicationfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtID,txtName,txtAdd,txtConNo;
    Button btnSave,btnShow;
    DatabaseReference dbRef;
    Student std;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID=findViewById(R.id.txtId);
        txtName=findViewById(R.id.txtName);
        txtAdd=findViewById(R.id.txtAddress);
        txtConNo=findViewById(R.id.txtNumber);

        btnSave=findViewById(R.id.btnSave);
        btnShow=findViewById(R.id.btnShow);

        std=new Student();
        btnSave.setOnClickListener(this);


    }
    private void clearControls(){
        txtID.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtConNo.setText("");
    }
    public void onClick(View view){
//        Save();
        /*Show();*/
        switch (view.getId()){
            case R.id.btnSave:Save();
            break;

            case R.id.btnShow:Show();
            break;


        }

    }
    private void Save(){
        dbRef= FirebaseDatabase.getInstance().getReference().child("Student");
        try {
            if (TextUtils.isEmpty(txtID.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please enter Id", Toast.LENGTH_LONG).show();

            }
            else if (TextUtils.isEmpty(txtID.getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter Name", Toast.LENGTH_LONG).show();

            }
            else if (TextUtils.isEmpty(txtID.getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter Address", Toast.LENGTH_LONG).show();

            }
            else{
                std.setID(txtID.getText().toString());
                std.setName(txtName.getText().toString());
                std.setAddress(txtAdd.getText().toString());
                std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

               // dbRef.push().setValue(std);
                dbRef.child("Std1").setValue(std);
                Toast.makeText(getApplicationContext(), "Successfully Inserted!!", Toast.LENGTH_LONG).show();
                clearControls();



            }
        }
        catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_LONG).show();
        }
    }
    private void Show(){
        DatabaseReference readRef=FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    txtID.setText(dataSnapshot.child("id").getValue().toString());
                    txtName.setText(dataSnapshot.child("name").getValue().toString());
                    txtAdd.setText(dataSnapshot.child("address").getValue().toString());
                    txtConNo.setText(dataSnapshot.child("conNo").getValue().toString());

                }
                else {
                    Toast.makeText(getApplicationContext(),"No Source to Display",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
