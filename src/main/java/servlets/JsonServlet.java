package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by vrjunior on 18/10/16.
 */
public abstract class JsonServlet extends javax.servlet.http.HttpServlet {
    private Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy").create();

    protected JsonObject parseToJson(ServletInputStream inputStream) throws UnsupportedEncodingException {
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        return this.getGson().fromJson(reader, JsonObject.class);
    }

    public Gson getGson() {
        return gson;
    }
}
