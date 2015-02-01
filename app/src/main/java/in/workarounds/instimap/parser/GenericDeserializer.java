package in.workarounds.instimap.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import in.workarounds.instimap.models.ExtendedSugarRecord;

public class GenericDeserializer<T extends ExtendedSugarRecord> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        T element = gson.fromJson(json, typeOfT);
        element.setDbId(element.getId());
        element.setId(null);

        return element;
    }
}
