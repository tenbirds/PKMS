$(document).ready(function(){

	// Gnb
	$(".gnb>li").hover(function(){
		if($(this).attr('class') != "active"){
			$(this).parents(".gnb").find("div.sub_menu").stop().slideUp("normal");
			$(this).find("div.sub_menu").slideDown("normal");
		}
	},function(){

		if($(this).attr('class') != "active"){
			$(this).find("div.sub_menu").stop().slideUp("normal");
			var nowSub_Menu = $('.active').attr('id');
			$('#'+nowSub_Menu).find("div.sub_menu").slideDown("normal");
		}
				
		if($(this).attr('class') == "active"){
			$(this).find("div.sub_menu").slideDown("fast");
		}
				
	});

	// Accodian
	$(".accodian_wrap .accodian_title").click(function(){
		$(".accodian_wrap li").removeClass("active");
		$(".accodian_wrap .accodian_cont").slideUp();
		if(!$(this).next().is(":visible"))
		{
			$(this).next().slideDown();
			$(this).parent().addClass("active");
		}
	});	

});

// 팝업
function popOpen (popup) {
	var popup = $(popup);
	popup.show();
}
function popClose (_this) {
	var _this = $(_this);
	_this.parents(".popup_wrap").hide();
}

// 탭
function tabFunc (_this, _thisId) {
	var _this= $(_this);
	var _thisId = $(_thisId);
	_this.parents(".tab_header").find(".active").removeClass("active");
	_this.addClass("active");
	_this.parents(".tab_wrap").find(".tab_cont").hide();
	$(_thisId).show();
}


