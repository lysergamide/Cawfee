package pw.tewi.cawfee.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pw.tewi.cawfee.models.Board;

@Dao
public interface BoardDao {

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    void insertBoard(Board board);

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    void insertAll(List<Board> boards);

    @Query("SELECT * FROM Board")
    List<Board> getAllBoards();

    @Query("SELECT * FROM Board WHERE board = :board")
    Board getBoard(String board);

    @Delete
    void deleteBoard(Board board);

    @Query("DELETE FROM Board")
    void deleteAll();
}
