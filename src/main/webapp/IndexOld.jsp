<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css" href="common/css/jquery.qtip.css"/>
<link rel="stylesheet" type="text/css" href="common/css/rackspace-header1.css"/>
<link rel="stylesheet" type="text/css" href="common/css/landing.css"/>

<script type="text/javascript" src="common/scripts/docs.js" ></script>
<script type="text/javascript" src="common/scripts/rackspace-header2.js"><!----></script>
<script type="text/javascript" src="common/scripts/rackspace-header3.js"><!----></script>

<!-- 
<link rel="stylesheet" type="text/css" href="common/css/newformat.css"/>
<link rel="stylesheet" type="text/css" href="common/css/style-new.css"/>
<link rel="stylesheet" type="text/css" href="http://rackspace.com/min/?f=css/managed.rackspace.css"/>
<link rel="stylesheet" type="text/css" href="common/css/custom.css"/>
<link rel="stylesheet" type="text/css" href="common/css/jquery-ui-1.8.2.custom.css"/>
<link rel="stylesheet" type="text/css" href="common/css/jquery.treeview.css"/>
<link rel="stylesheet" type="text/css" href="common/css/rackspace-header1.css"/>
 -->

<script>
$(function(){
	//check to see if the prod cookie is set, if so we need to select the proper product
	var prodId=getCookie("prod");
	if(null==prodId){
		//If no prod cookie was set, select product with the prodtId 1
		prodId="1";
		//Add cookie for 30 days
		setCookie("prod",prodId,30);
	}
	 $.getJSON("IndexServlet?action="+prodId,{"action" : prodId},function(data){
		 displayTypesDetails(data,prodId);
	 });
});
</script>

 
<script>
$(function(){
	 $.getJSON("IndexServlet?headerfooter=1",{"headerfooter" : "1"},function(data){
		 getHeader(data);
	 });
});
</script>

<script>
$(function(){
	 $.getJSON("IndexServlet?headerfooter=2",{"headerfooter" : "2"},function(data){
		 getFooter(data);
	 });
});
</script> 


<title>Index War</title>
</head>
<body>


<div id="page-wrap"> 
<div id="raxdocs-header">

</div>


<div id="content-home-wrap">

</div>

<div id="rax-footer">


</div>
</div>
</body>
</html>