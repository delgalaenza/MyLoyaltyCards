package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.myfirstapplication.DAO.LoyaltyCardDAO;
import com.example.myfirstapplication.DB.AppDatabase;
import com.example.myfirstapplication.entities.LoyaltyCard;
import com.example.myfirstapplication.utily.CodeConverter;
import com.example.myfirstapplication.utily.GetPath;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; //costante usata per i log
    private static int RESULT_LOAD_IMAGE = 1;

    private Context mContext = null;

    private CardView card;

    //stored image from gallery
    private ImageView imageView;
    private Button loadLogoButton;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    //barcode
    private EditText editTextBarcode;
    private ImageView imageViewBarcode;

    //QRcode
    private EditText editTextQR;
    private Button buttonQR;
    private  Button buttonBarcode;
    private  ImageView imageViewQR;

    //background color
    private AutoCompleteTextView autoCompleteTextView;
    private Button buttonApplyColor;
    private  String color_selector = "Green";

    //creazione carta
    LoyaltyCard newCard = new LoyaltyCard();
    private FloatingActionButton buttonNewCard;
    private  boolean codeFormat;

    // DB
    AppDatabase db;
    LoyaltyCardDAO cardDao;

    //metodo invocata ogni volta che l'activity viene creata
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "chiamato onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creo db
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        cardDao = db.loyaltyCardDAO();



        //recupero il contesto x passarlo a un altra classe
        this.mContext = this.getApplicationContext();

        //codice per gestione del logo
        imageView = (ImageView)findViewById(R.id.imageView);
        loadLogoButton = (Button)findViewById(R.id.buttonLoadPicture);
        loadLogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        //gestione QRcode
        editTextQR = (EditText)findViewById(R.id.editTextQR);
        buttonQR = (Button)findViewById(R.id.buttonQR);
        imageViewQR = (ImageView)findViewById(R.id.imageViewQR);

        buttonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeFormat = true;
                CodeConverter c = new CodeConverter(editTextQR.getText().toString(), imageViewQR);
                c.generateQR();
            }
        });

        //gestione Barcode
        buttonBarcode = (Button)findViewById(R.id.buttonBarcode);

        buttonBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeFormat = false;
                codeFormat = true;
                CodeConverter c = new CodeConverter(editTextQR.getText().toString(), imageViewQR);
                c.generateBarcode();
            }
        });

        //gestione colore di sfondo
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.backgroundColorSelector);
        buttonApplyColor = (Button) findViewById(R.id.buttonApplyColor);
        card = (CardView) findViewById(R.id.card);

        String[]  colors = getResources().getStringArray(R.array.colori);
        ArrayAdapter a  = new ArrayAdapter(this.getApplicationContext(),R.layout.dropdown_item,colors);
        autoCompleteTextView.setAdapter(a);

        buttonApplyColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_selector = autoCompleteTextView.getText().toString();
                switch(color_selector){
                    case "Green" :
                        card.setCardBackgroundColor(getResources().getColor(R.color.green));
                        break;

                    case "Red" :
                        card.setCardBackgroundColor(getResources().getColor(R.color.red));
                        break;

                    case "Blue" :
                        card.setCardBackgroundColor(getResources().getColor(R.color.blue));
                        break;

                    case "Yellow" :
                        card.setCardBackgroundColor(getResources().getColor(R.color.yellow));
                        break;

                    case "Orange" :
                        card.setCardBackgroundColor(getResources().getColor(R.color.orange));
                        break;

                    case "Pink" :
                        card.setCardBackgroundColor(getResources().getColor(R.color.pink));
                        break;
                }
        }});

        //salvataggio carta
        buttonNewCard= (FloatingActionButton)findViewById(R.id.buttonAddCard);
        EditText editTextFactoryName = (EditText)findViewById(R.id.editTextFactoryName);
        buttonNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    boolean controllo = true;

                    //nome azienda
                    List<String> nomi = cardDao.getAllName();

                    for(String s : nomi){
                        if(editTextFactoryName.getText().toString().equals(s)) {
                            editTextFactoryName.setError("change name");
                            System.out.println("TROVATO NOME UGUALE");
                            controllo = false;
                        }
                    }
                     if(controllo) {
                         newCard.setName(editTextFactoryName.getText().toString());
                         //logo
                         if (imageUri != null) {
                             newCard.setLogoUri(imageUri.toString());
                         }
                         //codice cliente
                         newCard.setClientCode(editTextQR.getText().toString());
                         //formato codice
                         newCard.setClientCodeFormat(codeFormat);
                         //colore sfondo
                         newCard.setBackgroundColor(color_selector);

                         System.out.println("carta pronta: ");
                         System.out.println(newCard);

                         salvaCarta(newCard);
                     }

                Bundle bundle = new Bundle();
                bundle.putSerializable("product", newCard);

                Intent i = new Intent(getApplicationContext(), CardVisualizationActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                }
            });

    }

    public void salvaCarta(LoyaltyCard c){
        cardDao.insertAll(c);
    }


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            getContentResolver().takePersistableUriPermission( imageUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageView.setImageURI(imageUri);
            System.out.println("L'uri dell immagine è" + imageUri);

        }
    }


}

