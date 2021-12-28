package com.example.myfirstapplication.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfirstapplication.entities.LoyaltyCard;

import java.util.List;

@Dao
public interface LoyaltyCardDAO {

    @Query("SELECT * FROM loyaltyCard WHERE name = :cardName")
    LoyaltyCard getcardByName(String cardName);

    @Query("SELECT * FROM loyaltyCard ")
    List<LoyaltyCard> getAllCards();

    @Query("SELECT name FROM loyaltycard")
    List<String> getAllName();

    @Insert
    void insertAll(LoyaltyCard... cards);

    @Query("DELETE FROM loyaltyCard ")
    void deleteAllCards();


}
