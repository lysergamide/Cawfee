package pw.tewi.cawfee.hilt.modules;

import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;
import pw.tewi.cawfee.db.BoardDao;
import pw.tewi.cawfee.db.CawfeeDatabase;

@Module
@InstallIn(ActivityComponent.class)
public final class BoardDaoModule {

    @Provides
    public static BoardDao provideBoardDao(@ActivityContext Context context) {
        return Room.databaseBuilder(context, CawfeeDatabase.class, "db").allowMainThreadQueries().build().boardDao();
    }
}
