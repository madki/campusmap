package in.workarounds.instimap.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import in.workarounds.instimap.parser.BlockDeserializer;

public class Notice extends ExtendedSugarRecord<Notice> {
    @Ignore
    public String syncUrl = "notices/modified";

    long dbId;
    String dataJson;
    @Ignore
    NoticeData noticeData;
    @SerializedName("user_id")
    long userId;
    @SerializedName("position_id")
    long positionId;
    Date created;
    Date modified;
    long parent;
    boolean visible;
    @SerializedName("comment_count")
    int commentCount;
    @SerializedName("like_count")
    int likeCount;
    @SerializedName("start_time")
    Date startTime;
    @SerializedName("end_time")
    Date endTime;
    @SerializedName("public")
    boolean isPublic;
    @SerializedName("is_event")
    boolean isEvent;
    String cornersJson;
    @SerializedName("updates_below")
    int updatesBelow;
    @SerializedName("updates_above")
    int updatesAbove;
    @SerializedName("venue_id")
    long venueId;

    @Override
    public String getSyncUrl() {
        return syncUrl;
    }

    @Override
    public long getDbId() {
        return dbId;
    }

    @Override
    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }


    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }


    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean isEvent) {
        this.isEvent = isEvent;
    }

    public String getCornersJson() {
        return cornersJson;
    }

    public void setCornersJson(String cornersJson) {
        this.cornersJson = cornersJson;
    }

    public int getUpdatesBelow() {
        return updatesBelow;
    }

    public void setUpdatesBelow(int updatesBelow) {
        this.updatesBelow = updatesBelow;
    }

    public int getUpdatesAbove() {
        return updatesAbove;
    }

    public void setUpdatesAbove(int updatesAbove) {
        this.updatesAbove = updatesAbove;
    }

    public long getVenueId() {
        return venueId;
    }

    public void setVenueId(long venueId) {
        this.venueId = venueId;
    }

    public NoticeData getNoticeData(){
        if(noticeData == null){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Block.class, new BlockDeserializer())
                    .create();
            noticeData = gson.fromJson(getDataJson(), NoticeData.class);
        }
        return noticeData;
    }

    public List<Notice> getUpdates(){
        if(getUpdatesAbove() == 0) {
            return Notice.find(Notice.class, "db_id = ? or parent = ?", Long.toString(getDbId()), Long.toString(getDbId()));
        }
        else{
            return Notice.find(Notice.class, "db_id = ? or parent = ?", Long.toString(getParent()), Long.toString(getParent()));
        }
    }



    public String getTitle() {
        String title = "";
        try {
            JSONObject data = new JSONObject(this.dataJson);
            title = data.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return title;
    }

    public class Corner {
        String name;
        String tag;

        public Corner() {
        }
    }

    public Notice() {
    }
}

