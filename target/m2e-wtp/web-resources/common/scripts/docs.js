$(document).ready(function() {  


	// When you click on a link to an anchor, scroll down 
	// 155 px to cope with the fact that the banner
	// hides the top 95px or so of the page.
	// This code deals with the problem when 
	// you click on a link within a page.
	$('a[href*=#]').click(function() {
		if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'')
		    && location.hostname == this.hostname) {
		    var $target = $(this.hash);
		    $target = $target.length && $target
			|| $('[name=' + this.hash.slice(1) +']');
		if (!(this.hash == "#searchDiv" || this.hash == "#treeDiv" || this.hash == "") && $target.length) {
			var targetOffset = $target.offset().top - 155;
			$('html,body')
			    .animate({scrollTop: targetOffset}, 210);
			return false;
		    }
		}
	    });


    // When you click on a link to an anchor, scroll down 
    // 155 px to cope with the fact that the banner
    // hides the top 95px or so of the page.
    // This code deals with the problem when 
    // you click on a link from another page. 
    var hash = window.location.hash;
    if(hash){ 
	var targetOffset = $(hash).offset().top - 155;
	$('html,body').animate({scrollTop: targetOffset}, 210);
	return false;
    }


});



function getContent(id){
	//alert("id="+id);
	 $.getJSON("IndexServlet?action="+id,{"action" : id},function(data){
		 displayTypesDetails(data,id);
	 });

	 //alert("Afterwards");
}

function getCookie(cookieName){
    var results = document.cookie.match ( '(^|;) ?' + cookieName + '=([^;]*)(;|$)' );
    if (results){
        return (unescape(results[2]));           
    }
    else{
        return null;            
    }
}

function deleteCookie( cookieName ){
    //current date & time
    var cookiedate = new Date();  
    cookiedate.setTime ( cookiedate.getTime() - 1 );
    document.cookie = cookieName += "=; expires=" + cookiedate.toGMTString();
}
    
function setCookie(cookieName, cookieVal, daysExpire){
    //Today's date
    var expireDate=new Date();
    expireDate.setDate(expireDate.getDate()+daysExpire);
    document.cookie=cookieName+"="+cookieVal+"; expires="+expireDate.toGMTString();
}

function getFooter(data, param){

	var retStr="<div id=\"footer-wrap\">\n";	
	retStr+="<div id=\"fatfooter-wrap\">\n";
	retStr+="<div class=\"container_12\">\n";
	
	var foot=data.footer;
	var colsnoimg=foot.noimgcols;

	var count=0;
	$.each(colsnoimg, function(index, acolnoimg){
		retStr+="<div class='fatfooter_1 push_"+count+"'>\n";
		retStr+="<div><a href=\""+acolnoimg.headerurl+"\">";
		retStr+=acolnoimg.headerdisplay;
		retStr+="</a></div>\n";
		retStr+="<ul>";
		
		var thelinks=acolnoimg.links;
		
		if(null!=thelinks && thelinks!=undefined && thelinks.length>0){
			$.each(thelinks, function(index1, alink){
				retStr+="<li><a href=\""+alink.linkurl+"\" class=\"footer\">"+alink.linkdisplay+"</a></li>\n"
			});
			retStr+="<div class=\"clear\"></div>\n";
		}
		
		retStr+="</ul>"
		retStr+="</div>\n";
		
		++count;
	});
	
	retStr+="<div class=\"grid_divider_vertical push_3\" style=\"height: 159px; \">\n";
	retStr+="<div class=\"top part\" style=\"height: 79.5px;\"></div>\n";
	retStr+="<div class=\"middle part\" style=\"display: none; height: 1px; \"></div>\n";
	retStr+="<div class=\"bottom part\" style=\"height: 79.5px; background-position: -48px -65.5px;\"></div>\n";
	retStr+="<div class=\"top part\"></div><div class=\"middle part\" style=\"height: 1px; \"></div><div class=\"bottom part\"></div>\n";	
	retStr+="</div>\n";
	var colsyesimg1=foot.imgcols;
	
	if(null!=colsyesimg1 && colsyesimg1!=undefined && colsyesimg1.length>0){
	    retStr+="<div class='fatfooter_1 push_"+count+"'>\n";
	    ++count;
	    $.each(colsyesimg1, function(index2, acolyesimg1){
		    retStr+="<div><a href=\""+acolyesimg1.headerurl+"\" rel=\"nofollow\">"+acolyesimg1.headerdisplay+"</a></div>\n";
		    retStr+="<div><a href=\"\"></a></div>\n";
		
		    var thelinks=acolyesimg1.links;
		    if(null!=thelinks && thelinks!=undefined && thelinks.length>0){

		    	$.each(thelinks, function(index3, alink){

		    		retStr+="<div class=\"column_1\">\n";
		    		
		    		var thelinkurl=alink.linkurl;
		    		if(null!=thelinkurl && thelinkurl!=undefined && thelinkurl.length>0){
		    			retStr+="<a href=\"";
		    			retStr+=thelinkurl;
		    			retStr+="\"></a>\n";
		    		}
		    		retStr+="<div class=\"";
		    		retStr+=alink.linkclass;
		    		retStr+="\"></div>\n";
		    		retStr+="<span style=\"position:relative; color:#4F81A6;\">";
		    		retStr+=alink.linkdisplay;
		    		retStr+="</span>\n";
		    		retStr+="</div>\n";
		    		retStr+="<div class=\"column_2\">\n";
		    		retStr+=alink.linknumber;
		    		retStr+="</div>\n";
		    		retStr+="<div class=\"clear\"></div>";
		    	});
		    }
	    });
	    
	    var colsyesimg2=foot.imgcols2;
	    
	    if(null!=colsyesimg2 && colsyesimg2!=undefined && colsyesimg2.length>0){

	    	$.each(colsyesimg2, function(index4,acolimg2){
	    		retStr+="<div>\n";
	    		if(param==null||param==undefined){
	    		    retStr+="<br><br><br><br><br><br>\n";
	    		}
	    		else if(param=="3"){
	    			retStr+="<br>\n";
	    		}
	    		retStr+="<a href=\"";
	    		retStr+=acolimg2.headerurl;
	    		retStr+="\" rel=\"nofollow\">";
	    		retStr+=acolimg2.headerdisplay;
	    		retStr+="</a>\n";
	    		retStr+="</div>\n";
	    		
	    		retStr+="<div>\n";
	    		retStr+="<a href></a>\n";
	    		retStr+="</div>\n";
	    		
	    		var theimg2links=acolimg2.links;
	    		if(null!=theimg2links && theimg2links!=undefined && theimg2links.length>0){
	    			$.each(theimg2links, function(anIndex,animg2link){
	    				retStr+="<div class=\"";
	    				retStr+=animg2link.linkclass;
	    				retStr+="\" ";
	    				retStr+="onclick=\"";
	    				retStr+="getURLNewWindow('";
	    				retStr+=animg2link.linkurl;
	    				retStr+="')";
	    				retStr+="\">\n";
	    				retStr+="</div>\n";
	    			});
	    			//retStr+="<div class=\"clear\"></div>\n";
	    		}    		
	    	});
	    }    
	    retStr+="</div>"
	    //retStr+="<div class=\"clear\"></div>\n";	
    }
	retStr+="</div>\n";
	retStr+="<br><br><br><br><br><br><br><br><br><br>\n";
	retStr+="<div id=\"rackerpowered\"></div>\n";
	retStr+="</div>\n";

	retStr+="</div>\n";
	
	var thebottom=foot.bottom;
	var thecounter=0;
	
	if(null!=thebottom && thebottom!=undefined && thebottom.length>0){
		retStr+="<div id=\"basement-wrap\" class=\"basement-wrap-nosnap\"> \n";
		retStr+="<span class=\"legalnoticelink\">&#169;<a class=\"legalnoticelink\" href=\"../legalnotice.html\">2012 Rackspace, US Inc.</a></span>\n";
		if(thebottom.length>0){
			retStr+=" | ";
		}
		$.each(thebottom, function(index9, abottom){
			retStr+="<span class=\"footerLink\">\n";
			retStr+="<a href=\"";
			retStr+=abottom.url;
			retStr+="\"";
			retStr+=" class=\"basement\">";
			retStr+=abottom.display;
			retStr+="</a>";
			retStr+="</span>\n";
			if(thecounter!=((thebottom.length)-1)){
				retStr+="|";
			}
			++thecounter;
		});
		retStr+="</div>\n";
	}
	
	$("#rax-footer").html(retStr);
}

function getHeader(data){
	
	var retStr="<div id=\"ceiling-wrap\">\n";

	retStr+="<div class=\"container_12\" id=\"pocket-container\">\n";
	retStr+="<div id=\"pocket-wrap\">\n";
	
	retStr+="<div id=\"pocket-salesnumber\" class=\"pocketitem\">\n";
	retStr+="<div class=\"icon\"></div>\n";
	retStr+="<div class=\"content\"><span class=\"pocketitem-gray\">Sales:</span> 1-800-961-2888</div>\n";
	retStr+="</div>\n";	

	retStr+="<div id=\"pocket-supportnumber\" class=\"pocketitem\"><a href=\"http://www.rackspace.com/support/\">\n";
	retStr+="<div class=\"icon\"></div></a>\n";
	retStr+="<div class=\"content\"><a href=\"http://www.rackspace.com/support/\"><span class=\"pocketitem-gray\">Support:</span></a> 1-800-961-4454</div>\n";
	
	retStr+="</div>\n";
	retStr+="<div class=\"clear\"></div>\n";	
	retStr+="</div>\n";		
	retStr+="</div>\n";	
	
	retStr+="<div id=\"navigation-wrap\">\n";
	retStr+="<div id=\"logo-wrap\" onclick=\"getURL('/new/index.jsp')\"></div>\n"
	retStr+="<div id=\"menu-wrap\">\n"
	
	var types=data.header;

	$.each(types, function(index, atype){
		//Get the type title as the header
		retStr+="<div class=\"menuoption\" id=\"menuoption-"+atype.headerid+"\">\n";		
		retStr+="<a href=\""+atype.headerurl+"\" class=\"menuoption menuoption-off\">"+atype.headername+"</a>\n";
		
		retStr+="<div class=\"arrow\"></div>\n";
		retStr+="<div class=\"menu\" id=\"menu-"+atype.headerid+"\" style=\"left: -215px;\">\n";
		retStr+="<div class=\"container_12\">\n";
		retStr+="<div class=\"navigation_1\">\n";
		retStr+="<div class=\"menu-title\">\n";	
		retStr+=atype.headername;
		retStr+="</div><br><br>\n";	
		retStr+="</div>\n";
		
		var products=atype.prods;
		var count=1;
		//var tempStr="<table>\n";
        var firstColStr =getACol();
        var secondColStr =getACol();
        var thirdColStr =getACol();
		//Iterate through all the header information to build the submenu
		$.each(products, function(index1, aProd){

            //Iterate through each product entry
            var pentries=aProd.prodentries;
            
            if(null!=pentries && pentries!=undefined &&pentries.length>0){
            	if((count==1)||(((count-1)%3)==0)){
            		firstColStr+=addToCol(pentries,aProd);
            	}
            	else if((count==2)||(((count-2)%3)==0)){
            		secondColStr+=addToCol(pentries,aProd);
            	}
            	else if((count%3)==0){
            		thirdColStr+=addToCol(pentries,aProd);
            	}            		
		    }

		    ++count;
		});
        firstColStr+="</ul>\n</div>\n";	
        secondColStr+="</ul>\n</div>\n";	
        thirdColStr+="</ul>\n</div>\n";	

		retStr+=firstColStr;
		retStr+=secondColStr;
		retStr+=thirdColStr;
		
		retStr+="</div>\n";		
		retStr+="</div>\n";				
		
		retStr+="</div>\n";
	});
	
	retStr+="</div>\n"		

	    	
	retStr+="<div id=\"search-wrap\">\n";
	retStr+="<script language=\"javascript\">\n";
	retStr+="google.load('search', '1'); function init(){google.search.CustomSearchControl.attachAutoCompletion('002915819921781682921:7fb-azesesk',document.getElementById('search'),'two-page-form');}\n";
	retStr+="function submitSiteSearch(){window.location = '/new/searchresults.jsp?q='+ encodeURIComponent(document.getElementById('search').value + ' site:docs.rackspace.com -filetype:pdf -inurl:docs.rackspace.com/test'); return false;}\n";
	retStr+="google.setOnLoadCallback(init);\n";
	retStr+="</script>\n";
	retStr+="<script src=\"http://www.google.com/uds/?file=search&amp;v=1\" type=\"text/javascript\"></script>\n";
	retStr+="<link href=\"http://www.google.com/uds/api/search/1.0/289eef681d59caef05fd602d47c986ce/default+en.css\" type=\"text/css\" rel=\"stylesheet\">\n";
	retStr+="<script src=\"http://www.google.com/uds/api/search/1.0/289eef681d59caef05fd602d47c986ce/default+en.I.js\" type=\"text/javascript\"></script>\n";
	retStr+="<form id=\"sitesearch\" name=\"sitesearch\" action=\"/new/searchresults.jsp\" onsubmit=\"return submitSiteSearch()\">\n";
	retStr+="<input type=\"text\" name=\"q\" id=\"search\" value=\"Search\" onclick=\"cleanSlate('search')\" autocomplete=\"off\" style=\"color: rgb(204, 204, 204); outline: none; \" dir=\"ltr\" spellcheck=\"false\">\n";
	retStr+="<div id=\"search-button\" class=\"inactive\" onclick=\"submitForm('sitesearch')\"></div>\n";
	retStr+="</form>\n";
	retStr+="<script language=\"javascript\">\n";
	retStr+="$('#search').focus(function(){$('#search-button').addClass('active').removeClass('inactive');}).focusout(function(){$('#search-button').addClass('inactive').removeClass('active');});</script>\n";
	retStr+="</div>\n";    	
    	
	retStr+="</div>\n";
	retStr+="</div>\n";	
	retStr+="<script type=\"text/javascript\">\n";
	retStr+="$(document).ready(function () { globalNav(); globalCeiling(); basementSnap(); printDividers(); $(window).bind('scroll resize', function () { $(this).unbind('scroll'); basementSnap(); }); subnavContactForm(); });\n";	
	retStr+="</script>\n";	

	$("#raxdocs-header").html(retStr);
}

function getACol(){
	var tempStr="<div class=\"navigation_2\">\n";
	tempStr+="<ul class=\"navigation\">\n";
	return tempStr;
}
function addToCol(pentries, aProd){

    //submenu header
    var tempStr="<li class=\"heading-link\">\n";
    tempStr+="<a href=\""+aProd.produrl+"\">"+aProd.producttitle+"</a>\n"
    tempStr+="<div class=\"arrow\"></div>\n";
    tempStr+="<div class=\"clear\"></div>\n";


    if(null!=pentries && pentries!=undefined){
        $.each(pentries, function(index2, pentry){
    	    //if(pentries.length==1){
	            //tempStr+="<li class=\"singlelist\">\n";            	    
	        //}
	        //else{
	    	    tempStr+="<li class=\"\">\n";
	        //}
	        tempStr+="<a href=\""+pentry.typeurl+"\" >";
	        tempStr+=pentry.typedisplayname+"</a>\n";
	        tempStr+="<div class=\"clear\"></div>\n";
	        tempStr+="</li>\n";
        });
    }
    return tempStr;
}

function displayTypesDetails(data,id){
	var product=data.product;

	//Set the prod cookie to the selected product
	var theProd=getCookie('prod');
	if(null!=theProd){
		deleteCookie('prod');
	}
	setCookie('prod',id,30);
	
	//the product var will have a value only if the clicked on product has configured types
	if(null!=product && product!=undefined){
	    var prodTitle=product.title;
	    var prodDescription=product.description;

	    var retStr="<div class=\"container_12\">\n";
        retStr+="<img id=\"landingtop\" src=\"imgs/landingtop.png\">\n";
	retStr+="<p class=\"mvp\">Welcome to our documentation beta site! The look and feel of this site is a work-in-progress. Suggestions are welcome. Use the comment features in each doc to provide feedback. To return to the previous site format, go to <a href=\"http://docs.rackspace.com\">http://docs.rackspace.com</a>.</p>\n"
        retStr+="<div id=\"productblock\"><table><tr>\n";

        retStr+="<td class=\"productbuttons\">\n";
        $.each(data.prods, function(index0,aprod){	
	        if(id===aprod.prodid){
						retStr+="<div class=\"productbutton\" id=\""+aprod.prodid+"\" style=\"background-image:url('./common/images/bluearrow.png')\" onclick=\"getContent('"+aprod.prodid+"')\">\n";
					}else{
						retStr+="<div class=\"productbutton\" id=\""+aprod.prodid+"\" style=\"\" onclick=\"getContent('"+aprod.prodid+"')\">\n";
					}
	        retStr+="<img src=\""+aprod.prodlogourl+"\"\>\n";
	        retStr+="</div>\n";
	    }); 
        retStr+="</td>\n";
	   
        retStr+="<td id=\"productpanel\">\n";
        
	    retStr+="<div class=\"productname\">"+prodTitle+" </div>\n";
	    retStr+="<div class=\"productsummary\">\n";
	    retStr+=prodDescription;
	    retStr+="</div><hr>\n";

        var count=1;
        var theHeaderTypes=product.headertypes;

        var theStyle="style=\"position:relative\"";	
        retStr+="<div class=\"linktable\">\n";

        var tempStr="<table>\n";
                
        $.each(theHeaderTypes, function(index, header){
        	if(count==1||(count%4==0)){
        		tempStr+="<tr>\n";
        	}
            if(count!=1){
                theStyle="";
            }
	        tempStr+="<td>\n";
	        tempStr+="<div class=\"tablecolumn \" "+theStyle+">\n";
	        tempStr+="<div class=\"columntitle\">\n";
	        tempStr+=header.headername;
		    //end of columntitle div
		    tempStr+="</div>\n";
		    
		    var prodtypes=header.types;

	        retStr+="<ul class=\"linklist\">\n"
		    $.each(prodtypes, function(index11,aType){
		    	tempStr+="<li><a href=\""+aType.url+"\">"+aType.displayname+"</a></li>\n";			
		    });	
		    tempStr+="</ul>\n";		    
		    
		    //end of tablecolumn
		    tempStr+="</div>\n";
		    tempStr+="</td>\n";
        	if(((count+1)%4)==0){
        		tempStr+="</tr>\n";      		
        	}
            ++count;
	    });	
		if(((count+1)%4)!=0){
			tempStr+="</tr>\n";
		}        

        tempStr+="</table>\n";
        retStr+=tempStr;        

        //end of linktable div
        retStr+="</div>\n";
    
        retStr+="</td>\n";
        //end of productblock div 
        retStr+="</tr></table></div>\n";
        //end of container_12 div
        retStr+="</div>"
        $("#content-home-wrap").html(retStr);
	} 
}
