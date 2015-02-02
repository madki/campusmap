package in.workarounds.instimap.models;


import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;

import java.util.Date;

public class Corner extends ExtendedSugarRecord<Corner> {
    @Ignore
    public String syncUrl = "corners/modified";

    long dbId;
    @SerializedName("user_id")
    long userId;
    String tag;
    String name;
    @SerializedName("filter_count")
    int filterCount;
    String headerJson;
    Date created;
    Date modified;
    @SerializedName("is_open")
    boolean isOpen;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(int filterCount) {
        this.filterCount = filterCount;
    }

    public String getHeaderJson() {
        return headerJson;
    }

    public void setHeaderJson(String headerJson) {
        this.headerJson = headerJson;
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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String toString() {
        return getName();
    }

    public Corner() {}
}

