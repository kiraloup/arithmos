package com.example.arithmos.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arithmos.model.Question;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Question.class}, version = 1, exportSchema = false)
public abstract class RoomDatabaseA extends RoomDatabase {

    public abstract QuestionDAO questionDAO();

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
                dao.deleteAll();

                Question word = new Question("combien font #1 roses + #2 tulipes ? ",
                        "additions", 2);
                dao.insert(word);
            });
        }
    };


}
