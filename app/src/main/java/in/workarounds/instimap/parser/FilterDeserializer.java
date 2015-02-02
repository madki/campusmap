package in.workarounds.instimap.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import in.workarounds.instimap.models.Filter;

public class FilterDeserializer implements JsonDeserializer<Filter> {
    @Override
    public Filter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Filter filter = gson.fromJson(json, typeOfT);
        filter.setDbId(filter.getId());
        filter.setId(null);

        if(filter.getNoticeStartTime() == null) {
            filter.setNoticeStartTime(new Date(0L));
            filter.setNoticeEndTime(new Date(0L));
        }
        return filter;
    }
}
