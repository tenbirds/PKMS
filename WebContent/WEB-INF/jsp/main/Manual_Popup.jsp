<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PKMS (패키지 관리 시스템)</title>

<script type="text/javaScript">
function ssetCookie( name, value, expiredays ){
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function closeWin(){
	ssetCookie( "manual_popup", "no" , 1);
	
	self.close();
}

function fn_check_ch(){
	var no;
	var otherNo;
	no = $(':radio[name="check_radio"]:checked').val();
	
	if(no == 1) {
		otherNo = 2;
	} else {
		otherNo = 1;
	}
	
	doDivSH("hide", "flow_tb_" + otherNo, 0);
	doDivSH("show", "flow_tb_" + no, 0);
}
</script>
<style type="text/css">
.vCenter{text-align: center;}
.vCenter img{width: 800px;}
</style>

</head>

<body>
	<form:form commandName="ManualModel" method="post">
		<br/>
		<b style="font-size: 18px; color: red; margin-left: 20px;">
				PKG 검증 FLOW에 '개발검증' 추가예정입니다. OPEN BETA 기간 11월 13일(금)~11월 20일(금)까지 입니다.
		</b>
		<br/>
		<div style="border:2px solid #dfdadc; text-align:center; font-size: 15px; margin-top: 10px; margin-left: 20px; width: 280px; height: 30px; padding-top: 6px; font-family: 맑은고딕; font-weight: bolder; color: grey;">
			<label for="check_radio1">
				<input name="check_radio" id="check_radio1" type="radio" value="1" onclick="fn_check_ch();" checked="checked" style="width: 15px; height: 15px; padding-top: 0px;" />협력업체
			</label>
			&nbsp;
			<label for="check_radio2">
				<input name="check_radio" id="check_radio2" type="radio" value="2" onclick="fn_check_ch();" style="width: 15px; height: 15px; padding-top: 0px;"/>SKT MANAGER
			</label>
			
		</div>
		<br/>
		<div id="flow_tb_1" style="display:block; border:1px solid #dfdfdf; height: 700px; width:950px; margin-left:20px; overflow-y: auto; overflow-x: hidden;">
			<p style="font-size: 25px; font-family: 맑은고딕; text-align: right; margin-right: 10px; margin-top: 15px;">
				Package Management System
				<br/><br/>
				<b style="font-size: 40px;">
					BP PKG 검증 사용설명서
				</b>
				<br/><br/>
				Version 1.0
				<br/><br/>
				<img src="/images/sktelecom_logo.png" style="width: 200px;"/>
				<br/><br/>
			</p>
			
			<p style="margin:10px; font-size: 17px; font-family: 맑은고딕;">
				기존 PKG 검증 요청에 개발 검증 요청 Flow가 추가 되어 개발 검증 절차가 필요한 PKG는 아래의 가이드와 같이 추가 항목 입력이 필요합니다.<br/>
				만약 개발 검증 절차가 필요하지 않은 PKG는 기존 사용 방식과 같이 사용하시면 됩니다.
			<br/>
			<b style="font-size: 15px; color: red; margin-left: 15px;">
				단, 상용검증내역 엑셀 업로드 파일이 변경되었으니 신규 템플릿을 다운로드 받아 사용하시면 됩니다.
			</b>
			</p>
			<b style="margin-left:10px; font-size: 22px; font-family: 맑은고딕;">
				[개발 검증 추가 항목 입력 방법]
			</b>
			<p style="margin-left:50px; font-size: 19px; font-family: 맑은고딕;">
				1. PKG 검증요청
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/bp_manual_1.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				1)	신규 PKG 검증요청 팝업의 “개발기본정보” 탭을 선택합니다.<br/>
				2)	개발 검증요청 컬럼의 “개발/상용 검증요청” 라디오 버튼을 선택 합니다.<br/>
				3)	각 항목을 입력하고 저장 합니다. 단,(*) 부분은 필수 항목 입니다.<br/>
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/bp_manual_2.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				4)	저장이 완료 되면 “상용기본정보”를 작성합니다.
				상용기본정보는 개발기본정보에서 입력한 내용이 그대로 노출되며,
				상용 시스템에 적용 시 필요한 항목들을 추가적으로 작성 합니다.
				필수 입력 항목은 없으며,
				검증 진행 단계에서 수정이 가능합니다.<br/>
				5)	추가 입력한 항목이 있으면 입력 후 저장 버튼을 눌러 다음단계로 진행 합니다.<br/>
				6)	다음은 “공급사검증내역” 입니다.<br/>
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/bp_manual_3.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				7)	공급사에서 자체 검증한 내역과 개발 검증에 필요한 파일들을 저장합니다.
				개발검증과 상용화 검증 필수 입력 항목은 없으며,
				검증 진행 단계에서 추가 등록이 가능합니다.<br/>
				8)	입력이 완료 되면 저장 버튼을 눌러 다음 단계인 “개발검증내역”으로 진행합니다.<br/>
				9)	“개발검증내역”은 PGK 관리를 위하여 정형화된 템플릿에 맞게 작성을 하셔야 합니다.
				템플릿 다운로드를 통해 템플릿을 다운받아 작성예시와 같이 작성을 하시면 됩니다.<br/>
				10)	작성 예시 탭의 빨간색으로 표시된 부분은 필수 항목이고 V 열부터 AA 열까지 작성 가능한 항목을 sheet0 탭에 작성을 합니다.<br/>
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/bp_manual_4.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				11)	엑셀 업로드를 선택하면 파일선택 팝업이 뜨고 작성된 파일을 업로드 합니다.
				이때 검사하기 버튼을 눌러 정상적으로 작성이 되었는지 시스템에서 체크를 합니다.<br/>
				12)	위의 화면과 같이 검사결과 “O” 이면 저장이 가능하고 검사결과가 “X” 이면 검사내용을 참고하여 수정하여 다시 파일 업로드를 하시면 됩니다.
				검증 진행 단계에서 등록된 내용을 기준으로 추가 작성하시면 됩니다.<br/>
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/bp_manual_5.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				13)	엑셀 업로드가 완료되면 위와 같이 시험결과내역이 표시되며, 제목 부분을 클릭하면 입력한 상세 정보가 나타납니다.
				검증 진행 과정의 결과를 추가적으로 상세정보 부분에 직접 입력하시면 됩니다.<br/>
				14)	모든 데이터의 입력이 완료 되면 하단의 “검증요청” 버튼을 눌러 개발 검증을 시작하면 됩니다.<br/>
			</p>
			<br/>
			<p style="font-size: 17px; font-family: 맑은고딕; text-align: center;">
				기능 문의 사항은 'IN-SOFT : 김준희대리(010-2032-2761)' 로 연락 주시면 됩니다.
			</p>
			<br/>
			
		</div>
		<div id="flow_tb_2" style="display:none; border:1px solid #dfdfdf; height: 700px; width:950px; margin-left:20px; overflow-y: auto; overflow-x: hidden;">
			<p style="font-size: 25px; font-family: 맑은고딕; text-align: right; margin-right: 10px; margin-top: 15px;">
				Package Management System
				<br/><br/>
				<b style="font-size: 40px;">
					SKT PKG 검증 사용설명서
				</b>
				<br/><br/>
				Version 1.0
				<br/><br/>
				<img src="/images/sktelecom_logo.png" style="width: 200px;"/>
				<br/><br/>
			</p>
			
			<p style="margin:10px; font-size: 17px; font-family: 맑은고딕;">
				기존 PKG 검증 요청에 개발 검증 요청 Flow가 추가 되어 개발 검증 절차가 필요한 PKG는 아래의 가이드와 같이 추가 항목 입력이 필요합니다.<br/>
				만약 개발 검증 절차가 필요하지 않은 PKG는 기존 사용 방식과 같이 사용하시면 됩니다.
			</p>
			
			<b style="margin-left:10px; font-size: 22px; font-family: 맑은고딕;">
				[PKG 개발검증 사전작업]
			</b>
			
			<p style="margin-left:50px; font-size: 19px; font-family: 맑은고딕;">
				1. 담당자 등록
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_1.png" />
			</div>
			<br/>
			
			<p style="margin:10px; font-size: 17px; font-family: 맑은고딕;">
				PKG 개발검증을 진행하기에 앞서 해당 시스템에 담당자 정보를 입력해야 합니다.
				<br/>
				&nbsp;&nbsp;&nbsp;1) 로그인 후 메인 화면에서 시스템관리를 선택 합니다.
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_2.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				2) 시스템 관리 화면에서 해당하는 시스템의 대분류, 중분류, 소분류 선택 후 해당 시스템 화면으로 이동합니다.
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_3.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				3) 시스템 상세에서 담당자 항목으로 이동 후 개발검증과 개발승인 담당자를 검색 후 등록 합니다.<br/>
				4) 등록 후 오른쪽 위의 수정버튼을 클릭하여 담당자를 저장 합니다.

			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_4.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				5) 담당자를 등록하게 되면 대표 개발담당자를 선택할 수 있습니다. 대표 개발 담당자는 개발승인 담당자중에 지정 가능합니다.<br/>
				6) 이 후 PKG 개발검증을 진행합니다.
			</p>
			
			<br/>
			<b style="margin-left:10px; font-size: 22px; font-family: 맑은고딕;">
				[PKG 개발검증]
			</b>
			
			<br/>
			<br/>
			
			<div class = "vCenter">
				<img src="/images/skt_manual_5.png" />
			</div>
			<br/>
			
			<p style="margin:10px; font-size: 17px; font-family: 맑은고딕;">
				개발검증 진행 단계입니다.<br/>
				개발검증 단계를 추가로 할 때 진행이 가능하며, 검증일정 전의 기본정보 입력단계에서 개발/상용검증을 선택해야 진행할 수 있습니다.
			</p>
			
			<br/>
			<p style="margin-left:50px; font-size: 19px; font-family: 맑은고딕;">
				1. 개발검증접수
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_6.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				1) 로그인 후 메인 화면에서 PKG 검증/일정을 선택 합니다.<br/>
				<b style="font-size: 15px; color: red; margin-left: 15px;">
					☞ PKG 현황 또는 PKG 검증 / 적용일정 내용을 클릭해도 패키지 검증을 하실 수 있습니다.
				</b>
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_7.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				2) 해당 패키지를 선택합니다.
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_8.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				3) 해당 업체에서 PKG 검증 과정에 필요한 로드맵, 개발기본정보, 상용기본정보, 공급사검증내역, 개발검증내역, 상용화 검증내역의 입력이 완료되어 검증요청을 하였다면 위 화면과 같은 화면이 나옵니다.
				<br/>
				<b style="font-size: 15px; color: red; margin-left: 15px;">
					☞ 기본정보에서 개발/상용 검증요청을 선택하셨다면 개발검증접수, 상용 검증요청만 선택하셨다면 상용검증 접수가 나옵니다.
				</b>
				<b style="font-size: 15px; color: red; margin-left: 15px;">
					☞ 상용 검증요청이라면 상용 검증접수부터 확인해 주시기 바랍니다.	
				</b>
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_9.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				4) 검증요청이 되면 개발검증접수 칸에 빨간색으로 표시 됩니다. 이때 개발검증접수를 클릭하시면 개발검증접수를 하실 수 있습니다.<br/>
				5) 검증접수와 반려 중 하나를 선택한 후 검증접수를 선택하셨다면 검증시작일 설정 후 검증 comment 및 PKG 검증장소 요청사항의 항목을 입력합니다. 이때 검증 시작일과 검증 comment 부분은 필수 요소이므로 입력을 꼭 하셔야 합니다. <br/>
				<b style="font-size: 15px; color: red; margin-left: 15px;">
					☞ 반려 선택 시 반려사유를 입력합니다.<br/>
				</b>
				6) 검증접수와 반려 중 선택을 하고 내용을 입력하셨다면 저장버튼을 누릅니다.<br/>
				<b style="font-size: 15px; color: red; margin-left: 15px;"> 
					☞ 반려선택 후 저장 시 해당 PKG 검증은 더 이상 진행하실 수 없습니다.<br/>
				</b>
				
			</p>
			
			<br/>
			<p style="margin-left:50px; font-size: 19px; font-family: 맑은고딕;">
				2. 개발검증완료
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_10.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				1)	오른쪽 메뉴 중 빨간색메뉴인 개발검증완료 단계를 클릭합니다.<br/>
				<b style="font-size: 15px; color: red; margin-left: 15px;">
					☞ 검증 완료 단계를 진행하기 전에 왼쪽 메뉴의 개발검증내역의 시험결과내역 중 개발검증결과(OK 또는 COK)를 선택해 주셔야 합니다.<br/>
				</b>
				2)	각 시험 결과 내역 항목을 확인하시고 개발검증결과 항목에서 OK 또는 COK 를 선택합니다.<br/>
				<b style="font-size: 15px; color: red; margin-left: 15px;">
					☞ 모든 시험 결과 내역 항목에 대해서 개발 검증 결과를 입력하셔야 합니다.<br/>
				</b>
				3)	각 항목 별 입력이 완료 되면 저장 버튼을 눌러 저장을 합니다.<br/>
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_11.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				4)	개발검증완료에서는 * 표시는 필수 입력 항목이므로 양호 / 불량 선택 하시면 됩니다. 그 외의 comment 나 파일업로드는 필수 사항이 아니므로 선택 값만 양호로 채워주신 후에 저장해 주시면 다음단계인 초도 일정수립 단계로 진행 됩니다.<br/>
				<b style="font-size: 15px; color: red; margin-left: 15px;">
					☞ 파일 내용이 변경 되거나 추가된 내용이 있을 경우 최신 버전의 파일로 업로드 하시면 됩니다.
				</b>
			</p>
			
			<br/>
			<p style="margin-left:50px; font-size: 19px; font-family: 맑은고딕;">
				3. 개발완료보고
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_12.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
				1) 개발완료보고에 대하여 승인할 매니저를 선택 후 저장을 누르면 선택한 승인자는 다음단계인 개발완료승인에서 선택된 사람의 차수에 따라서 순차적으로 승인을 하여야 합니다. 승인 요청 Comment 에 내용을 입력하면 개발승인 시 승인자가 승인할 수 있습니다.
			</p>
			
			<br/>
			<p style="margin-left:50px; font-size: 19px; font-family: 맑은고딕;">
				4. 개발완료승인
			</p>
			
			<br/>
			<div class = "vCenter">
				<img src="/images/skt_manual_13.png" />
			</div>
			<br/>
			
			<p style="margin-left:30px; margin-right:20px; font-size: 17px; font-family: 맑은고딕;">
			1)	개발완료승인 단계는 개발완료보고 단계와 매우 유사합니다. 승인 차수 대로 왼쪽에 승인 아이콘이 뜹니다. 자신의 승인 차례에서만 승인을 하실 수 있으며 모든 승인자가 승인을 하여야만 상용검증접수 단계로 넘어가실 수 있습니다.<br/>
			<b style="font-size: 15px; color: red; margin-left: 15px;">
			☞ 개발승인단계에서는 승인이나 반려를 선택하실 수 있습니다. 승인 시 comment 는 필수 요소는 아니며 반려 시에는 반려사유의 comment 는 필수이므로 입력하셔야 합니다.<br/>
			</b>
			<b style="font-size: 15px; color: red; margin-left: 15px;">
			☞ 최종 승인매니저가 승인 시에 상용검증접수 단계로 넘어갑니다.
			</b>
			</p>
			
			
			<br/>
			<p style="font-size: 17px; font-family: 맑은고딕; text-align: center;">
				기능 문의 사항은 'IN-SOFT : 김준희대리(010-2032-2761)' 로 연락 주시면 됩니다.
			</p>
			<br/>
		</div>
		
		<br/>
		
		<label for="cookie_check" style="margin-left: 20px; font-size: 13px;">
			<input type="checkbox" name="cookie_check" id="cookie_check" onclick="javascript:closeWin();" />
			오늘은 이 창을 다시 열지 않음
		</label>
	</form:form>
</body>
</html>

