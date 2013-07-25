package com.rackspace.cloud.api;

public class Product {
	
	private String id;
	private String productName;
	private String title;
	private String author;
	private String description;
	private String copyright;
	private String logoUrl;
	private String iconUrl;
	private String url;
	private String mousedown;
	
	public Product(){
		this.id=null;
		this.productName=null;
		this.title=null;
		this.author=null;
		this.description=null;
		this.copyright=null;
		this.logoUrl=null;
		this.iconUrl=null;
		this.url=null;
		this.mousedown=null;
	}
	
	public Product(String id, String prodName, String title, String author, 
			       String description, String copyRight, String logoUrl, String iconUrl,String url, String mousedown){
		this.id=id;
		this.productName=prodName;
		this.title=title;
		this.author=author;
		this.description=description;
		this.copyright=copyRight;
		this.logoUrl=logoUrl;
		this.iconUrl=iconUrl;
		this.url=url;
		this.mousedown=mousedown;
	}
	
	
	public String getMousedown() {
		return mousedown;
	}

	public void setMousedown(String mousedown) {
		this.mousedown = mousedown;
	}

	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getCopyright() {
		return copyright;
	}
	
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	public String getLogoUrl() {
		return logoUrl;
	}
	
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	public String getIconUrl() {
		return iconUrl;
	}
	
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString(){
		StringBuffer retVal=new StringBuffer("");
		retVal.append("{prodName="+this.getProductName()+", title="+this.getTitle()+", author="+this.getAuthor()+
				", description="+this.getDescription()+", copyright="+this.getCopyright()+
				", logourl="+this.getLogoUrl()+", iconurl="+this.getIconUrl()+", url="+this.getUrl()+"}");
		
		return retVal.toString();	
	}
}
