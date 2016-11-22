package us.guihouse.autobank.presenters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import us.guihouse.autobank.models.Card;

import java.lang.reflect.Type;

/**
 * Created by guilherme on 22/11/16.
 */
public class CardAdapter implements JsonSerializer<Card> {

    @Override
    public JsonElement serialize(Card card, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject output = new JsonObject();

        JsonElement emission = jsonSerializationContext.serialize(card.getEmission());
        JsonElement valid = jsonSerializationContext.serialize(card.getValidThrow());

    	output.addProperty("id", card.getId());
    	output.addProperty("cardNumber", card.getParcialCardNumber());
    	output.add("emission", emission);
    	output.add("expiration", valid);
    	output.addProperty("limit", card.getLimit());
    	output.addProperty("interestRate", card.getInterestRate());
    	output.addProperty("closingDay", card.getClosingDay());
    	output.addProperty("isActive", card.isActive());
    	output.addProperty("availableValue", card.getAvailableValue());
    	output.addProperty("usedValue", card.getUsedValue());

        return output;
    }
}
