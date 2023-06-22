package pw.tewi.cawfee.hilt.modules;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public final class GsonModule {
    @Provides public static Gson provideGson() { return new Gson(); }
}
