
package pw.tewi.cawfee.hilt.components;

import com.google.gson.Gson;

import dagger.Component;
import pw.tewi.cawfee.hilt.modules.GsonModule;

@Component(modules=GsonModule.class) public interface GsonComponent { Gson gson(); }
