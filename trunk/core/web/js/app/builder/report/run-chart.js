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
	else if(data[0][0] == 'gauge'){
		count = 1;
		chartTitle = null;
		if(data[0][17]){
			if(data[0][16] < data[2][0]){
				chartTitle = EURB.RunReport.ChartAlert;
				count = 2;
			}
		}
		options = {
				chart : {
					type: data[0][0],
			        renderTo: 'chart' + index,
			        height:heightValue
				},
				title: {
			           text: chartTitle ? chartTitle : data[0][count++],
			           style:{
			        	   fontFamily: data[0][count++],
			        	   fontSize: data[0][count++] + 'px',
			        	   color: '#' + data[0][count++]
			           }
			    },
			    series:[{
		        	name: data[0][count++],
		        	data: data[2],
		        	dataLabels: {
		                formatter: function () {
		                    return '<span style="font-family:' +  data[0][(11)] + '!important;font-size: ' + data[0][12] + 'px!important;color: #' + data[0][13] +'!important;">' 
		                    + data[0][10] + ':' + this.y + '</span><br/>';
		                },
		                backgroundColor: {
		                    linearGradient: {
		                        x1: 0,
		                        y1: 0,
		                        x2: 0,
		                        y2: 1
		                    },
		                    stops: [
		                        [0, '#DDD'],
		                        [1, '#FFF']
		                    ]
		                }
		            }
		        }],
		        pane: {
		            startAngle: -150,
		            endAngle: 150
		        },            
		    
			    yAxis: [{
			    	min: (data[0][14] ? parseInt(data[0][14]) : 0),
		           	max: (data[0][15] ? parseInt(data[0][15]) : 1000),
		           	lineColor: '#' + data[0][count],
		           	tickColor: '#' + data[0][count],
		           	minorTickColor: '#' + data[0][count++],
		           	offset: -25,
		           	lineWidth: 2,
		            labels: {
		                distance: 10,
		                rotation: 'auto'
		            },
		            tickLength: 5,
		            minorTickLength: 5,
		            endOnTick: false
		        }]
		};
	}
	else if(data[0][0] == 'bubble'){
		var arr = [];
		for(var i = 0; i < data[2].length; i++){
			arr.push([data[2][i], data[2][i]]);
		}
		count = 0;
		options = {
	        chart: {
	           type: data[0][count++],
	           renderTo: 'chart' + index,
	           height:heightValue,
	           zoomType: 'xy'
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
				data : arr,
				marker: {
		             fillColor: {
		                 radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
		                 stops: [
		                     [0, 'rgba(255,255,255,0.5)'],
		                     [1, 'rgba(69,114,167,0.5)']
		                 ]
		             }
		        }
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
	            padding: 3,
	            itemMarginTop: 5,
	            itemMarginBottom: 5,	            
	            layout: 'vertical',
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
	            padding: 3,
	            itemMarginTop: 5,
	            itemMarginBottom: 5,	            
	            layout: 'vertical',
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
	var sizeOfConfig = data[0].length;
	if(index == 0 && sizeOfConfig > 18 && data[0][sizeOfConfig - 2])
	{
		formulaTextfield = EURB.RunReport.chartPanel.find('name', 'chart' + index +'_formula')[0];
		formulaButton = EURB.RunReport.chartPanel.find('name', 'chart' + index + '_formula_button')[0];
		
		formulaTextfield.setVisible(true);
		formulaButton.setVisible(true);
		formulaTextfield.setValue(data[0][sizeOfConfig - 1]);
	}
	return chart;
};

var chartsArr;

updateChartData = function(charts){
	chartsArr = new Array();
	if(EURB.RunReport.chartCount > 2){
		hValue = 150;
	}
	else{
		hValue = 300;
	}
	for(i = 0; i < charts.length; i++){
		chartsArr[i] = createChart(charts[i], i, hValue);
	}
	
};
	
Highcharts.testExport = function() {
	Highcharts.exportCharts(chartsArr);
}


Highcharts.getSVG = function(charts) {
    var svgArr = [],
        top = 0,
        width = 0;

    $.each(charts, function(i, chart) {
        var svg = chart.getSVG();
        svg = svg.replace('<svg', '<g transform="translate(0,' + top + ')" ');
        svg = svg.replace('</svg>', '</g>');

        top += chart.chartHeight;
        width = Math.max(width, chart.chartWidth);

        svgArr.push(svg);
    });

    return '<svg height="'+ top +'" width="' + width + '" version="1.1" xmlns="http://www.w3.org/2000/svg">' + svgArr.join('') + '</svg>';
};

/**
 * Create a global exportCharts method that takes an array of charts as an argument,
 * and exporting options as the second argument
 */
Highcharts.exportCharts = function(charts, options) {
    var form
        svg = Highcharts.getSVG(charts);

    // merge the options
    options = Highcharts.merge(Highcharts.getOptions().exporting, options);

    // create the form
    form = Highcharts.createElement('form', {
        method: 'post',
        action: options.url
    }, {
        display: 'none'
    }, document.body);

    // add the values
    Highcharts.each(['filename', 'type', 'width', 'svg'], function(name) {
        Highcharts.createElement('input', {
            type: 'hidden',
            name: name,
            value: {
                filename: options.filename || 'chart',
                type: options.type,
                width: options.width,
                svg: svg
            }[name]
        }, null, form);
    });
    //console.log(svg); return;
    // submit
    form.submit();

    // clean up
    form.parentNode.removeChild(form);
};

