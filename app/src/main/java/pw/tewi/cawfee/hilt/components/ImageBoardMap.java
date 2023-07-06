package pw.tewi.cawfee.hilt.components;


import java.util.Map;

import dagger.Component;
import pw.tewi.cawfee.api.ImageBoard;
import pw.tewi.cawfee.hilt.modules.GsonModule;
import pw.tewi.cawfee.hilt.modules.ImageBoardModule;

@Component(modules={ GsonModule.class, ImageBoardModule.class })
public interface ImageBoardMap {
    Map<String, ImageBoard> imageBoardMap();
}
