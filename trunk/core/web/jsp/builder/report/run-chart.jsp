<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<spring:eval expression="@applicationProps['application.version']" var="appVersion"/>
<spring:eval expression="@applicationProps['application.mode']" var="appMode"/>
<spring:url value="/resources-{appVersion}" var="resourcesUrl">
    <spring:param name="appVersion" value="${appVersion}"/>
</spring:url>
<spring:url value="/" var="baseUrl"/>
<html>
	<head>
		<jsp:include page="/WEB-INF/include/common-header.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App custom css -->
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/builder/report/run-report.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		
		<script type="text/javascript">
		chartPanel = new Ext.Panel({
            layout: 'fit',
            border: false,
            width: '100%',
            //html: '<div id="container" style="width: 50%; height: 400px; direction: ltr !important;"></div>'
            items: [{
            		xtype: 'box',
	            	autoEl: {
	            		tag: 'div',
	            		id: 'container'
	            	}
	        }]
        });
		var chart1; 
		Ext.onReady(function() {
			EURB.mainPanel.items.add(chartPanel);
		    EURB.mainPanel.doLayout();
		    chart1 = new Highcharts.Chart({
		         chart: {
		            renderTo: 'container',
		            type: 'line'
		         },
		         title: {
		            text: 'Fruit Consumption'
		         },
		         xAxis: {
		            categories: ['Apples', 'Bananas', 'Oranges']
		         },
		         yAxis: {
		            title: {
		               text: 'Fruit eaten'
		            }
		         },
		         series: [{
		            name: 'Jane',
		            data: [1, 0, 4]
		         }, {
		            name: 'John',
		            data: [5, 7, 3]
		         }]
		      });
		});
		
		
		 
		</script>
		
		
	</body>
</html>