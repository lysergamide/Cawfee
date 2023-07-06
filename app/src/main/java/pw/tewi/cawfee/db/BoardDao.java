package pw.tewi.cawfee.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import pw.tewi.cawfee.models.Board;

@Dao
public interface BoardDao {

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    Completable insertBoard(Board board);

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    void insertAll(List<Board> boards);

    @Query("SELECT * FROM Board")
    Maybe<List<Board>> getAllBoards();

    @Query("SELECT * FROM Board WHERE board = :board")
    Maybe<Board> getBoard(String board);

    @Query("SELECT COUNT(board) FROM Board")
    Maybe<Integer> rowCount();

    @Delete
    Completable deleteBoard(Board board);

    @Query("DELETE FROM Board")
    Completable deleteAll();
}
