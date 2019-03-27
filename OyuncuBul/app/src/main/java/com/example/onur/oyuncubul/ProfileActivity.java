package com.example.onur.oyuncubul;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {



    private static final int CHOOSE_IMAGE = 101;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    ImageView imageView;
    Button buttonSave, geri;
    Uri uriProfilImage;
    ProgressBar progressBar;
    String profileImageUrl;
    FirebaseAuth mAuth;
    TextView Dogrulama,profileTel, profileYas,kullanici;
    DatabaseReference databaseReference;
    private String userID;
    private FirebaseDatabase mFirebasedatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private StorageReference firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Typeface font=Typeface.createFromAsset(getAssets(),"fonts/Pompadour.ttf");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        mFirebasedatabase=FirebaseDatabase.getInstance();
        databaseReference=mFirebasedatabase.getReference();
        FirebaseUser user=mAuth.getCurrentUser();
        userID=user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);





        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               /* showData(dataSnapshot);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        dl = findViewById(R.id.dl);
        kullanici =  findViewById(R.id.kullanici);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        imageView = (ImageView) findViewById(R.id.imageView);
        Dogrulama = findViewById(R.id.Dogrulama);
        profileYas = findViewById(R.id.profileYas);
        profileTel = findViewById(R.id.profileTel);


        abdt = new ActionBarDrawerToggle(this,dl,R.string.Ac,R.string.Kapat);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

               switch (id){
                   case R.id.cikis:
                       FirebaseAuth.getInstance().signOut();
                       finish();
                       startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                       break;
                   case R.id.teklif:
                       startActivity(new Intent(ProfileActivity.this, teklif.class));
                       gonder();
                       break;
                   case R.id.haberler:
                       startActivity(new Intent(ProfileActivity.this,haberler.class));
                       break;
                   case R.id.sahalar:
                       startActivity(new Intent(ProfileActivity.this,saha.class));
                       break;
                   case R.id.change:
                       startActivity(new Intent(ProfileActivity.this, Change.class));
                       break;
                   case R.id.ProfileActivity:
                       startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
               }


                return true;
            }
        });


        kullanici.setTypeface(font);
        profileYas.setTypeface(font);
        profileTel.setTypeface(font);
        Dogrulama.setTypeface(font);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String childValueY=String.valueOf(dataSnapshot.child("userYas").getValue());
                String childValueT=String.valueOf(dataSnapshot.child("userTel").getValue());
                profileYas.setText(childValueY);
                profileTel.setText(childValueT);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        loadUserInformation();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUserInformation();
            }
        });



    }





    public void gonder() {
        CharSequence charSequence = kullanici.getText();

        Intent intent = new Intent(getApplicationContext(), teklif.class);

        intent.putExtra("anahtar", charSequence);

        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            if (user.getPhotoUrl() != null) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String image = String.valueOf(dataSnapshot.child("downloadUrl").getValue());


                        Glide.with(ProfileActivity.this)
                                .load(image)
                                .into(imageView);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            if (user.getDisplayName() != null) {
                kullanici.setText(user.getDisplayName());
            }
            if (user.isEmailVerified()) {
                Dogrulama.setText("E-Posta doğrulanmış.");
            } else {
                Dogrulama.setText("E-Posta doğrulanmamış.(Doğrulamak için tıklayın)");
                Dogrulama.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ProfileActivity.this, "Doğrulama e-postası gönderildi.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }


    }

    private void saveUserInformation() {


        FirebaseUser user = mAuth.getCurrentUser();
        if (profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()

                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "Profil Güncellendi", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfilImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfilImage);
                imageView.setImageBitmap(bitmap);


                upladeImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }



    private void upladeImageToFirebaseStorage() {
        String imagename = "Users/"+userID+".jpg";

        StorageReference profileImageRef = firebaseStorage.child(imagename);
        if (uriProfilImage != null) {
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfilImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    profileImageUrl = taskSnapshot.getDownloadUrl().toString();

                    FirebaseUser user=mAuth.getCurrentUser();
                    databaseReference.child("downloadUrl").setValue(profileImageUrl);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
        }
    }

    private void showImageChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Profil Resmi Seçiniz"), CHOOSE_IMAGE);

    }





}
