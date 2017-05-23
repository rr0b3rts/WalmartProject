package com.walmart.reminder.model;

public enum Status {
	NOTDONE("Not Done"), DONE("Done");
	
	private String name;
	
	private Status(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public static Status getStatus(String status) {
		if(status == null || status.length() == 0) {
			return null;
		}
		
		for(Status s : values()) {
			if(s.getName().equalsIgnoreCase(status)) {
				return s;
			}
		}
		
		throw new IllegalArgumentException("Unknown status: " + status);
	}
}
