package com.example.myfirstapplication.utily;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CodeConverter {
    private String code;
    ImageView view;

    public CodeConverter(String s, ImageView v){
        this.view=v;
        this.code=s;
    }

    public void generateQR(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            view.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateBarcode(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.CODABAR,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            view.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
