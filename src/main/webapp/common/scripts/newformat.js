//Landing page code
function LandingMouseDown(whichBut)
{//whichBut can be a type, a format(lang), or an action (copy,expand)
  var productpanel = document.getElementById("productpanel");
  var panels=productpanel.children;
  var choices=panels.length;
  for (var panel=0;panel<choices;panel++ ){ //change which panel is displayed
    if (panels[panel].id===whichBut){
      panels[panel].setAttribute('style','display:block');
    }  else {
      panels[panel].setAttribute('style','display:none');
    }
  }

}


//example block code
//var exampleType="cURL";//defaults
var exampleType;
var exampleLang="raxJSON";
function ExMouseDown(whichBut, whichEx, exampleTypeDefault, buttonType)
{//whichBut can be a type, a format(lang), or an action (copy,expand)
    if( exampleType == null ){
	exampleType = exampleTypeDefault;
    }
  var example = document.getElementById(whichEx);
  var divs = example.getElementsByTagName('div');
  var len = divs.length;
  var contentblock, formatgroup,copyexpand;
  for (var i=0;i<len;i++){
  if( divs[i].className === 'excontent'){
    contentblock=divs[i];
  }else if (divs[i].className=="formatgroup"){
    formatgroup=divs[i];
  }else if (divs[i].className=="copyexpand"){
    copyexpand=divs[i];
    }
  }
  if (buttonType == 'tab'){ 
    exampleType=whichBut; //this is a tab button -- save which is now selected
  }else if (buttonType == 'format'){ //this is a  format button
    exampleLang=whichBut;
  }else { //this must be an action -- handle it
   if(whichBut==='expand' ){//expand action
     if(copyexpand.children[1].innerHTML==='expand'){
      contentblock.setAttribute('style','height:auto;width:auto;overflow:scroll');
      copyexpand.children[1].innerHTML='collapse';
   }else{ //shrink action
      contentblock.setAttribute('style','height:100px,width:300px');
      copyexpand.children[1].innerHTML='expand';
      } 
      return 0;
   }
  }
  var panelclass=exampleType + " " + exampleLang;
  var panels=contentblock.children;
  var choices=panels.length;
  for (var panel=0;panel<choices;panel++ ){ //change which panel is displayed
    if (panels[panel].className===panelclass){
      panels[panel].setAttribute('style','display:block');
    }  else {
      panels[panel].setAttribute('style','display:none');
    }
  }
  var typebuttons=example.getElementsByTagName('td');
  var formatbuttons=formatgroup.getElementsByTagName('span');
  SetSelectedButtons(typebuttons, formatbuttons, exampleType);
}
function SetSelectedButtons(typebuttons, formatbuttons, exampleType)
  { 
  for (var j=0;j<3;j++){
      if (typebuttons[j].innerHTML.replace(" ","")==exampleType.replace(/\s+/g, '')){ //This is the current one
    typebuttons[j].setAttribute('style','background-color:#DDD; font-weight:bold;color:#333;');
  }else{
    typebuttons[j].setAttribute('style','background-color:#F3F3F3;font-weight:normal;color:#999;');
  }
  }
  for (var i=0;i<2;i++){
      if ('rax'+formatbuttons[i].innerHTML===exampleLang){
    formatbuttons[i].setAttribute('style','font-weight:bold');
  }else{
    formatbuttons[i].setAttribute('style','font-weight:lighter');
  }
  }
}
//End example block code
