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

import java.util.ArrayList;
import java.util.List;
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

                User u = new User("nom", "prenom",18, "novice",
                        0);

                daoUser.insertUser(u);

                List<ExoStat> list = new ArrayList<>();
                // type reponse num pour exo simple
                list.add(new ExoStat(0, "add", 0, 0, 0,0));
                // type reponse txt pour exo simple
                list.add(new ExoStat(0, "add", 0, 0, 0,1));
                // type drag and drop
                list.add(new ExoStat(0, "add", 0, 0, 0,2));

                list.add(new ExoStat(0, "sous", 0, 0, 0,0));
                list.add(new ExoStat(0, "sous", 0, 0, 0,1));
                list.add(new ExoStat(0, "sous", 0, 0, 0,2));

                list.add(new ExoStat(0, "mult", 0, 0, 0,0));
                list.add(new ExoStat(0, "mult", 0, 0, 0,1));
                list.add(new ExoStat(0, "mult", 0, 0, 0,2));

                list.add(new ExoStat(0, "div", 0, 0, 0,0));
                list.add(new ExoStat(0, "div", 0, 0, 0,1));
                list.add(new ExoStat(0, "div", 0, 0, 0,2));

                daoUser.insertUserStat(u, list);


                dao.insert(new Question("Le fermier a r??colt?? #1 roses et #2 tulipes, combien de fleur poss??de-t-il ? ",
                        "add", 2, "rose"));
                dao.insert(new Question("Le fermier a r??colt?? #1 pomme hier et #2 aujourd'hui, combien de fleur poss??de-t-il ? ",
                        "add", 2, "pomme"));
                dao.insert(new Question("Le fermier a r??colt?? #1 fleur mais ?? jet?? #2 qui ??tait fan??es, combien de fleur poss??de-t-il ? ",
                        "sous", 2, "rose"));
                dao.insert(new Question("Cet arbustre avait #1 feuilles en ??t??, il ne lui en reste plus que #2 en automne, combien sont tomb?? ? ",
                        "sous", 2, "feuille"));
                dao.insert(new Question("L'ann??e derni??re le fermier avait #1 moutons il en a rachet?? et a multipli?? son nombre de moutons par #2 , combien sont tomb?? ? ",
                        "mult", 2, "moutons"));
                dao.insert(new Question("Cette arbre avait #1 feuilles, son nombre de feuille ?? ??t?? divis?? par #2, combien sont tomb?? ? ",
                        "div", 2, "feuille"));
            });
        }
    };


}
