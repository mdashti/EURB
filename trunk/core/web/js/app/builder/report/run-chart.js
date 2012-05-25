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

                        color: '#000000',

                        connectorColor: '#000000',

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
		options = {
	        chart: {
	           type: data[0][0],
	           renderTo: 'chart' + index,
	           height:heightValue
	        },
	        title: {
	           text: data[0][1]
	        },
	        xAxis: {
	           categories: data[1]
	        },
	        yAxis: {
	           title: {
	              text: data[0][3]
	           }
	        },
	        series: [{
				name : data[0][3],
				data : data[2]
	        }]
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
	
