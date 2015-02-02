package in.workarounds.instimap.fragments;

import android.content.Context;

import java.util.List;

import in.workarounds.instimap.models.Notice;

/**
 * Created by manidesto on 31/01/15.
 */
public class NoticesListAdapter extends BaseNoticesListAdapter {

    public NoticesListAdapter(Context context){
        super(context);
    }

    @Override
    protected List<Notice> getNoticesList() {
        List<Notice> notices = Notice.find(Notice.class, "is_event = 1 order by start_time");
        return notices;
    }

//    private void test(Notice notice){
//        String dataJson = notice.getDataJson();
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Block.class, new BlockDeserializer())
//                .create();
//
//        NoticeData noticeData = gson.fromJson(dataJson, NoticeData.class);
//    }
}
