package com.rackspace.cloud.api.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rackspace.cloud.api.Product;
import com.rackspace.cloud.api.ProductEntry;
import com.rackspace.cloud.api.Type;

public class IndexBean {
	private ServletContext servletCtx;
	//private List<Product> prods;
	private Map<String, Product>productsMap ;
	private List<ProductEntry>prodEntries;

	//This Map holds all the unique types associated with a given product
	private Map<String, Vector<ProductEntry>>productHeaderTypes;
	//This Map holds all the ProductEntries retrieved from all the different installed .wars via the respective
	//bookinfo.xml, keyed by the product ID, and categorized by all the unique Types
	private Map<String, Map<String,Vector<ProductEntry>>>productEntriesForProdsCategorizedByTypes;

	private List<Type>types;
	private String message;


	private static Logger log = Logger.getLogger(IndexBean.class);

	public IndexBean(){
		//this.prods=new ArrayList<Product>();
		this.types=new ArrayList<Type>();
		this.productHeaderTypes=new LinkedHashMap<String, Vector<ProductEntry>>();
		this.productEntriesForProdsCategorizedByTypes=new LinkedHashMap<String, Map<String,Vector<ProductEntry>>>();
		this.prodEntries=new ArrayList<ProductEntry>();
		this.productsMap=new LinkedHashMap<String, Product>();
		this.message=new String("");
	}

	public IndexBean(ServletContext servCtx){
		this();
		this.servletCtx=servCtx;		
	}

	public List<Type>getTypes(){
		if(types.size()==0){
			synchronized(this.types){
				if(this.types.size()==0){
					loadTypes();
				}
			}
		}
		return types;
	}

	public Map<String, Product>getProductsMap(){
		if(this.productsMap.size()==0){
			synchronized(this.productsMap){
				if(this.productsMap.size()==0){
					this.loadProducts();
				}
			}
		}
		return this.productsMap;
	}

	public String getTypesSize(){
		return String.valueOf(types.size());
	}

//	public String getProdSize(){
//		return prods.size()+"";
//	}

	public void setServletCtx(ServletContext ctx){
		this.servletCtx=ctx;
	}

	public String getMessage(){
		if(null==this.servletCtx){
			this.message= "servletCtx is null";
		}
		else{
			File productInfoXML=new File(this.servletCtx.getRealPath("/")+"WEB-INF/productinfo.xml");
			this.message= "productInfoXML.getAbsolutePath()="+productInfoXML.getAbsolutePath()+" productInfoXML.exist()="+productInfoXML.exists();
		}
		return this.message;
	}

//	public Product getFirstProduct(){
//		return prods.get(0);
//	}

	public List<Product>getProds(){
		List<Product> retVal=new ArrayList<Product>();
		if(this.productsMap.size()==0){
			synchronized(this.productsMap){
				if(this.productsMap.size()==0){
					loadProducts();
				}
			}
		}	
		for(Product aProd:this.productsMap.values()){
			retVal.add(aProd);
		}
		return retVal;
	}
	
	public void reloadAll()throws Throwable{
		if(log.isDebugEnabled()){
			log.debug("reloadAll(): Enter");
		}
		this.getProds();
		this.getTypes();
		log.debug("reloadAll(): loading productentries");
		synchronized(this.prodEntries){
			this.loadProductEntries();
		}
		log.debug("reloadAll(): loading ProductHeaderTypes");
		synchronized(this.productHeaderTypes){
			this.loadProductHeaderTypes();
		}
		log.debug("reloadAll(): loading ProductEntriesForProdsCategorizedByTypes");
		synchronized(this.productEntriesForProdsCategorizedByTypes){
			this.loadProductEntriesForProdsCategorizedByTypes();
		}
		if(log.isDebugEnabled()){
			log.debug("reloadAll(): Exit");
		}
	}
	
	private void loadTypes(){
		if(log.isDebugEnabled()){
			log.debug("loadTypes(): Enter: types.size()="+types.size());
		}
		if(null!=this.servletCtx){
			if(log.isDebugEnabled()){
				log.debug("loadTypes(): this.servletCtx is NOT null servletCtx.getReaLPath(/IndexWar)="+servletCtx.getRealPath("/IndexWar"));
			}
			this.types.clear();
			Map<String, Type>trackTypes=new HashMap<String, Type>();
			File productInfoXML=new File(this.servletCtx.getRealPath("/")+"WEB-INF/typeinfo.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc=dBuilder.parse(productInfoXML);
				doc.getDocumentElement().normalize();

				NodeList typesNodeList=doc.getElementsByTagName("type");

				if(null!=typesNodeList){
					for(int i=0; i<typesNodeList.getLength();++i){
						Node aNode=typesNodeList.item(i);
						if(aNode.getNodeType() == Node.ELEMENT_NODE){
							Element aType = (Element)aNode;

							String id="";							
							NodeList idsNodes=aType.getElementsByTagName("id");
							if(null!=idsNodes){
								Node idNode=idsNodes.item(0);
								NodeList children=idNode.getChildNodes();
								if(null!=children){
									Node node=children.item(0);
									id=node.getNodeValue();
								}
							}
							if(!trackTypes.containsKey(id)){
								String displayName="";
								NodeList displayNameNodes=aType.getElementsByTagName("displayname");
								if(null!=displayNameNodes){
									Node displayNameNode=displayNameNodes.item(0);
									NodeList children=displayNameNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										displayName=node.getNodeValue();
									}
								}
								String url="";
								NodeList urlNameNodes=aType.getElementsByTagName("url");
								if(null!=urlNameNodes){
									Node urlNode=urlNameNodes.item(0);
									NodeList children=urlNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										url=node.getNodeValue();
									}
								}

								Type theType = new Type(id,displayName,url);
								types.add(theType);
								trackTypes.put(id,theType);
							}
						}
					}
				}
			}
			catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			catch(SAXException e){
				e.printStackTrace();
			}
		}
		else{
			if(log.isDebugEnabled()){
				log.debug("loadTypes(): servletCtx is NULL");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("loadTypes(): Exit: types.size()="+this.types.size());
		}
	}

	private void loadProducts(){
		if(log.isDebugEnabled()){
			log.debug("loadProducts Enter: prods.size()="+this.productsMap.size());
		}
		if(null!=this.servletCtx){
			this.productsMap.clear();
			File productInfoXML=new File(this.servletCtx.getRealPath("/")+"WEB-INF/productInfo.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc=dBuilder.parse(productInfoXML);
				doc.getDocumentElement().normalize();

				NodeList productsNodeList=doc.getElementsByTagName("product");
				if(null!=productsNodeList){
					for(int i=0; i<productsNodeList.getLength();++i){
						Node aNode=productsNodeList.item(i);
						if(aNode.getNodeType() == Node.ELEMENT_NODE){

							Element aProduct = (Element)aNode;
							String id="";
							NodeList idsNodes=aProduct.getElementsByTagName("id");
							if(null!=idsNodes){
								Node idNode=idsNodes.item(0);
								NodeList children=idNode.getChildNodes();
								if(null!=children){
									Node node=children.item(0);
									id=node.getNodeValue();
								}
							}
							if(!this.productsMap.containsKey(id)){
								String name="";
								NodeList namesNodes=aProduct.getElementsByTagName("name");
								if(null!=namesNodes){
									Node idNode=namesNodes.item(0);
									NodeList children=idNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										name=node.getNodeValue();
									}
								}
								String title="";
								NodeList titlesNodes=aProduct.getElementsByTagName("title");
								if(null!=namesNodes){
									Node idNode=titlesNodes.item(0);
									NodeList children=idNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										title=node.getNodeValue();
									}
								}
								String author="";
								NodeList authorsNodes=aProduct.getElementsByTagName("author");
								if(null!=namesNodes){
									Node idNode=authorsNodes.item(0);
									NodeList children=idNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										author=node.getNodeValue();
									}
								}
								String description="";
								NodeList descriptionsNodes=aProduct.getElementsByTagName("description");
								if(null!=namesNodes){
									Node idNode=descriptionsNodes.item(0);
									NodeList children=idNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										description=node.getNodeValue();
									}
								}
								String copyright="";
								NodeList copyrightsNodes=aProduct.getElementsByTagName("copyright");
								if(null!=namesNodes){
									Node idNode=copyrightsNodes.item(0);
									NodeList children=idNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										copyright=node.getNodeValue();
									}
								}
								String icon1="";
								NodeList icon1sNodes=aProduct.getElementsByTagName("icon1");
								if(null!=namesNodes){
									Node idNode=icon1sNodes.item(0);
									NodeList children=idNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										icon1=node.getNodeValue();
									}
								}
								String icon2="";
								NodeList icon2sNodes=aProduct.getElementsByTagName("icon2");
								if(null!=namesNodes){
									Node idNode=icon2sNodes.item(0);
									NodeList children=idNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										icon2=node.getNodeValue();
									}
								}
								String prodUrl="";
								NodeList prodUrlNodes=aProduct.getElementsByTagName("url");
								if(null!=prodUrlNodes){
									Node idNode=prodUrlNodes.item(0);
									NodeList children=idNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										prodUrl=node.getNodeValue();
									}
								}

								String prodMouseDown="";
								NodeList prodMouseDownNodes=aProduct.getElementsByTagName("mousedown");
								if(null!=prodMouseDownNodes){
									Node prodMouseDownNode=prodMouseDownNodes.item(0);
									NodeList children=prodMouseDownNode.getChildNodes();
									if(null!=children){
										Node node=children.item(0);
										prodMouseDown=node.getNodeValue();
									}
								}
								
								Product aProd=new Product(id,name,title,author,description,copyright,icon1,icon2,prodUrl,prodMouseDown);
								this.productsMap.put(id, aProd);
							}
						}
					}
				}
			} 
			catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			catch(SAXException e){
				e.printStackTrace();
			}
		}
		if(log.isDebugEnabled()){
			log.debug("loadProducts Exit: prods.size()="+this.productsMap.size());
		}
	}

	private void loadProductEntriesForProdsCategorizedByTypes(){
		if(log.isDebugEnabled()){
			log.debug("loadProductEntriesForProdsCategorizedByTypes() Enter: poductEntriesForProdsCategorizedByTypes.size()="+this.productEntriesForProdsCategorizedByTypes.size());			                     
		}
		this.productEntriesForProdsCategorizedByTypes.clear();
		//Iterate through all the Products
		for(Product aProd:this.getProds()){
			//Get all the productHeaders for a given product
			if(this.productHeaderTypes.containsKey(aProd.getId())){
				Vector<ProductEntry>prodHeaderTypesForAProd=this.productHeaderTypes.get(aProd.getId());

				//Each product has Map of Vectors keyed by the product ID
				Map<String, Vector<ProductEntry>>prodEntriesCategorizedByTypeForAProd=new LinkedHashMap<String, Vector<ProductEntry>>();
				this.productEntriesForProdsCategorizedByTypes.put(aProd.getId(), prodEntriesCategorizedByTypeForAProd);

				//Iterate through all the unique product headers and add productEntries to the respective vector accordingly
				for(ProductEntry productHeader:prodHeaderTypesForAProd){
					Vector<ProductEntry>productEntriesForAHeaderType=new Vector<ProductEntry>();
					prodEntriesCategorizedByTypeForAProd.put(productHeader.getHeaderdisplayName(), productEntriesForAHeaderType);
					//Iterate through all the prodEntries and add them to the Vector accordingly
					for(ProductEntry aProdEntry:this.prodEntries){
						//The product entry must match the current headertype and product
						if(aProdEntry.getProdId().equals(aProd.getId()) && aProdEntry.getTypeId().equals(productHeader.getTypeId())){						

							ProductEntry theProdEntry=new ProductEntry(aProdEntry.getProdId(),aProdEntry.getTypeId(),
									aProdEntry.getTypedisplayname(),aProdEntry.getTypeurl(),aProdEntry.getSequence());
							theProdEntry.setHeaderTypeId(productHeader.getHeaderTypeId());
							theProdEntry.setHeaderdisplayName(productHeader.getHeaderdisplayName());
							theProdEntry.setHeaderUrl(productHeader.getHeaderUrl());

							int insertIndex=0;
							//Need to add to the Vector sorted by the sequence
							for(int i=0;i<productEntriesForAHeaderType.size();++i){
								insertIndex=i;
								ProductEntry prodEntryFromList=productEntriesForAHeaderType.get(i);
								//the compareTo method will first compqre the sequence number
								//if they are equal then it will compare the display name of the
								//productEntry
								int compared=aProdEntry.compareTo(prodEntryFromList);
								if(compared==-1||compared==0){
									if(insertIndex>0){
										--insertIndex;
									}
									break;
								}
								else if(insertIndex==(productEntriesForAHeaderType.size()-1)){
									insertIndex=productEntriesForAHeaderType.size();
								}
							}
							//add theProdEntry to the Vector sorted by the sequence
							productEntriesForAHeaderType.insertElementAt(theProdEntry,insertIndex);
						}
					}
				}
			}
		}
		if(log.isDebugEnabled()){
			log.debug("loadPoductEntriesForProdsCategorizedByTypes() Exit: productHeaderTypes.size()="+this.productHeaderTypes.size());
		}

	}

	public Map<String,Map<String,Vector<ProductEntry>>>getProductEntriesForProdsCategorizedByTypes(){
		if(this.productEntriesForProdsCategorizedByTypes.size()==0){
			this.getProductHeaderTypes();
			synchronized(this.productEntriesForProdsCategorizedByTypes){
				if(this.productEntriesForProdsCategorizedByTypes.size()==0){
					this.loadProductEntriesForProdsCategorizedByTypes();
				}
			}
		}
		return this.productEntriesForProdsCategorizedByTypes;
	}

	public Map<String, Vector<ProductEntry>> getProductHeaderTypes(){
		if(this.productHeaderTypes.size()==0){
			synchronized(this.productHeaderTypes){
				if(this.productHeaderTypes.size()==0){
					this.loadProductHeaderTypes();
				}
			}
		}
		return this.productHeaderTypes;
	}

	public int getProductHeaderTypesSize(){
		return this.productHeaderTypes.size();
	}

	//This can only be called if loadProductEntries has already been called
	private void loadProductHeaderTypes(){
		if(log.isDebugEnabled()){
			log.debug("loadProductHeaderTypes(): Enter: productHeaderTypes.size()="+this.productHeaderTypes.size());			
		}
		this.productHeaderTypes.clear();
		this.getProdEntries();
		this.getProds();
		if(log.isDebugEnabled()){
			log.debug("loadProductHeaderTypes(): types.size()="+types.size()+" this.productHeaderTypes.size()="+this.productHeaderTypes.size());
		}
		if(this.types.size()==0){	
			synchronized(this.types){
				if(this.types.size()==0){
					this.loadTypes();
				}
			}
		}

		//Iterate through all the types configured in typeinfo.xml
		for(int i=0; i<this.types.size();++i){
			Type aType=this.types.get(i);

			String headerTypeId=aType.getId();
			String headerDisplayName=aType.getDisplayName();
			String headerUrl=aType.getUrl();

			if(log.isDebugEnabled()){
				log.debug("loadProductHeaderTypes(): headerTypeId="+headerTypeId);
				log.debug("loadProductHeaderTypes(): headerDisplayName="+headerDisplayName);
				log.debug("loadProductHeaderTypes(): headerUrl="+headerUrl);
			}
			//Iterate through all the productEntries and match each productEntry with the respective
			//configured product in the productInfo.xml
			for(ProductEntry aProdEntry:this.prodEntries){
				String prodEntryProdId=aProdEntry.getProdId();
				//Match aProdEntry to a configured product and the current type iteration that is configured in typeInfo.xml
				if(this.productsMap.containsKey(prodEntryProdId) && aProdEntry.getTypeId().equals(headerTypeId)){
					//We have never encountered this prodId, create a new Vector to track all the
					//ProductEntries for the given prodId
					if(!this.productHeaderTypes.containsKey(prodEntryProdId)){
						Vector<ProductEntry>productEntriesForAProd=new Vector<ProductEntry>();

						ProductEntry theProdEntry=new ProductEntry(aProdEntry.getProdId(),aProdEntry.getTypeId(),
								aProdEntry.getTypedisplayname(),aProdEntry.getTypeurl(),aProdEntry.getSequence());
						theProdEntry.setHeaderTypeId(headerTypeId);
						theProdEntry.setHeaderdisplayName(headerDisplayName);
						theProdEntry.setHeaderUrl(headerUrl);

						if(log.isDebugEnabled()){
							log.debug("loadProductHeaderTypes(): This is the first time we have encountered prodId:"+prodEntryProdId);
							log.debug("loadProductHeaderTypes(): theProdEntry="+theProdEntry.toString());
						}
						productEntriesForAProd.add(theProdEntry);
						this.productHeaderTypes.put(prodEntryProdId, productEntriesForAProd);
					}
					//We have encountered this prodId before, retrieve it from the Map, and add the ProductEntry
					//in sorted order
					else{
						if(log.isDebugEnabled()){
							log.debug("loadProductHeaderTypes(): Already encountered prodId:"+prodEntryProdId);
						}
						Vector<ProductEntry>productEntriesForAProd=this.productHeaderTypes.get(prodEntryProdId);
						boolean foundType=false;
						for(ProductEntry retrievedProductEntry:productEntriesForAProd){
							if(retrievedProductEntry.getTypeId().equals(headerTypeId)){
								foundType=true;
								break;
							}
						}
						if(!foundType){
							ProductEntry theProdEntry=new ProductEntry(aProdEntry.getProdId(),aProdEntry.getTypeId(),
									aProdEntry.getTypedisplayname(),aProdEntry.getTypeurl(),aProdEntry.getSequence());
							theProdEntry.setHeaderTypeId(headerTypeId);
							theProdEntry.setHeaderdisplayName(headerDisplayName);
							theProdEntry.setHeaderUrl(headerUrl);
							if(log.isDebugEnabled()){
								log.debug("loadProductHeaderTypes(): theType="+theProdEntry.toString());
							}
							productEntriesForAProd.add(theProdEntry);											
						}
					}									    
				}
			}
		}
		if(log.isDebugEnabled()){
			log.debug("loadProductHeaderTypes() Exit: productHeaderTypes.size()="+this.productHeaderTypes.size());
		}
	}


	private void getProductEntriesForAnXML(String anXmlStr){
		if(log.isDebugEnabled()){
			log.debug("getProductEntriesForAnXML(): Entry");
			//log.debug("anXmlStr="+anXmlStr);
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(anXmlStr));
			Document doc=dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList products=doc.getElementsByTagName("product");
			if(null!=products){
				for(int i=0; i<products.getLength();++i){
					Node aNode=products.item(i);
					if(aNode.getNodeType() == Node.ELEMENT_NODE){
						Element aProduct = (Element)aNode;

						String prodId="";
						NodeList idsNodes=aProduct.getElementsByTagName("id");
						if(null!=idsNodes){
							Node idNode=idsNodes.item(0);
							NodeList children=idNode.getChildNodes();
							if(null!=children){				        		
								Node node=children.item(0);
								prodId=node.getNodeValue();
							}
						}
						NodeList typesNodes=aProduct.getElementsByTagName("types");
						if(null!=typesNodes){
							if(typesNodes.item(0).getNodeType() == Node.ELEMENT_NODE){
								Element typesElement=(Element)typesNodes.item(0);
								NodeList typesNodeList=typesElement.getElementsByTagName("type");
								if(null!=typesNodeList){
									for(int x=0;x<typesNodeList.getLength();++x){
										Element aTypeElement=(Element)typesNodeList.item(x);

										String typeId=aTypeElement.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
										String typeName=aTypeElement.getElementsByTagName("displayname").item(0).getChildNodes().item(0).getNodeValue();
										String typeUrl=aTypeElement.getElementsByTagName("url").item(0).getChildNodes().item(0).getNodeValue();
										String sequence=aTypeElement.getElementsByTagName("sequence").item(0).getChildNodes().item(0).getNodeValue();
										int    intSeq=1;
										try{
											intSeq=Integer.valueOf(sequence);
										}
										catch(NumberFormatException e){
											intSeq=1;
										}
										ProductEntry aProductEntry=new ProductEntry(prodId,typeId,typeName,typeUrl,intSeq);
										if(log.isDebugEnabled()){
											log.debug("getProductEntriesForAnXML(): "+aProductEntry);
										}
										this.prodEntries.add(aProductEntry);
										if(log.isDebugEnabled()){
											log.debug("getProductEntriesForAnXML(): thisProdEntries.size()="+prodEntries.size());
										}
									}
								}
							}
						}			        
					}
				}				
			}
		}
		catch (ParserConfigurationException e) {
			if(log.isDebugEnabled()){
				log.debug("getProductEntriesForAnXML(): ParserConfigurationException caught");
			}
			e.printStackTrace();
		}
		catch(IOException e){
			if(log.isDebugEnabled()){
				log.debug("getProductEntriesForAnXML(): IOException caught");
			}
			e.printStackTrace();
		}
		catch(SAXException e){
			if(log.isDebugEnabled()){
				log.debug("getProductEntriesForAnXML(): SAXException caught");
			}
			e.printStackTrace();
		}
		if(log.isDebugEnabled()){
			log.debug("getProductEntriesForAnXML(): Exit");
		}
	}

//	private List<String>getRunningApps(String host, int port, String managerUrl, final String username, final String password)throws MalformedURLException, 
//	IOException{
//		if(log.isDebugEnabled()){
//			log.debug("getRunningApps(): Enter");
//		}
//		List<String>retVal=new ArrayList<String>();
//
//		log.debug("getRunningApps():retVal.size()="+retVal.size());
//		BufferedReader r = null;		
//		InputStreamReader innyReader=null;
//		
//		Authenticator.setDefault(new Authenticator(){
//			protected PasswordAuthentication getPasswordAuthentication(){
//				return new PasswordAuthentication(username, password.toCharArray());
//			}
//		});
//		String requestUrl="http://"+host+":"+port+"/"+managerUrl;
//		URL url;
//		url = new URL(requestUrl);
//		URLConnection conn;
//		log.debug("getRunningApps():requestUrl="+requestUrl);
//		try{
//			log.debug("getRunningApps(): opening connection:");
//		conn = url.openConnection();
//
//		conn.setConnectTimeout(2000);
//		conn.setReadTimeout(3000);
//
//
//		String line = null;
//		log.debug("getRunningApps(): After opening connection");
//		try{
//			log.debug("getRunningApps(): getting innyReader");
//			innyReader=new InputStreamReader(conn.getInputStream());
//			log.debug("getRunningApps(): After getting innyReader");
//			r=new BufferedReader(innyReader);
//
//			log.debug("getRunninApps(): reading lines start:");
//			while(null!=(line=r.readLine())){
//				if(line.contains(":running:") && !line.startsWith("/IndexWar:") && !line.startsWith("/host-manager:")
//						&&!line.startsWith("/docs:") && !line.startsWith("/examples:")
//						&&!line.startsWith("/manager:")){
//					retVal.add(line);
//				}
//			}
//			log.debug("getRunninApps(): reading lines end:");
//			r.close();
//			innyReader.close();
//			log.debug("getRunninApps(): after closing buffers");
//		}
//		catch(ProtocolException e){
//			if(log.isDebugEnabled()){
//				log.debug("getRunningApps(): Authentication Failed");
//			}
//			e.printStackTrace();
//		}
//		catch(FileNotFoundException e){
//			if(log.isDebugEnabled()){
//				log.debug("getRunningApps(): Could not find resource for: "+line+" requestURL="+requestUrl);
//			}
//			e.printStackTrace();
//		}
//		}
//		catch(Throwable e){
//			log.debug("getRunningApps():caught unknown exception message: e.getMessage()="+e.getMessage()+" closing all buffers");
//			if(null!=r){
//				r.close();			
//			}
//			if(null!=innyReader){
//				innyReader.close();
//			}
//			e.printStackTrace();			
//		}
//		if(log.isDebugEnabled()){
//			log.debug("getRunningApps(): Exit retVal.size()="+retVal.size());
//		}
//		return retVal;
//	}

	public List<ProductEntry> getProdEntries(){
		if(log.isDebugEnabled()){
			log.debug("getProdEntries(): Enter prodEntries.size()="+prodEntries.size());
		}
		if(prodEntries.size()==0){
			synchronized(this.prodEntries){
				if(this.prodEntries.size()==0){
					loadProductEntries();
				}
			}
		}
		if(log.isDebugEnabled()){
			log.debug("getProdEntries(): Exit prodEntries.size()="+prodEntries.size());
		}
		return prodEntries;		
	}

	
//	private void loadProductEntriesOld(){
//		if(log.isDebugEnabled()){
//			log.debug("loadProductEntriesOld(): Enter: prodEntries.size()="+this.prodEntries.size());
//		}
//		this.prodEntries.clear();
//		Properties props = new Properties();
//		try{
//			props.load(new FileInputStream(this.servletCtx.getRealPath("/")+"WEB-INF/props.properties"));
//
//			if(log.isDebugEnabled()){
//				log.debug("loadProductEntriesOld(): loaded props.properties");
//				log.debug("loadProductEntriesOld(): props.getProperty(host)="+props.getProperty("host"));
//				log.debug("loadProductEntriesOld(): props.getProperty(port)="+props.getProperty("port"));
//				log.debug("loadProductEntriesOld(): props.getProperty(username)="+props.getProperty("username"));
//				log.debug("loadProductEntriesOld(): props.getProperty(password)="+props.getProperty("password"));
//				log.debug("loadProductEntriesOld(): props.getProperty(bookinfofilename)="+props.getProperty("bookinfofilename"));
//			}
//			//Now we have to get the 
//			String host=props.getProperty("host","localhost");
//			String port=props.getProperty("port", "80");
//			String managerUrl="manager/list";
//			final String username=props.getProperty("username","admin");
//			final String password=props.getProperty("password","admin");
//			String bookinfoFileName=props.getProperty("bookinfofilename","bookinfo.xml");
//
//			Authenticator.setDefault(new Authenticator(){
//				protected PasswordAuthentication getPasswordAuthentication(){
//					return new PasswordAuthentication(username, password.toCharArray());
//				}
//			});
//			try{
//				List<String>runningApps=getRunningApps(host, Integer.valueOf(port), managerUrl,username,password);
//				log.debug("loadProductEntriesOld(): runningApps.size()="+runningApps.size());
//				for(String aRunningApp:runningApps){
//					String[]partsOfARunningApp=aRunningApp.split(":");
//					if(null!=partsOfARunningApp){
//						String firstPart=partsOfARunningApp[0];
//						//We do not care about /:running:0:ROOT
//						if(firstPart.length()>1){
//							String appName=firstPart.substring(1);
//							String requestUrl="http://"+host+":"+port+"/"+appName+"/"+bookinfoFileName;
//							if(log.isDebugEnabled()){
//								log.debug("loadProductEntriesOld(): requestUrl="+requestUrl);
//							}
//							BufferedReader r = null;
//							InputStreamReader innyReader=null;
//							try{
//								URL url;
//								url = new URL(requestUrl);
//								URLConnection conn;
//								
//								conn = url.openConnection();
//								conn.setConnectTimeout(1000);
//								conn.setReadTimeout(3000);
//								r = null;
//								innyReader=new InputStreamReader(conn.getInputStream());
//								r=new BufferedReader(innyReader);
//								String line = null;
//								StringBuffer anXmlStrBuff=new StringBuffer("");
//								while(null!=(line=r.readLine())){
//									anXmlStrBuff.append(line);
//									anXmlStrBuff.append("\n");
//								}
//								r.close();
//								innyReader.close();
//								this.getProductEntriesForAnXML(anXmlStrBuff.toString());
//							}
//							catch(ProtocolException e){
//								log.debug("loadProductEntriesOld(): Authentication Failed");
//							}
//							catch(FileNotFoundException e){
//								log.debug("loadProductEntriesOld(): Could not find resource from the URL: "+requestUrl);
//							}
//							catch(Throwable e){
//								log.debug("loadProductEntriesOld(): Unknown Exception caught, closing all buffers");
//								if(null!=r){
//									r.close();
//								}
//								if(null!=innyReader){
//									innyReader.close();
//								}
//							}
//						}
//					}
//				}
//			} 
//			catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		catch(FileNotFoundException e){
//			e.printStackTrace();
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
//		if(log.isDebugEnabled()){
//			log.debug("loadProductEntriesOld() Exit: this.prodEntries.size()="+this.prodEntries.size());
//		}
//	}
	
	private void loadProductEntries(){
		if(log.isDebugEnabled()){
			log.debug("loadProductEntries(): Enter: prodEntries.size()="+this.prodEntries.size());
		}
		this.prodEntries.clear();
		Properties props = new Properties();
		BufferedReader r = null;
		InputStreamReader innyReader=null;
		try{
			props.load(new FileInputStream(this.servletCtx.getRealPath("/")+"WEB-INF/props.properties"));
			//Now we have to get the webapps folder
			String webappPath=this.servletCtx.getRealPath("..");
			if(log.isDebugEnabled()){
				log.debug("loadProductEntries(): loaded props.properties");
				log.debug("loadProductEntries(): props.getProperty(host)="+props.getProperty("host"));
				log.debug("loadProductEntries(): props.getProperty(port)="+props.getProperty("port"));
				log.debug("loadProductEntries(): props.getProperty(username)="+props.getProperty("username"));
				log.debug("loadProductEntries(): props.getProperty(password)="+props.getProperty("password"));
				log.debug("loadProductEntries(): props.getProperty(bookinfofilename)="+props.getProperty("bookinfofilename"));
				log.debug("loadProductEntries(): webappPath="+webappPath);
			}
			File theFile=new File(webappPath);
			//Make sure that we are deailing with a directory
			if(theFile.isDirectory()){
				//Get all the files in the directory
				File[] allFiles=theFile.listFiles();
				if(allFiles!=null && allFiles.length>0){
					if(log.isDebugEnabled()){
						log.debug("loadProductEntries(): allFiles.length="+allFiles.length);
					}
					for(File aFile:allFiles){
						//We are only interested in the subfolders
						if(aFile.isDirectory()){
							if(log.isDebugEnabled()){
								log.debug("loadProductEntries(): aFile is a directory aFile.getName()="+aFile.getName());
							}
							//Check to see if a bookinfo.xml exists
							String bookInfoStr=aFile.getAbsoluteFile()+"/bookinfo.xml";
							if(log.isDebugEnabled()){
								log.debug("loadProductEntries(): bookInfoStr="+bookInfoStr);
							}
							File bookInfoFile=new File(bookInfoStr);
							if(bookInfoFile.exists()){
								if(log.isDebugEnabled()){
									log.debug("loadProductEntries(): bookInfoFile exists");
								}
								innyReader=new InputStreamReader(new FileInputStream(bookInfoStr));

								r=new BufferedReader(innyReader);
								StringBuffer anXmlStrBuff=new StringBuffer("");
								String line=null;
								while(null!=(line=r.readLine())){
									anXmlStrBuff.append(line);
									anXmlStrBuff.append("\n");
								}
								if(log.isDebugEnabled()){
									log.debug("loadProductEntries(): finished reading file");
								}
								r.close();
								innyReader.close();
								this.getProductEntriesForAnXML(anXmlStrBuff.toString());
							}
							else{
								if(log.isDebugEnabled()){
									log.debug("loadProductEntries(): bookInfoFile does not exist~~~");
								}
							}
						}
					}
				}
				else{
					if(log.isDebugEnabled()){
						log.debug("loadProductEntries(): allFiles is null or length is 0");
					}
				}
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			if(null!=r){
				try {
					r.close();
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					if(log.isDebugEnabled()){
						log.debug("loadProductEntries(): Error closing BurfferedReader r");
					}
				}
			}
			if(null!=innyReader){
				try {
					innyReader.close();					
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					if(log.isDebugEnabled()){
						log.debug("loadProductEntries(): Error closing InputStreamReader innyReader");
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
			if(log.isDebugEnabled()){
				log.debug("loadProductEntries(): IOException caught, trying to close BufferedReader and InputStreamReader");
			}
			if(null!=r){
				try {
					r.close();
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					if(log.isDebugEnabled()){
						log.debug("loadProductEntries(): Error closing BurfferedReader r");
					}
				}
			}
			if(null!=innyReader){
				try {
					innyReader.close();					
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					if(log.isDebugEnabled()){
						log.debug("loadProductEntries(): Error closing InputStreamReader innyReader");
					}
				}
			}
		}
		if(log.isDebugEnabled()){
			log.debug("loadProductEntries() Exit: this.prodEntries.size()="+this.prodEntries.size());
		}
	}
}
