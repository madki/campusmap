package in.workarounds.instimap.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manidesto on 02/02/15.
 */
public class Block {
    public static String CONTENT_TYPE_TEXT = "text";
    public static String CONTENT_TYPE_IMAGE = "image";
    public static String CONTENT_TYPE_TABLE = "table";

    @SerializedName("type")
    String type;

    Object contentObject;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContentObject() {
        return contentObject;
    }

    public void setContentObject(Object content) {
        this.contentObject = content;
    }
}
