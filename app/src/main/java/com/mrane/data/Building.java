package com.mrane.data;

public class Building extends Marker {
	public String[] children;
	
	public Building(long id, String name, String shortName, float x, float y,
			int groupIndex, String[] children, String description) {
		super(id, name, shortName, x, y, groupIndex, description);
		this.children = children;
	}

}
