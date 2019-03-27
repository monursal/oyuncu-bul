package com.example.onur.oyuncubul;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class change_password extends AppCompatActivity {

    private EditText mailpassword;
    private Button buttonpassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();
        mailpassword = findViewById(R.id.mailpassword);
        buttonpassword = findViewById(R.id.buttonpassword);

        buttonpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=mailpassword.getText().toString().trim();

                if(useremail.equals("")){
                    Toast.makeText(getApplicationContext(),"Lütfen e-posta giriniz",Toast.LENGTH_LONG).show();
                }else{
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Şifre e-postanıza gönderildi",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(change_password.this,MainActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(),"Hata Şifreniz gönderilemedi!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
