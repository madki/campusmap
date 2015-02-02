package in.workarounds.instimap.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.workarounds.instimap.models.Board;
import in.workarounds.instimap.models.Corner;
import in.workarounds.instimap.models.CornerRelation;
import in.workarounds.instimap.models.Filter;
import in.workarounds.instimap.models.Notice;
import in.workarounds.instimap.models.Venue;

public class CustomGson {
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson;

    public CustomGson() {
        this.gsonBuilder.registerTypeAdapter(Notice.class, new NoticeDeserializer());
        this.gsonBuilder.registerTypeAdapter(Venue.class, new VenueDeserializer());
        this.gsonBuilder.registerTypeAdapter(Board.class, new BoardDeserializer());
        this.gsonBuilder.registerTypeAdapter(Corner.class, new CornerDeserializer());
        this.gsonBuilder.registerTypeAdapter(CornerRelation.class, new GenericDeserializer<>());
        this.gsonBuilder.registerTypeAdapter(Filter.class, new FilterDeserializer());

        this.gson = this.gsonBuilder.create();
    }

    public Gson getGson() {
        return this.gson;
    }
}
