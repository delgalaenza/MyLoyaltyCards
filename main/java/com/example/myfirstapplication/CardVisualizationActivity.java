package com.example.myfirstapplication;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.myfirstapplication.entities.LoyaltyCard;
import com.example.myfirstapplication.utily.CodeConverter;


public class CardVisualizationActivity extends AppCompatActivity {

    private CardView cardView;
    private ImageView imageView;
    private Uri imageUri;
    private TextView codice;
    private TextView nome;
    private ImageView imageViewQR;
    private TextView labelCodice;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_visualization_acrivity);

        Bundle bundle = this.getIntent().getExtras();
        LoyaltyCard card = null;

        if (bundle != null) {
            card = (LoyaltyCard) bundle.getSerializable("product");
        }

        //visualizzazione logo
        imageView = (ImageView)findViewById(R.id.imageView);
        if(card.getLogoUri()!=null) {
            System.out.println("list view image uri: " + card.getLogoUri());
            Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            imageUri =  Uri.parse(card.getLogoUri());
            imageView.setImageURI(imageUri);
        }

        //nome azienda e codice cliente
        codice = (TextView) findViewById(R.id.codice);
        nome = (TextView) findViewById(R.id.nome);
        labelCodice = (TextView) findViewById(R.id.labelCodice);

        codice.setText(card.getClientCode());
        nome.setText(card.getName());

        codice.setTextColor(getResources().getColor(R.color.black));
        nome.setTextColor(getResources().getColor(R.color.black));
        labelCodice.setTextColor(getResources().getColor(R.color.black));

        nome.setShadowLayer(5,5,5,R.color.pink);



        //colore di sfondo
        cardView = (CardView) findViewById(R.id.card);

        String color_selector = card.getBackgroundColor();
        switch(color_selector) {
            case "Green":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.green));
                break;

            case "Red":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                break;

            case "Blue":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.blue));
                codice.setTextColor(getResources().getColor(R.color.white));
                nome.setTextColor(getResources().getColor(R.color.white));
                labelCodice.setTextColor(getResources().getColor(R.color.white));
                nome.setShadowLayer(5,5,5,R.color.black);
                labelCodice.setShadowLayer(5,5,5,R.color.black);
                break;

            case "Yellow":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.yellow));
                break;

            case "Orange":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.orange));
                break;

            case "Pink":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.pink));
                break;
        }

        //generazione QR/barcode
        imageViewQR = (ImageView) findViewById(R.id.imageViewQR);
        CodeConverter c = new CodeConverter(card.getClientCode(),imageViewQR );
        if(card.getClientCodeFormat())
            c.generateQR();
        else
            c.generateBarcode();

        imageViewQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreen();
            }
        });


        //bottone di ritorno aalla lista delle carte
        Button button = (Button)  findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), card_list.class);
                startActivity(i);
            }
        });

    }

    public void fullScreen() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
        } else {

        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }


}