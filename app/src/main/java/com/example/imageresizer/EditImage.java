package com.example.imageresizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EditImage extends AppCompatActivity {
    MainActivity activity;
    ImageView image;
    int i = 0;
    BitmapDrawable drawable;
    Bitmap bitmap;

    Button undo_btn, rotate_btn, save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        image = (ImageView) findViewById(R.id.edit_img);
        rotate_btn = (Button) findViewById(R.id.rotate);
        undo_btn = (Button) findViewById(R.id.undo);
        save_btn = (Button) findViewById(R.id.save);
        image.setImageURI(activity.resultUri);
        rotate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i % 4 == 0) {
                    image.setRotation(90);
                    i = i + 1;
                } else if (i % 4 == 1) {
                    image.setRotation(180);
                    i = i + 1;
                } else if (i % 4 == 2) {
                    image.setRotation(270);
                    i = i + 1;
                } else if (i % 4 == 3) {
                    image.setRotation(360);
                    i = i + 1;
                }
            }
        });
        undo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save image to gallery
                drawable=(BitmapDrawable) image.getDrawable();
                bitmap=drawable.getBitmap();
                FileOutputStream outputStream=null;
                File sdCard= Environment.getExternalStorageDirectory();
                File directry=new File(sdCard.getAbsolutePath() +"/ImageResizer");
                directry.mkdir();
                String fileName=String.format("%d.jpg",System.currentTimeMillis());
                File outputFile=new File(directry,fileName);
                Toast.makeText(getApplicationContext(),"Image Saved successfully",Toast.LENGTH_SHORT).show();
                try {
                    outputStream=new FileOutputStream(outputFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outputFile));
                    sendBroadcast(intent);
                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });


    }
}

