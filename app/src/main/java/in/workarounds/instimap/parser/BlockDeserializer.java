package in.workarounds.instimap.parser;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import in.workarounds.instimap.models.Block;
import in.workarounds.instimap.models.ImageContent;
import in.workarounds.instimap.models.TableContent;
import in.workarounds.instimap.models.TextContent;

/**
 * Created by manidesto on 02/02/15.
 */
public class BlockDeserializer implements JsonDeserializer<Block> {
    private static String TAG = "BlockDeserializer";
    @Override
    public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder().create();
        Block block = gson.fromJson(json, Block.class);

        JsonObject jsonObject = json.getAsJsonObject();
        String contentJson = jsonObject.get("content").toString();

        if(block.getType().equals(Block.CONTENT_TYPE_IMAGE)){
            ImageContent imageContent = gson.fromJson(contentJson, ImageContent.class);
            block.setContentObject(imageContent);
        }
        else if(block.getType().equals(Block.CONTENT_TYPE_TABLE)){
            TableContent tableContent = gson.fromJson(contentJson, TableContent.class);
            block.setContentObject(tableContent);
        }
        else if(block.getType().equals(Block.CONTENT_TYPE_TEXT)) {
            TextContent textContent = gson.fromJson(contentJson, TextContent.class);
            block.setContentObject(textContent);
        }

        return block;
    }
}
