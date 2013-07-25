
function highlightCode2(e){

	var target = e.target;
    if(null==target){
	    target=e.srcElement;
	}
    
	var theParent=target.parentNode;
	var theNode;
	if(null!=theParent){
		
	    while(theParent.className!='exampleblock'){
	    	theParent=theParent.parentNode;
	    }
	    var childrenNodes=theParent.childNodes;
	    var childExcontentNode;
	    for(var i=0;i<childrenNodes.length;++i){
	    	if(childrenNodes[i].className=='excontent'){
	    		childExcontentNode=childrenNodes[i];
	    		break;
	    	}
	    }
	    if(null!=childExcontentNode){
	        var grandChildNodes=childExcontentNode.childNodes;
	        var grandChildNode;
	        for(var i=0;i<grandChildNodes.length;++i){
	        	var aChildNode=grandChildNodes[i];
	        	if(aChildNode.className != null){
	        		grandChildNode=aChildNode;
	        		break;
	        	}       		
	        }
	        
	        var greatGrandChildNodes=grandChildNode.childNodes;
	        var greatGrandChildNode;
	        for(var i=0;i<greatGrandChildNodes.length;++i){
	        	greatGrandChildNode=greatGrandChildNodes[i];
	        	if(greatGrandChildNode.className!=null){	        		
	        		var aCName=greatGrandChildNode.className.trim();
	        		if(aCName==''||((aCName.indexOf('source'))!=-1)){
	        			var greatGreatGrandChildNodes=greatGrandChildNode.childNodes;
	        			var greatGreatGrandChildNode;
	        			for(var x=0;x<greatGreatGrandChildNodes.length;++x){
	        				greatGreatGrandChildNode=greatGreatGrandChildNodes[x];
	        			    if(greatGreatGrandChildNode.className!=null){
	        			    	aCName=greatGreatGrandChildNode.className;
	        		            if((aCName.indexOf('syntaxhighlighter'))!=-1){	        		            	
	        			            theNode=greatGreatGrandChildNode;
	        			            break;
	        		            }
	        		        }
	        		    }
	        			break;
	        		}
	        	}
	        }
	    }
	}
	
	var highlighterDiv = theNode;//document.getElementById(id);//findParentElement2(target, '.syntaxhighlighter'),
	
	//Need to find the container area
	var	container = findContainerElement3(highlighterDiv);//findContainerElement2(highlighterDiv);
	var	textarea = document.createElement('textarea');
	var	highlighter;
	

	
    //var highlightId=getHighlighterId2(highlighterDiv.id);
    
    highlighter = highlighterDiv;//document.getElementById(id);//document.getElementById(highlightId);

	// add source class name
	addClass2(highlighterDiv, 'source');

	// Have to go over each line and grab it's text, can't just do it on the
	// container because Firefox loses all \n where as Webkit doesn't.
	var lines = container.childNodes,
		code = []
		;
	
	for (var i = 0; i < lines.length; i++)
		code.push(lines[i].innerText || lines[i].textContent);
	
	// using \r instead of \r or \r\n makes this work equally well on IE, FF and Webkit
	code = code.join('\r');

    // For Webkit browsers, replace nbsp with a breaking space
    code = code.replace(/\u00a0/g, " ");	


	// inject <textarea/> tag
	textarea.appendChild(document.createTextNode(code));
	container.appendChild(textarea);
	
	// preselect all text
	textarea.focus();
	textarea.select();

	if(textarea.addEventListener){
		textarea.addEventListener('blur', function(){removeHighlight2(textarea, highlighterDiv)}, false);
    }
    else if(textarea.attachEvent){
        contextareatainer.attachEvent('blur', function(){removeHighlight2(textarea, highlighterDiv)}, false);
   }
}


function removeHighlight2(textarea, highlighterDiv){
	if(textarea.parentNode && textarea.parentNode.childNodes){
        textarea.parentNode.removeChild(textarea);
        removeClass2(highlighterDiv, 'source');
	}
}

function findContainerElement4(syntaxDiv){
	var retVal=null;
	if(null!=syntaxDiv){
		var childNodes=syntaxDiv.childNodes;
		for(var i=0;i<childNodes.length;++i){
			var childNode=childNodes[i];
			if(null!=childNode.className){
			    var cName=childNode.className;
			    if((cName.indexOf('syntaxhighlighter'))!=-1){
			    	var grandChildNodes=childNode.childNodes;
			    	var grandChildNode;
			    	for(var x=0;x<grandChildNodes.length;++x){
			    		grandChildNode=grandChildNodes[x];
			    		if(null!=grandChildNode.localName){
			    			var locName=grandChildNode.localName;
				            if(null!=locName && locName=='table'){
				                var codeTdNode=grandChildNode.rows[0].cells[1];
				                var greatGrandChildNodes=codeTdNode.childNodes;
				                var greatGrandChildNode;
				                for(var n=0;n<greatGrandChildNodes.length;++n){
				                	greatGrandChildNode=greatGrandChildNodes[n];
				                	if(null!=greatGrandChildNode.className){
				                		cName=greatGrandChildNode.className;
				                		if(cName=='container'){
				                			retVal=greatGrandChildNode;
				                			break;
				                		}
				                	}
				                }
				                break;
			                } 
				        }
				    }
			        break;
				}			    
			}
		}
	}
	
	return retVal;
}

function findContainerElement3(syntaxDiv){
	var retVal=null;
	if(null!=syntaxDiv){

        if(null!=syntaxDiv.className){
			var cName=syntaxDiv.className;
			if((cName.indexOf('syntaxhighlighter'))!=-1){
			    var grandChildNodes=syntaxDiv.childNodes;
			    var grandChildNode;
			    for(var x=0;x<grandChildNodes.length;++x){
			    	grandChildNode=grandChildNodes[x];
			    	if(null!=grandChildNode.localName){
			    		var locName=grandChildNode.localName;
				        if(null!=locName && locName=='table'){
				        	
				            var codeTdNode;
				            //There is a gutter class
				        	if(grandChildNode.rows[0].cells[1]){
				        		codeTdNode=grandChildNode.rows[0].cells[1];       
				            }
				        	//There is no gutter class
				            else{
				            	codeTdNode=grandChildNode.rows[0].cells[0];
				            }
				            var greatGrandChildNodes=codeTdNode.childNodes;
				            var greatGrandChildNode;
				            for(var n=0;n<greatGrandChildNodes.length;++n){
				            	greatGrandChildNode=greatGrandChildNodes[n];
				                if(null!=greatGrandChildNode.className){
				                	cName=greatGrandChildNode.className;
				                	if(cName=='container'){
				                		retVal=greatGrandChildNode;
				                		break;
				                	}
				                }
				            }
				            break;
			            } 
				    }
				}
			}			    
		}
		
	}
	
	return retVal;
}


function findContainerElement2(syndiv){
    var syntaxdiv = syndiv;//document.getElementById(id);//findParentElement2(target, '.syntaxhighlighter');
    var retVal=null;
    if(null!=syntaxdiv && syntaxdiv!=undefined){

        var theDivs=syntaxdiv.getElementsByTagName('div');
        
        if(null!=theDivs && theDivs!=undefined && theDivs.length>0){
            var index=0;            
            var tmpDiv=null;
            for(index=0;index<theDivs.length;++index){
                tmpDiv=theDivs[index];
                if(tmpDiv.className=='container'){

                   retVal=tmpDiv;
                   break;
                }
            }
        }
        
    }
    return retVal;   
}

/**
 * Looks for a child or parent node which has specified classname.
 * Equivalent to jQuery's $(container).find(".className")
 * @param {Element} target Target element.
 * @param {String} search Class name or node name to look for.
 * @param {Boolean} reverse If set to true, will go up the node tree instead of down.
 * @return {Element} Returns found child or parent element on null.
 */
function findElement2(target, search, reverse /* optional */)
{
	if (target == null)
		return null;
		
	var nodes			= reverse != true ? target.childNodes : [ target.parentNode ],
		propertyToFind	= { '#' : 'id', '.' : 'className' }[search.substr(0, 1)] || 'nodeName',
		expectedValue,
		found
		;

	expectedValue = propertyToFind != 'nodeName'
		? search.substr(1)
		: search.toUpperCase()
		;
		
	// main return of the found node
	if ((target[propertyToFind] || '').indexOf(expectedValue) != -1)
		return target;
	
	for (var i = 0; nodes && i < nodes.length && found == null; i++)
		found = findElement2(nodes[i], search, reverse);
	
	return found;
};

/**
 * Looks for a parent node which has specified classname.
 * This is an alias to <code>findElement2(container, className, true)</code>.
 * @param {Element} target Target element.
 * @param {String} className Class name to look for.
 * @return {Element} Returns found parent element on null.
 */
function findParentElement2(target, className)
{
	return findElement2(target, className, true);
};

/**
 * Generates HTML ID for the highlighter.
 * @param {String} highlighterId Highlighter ID.
 * @return {String} Returns HTML ID.
 */
function getHighlighterId2(id)
{
	var prefix = 'highlighter_';
	return id.indexOf(prefix) == 0 ? id : prefix + id;
};

/**
 * Adds CSS class name to the target DOM element.
 * @param {DOMElement} target Target DOM element.
 * @param {String} className New CSS class to add.
 */
function addClass2(target, className)
{
	if (!hasClass2(target, className))
		target.className += ' ' + className;
};

/**
 * Checks if target DOM elements has specified CSS class.
 * @param {DOMElement} target Target DOM element to check.
 * @param {String} className Name of the CSS class to check for.
 * @return {Boolean} Returns true if class name is present, false otherwise.
 */
function hasClass2(target, className)
{
	return target.className.indexOf(className) != -1;
};

/**
 * Removes CSS class name from the target DOM element.
 * @param {DOMElement} target Target DOM element.
 * @param {String} className CSS class to remove.
 */
function removeClass2(target, className)
{
	target.className = target.className.replace(className, '');
};

