package pw.tewi.cawfee.hilt.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import pw.tewi.cawfee.ui.preferences.CawfeePreferenceManager;
import pw.tewi.cawfee.ui.preferences.ChanPreferenceManager;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ChanPreferenceManagerModule {
    @Binds public abstract CawfeePreferenceManager bindPrefs(ChanPreferenceManager __);
}
