package com.example.bsachs_courseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static Bitmap image;
    public static int width;
    public static int height;
    public static int sized;
    public static colorPair[] catalog = new colorPair[10];

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public Button butt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.imageView = (ImageView) this.findViewById(R.id.imageView);
        Button photoButton = (Button) this.findViewById(R.id.button);
        butt = (Button) this.findViewById(R.id.button2);
        butt.setEnabled(false);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                butt.setEnabled(true);
            }
        });
    }

    public void onClick(View view) {
        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        image = drawable.getBitmap();
        int co = findMostUsed(image);
        MainActivity2.colorNumber = co;
        MainActivity2.red = Color.red(co);
        MainActivity2.green = Color.green(co);
        MainActivity2.blue = Color.blue(co);
        Intent n = new Intent(this,MainActivity2.class);
        startActivity(n);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
    public int findMostUsed(Bitmap image)
    {
        width = image.getWidth();
        height = image.getHeight();
        catalogColors(image);
        int biggest = -1;
        int biggestCount = -1;
        for(int x=0;x<sized;x++)
        {
            if(catalog[x].getCount() > biggestCount)
            {
                biggest = catalog[x].getID();
                biggestCount = catalog[x].getCount();
            }
        }
        return biggest;

    }
    public static void catalogColors(Bitmap image)
    {

        for(int x=0;x<height-1;x++)
        {
            for(int y=0;y<width;y++)
            {
                sized=size();
                if(hasCode(image.getPixel(y,x)))
                {
                    catalog[getLocation(image.getPixel(y,x))].setCount(catalog[getLocation(image.getPixel(y,x))].getCount()+1);
                }
                else
                {
                    addColor(image.getPixel(y,x));
                }
            }
        }
    }
    public static boolean hasCode(int id)
    {
        for(int x=0;x<sized;x++)
        {
            if(catalog[x].getID() == id)
                return true;
        }
        return false;
    }
    public static int size()
    {
        int size=0;
        for(int x=0;x<catalog.length;x++)
        {
            if(catalog[x] != null)
            {
                size++;
            }
        }
        return size;
    }
    public static void addColor(int id)
    {
        if(catalog.length == sized)
        {
            colorPair[] newCatalog = new colorPair[sized+10];
            for(int x=0;x<sized;x++)
            {
                newCatalog[x]=catalog[x];
            }
            catalog=newCatalog;
        }
        catalog[sized] = new colorPair(id,1);
    }
    public static int getLocation(int id)
    {
        if(hasCode(id))
        {
            for(int x=0;x<sized;x++)
            {
                if(catalog[x].getID() == id)
                {
                    return x;
                }
            }
        }
        return -1;
    }
    public static class colorPair
    {
        private int ID; //first member of pair
        private int Count; //second member of pair

        public colorPair(int ID, int Count) {
            this.ID = ID;
            this.Count = Count;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public int getID() {
            return ID;
        }

        public int getCount() {
            return Count;
        }
    }

}


