package com.example.arithmos.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arithmos.model.ExoStat;
import com.example.arithmos.model.Question;
import com.example.arithmos.model.User;
import com.example.arithmos.model.UserWithExoStat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Question.class, User.class, ExoStat.class}, version = 2, exportSchema = false)
public abstract class RoomDatabaseA extends RoomDatabase {


    public abstract QuestionDAO questionDAO();
    public abstract UserDao userDao();

    private static volatile RoomDatabaseA INSTANCE;
    private static final int NUMBER_THREADS = 4;

    static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_THREADS);

    static RoomDatabaseA getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabaseA.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabaseA.class,
                            "arithmos_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                QuestionDAO dao = INSTANCE.questionDAO();
                UserDao daoUser = INSTANCE.userDao();
                daoUser.deleteAll();
                dao.deleteAll();

                daoUser.insertUser(new User("nom", "prenom",18, "novice",  0));

                dao.insert(new Question("Le fermier a récolté #1 roses et #2 tulipes, combien de fleur possède-t-il ? ",
                        "add", 2));
                dao.insert(new Question("Le fermier a récolté #1 fleur mais à jeté #2 qui était fanées, combien de fleur possède-t-il ? ",
                        "sous", 2));
                dao.insert(new Question("Cet arbustre avait #1 feuilles en été, il ne lui en reste plus que #2 en automne, combien sont tombé ? ",
                        "sous", 2));
            });
        }
    };


}
