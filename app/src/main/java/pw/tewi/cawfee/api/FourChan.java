package pw.tewi.cawfee.api;

import static pw.tewi.cawfee.api.Constants.FourChan.API;
import static pw.tewi.cawfee.api.Constants.FourChan.IMAGE;
import static pw.tewi.cawfee.api.Constants.FourChan.NAME;
import static pw.tewi.cawfee.api.Constants.FourChan.STATIC;

import com.google.gson.Gson;

import javax.inject.Inject;


public class FourChan extends ChanImageBoard {
    @Inject public FourChan(Gson gson) { super(API, IMAGE, STATIC, NAME, gson); }
}
