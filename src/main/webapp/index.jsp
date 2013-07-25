<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css" href="common/css/jquery.qtip.css"/>

<link rel="stylesheet" type="text/css" href="common/css/rackspace-header.css"/>
<!--  
<link rel="stylesheet" type="text/css" href="common/css/newformat.css"/>
<link rel="stylesheet" type="text/css" href="common/css/style-new.css"/>
<link rel="stylesheet" type="text/css" href="http://rackspace.com/min/?f=css/managed.rackspace.css"/>
<link rel="stylesheet" type="text/css" href="common/css/custom.css"/>
<link rel="stylesheet" type="text/css" href="common/css/jquery-ui-1.8.2.custom.css"/>
<link rel="stylesheet" type="text/css" href="common/css/jquery.treeview.css"/>
-->

<link rel="stylesheet" type="text/css" href="common/css/landing.css"/>

<script type="text/javascript" src="common/scripts/jquery-1.4.3.min.js" ><!----></script>
<script type="text/javascript" src="common/scripts/docs.js" ><!----></script>
<script type="text/javascript" src="common/scripts/rackspace-header2.js"><!----></script>
<script type="text/javascript" src="common/scripts/rackspace-header3.js"><!----></script>

<script type="text/javascript">
$(function(){
	var prodId=getCookie("prod");
	if(null==prodId){
		prodId="1";
		//Add cookie for 30 days
		setCookie("prod",prodId,30);
	}
	var firstArg="IndexServlet?action="+prodId;
	 $.getJSON(firstArg,{"action" : prodId},function(data){
		 displayTypesDetails(data,prodId);
	 });
});
</script>

<script type="text/javascript">
$(function(){
	 $.getJSON("IndexServlet?headerfooter=1",{"headerfooter" : "1"},function(data){
		 getHeader(data);
	 });
});
</script>

<script type="text/javascript">
$(function(){
	 $.getJSON("IndexServlet?headerfooter=2",{"headerfooter" : "2"},function(data){
		 getFooter(data);
	 });
});
</script> 

<title>Hack the Rack! Rackspace API Documentation</title>
</head>
<body>

<div id="raxdocs-header">

</div>

<div id="content-home-wrap">

<!-- end of content-home-wrap div -->
</div>

<div class="pagebottom">
    
    <!-- <center> -->
    <!-- <table> -->
    <!-- <tr> -->
    <!-- <td> -->
    <!-- <img src="imgs/landingbottom.png" alt="Not sure where to start?"> -->
    <!-- </td> -->
    <!-- </tr> -->
    <!-- <tr> -->
    <!-- <td> -->
    <!-- <table class="partstable"> -->
    <!--     <tr> -->
    <!--         <th>API Console</th> -->
    <!--         <th>Quickstart Guides</th> -->
    <!--         <th>Knowledge Center</th> -->
    <!--     </tr> -->
    <!--     <tr> -->
    <!--         <td>Explore our product APIs through an interactive console.</td> -->
    <!--         <td>The best place to get started with common tasks.</td> -->
    <!--         <td>Questions?  We've got answers.</td> -->
    <!--     </tr> -->
    <!-- </table> -->
    <!-- </td> -->
    <!-- </tr> -->
    <!-- </table> -->
    <!-- </center> -->
<!-- end of pagebottom div -->
</div>

<div id="rax-footer">

<!-- end of rax-foter div --> 
</div>
</body>
</html>
