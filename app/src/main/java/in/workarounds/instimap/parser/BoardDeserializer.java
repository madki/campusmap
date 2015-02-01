package in.workarounds.instimap.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import in.workarounds.instimap.models.Board;

public class BoardDeserializer
        implements JsonDeserializer<Board> {

    @Override
    public Board deserialize(JsonElement json, Type type,
                              JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Board board = gson.fromJson(json, Board.class);
        board.setDbId(board.getId());
        board.setId(null);

        JsonObject jsonElement = json.getAsJsonObject();
        JsonElement headerElement = jsonElement.get("header");
        String header;
        if(headerElement.isJsonNull()) {
            header = null;
        } else {
            header = headerElement.getAsString();
        }

        board.setHeaderJson(header);
        return board;
    }
}