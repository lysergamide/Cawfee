package pw.tewi.cawfee.hilt.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import pw.tewi.cawfee.api.FourChan;
import pw.tewi.cawfee.api.ImageBoard;


@Module
@InstallIn(ViewModelComponent.class)
public abstract class ImageBoardModule {
    @Binds @IntoMap @StringKey(FourChan.NAME) public abstract ImageBoard bindMap(FourChan __);
    @Binds public abstract ImageBoard bind(FourChan __);
}
