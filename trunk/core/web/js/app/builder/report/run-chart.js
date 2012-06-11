createChart = function(data, index, heightValue){
	var options;
	if(data[0][0] == 'pie'){
		var pieData = [];
		for(i = 0; i < data[1].length; i++){
			pieData.push([data[1][i], data[2][i]/500]);
		}
		options = {
			chart :{
				type: data[0][0],
		        renderTo: 'chart' + index,
		        height:heightValue
			},
			title: {
	           text: data[0][1]
	        },
	        plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        formatter: function() {
                            return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
                        }
                    }
                }
            },
	        series:[{
	        	name: data[0][3],
	        	data: pieData,
	        	type: 'pie'
	        }]
		};
	}
	else{
		count = 0;
		options = {
	        chart: {
	           type: data[0][count++],
	           renderTo: 'chart' + index,
	           height:heightValue
	        },
	        title: {
	           text: data[0][count++],
	           style:{
	        	   fontFamily: data[0][count++],
	        	   fontSize: data[0][count++] + 'px',
	        	   color: '#' + data[0][count++]
	           }
	        },
	        series: [{
				name : data[0][count++],
				data : data[2]
	        }],
	        colors : [('#' + data[0][count++]),'#4572A7','#AA4643','#89A54E','#80699B','#3D96AE','#DB843D','#92A8CD','#A47D7C'],
	        xAxis: {
	           categories: data[1]
	           ,labels : {
	        	   style:{
	        		   fontFamily: data[0][(count)],
		        	   fontSize: data[0][(count + 1)] + 'px',
		        	   color: '#' + data[0][(count + 2)]  
	        	   }
	           }
	        },
	        legend: {
	        	itemStyle: {
		           fontFamily: data[0][count++],
			       fontSize: data[0][count++] + 'px',
			       color: '#' + data[0][count++] 
		        }
	        },
	        yAxis: {
	        	title: {
	        		text: data[0][count++]
	        		,style: {
		        		   fontFamily: data[0][(count)],
			        	   fontSize: data[0][(count + 1)] + 'px',
			        	   color: '#' + data[0][(count + 2)] 
		        	   }
		         }, 
		         labels : {
	        	   style:{
	        		   fontFamily: data[0][count++],
		        	   fontSize: data[0][count++] + 'px',
		        	   color: '#' + data[0][count++] 
	        	   }
	           }
	           	
	        }
	     };
	}
	
	chart = new Highcharts.Chart(options);
};

updateChartData = function(charts){
	if(EURB.RunReport.chartCount > 2){
		hValue = 150;
	}
	else{
		hValue = 300;
	}
	for(i = 0; i < charts.length; i++){
		createChart(charts[i], i, hValue);
	}
};
	
