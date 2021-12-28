package com.example.myfirstapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@SuppressWarnings("serial")
@Entity
public class LoyaltyCard implements Serializable {
    @PrimaryKey
    @NonNull
    public String name;

    @ColumnInfo(name = "logo_uri")
    public String logoUri;

    @ColumnInfo(name = "client_code")
    public String clientCode;

    @ColumnInfo(name = "client_code_format")
    public Boolean clientCodeFormat;               //true=QR, false=Barcode

    @ColumnInfo(name = "background_color")
    public String backgroundColor;

    @Override
    public String toString() {
        return "LoyaltyCard{" +
                "name='" + name + '\'' +
                ", logoUri='" + logoUri + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", clientCodeFormat=" + clientCodeFormat +
                ", backgroundColor='" + backgroundColor + '\'' +
                '}';
    }
//getters and setters

    public Boolean getClientCodeFormat() {
        return clientCodeFormat;
    }

    public void setClientCodeFormat(Boolean clientCodeFormat) {
        this.clientCodeFormat = clientCodeFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor( String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
}
