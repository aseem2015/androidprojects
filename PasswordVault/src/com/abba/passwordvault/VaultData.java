package com.abba.passwordvault;

public class VaultData {

	String name;
	String password;
	String description;
	
	public VaultData() {
		
	}
	
	public VaultData(String name, String password, String description) {
		this.name = name;
		this.password = password;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Override
	public String toString() {
		return "VaultData [name=" + name + ", password=" + password
				+ ", description=" + description + "]";
	}
	
	
	
}
