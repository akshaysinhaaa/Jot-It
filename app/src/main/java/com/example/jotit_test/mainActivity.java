package com.example.jotit_test;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.jotit_test.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Continuation;
import android.provider.MediaStore;
import android.database.Cursor;
import android.provider.OpenableColumns;
import com.google.android.gms.tasks.OnCompleteListener;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_CODE_PICK_FILE = 1;
    private Uri selectedFileUri;
    ActivityMainBinding binding;
    FirebaseAuth auth;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    Button Maths, Physics, Coding, Online, DLD;
    Button logout_button;
    Button upload_button;

    private void launchFileChooser() {
        Log.d(TAG,"running log filechooser");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("/");
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE);

    }
//    private void uploadFile(Uri fileUri) {
//        // Get the file name from the file URI
//        String fileName = getFileName(fileUri);
//
//        // Create a storage reference to the file location
//        StorageReference fileRef = storageRef.child("uploads/" + fileName);
//
//        // Upload the file to Firebase Storage
//        UploadTask uploadTask = fileRef.putFile(fileUri);
//
//        // Get the download URL of the uploaded file and handle the upload result
//        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw Objects.requireNonNull(task.getException());
//                }
//
//                return fileRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    // Get the download URL of the uploaded file
//                    Uri downloadUri = task.getResult();
//
//                    // Handle successful upload
//                    Toast.makeText(MainActivity.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle unsuccessful upload
//                    Toast.makeText(MainActivity.this, "Error uploading file: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
private void uploadFile(Uri fileUri) {
    String fileName = getFileName(fileUri);

    StorageReference fileRef = storageRef.child("uploads/" + fileName);
    UploadTask uploadTask = fileRef.putFile(fileUri);

    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
        @Override
        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }

            return fileRef.getDownloadUrl();
        }
    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                // Handle successful upload
                Toast.makeText(MainActivity.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Handle unsuccessful upload
                Toast.makeText(MainActivity.this, "Error uploading file: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        // To hide the App Bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hides Action Bar


        setContentView(R.layout.activity_main);


        //Logout button

        auth = FirebaseAuth.getInstance();
        logout_button = findViewById(R.id.logout_button);
        upload_button = findViewById(R.id.upload_button);

        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermission();
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//                    Log.d(TAG,"vrnonclickif");
//                }
//                else {
//                    launchFileChooser();
//                    Log.d(TAG,"vronfileuser");
//                }
            }


        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        Maths = findViewById(R.id.maths_button);
        Physics = findViewById(R.id.phy_button);
        Coding = findViewById(R.id.coding_button);
        Online = findViewById(R.id.online_button);
        DLD = findViewById(R.id.digital_button);

        Maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1u5_C8Uu6mMoL9UNb4aFV13r8jPY4-D23?usp=sharing");
            }
        });
        Physics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1Y3UXRk95MPXXsdD0XDZv38dyUcmXjOLo?usp=sharing");
            }
        });

        Coding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1O1PAVe0bI75GIN0sq_gF6lp_8Vy2Vghp?usp=sharing");
            }
        });

        Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1hY2np-8SP_ImwACnQ2gyHNbde3ocFW6l?usp=sharing");
            }
        });

        DLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1JPfxvLMTBfnGfQj6f770CVrXuAXijzAU?usp=sharing");
            }
        });
        
    }
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1;
    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.d(TAG,"line 218");
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user
                Log.d(TAG,"line 221");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("This app needs to access your external storage to upload files.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Request the permission again
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            } else {
                Log.d(TAG,"line 234");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {
            // Permission is already granted, launch file chooser
            Log.d(TAG,"infilechoser");
            launchFileChooser();
        }
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK) {
            selectedFileUri = data.getData();
            uploadFile(selectedFileUri);
        }
    }
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            launchFileChooser();
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu , menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.settings:
//                Toast.makeText(this, "Setting Clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.logout:
//                auth.signOut();
//                break;
//        }
//        return true;
//    }
}