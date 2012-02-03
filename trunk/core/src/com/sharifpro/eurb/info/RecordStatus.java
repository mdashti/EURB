package com.sharifpro.eurb.info;

public class RecordStatus {
	private String id;
	
	public static RecordStatus ACTIVE = new RecordStatus("A");
	public static RecordStatus PASSIVE = new RecordStatus("P");
	public static RecordStatus DELETED = new RecordStatus("D");
	
	private RecordStatus(String id) {
		this.id = id;
	}
	
	public static RecordStatus get(String id) {
		if(PASSIVE.getId().equals(id)) {
			return PASSIVE;
		} else if(DELETED.getId().equals(id)) {
			return DELETED;
		}
		return ACTIVE;
	}
	
	public String getId(){
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RecordStatus) {
			return this.getId().equals(((RecordStatus)obj).getId());
		}
		return false;
	}
}
