package com.example.bsachs_courseproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    public static int red = 0;
    public static int green = 0;
    public static int blue = 0;
    public static int colorNumber= 0;
    private SQLiteDatabase db = null;

    public ListView list;
    public SimpleCursorAdapter names;
    Cursor c;

    colorSet colors[] = new colorSet[45];
    int colorCount = 0;
    ImageView imagec;
    TextView tv2, tv3,tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        deleteDatabase("colors");
        ImageView image = (ImageView) findViewById((R.id.imageView3));
        imagec = (ImageView) findViewById((R.id.imageView4));
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap colo = Bitmap.createBitmap(200,200,conf);
        setColor(colo);
        image.setImageBitmap(colo);
        TextView tp = (TextView) findViewById(R.id.textView);
        tp.setText(red + " " + green + " " + blue);
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        //Sample yellows
        createColor("FFF200",254,242,0,"Yellow");
        createColor("F8DE7E",247,223,125,"Mellow");
        createColor("FFD300",255,211,2,"Cyber");
        createColor("FADA5E",249,217,96,"Royal");
        createColor("D2B55B",208,181,92,"Trombone");

        //Sample oranges
        createColor("FC6600",251,102,0,"Orange");
        createColor("F9A602",248,166,4,"Gold");
        createColor("DBA520",221,165,26,"Goldenrod");
        createColor("FF7417",255,115,23,"Pumpkin");
        createColor("GDA50F",253,165,15,"Fire");

        //Sample reds
        createColor("D30000",212,0,0,"Red");
        createColor("FA8072",250,128,113,"Salmon");
        createColor("FF2400",254,36,0,"Scarlet");
        createColor("7C0A02",123,11,0,"Barn Red");
        createColor("ED2939",237,40,57,"Imperial");

        //sample pinks
        createColor("FC0FC0",253,15,191,"Pink");
        createColor("E0115F",224,17,97,"Ruby");
        createColor("FF6FFF",255,110,253,"Ultra");
        createColor("FDE6FA1",223,110,162,"Thulian");
        createColor("FF0090",255,0,144,"Magenta");

        //sample violet
        createColor("B200ED",178,0,238,"Violet");
        createColor("B43757",180,55,85,"Hibiscus");
        createColor("784B84",120,75,132,"Mauve");
        createColor("C64B8C",198,75,138,"Mulberry");
        createColor("E4A0F7",228,160,247,"Lavender");

        //sample blue
        createColor("0018F9",0,23,249,"Blue");
        createColor("131E3A",19,30,58,"Denim");
        createColor("7285A5",114,133,165,"Pigeon");
        createColor("95C8D8",149,200,217,"Sky");
        createColor("4D516D",77,81,110,"Independence");

        //sample green
        createColor("3BB143",60,176,67,"Green");
        createColor("0B6623",10,102,35,"Forest");
        createColor("9DC183",157,193,131, "Sage");
        createColor("708238",112,130,56,"Olive");
        createColor("C7EA46",199,234,70,"Lime");

        //sample brown
        createColor("7C4700",125,71,0,"Brown");
        createColor("4B3A26",75,58,40,"Cedar");
        createColor("622A0F",98,42,17,"Cinnamon");
        createColor("3A1F04",58,31,4,"Brunette");
        createColor("7E481C",125,72,28,"Tawny");

        //sample grey
        createColor("828282",130,130,130,"Grey");
        createColor("787276",119,114,118,"Fossil");
        createColor("88807B",137,127,125,"Mink");
        createColor("D9DDDC",217,221,220,"Pearl River");
        createColor("D6CFC7",214,207,197,"Abalone");


        closestSet();
    }
    public void setColor(Bitmap iv)
    {
        for(int x = 0; x < iv.getWidth();x++)
        {
            for(int y = 0; y< iv.getHeight();y++)
            {
                iv.setPixel(x,y,colorNumber);
            }
        }
    }
    public void closestSet()
    {
        //c = db.query("colors",null, null,null, null, null, null);


        int cR = 0;
        int cG = 0;
        int cB = 0;
        int cN = 765;
        String cCode = "";
        String cName = "";
        /*if(c.moveToFirst())
        {
            do {
                int rd = c.getInt(c.getColumnIndex("Red"));
                int gr = c.getInt(c.getColumnIndex("Green"));
                int blu = c.getInt(c.getColumnIndex("Blue"));

               int total =  Math.abs(red - rd) + Math.abs(green - gr) + Math.abs(blue - blu);

               if(total < cN)
               {
                   cR = rd;
                   cG = gr;
                   cB = blu;
                   cN = total;

               }




            } while(c.moveToNext());
        }*/

        for(int x = 0;x<45;x++)
        {
            int rd = colors[x].getRed();
            int gr = colors[x].getGreen();
            int bl = colors[x].getBlue();

            int total =  Math.abs(red - rd) + Math.abs(green - gr) + Math.abs(blue - bl);

            if(total < cN)
            {
                cR = rd;
                cG = gr;
                cB = bl;
                cN = total;
                cCode = colors[x].getID();
                cName = colors[x].getName();

            }
        }


        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap colo2 = Bitmap.createBitmap(200,200,conf);
        for(int x = 0; x < colo2.getWidth();x++)
        {
            for(int y = 0; y< colo2.getHeight();y++)
            {
                colo2.setPixel(x,y,Color.rgb(cR,cG,cB));
            }
        }
        imagec.setImageBitmap(colo2);
        tv2 = (TextView) findViewById(R.id.textView3);
        tv3 = (TextView) findViewById(R.id.textView4);
        tv4 = (TextView) findViewById(R.id.textView5);
        tv2.setText(cName);
        tv3.setText(cCode);
        tv4.setText(cR + " " + cG + " " +cB);
    }
    public void createColor(String _id, int red, int green, int blue,String name)
    {
        /*ContentValues vs = new ContentValues();
        vs.put("_ID",_id);
        vs.put("Red",red);
        vs.put("Green",green);
        vs.put("Blue",blue);
        vs.put("Name",name);
        db.insert("colors",null,vs);*/

        /*colors[colorCount].ID = _id;
        colors[colorCount].Red = red;
        colors[colorCount].Green = green;
        colors[colorCount].Blue = blue;
        colors[colorCount].Name = name;*/

        colors[colorCount] = new colorSet(_id,red,green,blue,name);
        colorCount++;
    }
    public void onClick(View view)
    {
        double latitude = 0;
        double longitude = 0;
        List<Address> geocodeMatches = null;

        try {
            geocodeMatches = new Geocoder(this).getFromLocationName("Home Depot", 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!geocodeMatches.isEmpty()) {
            latitude = geocodeMatches.get(0).getLatitude();
            longitude = geocodeMatches.get(0).getLongitude();
        }
        MapsActivity.lat = latitude;
        MapsActivity.lon = longitude;
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public static class colorSet
    {
        private String ID; //first member of pair
        private int Red;//second member of pair
        private int Green;
        private int Blue;
        private String Name;

        public colorSet(String ID, int red, int green, int blue, String name) {
            this.ID = ID;
            this.Red = red;
            this.Green = green;
            this.Blue = blue;
            this.Name = name;
        }

        public void setID(String ID) {
            this.ID = ID;
        }
        public void setRed(int red) {
            this.Red = red;
        }
        public void setGreen(int green) {
            this.Green = green;
        }
        public void setBlue(int blue) {
            this.Blue = blue;
        }
        public void setName(String name) {
            this.Name = name;
        }

        public String getID() {
            return ID;
        }
        public int getRed() { return Red;  }
        public int getGreen() { return Green;  }
        public int getBlue() { return Blue;  }
        public String getName() { return Name;  }
    }
}