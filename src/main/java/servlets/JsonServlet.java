package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by vrjunior on 18/10/16.
 */
public abstract class JsonServlet extends javax.servlet.http.HttpServlet {

    protected JsonObject parseToJson(String rawData) {
        return new Gson().fromJson(rawData, JsonObject.class);
    }

}
