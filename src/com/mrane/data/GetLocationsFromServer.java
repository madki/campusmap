package com.mrane.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class GetLocationsFromServer extends AsyncTask<Void, Void, Void> {

	String url = "";
	private final String ID = "id";
	private final String NAME = "name";
	private final String SHORTNAME = "short_name";
	private final String GROUPID = "group_id";
	private final String PIXELX = "pixel_x";
	private final String PIXELY = "pixel_y";
	private final String PARENTID = "parent_id";
	private final String PARENTREL = "parent_rel";
	private final String DESC = "description";
	private final String CHILDIDS = "child_ids";
	private final String LAT = "lat";
	private final String LONG = "long";

	public GetLocationsFromServer(String URL) {
		this.url = URL;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Void doInBackground(Void... params) {
		// Creating service handler class instance
		ServiceHandler sh = new ServiceHandler();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

		if (jsonStr != null) {

			JSONArray markers;
			try {
				markers = new JSONArray(jsonStr);

				for (int i = 0; i < markers.length(); i++) {
					JSONObject marker = markers.getJSONObject(i);
					int id = marker.getInt(ID);
					String name = marker.getString(NAME);
					String shortName = marker.getString(SHORTNAME);
					int groupIndex = marker.getInt(GROUPID);
					float pixelX = marker.getLong(PIXELX);
					float pixelY = marker.getLong(PIXELY);
					int parentId = marker.getInt(PARENTID);
					String parentRel = marker.getString(PARENTREL);
					String description = marker.getString(DESC);

					List<Integer> childIdList = new ArrayList<Integer>();
					JSONArray children = marker.getJSONArray(CHILDIDS);
					for (int j = 0; j < children.length(); j++) {
						childIdList.add(children.getInt(j));
					}
					int[] childIds = toIntArray(childIdList);

					long lat = marker.getLong(LAT);
					long lng = marker.getLong(LONG);

					Marker m = new Marker(id, name, shortName, pixelX, pixelY,
							groupIndex, description, parentId, parentRel,
							childIds, lat, lng);
					Log.d("JsonParse","" + name);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		

	}

	int[] toIntArray(List<Integer> list) {
		int[] ret = new int[list.size()];
		int i = 0;
		for (Integer e : list)
			ret[i++] = e.intValue();
		return ret;
	}

}
