/**
* JSGantt component is a UI control that displays gantt charts based by using CSS and HTML 
* @module    jsgantt
* @title    JSGantt
*/

var JSGantt; if (!JSGantt) JSGantt = {};

JSGantt.DelayItem = function(pID, pName, pStart, pEnd, pColor, pLink, pCaption, pMapping)
{
var vID       = pID;
var vName     = pName;
var vStart    = new Date();
var vEnd      = new Date();
var vColor    = pColor;
var vLink     = pLink;
var vCaption  = pCaption;
var vMapping = pMapping;
      vStart = JSGantt.parseDateStr(pStart,g.getDateInputFormat());
      vEnd   = JSGantt.parseDateStr(pEnd,g.getDateInputFormat());

      this.getID       = function(){ return vID; 	};
      this.getName     = function(){ return vName; 	};
      this.getStart    = function(){ return vStart;	};
      this.getEnd      = function(){ return vEnd;  	};
      this.getColor    = function(){ return vColor;	};
      this.getLink     = function(){ return vLink; 	};
      this.getCaption  = function(){ return vCaption; };
      this.getMapping  = function(){ return vMapping; };
      
};

JSGantt.TaskItem = function(pID, pName, pStart, pEnd, pBgColor, pColor, pLink, pRes, pComp, pGroup, pParent, pOpen, pDepend, pCaption, pMapping ,pDelay)
{
var vID       = pID;
var vName     = pName;
var vStart    = new Date();
var vEnd      = new Date();
var vBgColor  = pBgColor;
var vColor    = pColor;
var vLink     = pLink;
var vRes      = pRes;
var vComp     = pComp;
var vGroup    = pGroup;
var vParent   = pParent;
var vOpen     = pOpen;
var vDepend   = pDepend;
var vCaption  = pCaption;
var vDuration = '';
var vLevel    = 0;
var vNumKid   = 0;
var vVisible  = 1;
var x1, y1, x2, y2;
var vMapping = pMapping;
var vDelay    = pDelay;
// 입력값 vOpen 무시하고 재 설정.
if((vGroup == 1) && (vParent == 0)) vOpen = 0; else vOpen = 1;

      vStart = JSGantt.parseDateStr(pStart,g.getDateInputFormat());
      vEnd   = JSGantt.parseDateStr(pEnd,  g.getDateInputFormat());
      this.getID       = function(){ return vID; 		};
      this.getName     = function(){ return vName; 		};
      this.getStart    = function(){ return vStart;		};
      this.getEnd      = function(){ return vEnd;  		};
      this.getBgColor  = function(){ return vBgColor;	};
      this.getColor    = function(){ return vColor;		};
      this.getLink     = function(){ return vLink; 		};
      this.getDepend   = function(){ if(vDepend) return vDepend; else return null; 	};
      this.getCaption  = function(){ if(vCaption) return vCaption; else return ''; 	};
      this.getResource = function(){ if(vRes) return vRes; else return '&nbsp';  	};
      this.getCompVal  = function(){ if(vComp) return vComp; else return 0; 		};
      this.getCompStr  = function(){ if(vComp) return vComp+'%'; else return ''; 	};
      this.getDuration = function(vFormat){ 
         tmpPer =  Math.ceil((this.getEnd() - this.getStart()) /  (24 * 60 * 60 * 1000) + 1);
         vDuration = tmpPer + ' 일';
         return( vDuration );
      };
      this.getMapping     = function(){ return vMapping; };
      this.getDelay    = function(){ if(vDelay) return vDelay; else return new Array(); };
      this.isDelay     = function(){ if(vDelay) return vDelay.length; else 0; };
      this.getParent   = function(){ return vParent; 	};
      this.getGroup    = function(){ return vGroup; 	};
      this.getOpen     = function(){ return vOpen; 		};
      this.getLevel    = function(){ return vLevel; 	};
      this.getNumKids  = function(){ return vNumKid; 	};
      this.getStartX   = function(){ return x1; 		};
      this.getStartY   = function(){ return y1; 		};
      this.getEndX     = function(){ return x2;	 		};
      this.getEndY     = function(){ return y2; 		};
      this.getVisible  = function(){ return vVisible; 	};

      this.setDepend   = function(pDepend) { vDepend = pDepend;};
      this.setStart    = function(pStart)  { vStart = pStart;};
      this.setEnd      = function(pEnd)    { vEnd   = pEnd;  };
      this.setLevel    = function(pLevel)  { vLevel = pLevel;};
      this.setNumKid   = function(pNumKid) { vNumKid = pNumKid;};
      this.setCompVal  = function(pCompVal){ vComp = pCompVal;};
      this.setStartX   = function(pX) {x1 = pX; };
      this.setStartY   = function(pY) {y1 = pY; };
      this.setEndX     = function(pX) {x2 = pX; };
      this.setEndY     = function(pY) {y2 = pY; };
      this.setOpen     = function(pOpen)    {vOpen = pOpen; };
      this.setVisible  = function(pVisible) {vVisible = pVisible; };


  };

JSGantt.GanttChart =  function(pGanttVar, pDiv, pFormat)
{
var vGanttVar = pGanttVar;
var vDiv      = pDiv;
var vFormat   = pFormat;

var vShowRes  = 0;
var vShowDur  = 1;
var vShowComp = 0;
var vShowStartDate = 0;
var vShowEndDate = 0;
var vDateInputFormat   = "yyyy-mm-dd";
var vDateDisplayFormat = "yyyy-mm-dd";

var vNumUnits  = 0;
var vDepId = 1;
var vCaptionType  = '';
var vTaskList     = new Array();
var vFormatArr    = new Array("day","week","month","quarter");
var vQuarterArr   = new Array(1,1,1,2,2,2,3,3,3,4,4,4);
var vMonthDaysArr = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
var vMonthArr     = new Array("1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월");

  this.setShowRes  = function(pShow) { vShowRes  = pShow; };
  this.setShowDur  = function(pShow) { vShowDur  = pShow; };
  this.setShowComp = function(pShow) { vShowComp = pShow; };
  this.setShowStartDate = function(pShow) { vShowStartDate = pShow; };
  this.setShowEndDate = function(pShow) { vShowEndDate = pShow; };
  this.setCaptionType = function(pType) { vCaptionType = pType; };
  this.setFormat = function(pFormat){ 
         vFormat = pFormat; 
         this.Draw(); 
      };
  this.getFormat   = function(){ return vFormat; 	};
  this.getShowRes  = function(){ return vShowRes; 	};
  this.getShowDur  = function(){ return vShowDur; 	};
  this.getShowComp = function(){ return vShowComp; 	};
  this.getShowStartDate = function(){ return vShowStartDate; 	};
  this.getShowEndDate   = function(){ return vShowEndDate; 		};
  this.getDateInputFormat   = function() { return vDateInputFormat; 	};
  this.getDateDisplayFormat = function() { return vDateDisplayFormat; 	};
  this.getCaptionType       = function() { return vCaptionType; 		};
  this.CalcTaskXY = function () {
         var vList = this.getList();
         var vTaskDiv;
         var vParDiv;
         var vLeft, vTop, vHeight, vWidth;

         for(var i = 0; i < vList.length; i++)
         {
            vID = vList[i].getID();
            vTaskDiv = document.getElementById("taskbar_"+vID);
            vBarDiv  = document.getElementById("bardiv_"+vID);
            vParDiv  = document.getElementById("childgrid_"+vID);

            if(vBarDiv) {
               vList[i].setStartX( vBarDiv.offsetLeft );
               vList[i].setStartY( vParDiv.offsetTop+vBarDiv.offsetTop+6 );
               vList[i].setEndX( vBarDiv.offsetLeft + vBarDiv.offsetWidth );
               vList[i].setEndY( vParDiv.offsetTop+vBarDiv.offsetTop+6 );
            };
         };
      };

  this.AddTaskItem = function(value)
      {
         vTaskList.push(value);
      };
  this.getList   = function() { return vTaskList; };
  this.clearDependencies = function()
      {
         var parent = document.getElementById('rightside');
         var depLine;
         var vMaxId = vDepId;
         for (var i=1; i<vMaxId; i++ ) {
            depLine = document.getElementById("line"+i);
            if (depLine) { parent.removeChild(depLine); }
         };
         vDepId = 1;
      };
  this.sLine = function(x1,y1,x2,y2) {

         vLeft = Math.min(x1,x2);
         vTop  = Math.min(y1,y2);
         vWid  = Math.abs(x2-x1) + 1;
         vHgt  = Math.abs(y2-y1) + 1;

         vDoc = document.getElementById('rightside');

         // retrieve DIV
         var oDiv = document.createElement('div');
         oDiv.id = "line"+vDepId++;
         oDiv.style.position = "absolute";
         oDiv.style.margin = "0px";
         oDiv.style.padding = "0px";
         oDiv.style.overflow = "hidden";
         oDiv.style.border = "0px";

         // set attributes
         oDiv.style.zIndex = 0;
         oDiv.style.backgroundColor = "red";
         oDiv.style.left = vLeft + "px";
         oDiv.style.top = vTop + "px";
         oDiv.style.width = vWid + "px";
         oDiv.style.height = vHgt + "px";
         oDiv.style.visibility = "visible";
        
         vDoc.appendChild(oDiv);

      };

/**
* Draw a diaganol line (calc line x,y pairs and draw multiple one-by-one sLines)
* @method dLine
* @return {Void}
*/  this.dLine = function(x1,y1,x2,y2) {

         var dx = x2 - x1;
         var dy = y2 - y1;
         var x = x1;
         var y = y1;

         var n = Math.max(Math.abs(dx),Math.abs(dy));
         dx = dx / n;
         dy = dy / n;
         for (var i = 0; i <= n; i++ )
         {
            vx = Math.round(x); 
            vy = Math.round(y);
            this.sLine(vx,vy,vx,vy);
            x += dx;
            y += dy;
         };

      };

/**
* Draw dependency line between two points (task 1 end -> task 2 start)
* @method drawDependency
* @return {Void}
*/ this.drawDependency =function(x1,y1,x2,y2)
      {
         if(x1 + 10 < x2)
         { 
            this.sLine(x1,y1,x1+4,y1);
            this.sLine(x1+4,y1,x1+4,y2);
            this.sLine(x1+4,y2,x2,y2);
            this.dLine(x2,y2,x2-3,y2-3);
            this.dLine(x2,y2,x2-3,y2+3);
            this.dLine(x2-1,y2,x2-3,y2-2);
            this.dLine(x2-1,y2,x2-3,y2+2);
         }
         else
         {
            this.sLine(x1,y1,x1+4,y1);
            this.sLine(x1+4,y1,x1+4,y2-10);
            this.sLine(x1+4,y2-10,x2-8,y2-10);
            this.sLine(x2-8,y2-10,x2-8,y2);
            this.sLine(x2-8,y2,x2,y2);
            this.dLine(x2,y2,x2-3,y2-3);
            this.dLine(x2,y2,x2-3,y2+3);
            this.dLine(x2-1,y2,x2-3,y2-2);
            this.dLine(x2-1,y2,x2-3,y2+2);
         }
      };

/**
* Draw all task dependencies 
* @method DrawDependencies
* @return {Void}
*/  this.DrawDependencies = function () {

         //First recalculate the x,y
         this.CalcTaskXY();

         this.clearDependencies();

         var vList = this.getList();
         for(var i = 0; i < vList.length; i++)
         {

            vDepend = vList[i].getDepend();
            if(vDepend) {
         
               var vDependStr = vDepend + '';
               var vDepList = vDependStr.split(',');
               var n = vDepList.length;
               for(var k=0;k<n;k++) {
                  var vTask = this.getArrayLocationByID(vDepList[k]);
                  if (typeof vTask != "undefined") {
                	  if(vList[vTask].getVisible() == 1)
                		  this.drawDependency(vList[vTask].getEndX(),vList[vTask].getEndY(),vList[i].getStartX()-1,vList[i].getStartY());
                  }
               } // for
            } // if
         } // for
      };

/**
* Find location of TaskItem based on the task ID
* @method getArrayLocationByID
* @return {Void}
*/  this.getArrayLocationByID = function(pId)  {

         var vList = this.getList();
         for(var i = 0; i < vList.length; i++)
         {
            if(vList[i].getID()==pId)
               return i;
         }
      };

/**
* Draw gantt chart
* @method Draw
* @return {Void}
*/ this.Draw = function()
   {
      var vMaxDate = new Date();
      var vMinDate = new Date();    
      var vTmpDate = new Date();
      var vNxtDate = new Date();
      var vCurrDate = new Date();
      var vTaskLeft   = 0;
      var vTaskRight  = 0;
      var vDelayLeft  = 0;
      var vDelayRight = 0;
      var vNumCols    = 0;
      var vID         = 0;
      var vMainTable  = "";
      var vLeftTable  = "";
      var vRightTable = "";
      var vDateRowStr = "";
      var vItemRowStr = "";
      var vColWidth   = 0;
      var vColUnit    = 0;
      var vChartWidth = 0;
      var vNumDays    = 0;
      var vDayWidth   = 0;
      var vStr        = "";

      var vNameWidth   = 200;  //##
      var vStatusWidth = 70;
      //var vLeftWidth = 15 + vNameWidth + 70 + 70 + 70 + 70 + 70;
      //var vLeftWidth = 15 + vNameWidth;
      var vLeftWidth = vNameWidth + vStatusWidth;

      if(vTaskList.length > 0)
      {
        
         // Process all tasks preset parent date and completion %
         JSGantt.processRows(vTaskList, 0, -1, 1, 1);

         // get overall min/max dates plus padding
         vMinDate = JSGantt.getMinDate(vTaskList, vFormat);
         vMaxDate = JSGantt.getMaxDate(vTaskList, vFormat);

         // Calculate chart width variables.  vColWidth can be altered manually to change each column width
         // May be smart to make this a parameter of GanttChart or set it based on existing pWidth parameter
         if(vFormat == 'day') {
            vColWidth = 28;
            vColUnit = 1;
         }
         else if(vFormat == 'week') {
        	 vColWidth = 57; //vColWidth = 37;
            vColUnit = 7;
         }
         else if(vFormat == 'month') {
        	vColWidth = 67; //vColWidth = 37;
            vColUnit = 30;
         }
         else if(vFormat == 'quarter') {
        	vColWidth = 100; //vColWidth = 60;
            vColUnit = 90;
         }
         
         vNumDays    = (Date.parse(vMaxDate) - Date.parse(vMinDate)) / ( 24 * 60 * 60 * 1000);
         vNumUnits   = vNumDays / vColUnit;
         vChartWidth = vNumUnits * vColWidth + 1;
         vDayWidth   = (vColWidth / vColUnit) + (1/vColUnit);

         vMainTable =
             '<table id=thetable cellspacing=0 cellpadding=0 border=0><tbody><tr>' +
             '<td valign=top bgcolor=#ffffff>';


         
         /////////////////////////////////////////////////
         // Task 항목 테이블 그리기
         vLeftTable = '<div class="scroll" id=leftside style="width:' + vLeftWidth + 'px">';
         vLeftTable +=
        	 '<table id=thetable cellspacing=0 cellpadding=0 border=0><tbody><tr>' +
        	 '<td class="gname_head" style="width:'+vLeftWidth+'px;">제목</td></tr></tbody></table>';
         
            for(var i = 0; i < vTaskList.length; i++)
            {
               vGNameClass = 'gname';
               vGDurClass  = 'gname_dur';
               if( vTaskList[i].getGroup() ) {
                  vRowType = "group";
                  if(vTaskList[i].getParent() == 0) { 
                	  vGNameClass = 'gname_t';
                	  vGDurClass  = 'gname_dur_t';
                  }
               } else {
                  vRowType  = "row";
               }
               vID = vTaskList[i].getID();

               if(vTaskList[i].getVisible() == 0) 
            	   vLeftTable += '<div id=childname_' + vID + ' style="position:relative; display:none;">';
               else
            	   vLeftTable += '<div id=childname_' + vID + ' style="position:relative">';
               vLeftTable +=
            	   '<div id="child_'+ vID +'" style="position:relative">'+
                   '<table class="g_task_row" style="width:'+ vLeftWidth +'px;">'+
                   '<tr><td class='+vGNameClass+' style="width:' + vNameWidth + 'px;" nowrap><nobr><span style="color: #aaaaaa">';

               for(var j=1; j<vTaskList[i].getLevel(); j++) {
                  vLeftTable += '&nbsp&nbsp&nbsp&nbsp';
               }

               vLeftTable += '</span>';
               vLeftTable += '<span style="color: #000000;">&nbsp&nbsp&nbsp</span>';

               if( vTaskList[i].getGroup()) {
           		   vLeftTable += '<span id="group_' + vID + '" onclick="JSGantt.folder(' + vID + ','+vGanttVar+');'+vGanttVar+'.DrawDependencies();" style="cursor:pointer;font-weight:bold;"> ' + vTaskList[i].getName() + '</span></nobr></td>' ;
               } else {
            	   vLeftTable += '<span> ' + vTaskList[i].getName() + '</span></nobr></td>' ;
               }

//               if(vShowDur == 1) 
//            	   vLeftTable += '  <td class="'+vGNameClass+' '+vGDurClass+'"><nobr>' + vTaskList[i].getDuration(vFormat) + '</nobr></td>' ;

               vLeftTable += 
            	   '</tr></table></div>'+
                   '</div>';
            }
            vLeftTable += '</div>';

            vMainTable += vLeftTable;

            
            
         /////////////////////////////////////////////////
         // 차트 테이블 그리기
         vRightTable = 
            '<td style="width: ' + vChartWidth + 'px;" valign=top bgcolor=#ffffff>' +
            '<div class="scroll2" id=rightside>' +
            '<table style="width: ' + vChartWidth + 'px;" cellSpacing=0 cellPadding=0 border=0>' +
            '<tbody><tr style="height: 18px">';

         vTmpDate.setFullYear(vMinDate.getFullYear(), vMinDate.getMonth(), vMinDate.getDate());
         vTmpDate.setHours(0);
         vTmpDate.setMinutes(0);

         // 날짜 타이틀 (상단 기간)
         while(Date.parse(vTmpDate) <= Date.parse(vMaxDate))
         {  
            vStr = vTmpDate.getFullYear() + '';
            vStr = vStr.substring(2,4);
            
            if(vFormat == 'day')
            {
               vRightTable += '<td class=gdatehead align=center colspan=7><nobr>' +
               JSGantt.formatDateStr(vTmpDate, vDateDisplayFormat) + ' ~ ';
               vTmpDate.setDate(vTmpDate.getDate()+6);
               vRightTable += JSGantt.formatDateStr(vTmpDate,vDateDisplayFormat.substring(5,10)) + '</nobr></td>';
               vTmpDate.setDate(vTmpDate.getDate()+1);
            }
            else if(vFormat == 'week')
            {
               vRightTable += '<td class=gdatehead align=center width='+vColWidth+'px>`'+ vStr + '</td>';
               vTmpDate.setDate(vTmpDate.getDate()+7);
            }
            else if(vFormat == 'month')
            {
               vRightTable += '<td class=gdatehead align=center width='+vColWidth+'px>`'+ vStr + '</td>';
               vTmpDate.setDate(vTmpDate.getDate() + 1);
               while(vTmpDate.getDate() > 1)
               {
                 vTmpDate.setDate(vTmpDate.getDate() + 1);
               }
            }
            else if(vFormat == 'quarter')
            {
               vRightTable += '<td class=gdatehead align=center width='+vColWidth+'px>`'+ vStr + '</td>';
               vTmpDate.setDate(vTmpDate.getDate() + 81);
               while(vTmpDate.getDate() > 1)
               {
                 vTmpDate.setDate(vTmpDate.getDate() + 1);
               }
            }
         }

         vRightTable += '</TR><TR>';

         // 날짜 타이틀 (하단 날짜단위)
         vTmpDate.setFullYear(vMinDate.getFullYear(), vMinDate.getMonth(), vMinDate.getDate());
         vNxtDate.setFullYear(vMinDate.getFullYear(), vMinDate.getMonth(), vMinDate.getDate());
         vNumCols = 0;
         while(Date.parse(vTmpDate) <= Date.parse(vMaxDate))
         {  
             if(vFormat == 'day' )
             {
               if(vTmpDate.getDay() % 6 == 0) {
           	      vDateRowStr += '<td class="gheadwkend_dt"><div style="width:'+vColWidth+'px;">' + vTmpDate.getDate() + '</div></td>';
           	      if(JSGantt.formatDateStr(vCurrDate,'yyyy-mm-dd') == JSGantt.formatDateStr(vTmpDate,'yyyy-mm-dd')) {
                     vItemRowStr += '<td class="gheadwkend_today"><div style="width:'+vColWidth+'px;">&nbsp</div></td>';
           	      } else {
           	    	 vItemRowStr += '<td class="gheadwkend"><div style="width:'+vColWidth+'px;">&nbsp</div></td>';
           	      }
               }
               else {
                  vDateRowStr += '<td class="ghead_dt"><div style="width:'+vColWidth+'px;">'+ vTmpDate.getDate() +'</div></td>';
                  if(JSGantt.formatDateStr(vCurrDate,'yyyy-mm-dd') == JSGantt.formatDateStr(vTmpDate,'yyyy-mm-dd')) {
                     vItemRowStr += '<td class="ghead_today"><div style="width:'+vColWidth+'px;">&nbsp</div></td>';
                  } else {
               	     vItemRowStr += '<td class="ghead"><div style="width:'+vColWidth+'px;">&nbsp</div></td>';
                  }
               }
               vTmpDate.setDate(vTmpDate.getDate() + 1);
            }
            else if(vFormat == 'week')
            {
               vNxtDate.setDate(vNxtDate.getDate() + 7);
               vDateRowStr += '<td class="ghead_dt"><div style="width:'+vColWidth+'px;">'+ (vTmpDate.getMonth()+1) + '/' + vTmpDate.getDate() +'</div></td>';
               vItemRowStr += '<td class="ghead"><div style="width:'+vColWidth+'px;">&nbsp</div></td>';
               vTmpDate.setDate(vTmpDate.getDate() + 7);

            }
            else if(vFormat == 'month')
            {
               vNxtDate.setFullYear(vTmpDate.getFullYear(), vTmpDate.getMonth(), vMonthDaysArr[vTmpDate.getMonth()]);
               vDateRowStr += '<td class="ghead_dt"><div style="width:'+vColWidth+'px;">'+ vMonthArr[vTmpDate.getMonth()].substr(0,3) +'</div></td>';
               vItemRowStr += '<td class="ghead"><div style="width:'+vColWidth+'px;">&nbsp</div></td>';
               vTmpDate.setDate(vTmpDate.getDate() + 1);
               while(vTmpDate.getDate() > 1) 
               {
                  vTmpDate.setDate(vTmpDate.getDate() + 1);
               }
            }
            else if(vFormat == 'quarter')
            {
               vNxtDate.setDate(vNxtDate.getDate() + 122);
               if( vTmpDate.getMonth()==0 || vTmpDate.getMonth()==1 || vTmpDate.getMonth()==2 )
                  vNxtDate.setFullYear(vTmpDate.getFullYear(), 2, 31);
               else if( vTmpDate.getMonth()==3 || vTmpDate.getMonth()==4 || vTmpDate.getMonth()==5 )
                  vNxtDate.setFullYear(vTmpDate.getFullYear(), 5, 30);
               else if( vTmpDate.getMonth()==6 || vTmpDate.getMonth()==7 || vTmpDate.getMonth()==8 )
                  vNxtDate.setFullYear(vTmpDate.getFullYear(), 8, 30);
               else if( vTmpDate.getMonth()==9 || vTmpDate.getMonth()==10 || vTmpDate.getMonth()==11 )
                  vNxtDate.setFullYear(vTmpDate.getFullYear(), 11, 31);
               vDateRowStr += '<td class="ghead_dt"><div style="width:'+vColWidth+'px;">' + vQuarterArr[vTmpDate.getMonth()] + '분기</div></td>';
               vItemRowStr += '<td class="ghead"><div style="width:'+vColWidth+'px;">&nbsp</div></td>';
               vTmpDate.setDate(vTmpDate.getDate() + 81);
               while(vTmpDate.getDate() > 1) 
               {
                  vTmpDate.setDate(vTmpDate.getDate() + 1);
               }
            }
         }
         vRightTable += vDateRowStr + '</tr>';
         vRightTable += '</tbody></table>';

         // Draw each row

         for(i = 0; i < vTaskList.length; i++)
         {

            vTmpDate.setFullYear(vMinDate.getFullYear(), vMinDate.getMonth(), vMinDate.getDate());
            vTaskStart = vTaskList[i].getStart();
            vTaskEnd   = vTaskList[i].getEnd();

            vNumCols = 0;
            vID = vTaskList[i].getID();

           // vNumUnits = Math.ceil((vTaskList[i].getEnd() - vTaskList[i].getStart()) / (24 * 60 * 60 * 1000)) + 1;
            vNumUnits = (vTaskList[i].getEnd() - vTaskList[i].getStart()) / (24 * 60 * 60 * 1000) + 1;
           
             if(vTaskList[i].getVisible() == 0) 
                 vRightTable += '<div id=childgrid_' + vID + ' style="position:relative; display:none;">';
             else
                 vRightTable += '<div id=childgrid_' + vID + ' style="position:relative">';

               // Build date string for Title
               vDateRowStr = JSGantt.formatDateStr(vTaskStart,vDateDisplayFormat) + ' - ' + JSGantt.formatDateStr(vTaskEnd,vDateDisplayFormat);

               vTaskRight = (Date.parse(vTaskList[i].getEnd()) - Date.parse(vTaskList[i].getStart())) / (24 * 60 * 60 * 1000) + 1/vColUnit;
               vTaskLeft = Math.ceil((Date.parse(vTaskList[i].getStart()) - Date.parse(vMinDate)) / (24 * 60 * 60 * 1000));
               if (vFormat='day')
               {
                   var tTime=new Date();
                   tTime.setTime(Date.parse(vTaskList[i].getStart()));
                   if (tTime.getMinutes() > 29)
                       vTaskLeft+=.5;
               }


               // 프로젝트 첫줄 (그룹이면서 상위ID가 0)
               if((vTaskList[i].getGroup() == 1) && (vTaskList[i].getParent() == 0)) {
                  vRightTable += 
                	  '<div><table class="g_task_prj" style="width:'+ vChartWidth +'px;"><tr id=childrow_'+ vID +'>'+ vItemRowStr +'</tr></table></div>';
                  vRightTable +=
                     '<div id=bardiv_' + vID + ' style="position:absolute; top:5px; left:' + Math.ceil(vTaskLeft * (vDayWidth) + 1) + 'px; height: 7px; width:' + Math.ceil((vTaskRight) * (vDayWidth) - 1) + 'px">' +
                       '<div id=taskbar_' + vID + ' title="' + vTaskList[i].getCaption() + '" class="grp_task" style="width:'+ Math.ceil((vTaskRight) * (vDayWidth) - 2) + 'px;"></div>' +
                       '<div class="grp_tbar4r"></div>' +
                       '<div class="grp_tbar4l"></div>' +
                       '<div class="grp_tbar3r"></div>' +
                       '<div class="grp_tbar3l"></div>' +
                       '<div class="grp_tbar2r"></div>' +
                       '<div class="grp_tbar2l"></div>' +
                       '<div class="grp_tbar1r"></div>' +
                       '<div class="grp_tbar1l"></div>';

                        if( g.getCaptionType() ) {
                        	vCaptionStr = vTaskList[i].getCaption();
                            vRightTable += '<div class="captionbox" style="left:'+ (Math.ceil((vTaskRight) * (vDayWidth) - 1) + 6) + 'px;">'+ vCaptionStr +'</div>';
                        };
                  vRightTable += '</div>' ;

               } else {
            	  // 한 줄
                  vRightTable += 
                	  '<div><table class="g_task_row" style="width:'+ vChartWidth +'px;"><tr id=childrow_'+ vID +'>' + vItemRowStr + '</tr></table></div>';
                  
                  var cssType ="";
                  // 프로젝트 챠트바
                  if(vTaskList[i].getMapping()=="Y"){
                	  cssType ="delay_task";
                  }else{
                	  cssType ="g_task"; 
                  }
                	  
                  vRightTable +=
                     '<div id=bardiv_'+ vID +' style="position:absolute; top:4px; left:' + Math.ceil(vTaskLeft * (vDayWidth) + 1) + 'px;">' +
                        '<div id=taskbar_'+ vID +' title="' + vTaskList[i].getCaption() + '" class="'+cssType+'" style="width:' + Math.ceil((vTaskRight) * (vDayWidth) - 1) + 'px;" onclick="' + vTaskList[i].getLink() + '" >' +
                           '<div class="gcomplete" style="width:' + vTaskList[i].getCompStr() + ';"></div>' +
                        '</div>';

                  		// 챠트의 팝업설명박스
                        if( g.getCaptionType() ) {
                           vCaptionStr = vTaskList[i].getCaption();
                           vRightTable += '<div class="captionbox" style="left:' + (Math.ceil((vTaskRight) * (vDayWidth) - 1) + 6) + 'px;">'+ vCaptionStr +'</div>';
                        }
                  vRightTable += '</div>' ;

                  // 지연된 차트바 그리기
                  if( vTaskList[i].isDelay()) 
                  {
                      var vDelayList = vTaskList[i].getDelay();
                      for(j = 0; j < vDelayList.length; j++) 
                      {
                         vDelayRight = (Date.parse(vDelayList[j].getEnd()) - Date.parse(vDelayList[j].getStart())) / (24 * 60 * 60 * 1000) + 1/vColUnit;
                         vDelayLeft  = Math.ceil((Date.parse(vDelayList[j].getStart()) - Date.parse(vMinDate)) / (24 * 60 * 60 * 1000));
                         if (vFormat='day') 
                         {
                             var tTime=new Date();
                             tTime.setTime(Date.parse(vDelayList[j].getStart()));
                             if (tTime.getMinutes() > 29)
                                 vDelayLeft+=.5;
                         }
                         var cssType2 = "";
                         if(vDelayList[j].getMapping() == "Y"){
                        	 cssType2 = "delay_task";
                         }else{
                        	 cssType2 = "g_task";
                         }
                         vRightTable +=
                             '<div style="position:absolute; left:'+ Math.ceil(vDelayLeft * (vDayWidth) + 1) +'px; top:4px;">' +
                             '<div id=taskbar_' + vDelayList[j].getID() + ' title="' + vDelayList[j].getCaption() + '" class="'+cssType2+'" style="width:' + Math.ceil((vDelayRight) * (vDayWidth) - 1) + 'px;" onclick="' + vDelayList[j].getLink() + '" ></div>' +
                             '</div>';
                      } // for
                  }

               }


            vRightTable += '</DIV>';

         }

         vMainTable += vRightTable + '</DIV></TD></TR></TBODY></TABLE></BODY></HTML>';

           vDiv.innerHTML = vMainTable;

      }

   }; //this.draw

/**
* Mouseover behaviour for gantt row
* @method mouseOver
* @return {Void}
*/  this.mouseOver = function( pObj, pID, pPos, pType ) {
      if( pPos == 'right' )  vID = 'child_' + pID;
      else vID = 'childrow_' + pID;
      
      pObj.bgColor = "#ffffaa";
      vRowObj = JSGantt.findObj(vID);
      if (vRowObj) vRowObj.bgColor = "#ffffaa";
   };

/**
* Mouseout behaviour for gantt row
* @method mouseOut
* @return {Void}
*/  this.mouseOut = function( pObj, pID, pPos, pType, pBgColor ) {
      if( pPos == 'right' )  vID = 'child_' + pID;
      else vID = 'childrow_' + pID;
      
      pObj.bgColor = "#ffffff";
      vRowObj = JSGantt.findObj(vID);
      if (vRowObj) {
         //if( pType == "group") {
         //   pObj.bgColor = "#f3f3f3";
         //   vRowObj.bgColor = "#f3f3f3";
         //} else {
            pObj.bgColor = pBgColor;
            vRowObj.bgColor = pBgColor;
         //}
      }
   };

}; //GanttChart


/**
* 
@class 
*/

/**
* Checks whether browser is IE
* 
* @method isIE 
*/
JSGantt.isIE = function () {
    
    if(typeof document.all != 'undefined')
        {return true;}
    else
        {return false;}
};
    
/**
* Recursively process task tree ... set min, max dates of parent tasks and identfy task level.
*
* @method processRows
* @param pList {Array} - Array of TaskItem Objects
* @param pID {Number} - task ID
* @param pRow {Number} - Row in chart
* @param pLevel {Number} - Current tree level
* @param pOpen {Boolean}
* @return void
*/ 
JSGantt.processRows = function(pList, pID, pRow, pLevel, pOpen)
{

   var vMinDate = new Date();
   var vMaxDate = new Date();
   var vMinSet  = 0;
   var vMaxSet  = 0;
   var vList    = pList;
   var vLevel   = pLevel;
   var i        = 0;
   var vNumKid  = 0;
   var vCompSum = 0;
   var vVisible = pOpen;
   
   for(i = 0; i < pList.length; i++)
   {
      if(pList[i].getParent() == pID) {
         vVisible = pOpen;
         pList[i].setVisible(vVisible);
         if(vVisible==1 && pList[i].getOpen() == 0) 
           {vVisible = 0;}
            
         pList[i].setLevel(vLevel);
         vNumKid++;

         if(pList[i].getGroup()) {
            JSGantt.processRows(vList, pList[i].getID(), i, vLevel+1, vVisible);
         };

         if( vMinSet==0 || pList[i].getStart() < vMinDate) {
            vMinDate = pList[i].getStart();
            vMinSet = 1;
         };

         if( vMaxSet==0 || pList[i].getEnd() > vMaxDate) {
            vMaxDate = pList[i].getEnd();
            vMaxSet = 1;
         };
         
         if (pList[i].getDelay().length > 0) {
             if( pList[i].getDelay()[0].getEnd() > vMaxDate) {
                 vMaxDate = pList[i].getDelay()[0].getEnd();
                 vMaxSet = 1;
              };
         };

         vCompSum += pList[i].getCompVal();

      }
   }

   if(pRow >= 0) {
      pList[pRow].setStart(vMinDate);
      pList[pRow].setEnd(vMaxDate);
      pList[pRow].setNumKid(vNumKid);
      pList[pRow].setCompVal(Math.ceil(vCompSum/vNumKid));
   }

};

/**
* Determine the minimum date of all tasks and set lower bound based on format
*
* @method getMinDate
* @param pList {Array} - Array of TaskItem Objects
* @param pFormat {String} - current format (day...)
* @return {Datetime}
*/
JSGantt.getMinDate = function getMinDate(pList, pFormat)  
      {

         var vDate = new Date();

         vDate.setFullYear(pList[0].getStart().getFullYear(), pList[0].getStart().getMonth(), pList[0].getStart().getDate());

         // Parse all Task End dates to find min
         for(var i = 0; i < pList.length; i++)
         {
            if(Date.parse(pList[i].getStart()) < Date.parse(vDate))
               vDate.setFullYear(pList[i].getStart().getFullYear(), pList[i].getStart().getMonth(), pList[i].getStart().getDate());
         }

         if (pFormat=='day')
         {
            vDate.setDate(vDate.getDate() - 1);
            while(vDate.getDay() % 7 > 0)
            {
                vDate.setDate(vDate.getDate() - 1);
            }

         }

         else if (pFormat=='week')
         {
            vDate.setDate(vDate.getDate() - 7);
            while(vDate.getDay() % 7 > 0)
            {
                vDate.setDate(vDate.getDate() - 1);
            }

         }

         else if (pFormat=='month')
         {
            while(vDate.getDate() > 1)
            {
                vDate.setDate(vDate.getDate() - 1);
            }
         }

         else if (pFormat=='quarter')
         {
            if( vDate.getMonth()==0 || vDate.getMonth()==1 || vDate.getMonth()==2 )
               {vDate.setFullYear(vDate.getFullYear(), 0, 1);}
            else if( vDate.getMonth()==3 || vDate.getMonth()==4 || vDate.getMonth()==5 )
               {vDate.setFullYear(vDate.getFullYear(), 3, 1);}
            else if( vDate.getMonth()==6 || vDate.getMonth()==7 || vDate.getMonth()==8 )
               {vDate.setFullYear(vDate.getFullYear(), 6, 1);}
            else if( vDate.getMonth()==9 || vDate.getMonth()==10 || vDate.getMonth()==11 )
               {vDate.setFullYear(vDate.getFullYear(), 9, 1);}

         };

         return(vDate);

      };




/**
* Used to determine the minimum date of all tasks and set lower bound based on format
*
* @method getMaxDate
* @param pList {Array} - Array of TaskItem Objects
* @param pFormat {String} - current format (day...)
* @return {Datetime}
*/
JSGantt.getMaxDate = function (pList, pFormat)
{
   var vDate = new Date();
   
   var temp;
   var temp2 ="0";
   var temp3 = false;
   	for (var z = 0; z < pList.length; z++) {
   		if(pList[z].isDelay()){
   			temp1 = pList[z].getDelay()[pList[z].getDelay().length - 1].getEnd();
   			if(temp1 > temp2){
   				temp2 = temp1;
   			}
   			temp3 = true;
   		}
	}
   		 if(temp3){
   			vDate.setFullYear(temp2.getFullYear(),temp2.getMonth(),temp2.getDate());
   		 }
         for(var i = 0; i < pList.length; i++)
         {
            if(Date.parse(pList[i].getEnd()) > Date.parse(vDate))
            {
                 vDate.setTime(Date.parse(pList[i].getEnd()));
            }   
         }
         
         if (pFormat=='day')
         {
            vDate.setDate(vDate.getDate() + 1);

            while(vDate.getDay() % 6 > 0)
            {
                vDate.setDate(vDate.getDate() + 1);
            }

         }

         if (pFormat=='week')
         {
            //For weeks, what is the last logical boundary?
            vDate.setDate(vDate.getDate() + 11);

            while(vDate.getDay() % 6 > 0)
            {
                vDate.setDate(vDate.getDate() + 1);
            }

         }

         // Set to last day of current Month
         if (pFormat=='month')
         {
            while(vDate.getDay() > 1)
            {
                vDate.setDate(vDate.getDate() + 1);
            }

            vDate.setDate(vDate.getDate() - 1);
         }

         // Set to last day of current Quarter
         if (pFormat=='quarter')
         {
            if( vDate.getMonth()==0 || vDate.getMonth()==1 || vDate.getMonth()==2 )
               vDate.setFullYear(vDate.getFullYear(), 2, 31);
            else if( vDate.getMonth()==3 || vDate.getMonth()==4 || vDate.getMonth()==5 )
               vDate.setFullYear(vDate.getFullYear(), 5, 30);
            else if( vDate.getMonth()==6 || vDate.getMonth()==7 || vDate.getMonth()==8 )
               vDate.setFullYear(vDate.getFullYear(), 8, 30);
            else if( vDate.getMonth()==9 || vDate.getMonth()==10 || vDate.getMonth()==11 )
               vDate.setFullYear(vDate.getFullYear(), 11, 31);

         }

         return(vDate);

      };


/**
* Returns an object from the current DOM
*
* @method findObj
* @param theObj {String} - Object name
* @param theDoc {Document} - current document (DOM)
* @return {Object}
*/
JSGantt.findObj = function (theObj, theDoc)

      {

         var p, i, foundObj;

         if(!theDoc) {theDoc = document;}

         if( (p = theObj.indexOf("?")) > 0 && parent.frames.length){

            theDoc = parent.frames[theObj.substring(p+1)].document;

            theObj = theObj.substring(0,p);

         }

         if(!(foundObj = theDoc[theObj]) && theDoc.all) 

            {foundObj = theDoc.all[theObj];}



         for (i=0; !foundObj && i < theDoc.forms.length; i++) 

            {foundObj = theDoc.forms[i][theObj];}



         for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)

            {foundObj = JSGantt.findObj(theObj,theDoc.layers[i].document);}



         if(!foundObj && document.getElementById)

            {foundObj = document.getElementById(theObj);}



         return foundObj;

      };


/**
* Change display format of current gantt chart
*
* @method changeFormat
* @param pFormat {String} - Current format (day...)
* @param ganttObj {GanttChart} - The gantt object
* @return {void}
*/
JSGantt.changeFormat =      function(pFormat,ganttObj) {

        if(ganttObj) 
        {
        ganttObj.setFormat(pFormat);
        ganttObj.DrawDependencies();
        }
        else
        {alert('Chart undefined');};
      };


/**
* Open/Close and hide/show children of specified task
*
* @method folder
* @param pID {Number} - Task ID
* @param ganttObj {GanttChart} - The gantt object
* @return {void}
*/
JSGantt.folder= function (pID,ganttObj) {
   var vList = ganttObj.getList();

   for(var i = 0; i < vList.length; i++)
   {
      if(vList[i].getID() == pID) {
         if( vList[i].getOpen() == 1 ) {
            vList[i].setOpen(0);
            JSGantt.hide(pID, ganttObj);
         } else {
            vList[i].setOpen(1);
            JSGantt.show(pID, ganttObj);
         }
      }
   }
};


/**
* Hide children of a task
*
* @method hide
* @param pID {Number} - Task ID
* @param ganttObj {GanttChart} - The gantt object
* @return {void}
*/
JSGantt.hide = function (pID, ganttObj) {
   var vList = ganttObj.getList();
   var vID   = 0;

   for(var i = 0; i < vList.length; i++)
   {
      if(vList[i].getParent() == pID) {
         vID = vList[i].getID();
         JSGantt.findObj('childname_' + vID).style.display = "none";
         JSGantt.findObj('childgrid_' + vID).style.display = "none";
         vList[i].setVisible(0);
         if(vList[i].getGroup() == 1) 
            {JSGantt.hide(vID, ganttObj);}
      }

   }
};


/**
* Show children of a task
*
* @method show
* @param pID {Number} - Task ID
* @param ganttObj {GanttChart} - The gantt object
* @return {void}
*/
JSGantt.show = function (pID, ganttObj) {
   var vList = ganttObj.getList();
   var vID   = 0;

   for(var i = 0; i < vList.length; i++)
   {
      if(vList[i].getParent() == pID) {
         vID = vList[i].getID();

         JSGantt.findObj('childname_'+vID).style.display = "";
         JSGantt.findObj('childgrid_'+vID).style.display = "";
         vList[i].setVisible(1);

         if(vList[i].getGroup() == 1) 
            {JSGantt.show(vID, ganttObj);}

      }
   }
};

/**
* Parse dates based on gantt date format setting as defined in JSGantt.GanttChart.setDateInputFormat()
*
* @method parseDateStr
* @param pDateStr {String} - A string that contains the date (i.e. "01/01/09")
* @param pFormatStr {String} - The date format (mm/dd/yyyy,dd/mm/yyyy,yyyy-mm-dd)
* @return {Datetime}
*/
JSGantt.parseDateStr = function(pDateStr,pFormatStr) {
   var vDate =new Date();   
   vDate.setTime( Date.parse(pDateStr));

   switch(pFormatStr) 
   {
      case 'yyyy-mm-dd':
         var vDateParts = pDateStr.split('-');
         vDate.setFullYear(parseInt(vDateParts[0], 10), parseInt(vDateParts[1], 10) - 1, parseInt(vDateParts[2], 10));
         break;
      case 'yyyy/mm/dd':
         var vDateParts = pDateStr.split('/');
         vDate.setFullYear(parseInt(vDateParts[0], 10), parseInt(vDateParts[1], 10) - 1, parseInt(vDateParts[2], 10));
         break;
    }

    return(vDate);
    
};

/**
* Display a formatted date based on gantt date format setting as defined in JSGantt.GanttChart.setDateDisplayFormat()
*
* @method formatDateStr
* @param pDate {Date} - A javascript date object
* @param pFormatStr {String} - The date format (mm/dd/yyyy,dd/mm/yyyy,yyyy-mm-dd...)
* @return {String}
*/
JSGantt.formatDateStr = function(pDate,pFormatStr) {
       vYear4Str = pDate.getFullYear() + '';
       vYear2Str = vYear4Str.substring(2,4);
       vMonthStr = (pDate.getMonth()+1) + '';
       vDayStr   = pDate.getDate() + '';

      var vDateStr = "";    

      switch(pFormatStr) {
            case 'yyyy-mm-dd':
               return( vYear4Str + '-' + vMonthStr + '-' + vDayStr );
            case 'yyyy/mm/dd':
               return( vYear4Str + '/' + vMonthStr + '/' + vDayStr );
            case 'yy-mm-dd':
               return( vYear2Str + '-' + vMonthStr + '-' + vDayStr );
            case 'yy/mm/dd':
               return( vYear2Str + '/' + vMonthStr + '/' + vDayStr );
            case 'mm-dd':
               return( vMonthStr + '-' + vDayStr );
            case 'mm/dd':
               return( vMonthStr + '/' + vDayStr );
      }      
      
};

/**
* Parse an external XML file containing task items.
*
* @method parseXML
* @param ThisFile {String} - URL to XML filelsls
* @param pGanttVar {Gantt} - Gantt object
* @return {void}
*/
JSGantt.parseXML = function(ThisFile,pGanttVar){
};


