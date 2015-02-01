package in.workarounds.instimap.models;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

public abstract class ExtendedSugarRecord<T extends SugarRecord<T>> extends SugarRecord<T> {
    public void saveOrUpdate(Class type, long dbId) {
        List<T> entries = (List<T>) this.find(type, "db_id = ?", Long.toString(dbId));
        if(!entries.isEmpty()){
            long id = entries.get(0).getId();
            this.setId(id);
            this.save();
        }
        else{
            this.save();
        }
    }

    public abstract long getDbId();
    public abstract void setDbId(long dbId);
    public abstract Date getModified();
    public abstract String getSyncUrl();
}
