package pw.tewi.cawfee.hilt.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import pw.tewi.cawfee.api.FourChan;
import pw.tewi.cawfee.api.ImageBoard;
import pw.tewi.cawfee.preferences.ChanPreferenceManager;
import pw.tewi.cawfee.preferences.PreferenceManager;


@Module
@InstallIn(ViewModelComponent.class)
public abstract class FourChanModule {
    @Binds public abstract ImageBoard bind(FourChan __);
    @Binds public abstract PreferenceManager bindPrefs(ChanPreferenceManager __);
}
