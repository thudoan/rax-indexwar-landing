package com.rackspace.cloud.api;

public class Type{
	private String id;
	private String displayName;
	private String url;

	public Type(){
		this.id=null;
		this.displayName=null;
		this.url=null;
	}

	public Type(String id, String displayName, String url){
		this.id=id;
		this.displayName=displayName;
		this.url=url;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString(){
		return "{id="+this.getId()+", "+"displayName="+this.getDisplayName()+", url="+this.getUrl()+"}";
	}
}
