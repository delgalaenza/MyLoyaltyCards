package com.example.myfirstapplication.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myfirstapplication.DAO.LoyaltyCardDAO;
import com.example.myfirstapplication.entities.LoyaltyCard;

@Database(entities = {LoyaltyCard.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LoyaltyCardDAO loyaltyCardDAO();
}
