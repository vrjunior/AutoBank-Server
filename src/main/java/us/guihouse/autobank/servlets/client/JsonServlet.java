package us.guihouse.autobank.servlets.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import us.guihouse.autobank.models.Card;
import us.guihouse.autobank.other.gson.ExcludeStrategy;
import us.guihouse.autobank.presenters.CardAdapter;

import javax.servlet.ServletInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by vrjunior on 18/10/16.
 */
public abstract class JsonServlet extends javax.servlet.http.HttpServlet {
    private final Gson gson;

    public JsonServlet() {
        gson = new GsonBuilder()
                .setDateFormat("MM-dd-yyyy")
                .addSerializationExclusionStrategy(new ExcludeStrategy())
                .registerTypeAdapter(Card.class, new CardAdapter())
                .create();
    }

    protected JsonObject parseToJson(ServletInputStream inputStream) throws UnsupportedEncodingException {
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        return this.getGson().fromJson(reader, JsonObject.class);
    }

    public Gson getGson() {
        return gson;
    }
}
