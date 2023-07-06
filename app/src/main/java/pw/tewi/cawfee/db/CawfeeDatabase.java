package pw.tewi.cawfee.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import pw.tewi.cawfee.models.Board;

@Database(entities={ Board.class }, version=1)
public abstract class CawfeeDatabase extends RoomDatabase {
    public abstract BoardDao boardDao();
}
