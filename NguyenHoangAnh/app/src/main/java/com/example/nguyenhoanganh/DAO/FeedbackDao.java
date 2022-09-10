package com.example.nguyenhoanganh.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nguyenhoanganh.FeedBack;

import java.util.List;

@Dao
public interface FeedbackDao {
    @Insert
    void insertFeedback(FeedBack feedbackEntity);

    @Query("SELECT * FROM feedback")
    List<FeedBack> getAllFeedback();
}
