/*
 * jQuery JavaScript Library v1.5.2
 * http://jquery.com/
 *
 * Copyright 2011, John Resig
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * Includes Sizzle.js
 * http://sizzlejs.com/
 * Copyright 2011, The Dojo Foundation
 * Released under the MIT, BSD, and GPL Licenses.
 *
 * Date: Thu Mar 31 15:28:23 2011 -0400
 */
function globalCeiling()
{
	$('.pocketitemButton').hover(function(){
		    $(this).addClass('pocketitemButtonHover').removeClass('pocketitemButton');
		    $(this).find('ul').show();
		},
		function(){
			$(this).removeClass('pocketitemButtonHover').addClass('pocketitemButton');
		    $(this).find('ul').hide();
		});
}

function globalNav(){
	var timeout=50;
	var interval=50;
	var rackspaceMenu={over:showMenu,timeout:timeout,interval:interval,out:hideMenu};
	$('div.menuoption').hoverIntent(rackspaceMenu);
	function showMenu(){
		var menu=$(this).attr("id").replace(/menuoption/g,"menu");
		$(this).find('a.menuoption').addClass('menuoption-on').removeClass('menuoption-off');$('#'+menu).show();
	}
    function hideMenu(){
	var menu=$(this).attr("id").replace(/menuoption/g,"menu");
	$(this).find('a.menuoption').addClass('menuoption-off').removeClass('menuoption-on');$('#'+menu).hide();
	}
    $('.menuoption').find('a.menuoption').each(function(i,menuoptions){
    	                                      $(this).append('<div class="arrow"></div>');
                                              });
}
function basementSnap(){
	if($('body').height()<$(window).height()){
		$('#basement-wrap').addClass('basement-wrap-snap').removeClass('basement-wrap-nosnap');
	}
	else{
		$('#basement-wrap').addClass('basement-wrap-nosnap').removeClass('basement-wrap-snap');
	}
}
/*
$carouselTimer=0;$carouselStageWidth=1580;$carouselInterval=7;$carouselSpeed=1;function initCarousel(){$('.stage').each(function(){$carouselStageId=$(this).attr("id").replace(/stage-/g,"");$carouselStageClass=$(this).attr("class").replace(/stage /g,"").replace(/ active/g,"");$(this).css({'left':($carouselStageWidth*($carouselStageId-1))+'px'});$('#control-wrap .controls').append('<div id="control-'+$carouselStageId+'" class="control"></div>');$('#control-wrap .controls div').first().addClass('active');if($(this).hasClass("gartner"))
$('#headline-wrap .headlines').append('<div id="headline-'+$carouselStageId+'" class="headline '+$carouselStageClass+'" onclick="getURL(\''+$(this).attr("url")+'\')" style="cursor:pointer"></div>');else if($(this).hasClass("enterprise"))
$('#headline-wrap .headlines').append('<div id="headline-'+$carouselStageId+'" class="headline '+$carouselStageClass+'" onclick="getURL(\''+$(this).attr("url")+'\')" style="cursor:pointer"></div>');else
$('#headline-wrap .headlines').append('<div id="headline-'+$carouselStageId+'" class="headline '+$carouselStageClass+'"><div class="rsButton" url=\''+$(this).attr("url")+'\' target=\''+$(this).attr("target")+'\'>Learn More</div></div>');$('#headline-wrap .headlines div').first().addClass('active');});$(document).ready(function(){enableCarouselControls();$carouselTimer=setInterval("swapCarousel()",$carouselInterval*1000);});}
function controlHandler(){disableCarouselControls();$nextCarousel=$(this).attr("id").replace(/control-/g,"");$('#control-wrap .controls .active').each(function(){$activeCarousel=$(this).attr("id").replace(/control-/g,"");});swapCarousel($nextCarousel,$activeCarousel);}
function swapCarousel($nextCarousel,$activeCarousel){disableCarouselControls();if($nextCarousel&&$activeCarousel){clearInterval($carouselTimer);}else{$('#control-wrap .controls .active').each(function(){$activeCarousel=$(this).attr("id").replace(/control-/g,"");});$nextCarousel=parseInt($activeCarousel)+1;if($nextCarousel==$('.stage').length)
clearInterval($carouselTimer);}
if($activeCarousel!=$nextCarousel){swapCarouselControls($nextCarousel,$activeCarousel);swapCarouselStages($nextCarousel,$activeCarousel);swapCarouselHeadlines($nextCarousel,$activeCarousel);}else{enableCarouselControls();}}
function swapCarouselControls($nextCarousel,$activeCarousel){$('#'+getCarouselControlId($nextCarousel)).addClass('active');$('#'+getCarouselControlId($activeCarousel)).removeClass('active');}
function swapCarouselStages($nextCarousel,$activeCarousel){$('.stage').each(function(){$carouselStageId=$(this).attr("id").replace(/stage-/g,"");$carouselLeftPos=($carouselStageWidth*$carouselStageId)-($carouselStageWidth*$nextCarousel);$(this).animate({left:$carouselLeftPos+'px'},$carouselSpeed*1000,'easeInOutCirc');});}
function swapCarouselHeadlines($nextCarousel,$activeCarousel){$headlineSpeed=($carouselSpeed*.5)*1000;$('#headline-'+$activeCarousel).animate({left:'-1200px'},$headlineSpeed);$('#headline-'+$nextCarousel).delay($headlineSpeed).animate({left:'0px'},$headlineSpeed,function(){enableCarouselControls()});}
function enableCarouselControls(){$('.control').bind('click',controlHandler);}
function disableCarouselControls(){$('.control').unbind('click',controlHandler);}
function getCarouselStageId(id){return"stage-"+id;}
function getCarouselControlId(id){return"control-"+id;}
*/
function printDividers(){
	$('.grid_divider_vertical').each(function(i,dividers){
		var containerHeight=$(this).parent().height();
		$(this).append('<div class="top part"></div>');
		$(this).append('<div class="middle part"></div>');
		$(this).append('<div class="bottom part"></div>');
		var dividerTopHeight=$(this).find('.top').height();
		var dividerBottomHeight=$(this).find('.bottom').height();
		$(this).height(containerHeight);
		if(containerHeight<(dividerTopHeight+dividerBottomHeight)){
			$(this).find('.top').height(containerHeight/2);
			$(this).find('.bottom').height(containerHeight/2);
			$(this).find('.middle').hide();
			var dividerBottomPosition=(dividerBottomHeight-(containerHeight/2))*-1;
			$(this).find('.bottom').css('backgroundPosition','-48px '+dividerBottomPosition+'px');
		}
		else{
			var dividerMiddleHeight=containerHeight-(dividerTopHeight+dividerBottomHeight);
			$(this).find('.middle').height(dividerMiddleHeight);
		}
	});
	$('div.menu').hide();$('div.menu').css('left','-215px');
}

function subnavContactForm(){
	$('.subnav.contact a').click(function(e){
		e.preventDefault();
		if($('#subnav-contact-form').is(':hidden')){
			$('#subnav-contact-form').fadeIn('fast');
			cmCreateConversionEventTag("Enterprise Contact Form","1","Open-_-EnterpriseContact-_-Form","0");
		}
		else{
			$('#subnav-contact-form').fadeOut('fast');
			cmCreateConversionEventTag("Enterprise Contact Form","1","Close-_-EnterpriseContact-_-Form","0");
		}
	});
	$(document).click(function(e){
		if(!$(e.target).is('#subnav-contact-form, #subnav-contact-form *, .subnav.contact a')&&$('#subnav-contact-form').is(':visible')){
			$('#subnav-contact-form').fadeOut('fast');cmCreateConversionEventTag("Enterprise Contact Form","1","Close-_-EnterpriseContact-_-Form","0");
		}
	});
	$('#subnav-contact-form form').submit(function(e){
		e.preventDefault();cmCreateConversionEventTag("Enterprise Contact Form","1","Submit-_-EnterpriseContact-_-Form","0");
		$.ajax({type:'POST',url:'/enterprise_hosting/leadgen.php',data:$(this).serialize(),beforeSend:function(){
			$('#subnav-contact-form dl dd.submit').addClass('loading');
		},success:function(data,textStatus,jqXHR){
			switch(data.status){
                case'success':
                    cmCreateConversionEventTag("Enterprise Contact Form","1","Success-_-EnterpriseContact-_-Form","0");break;
                case'error':
                	cmCreateConversionEventTag("Enterprise Contact Form","1","Error-_-EnterpriseContact-_-Form","0");break;
            }
        $('#subnav-contact-form .form').fadeOut(function(){
        	$('#subnav-contact-form .'+data.status).fadeIn();
        });
        },error:function(jqXHR,textStatus,errorThrown){
        	$('#subnav-contact-form .form').fadeOut(function(){
        		$('#subnav-contact-form .error').fadeIn();
        	});
        },complete:function(jqXHR,textStatus){
        	$('#subnav-contact-form dl dd.submit').removeClass('loading');
        },dataType:'json'});
	});
}

$(document).ready(function(){
	globalNav();
	globalCeiling();
	subnavContactForm();
});

