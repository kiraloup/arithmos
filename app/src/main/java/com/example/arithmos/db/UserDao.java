package com.example.arithmos.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.arithmos.model.ExoStat;
import com.example.arithmos.model.User;
import com.example.arithmos.model.UserWithExoStat;

import java.util.List;
@Dao
public interface UserDao {
    @Insert void insertUser(User u);

    @Insert void inserAllExosStat(List<ExoStat> exoStatList);

    default void insertUserStat(User e, List<ExoStat> statList) {
        for (ExoStat exo: statList) {
            exo.setUserId(e.getId());
        }

        inserAllExosStat(statList);
    }

    @Query("UPDATE exo_stat set incorrect = incorrect + :nbResponse  where exo = :exoType and typeReponse = :typeReponse")
    void updateNbError(String exoType, int nbResponse,int typeReponse);

    @Query("UPDATE exo_stat set correct = correct + :nbResponse  where exo = :exoType and typeReponse = :typeReponse")
    void updateNbCorrect(String exoType, int nbResponse, int typeReponse);

    @Query("UPDATE exo_stat set pourcentage = ((correct*100)/((correct)+(incorrect)))   where exo = :exoType and typeReponse = :typeReponse")
    void updatePourcentage(String exoType, int typeReponse);

    @Query("SELECT exo, SUM(correct) as correct, SUM(incorrect) as incorrect FROM Exo_Stat where userId = 0 group by exo")
    List<allUserStat> getTotalReponse();

    @Query("SELECT * FROM Exo_Stat where userId = 0")
    List<ExoStat> getUserWithExoStat();

    @Query("Delete from user")
    void deleteAll();
}
