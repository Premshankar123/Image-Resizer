package com.example.imageresizer;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity {
    Button camera,select_img;
    static ImageView image;
    static Uri resultUri;
    EditImage editImage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera= (Button)findViewById(R.id.take_selfie);
        image = (ImageView)findViewById(R.id.imageView);
        select_img=(Button) findViewById(R.id.upload_img);
        image.setImageURI(resultUri);
        // and add the setOnClickListener in this button
        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                ImagePicker.Companion.with(MainActivity.this)
                        .cameraOnly()
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
        //code for select image from gallery
        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(MainActivity.this)
                        .galleryOnly()
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
    }

    // This method will help to retrieve the image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){
            resultUri=data.getData();
            //image.setImageURI(resultUri);
            Intent intent=new Intent(getApplicationContext(),EditImage.class);
            startActivity(intent);
        }

    }

}
