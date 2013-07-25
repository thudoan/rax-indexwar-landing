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
<style>
#content {
	width: 63em;
	margin: 10em;
	margin-left: auto;
	margin-right: auto;
}
.gsc-search-box {
    display: none;
}
</style>

<script type="text/javascript" src="common/scripts/docs.js" ><!----></script>
<script type="text/javascript" src="common/scripts/rackspace-header2.js"><!----></script>
<script type="text/javascript" src="common/scripts/rackspace-header3.js"><!----></script>

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

<title>Hack the Rack! Search Results</title>
<div id="raxdocs-header">

</div>


<script src="//www.google.com/jsapi" type="text/javascript"></script>
</head>

<body>
	<div id="container">
		<div id="header">
			<a href="#" onclick="_gaq.push(['_trackEvent', 'Header', 'logo', 'click', 1]);"><img src="img/logo.png" alt="Rackspace Hosting"></a>
			<div class="supportphone">Support: 1-877-934-0407</div>
			<div class="supportphone">Sales: 1-877-934-0409</div>
			<div class="clear"></div>
		</div>
		<div id="content">
			<div id="title"><a href="http://docs.rackspace.com">
				<h1>Rackspace Cloud</h1>
				<h2>API Documentation - Search Results</h2> </a>
				<div id="googlesearch">
				<span id="search-wrap">    
					<script language="javascript">				  
						google.load('search', '1');				
						function init() {
						google.search.CustomSearchControl.attachAutoCompletion('002915819921781682921:7fb-azesesk',document.getElementById('search'),'two-page-form');
						}
						function submitSiteSearch() {
						window.location = 'http://docs.rackspace.com/api/searchresults.html?q='+ encodeURIComponent(document.getElementById('search').value) + ' site:docs.rackspace.com -filetype:pdf -inurl:docs.rackspace.com/test';
						return false;
						}      
						google.setOnLoadCallback(init);      
					</script>
					
					<form id="sitesearch" name="sitesearch" action="/searchresults/" onsubmit="return submitSiteSearch()">  
					
					<input type="text" name="q"
						id="search"
						placeholder="Search docs.rackspace.com..." autocomplete="off" size="50" /><!--  value="Search" onclick="cleanSlate('search')" -->
						<div id="search-button" class="inactive" onclick="submitForm('sitesearch')"></div>      
					</form>
					
       </span>	

				</div>
			</div>
	<div id="cse" style="width: 1000px;">Loading</div>
<script type="text/javascript"> 
  function parseQueryFromUrl () {
    var queryParamName = "q";
    var search = window.location.search.substr(1);
    var parts = search.split('&');
    for (var i = 0; i < parts.length; i++) {
      var keyvaluepair = parts[i].split('=');
      if (decodeURIComponent(keyvaluepair[0]) == queryParamName) {
        return decodeURIComponent(keyvaluepair[1].replace(/\+/g, ' '));
      }
    }
    return '';
  }

  google.load('search', '1', {language : 'en'});
  google.setOnLoadCallback(function() {
    var googleSearchFrameWidth = 2000;
    var customSearchControl = new google.search.CustomSearchControl('002915819921781682921:7fb-azesesk');
    customSearchControl.setResultSetSize(google.search.Search.FILTERED_CSE_RESULTSET);
    customSearchControl.draw('cse');
    var queryFromUrl = parseQueryFromUrl();
    if (queryFromUrl) {
      customSearchControl.execute(queryFromUrl);
    }
  }, true);
</script>
</div id="container>
<link rel="stylesheet" href="//www.google.com/cse/style/look/default.css" type="text/css" />
<div id="rax-footer">

<!-- end of rax-foter div --> 
</div>

</body>
</html>
