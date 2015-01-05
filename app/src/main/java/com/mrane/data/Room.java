package com.mrane.data;

public class Room extends Marker {
	public String parentKey;
	public String tag;

	public Room(long id, String fullName, String shortName, float x, float y,
			int groupId, String parentName, String tag, String desc) {
		super(id, fullName, shortName, x, y, groupId, desc);
		this.tag = tag;
		this.parentKey = parentName;		
	}

}
