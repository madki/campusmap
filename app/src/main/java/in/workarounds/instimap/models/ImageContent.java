package in.workarounds.instimap.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manidesto on 02/02/15.
 */
public class ImageContent {
    @SerializedName("width")
    int width;

    @SerializedName("height")
    int height;

    @SerializedName("size")
    long size;

    @SerializedName("link")
    String link;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
