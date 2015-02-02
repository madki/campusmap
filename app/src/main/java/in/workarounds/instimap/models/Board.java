package in.workarounds.instimap.models;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;

import java.util.Date;

public class Board extends ExtendedSugarRecord<Board> {
    @Ignore
    public String syncUrl = "boards/modified";

    long dbId;
    String title;
    String headerJson;
    @SerializedName("user_id")
    long userId;
    Date created;
    Date modified;
    @SerializedName("corner_id")
    long cornerId;
    String tag;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaderJson() {
        return headerJson;
    }

    public void setHeaderJson(String headerJson) {
        this.headerJson = headerJson;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public long getCornerId() {
        return cornerId;
    }

    public void setCornerId(long cornerId) {
        this.cornerId = cornerId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String toString() {
        return getTitle();
    }

    public Board() {}
}
