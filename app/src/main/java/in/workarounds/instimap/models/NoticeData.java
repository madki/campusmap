package in.workarounds.instimap.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by manidesto on 02/02/15.
 */
public class NoticeData{
    String title;

    @SerializedName("position_name")
    String positionName;

    @SerializedName("user_name")
    String userName;

    @SerializedName("parent_title")
    String parentTitle;

    ArrayList<Block> blocks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }
}
