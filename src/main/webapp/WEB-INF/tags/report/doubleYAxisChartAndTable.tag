<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ include file="/WEB-INF/views/include/charttable.jsp"%>

<%@ attribute name="chartTitle" type="java.lang.String" required="true" description="chart title"%>
<%@ attribute name="chartSubTitle" type="java.lang.String" required="true" description="chart subtitle"%>
<%@ attribute name="chartCategories" type="java.lang.String" required="true" description="chart categories"%>
<%@ attribute name="chartyTitle1" type="java.lang.String" required="true" description="chart yTitle left"%>
<%@ attribute name="chartyTitle2" type="java.lang.String" required="true" description="chart yTitle right"%>
<%@ attribute name="chartSeries" type="java.lang.String" required="true" description="chart series"%>

<%@ attribute name="chartyAxisUnit1" type="java.lang.String" required="true" description="grid yAxisUnit"%>
<%@ attribute name="chartyAxisUnit2" type="java.lang.String" required="true" description="grid yAxisUnit"%>

<%@ attribute name="gridData" type="java.lang.String" required="true" description="grid data"%>
<%@ attribute name="gridColModel" type="java.lang.String" required="true" description="grid ColModel"%>

		
<script type="text/javascript">
$(function () {
    $('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '${chartTitle}'
        },
        subtitle: {
            text: '${chartSubTitle}'
        },
        xAxis: {
            categories: ${chartCategories},
            labels:{
            	style:{
            		fontWeight:'bold'
            	}
            },
            crosshair: true
        },
        yAxis: [{
            min: 0,
            title: {
                text: '${chartyTitle1}'
            }
        },{
            min: 0,
            title: {
                text: '${chartyTitle2}'
            },
            opposite: true
        }],
        tooltip: {
            shared: true,
            formatter: function() {
                return this.x +"<br>"
                   + "<span style='color:#4572A7'>${chartyTitle1}：" + this.points[0].y +" ${chartyAxisUnit1}</span><br>" 
                   + "<span style='color:#89A54E'>${chartyTitle2}：" + this.points[1].y +" ${chartyAxisUnit2}</span>"
                ;
            }
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: ${chartSeries}
    });
});

/*table*/
/*
var mydata = [
       { id: "1", invdate: "2007-10-01", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00" },
       { id: "9", invdate: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00" }
];
*/
var mydata = ${gridData};     

$(document).ready(function () {
    $("#jqGrid").jqGrid({
        datatype: "local",
		data: mydata,
        height: 400,
		width: 900,
		/*
        colModel: [
            { label: 'Inv No', name: 'id', width: 75, key:true },
            { label: 'Notes', name: 'note', width: 150 }
        ],
        */
        colModel: ${gridColModel},                
        viewrecords: true, // show the current page, data rang and total records on the toolbar
		loadonce:true,
		sortable:false,
        rowNum: 50,
        caption: "",
        pager: "#jqGridPager"        
    });
});

</script>

<table wifth="80%" align="center">
  <tr>
  	<td width="100%" align="center">
 <div id="container" style="align:center; width: 900px; height: 400px; margin: 0 auto"></div> 		
  	</td>
  </tr>
  <tr>
  	<td align="center">
  <table id="jqGrid" align="center"></table>
  <div id="jqGridPager" style="align:center"></div>		
  	</td>
  </tr>	
</table>

