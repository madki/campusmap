package in.workarounds.instimap.sync;


import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.workarounds.instimap.models.ExtendedSugarRecord;
import in.workarounds.instimap.parser.CustomGson;

public class SyncTableHelper {

    public static <T extends ExtendedSugarRecord> void syncTable(Class<T> type, Type listType) {
        List<T> maxElements = T.find(type, null, null, null, "db_id DESC", "1");
        long maxId = 0;
        if(!maxElements.isEmpty()) {
            maxId = maxElements.get(0).getDbId();
        }

        List<T> fromElements = T.find(type, null, null, null, "modified DESC", "1");
        Date from = null;
        if(!fromElements.isEmpty()) {
            from = fromElements.get(0).getModified();
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d("SyncAdapter", "maxId: " + maxId);
        ServiceHandler serviceHandler = new ServiceHandler();
        String data = null;
        List<NameValuePair> params = new ArrayList<>();
        String url = "";
        try {
            T element = type.newInstance();
            url = element.getSyncUrl();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        params.add(new BasicNameValuePair("id", Long.toString(maxId)));
        if(from != null) {
            Log.d("SyncAdapter", "modified: " + df.format(from));
            params.add(new BasicNameValuePair("from", df.format(from)));
        }
        if(!url.equals("")) {
            data = serviceHandler.getExtractedResponse(url, ServiceHandler.GET, params);
            Log.d("SyncAdapter", data);
            CustomGson customGson = new CustomGson();
            Gson gson = customGson.getGson();
            List<T> models = gson.fromJson(data, listType);
            for (T model : models) {
                model.saveOrUpdate(type, model.getDbId());
            }
        } else {
            Log.e("SyncTableHelper", "Url is empty, something is wrong");
        }

    }
}
