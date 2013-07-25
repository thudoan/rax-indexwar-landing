package com.rackspace.cloud.api;

public class ProductEntry implements Comparable<ProductEntry>{
	private String prodId;
	private String typeId;
	private String typedisplayname;
	private String typeurl;
	private int    sequence;

	private String headerTypeId;
	private String headerdisplayName;
	private String headerUrl;

	public ProductEntry(){
		this.prodId=null;
		this.typedisplayname=null;
		this.typeId=null;
		this.typeurl=null;
		this.sequence=1;
	}
	
	public ProductEntry(String prodId, String typeId, String typedisplayname, String typeurl, int seq){
		this.prodId=prodId;
		this.typeId=typeId;
		this.typedisplayname=typedisplayname;
		this.typeurl=typeurl;
		this.sequence=seq;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypedisplayname() {
		return typedisplayname;
	}

	public void setTypedisplayname(String typedisplayname) {
		this.typedisplayname = typedisplayname;
	}

	public String getTypeurl() {
		return typeurl;
	}

	public void setTypeurl(String typeurl) {
		this.typeurl = typeurl;
	}
	
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getHeaderTypeId() {
		return headerTypeId;
	}

	public void setHeaderTypeId(String headerTypeId) {
		this.headerTypeId = headerTypeId;
	}

	public String getHeaderdisplayName() {
		return headerdisplayName;
	}

	public void setHeaderdisplayName(String headerdisplayName) {
		this.headerdisplayName = headerdisplayName;
	}

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	public String toString(){
		return ("{prodId="+this.getProdId()+", typeId="+this.getTypeId()+", " + "typedisplayname="+this.getTypedisplayname()+
				", typeurl="+this.getTypeurl()+", sequence="+this.getSequence());
	}

	public int compareTo(ProductEntry o) {
		int retVal=0;
		
		if(this.sequence>o.sequence){
			retVal=1;
		}
		else if(this.sequence<o.sequence){
			retVal=-1;
		}
		else{
			if(null!=this.typedisplayname && null!=o.typedisplayname){
				retVal=this.typedisplayname.compareTo(o.typedisplayname);
			}
			else{
				if(null!=this.typedisplayname){
				    retVal=-1;
				}
				else if(null!=o.typedisplayname){
					retVal=1;
				}
			}
		}
		return retVal;
	}
	
	public boolean equals(ProductEntry o){
		boolean retVal=false;
		if(this.sequence==o.sequence){
			if(null!=this.prodId && null!=o.prodId && null!=this.typeId && 
			   null!=o.typeId && null!=this.typedisplayname && null!=o.typedisplayname &&
			   null!=this.typeurl && null!=o.typeurl){
				if(this.prodId.equals(o.prodId) && this.typeId.equals(o.typeId) &&
				   this.typedisplayname.equals(o.typedisplayname) && this.typeurl.equals(o.typeurl)){
					retVal=true;
				}
			}
		}
		return retVal;
	}
	
	public int hashCode(){
		int retVal=0;
		if(null!=this.typedisplayname){
			retVal+=this.typedisplayname.hashCode();
		}
		if(null!=this.typeId){
			retVal+=this.typeId.hashCode();
		}
		if(null!=this.typeurl){
			retVal+=this.typeurl.hashCode();
		}
		if(null!=this.prodId){
			retVal+=this.typeurl.hashCode();
		}
		retVal+=this.sequence;
		return retVal;
	}

}
