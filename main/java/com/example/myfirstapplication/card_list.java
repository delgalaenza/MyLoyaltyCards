package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapplication.DAO.LoyaltyCardDAO;
import com.example.myfirstapplication.DB.AppDatabase;
import com.example.myfirstapplication.entities.LoyaltyCard;
import com.example.myfirstapplication.utily.CodeConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class card_list extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Uri imageUri;


    ListView lv;
    FloatingActionButton buttonNewCard;

    // DB
    AppDatabase db;
    LoyaltyCardDAO cardDao;

    private ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        //creo db
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        cardDao = db.loyaltyCardDAO();

        //cardDao.deleteAllCards();


        lv = (ListView) findViewById(R.id.lv);

        ArrayList<LoyaltyCard> cards = (ArrayList<LoyaltyCard>) cardDao.getAllCards();

        CardListAdapter adapter = new CardListAdapter(this, cards);


        lv.setAdapter(adapter);

        buttonNewCard = (FloatingActionButton) findViewById(R.id.buttonNewCard);
        buttonNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i = new Intent(getApplicationContext(),MainActivity.class);
              startActivity(i);
            }
        });

    }


    private class CardListAdapter extends ArrayAdapter<LoyaltyCard> {
        public CardListAdapter(@NonNull Context context,  @NonNull ArrayList<LoyaltyCard> cards) {
            super(context, 0, cards);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LoyaltyCard card = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_items, parent, false);
            }

            //BOTTONE CARTA
            Button button = (Button)  convertView.findViewById(R.id.itemButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", card);

                    Intent i = new Intent(getContext(), CardVisualizationActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            });


            //NOME CARTA
            TextView cardName = (TextView) convertView.findViewById(R.id.itemCardName);
            cardName.setText(card.getName());


            //LOGO CARTA
            ImageView logo = (ImageView)  convertView.findViewById(R.id.itemLogo);
            if(card.getLogoUri()!=null) {
                System.out.println("list view image uri: " + card.getLogoUri());
                Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                imageUri =  Uri.parse(card.getLogoUri());
                logo.setImageURI(imageUri);
            }







            return convertView;
        }


    }



}