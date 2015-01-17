package in.workarounds.instimap.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import in.workarounds.instimap.models.Venue;

public class VenueDeserializer
        implements JsonDeserializer<Venue> {

    @Override
    public Venue deserialize(JsonElement json, Type type,
                              JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        in.workarounds.instimap.models.Venue venue = gson.fromJson(json, Venue.class);
        venue.setDbId(venue.getId());
        venue.setId(null);
        return venue;
    }
}