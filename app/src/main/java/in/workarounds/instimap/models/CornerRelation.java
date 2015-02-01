package in.workarounds.instimap.models;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;

import java.util.Date;

public class CornerRelation extends ExtendedSugarRecord<CornerRelation> {
    @Ignore
    public String syncUrl = "cornerRelations/modified";

    @SerializedName("id")
    long dbId;
    @SerializedName("board_id")
    long boardId;
    @SerializedName("corner_id")
    long cornerId;
    Date created;
    Date modified;
    @SerializedName("is_child")
    boolean isChild;

    @Override
    public String getSyncUrl() {
        return syncUrl;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean isChild) {
        this.isChild = isChild;
    }

    public long getCornerId() {
        return cornerId;
    }

    public void setCornerId(long cornerId) {
        this.cornerId = cornerId;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    @Override
    public long getDbId() {
        return dbId;
    }

    @Override
    public void setDbId(long dbId) {
        this.dbId = dbId;
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

    public CornerRelation() {}
}
