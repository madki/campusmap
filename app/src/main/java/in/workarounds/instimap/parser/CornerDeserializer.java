package in.workarounds.instimap.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import in.workarounds.instimap.models.Corner;

public class CornerDeserializer
        implements JsonDeserializer<Corner> {

    @Override
    public Corner deserialize(JsonElement json, Type type,
                             JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Corner corner = gson.fromJson(json, Corner.class);
        corner.setDbId(corner.getId());
        corner.setId(null);

        JsonObject jsonElement = json.getAsJsonObject();
        JsonElement headerElement = jsonElement.get("header");
        String header;
        if(headerElement.isJsonNull()) {
            header = null;
        } else {
            header = headerElement.getAsString();
        }

        corner.setHeaderJson(header);
        return corner;
    }
}