//监控页面加载时，自动加载“本天”的数据
$(function(){  
	/*$("#day").click();  */
	var hostId = $("#hostId").val();
	updateMonitor('day', hostId);
});
//监控操作
function updateMonitor(type, hostName) {
	var cpuChartOptions = configChartOptions('cpuhighcharts', 'CPU使用率(%)');
	var memChartOptions = configChartOptions('memhighcharts', '内存占用率(%)');
	var diskChartOptions = configChartOptions('diskhighcharts', '硬盘占用率(%)');
	var ioChartOptions = configChartOptions('iohighcharts', '磁盘读写(MB/s)');
	var netChartOptions = configChartOptions('nethighcharts', '网络收发(MB/S)');
	var loadChartOptions = configChartOptions('loadhighcharts', '平均负载');
    //var chart;
    var xstep;
    var url="system/hostmonitor";   
    showLoading();
    $.post(url, {type:type,hostName:hostName}, function(data){
    	 hideLoading();
    	 if(type=="day"){
	    	 /*$("button.selected").removeClass("selected");
	    	 $("#day").addClass("selected");   	*/
	    	 xstep = Math.ceil(data.time.length / 10);
	     }/*else if(type=="month"){
    	    	$("button.selected").removeClass("selected");
    	    	$("#month").addClass("selected");
    	    	xstep = null;
    	    }else{
    	    	$("button.selected").removeClass("selected");
    	    	$("#year").addClass("selected");
    	    	xstep = null;
    	    }*/	 
    	 var tip;
    	 if(type=="day"){
    		 tip='<b>';
    	 }/*else if(type=="month"){
    		 tip='<b>'+ data.current + '-';
    	 }else{
    		 tip='<b>'+ data.current + '-';
    	 }*/
         //cpu使用率
    	 cpuChartOptions.series=[{
             name: 'CPU使用率',
             data: data.cpuPercent,
             marker: {
                 enabled: false
             }
         }];    
         
         cpuChartOptions.tooltip = {
             formatter: function() {
                     return tip + this.x + ' ' + this.series.name +': '+ this.y ;
             }
         };
         cpuChartOptions.xAxis={ 		 
       		  categories:data.time,
       	 	  labels:{
                     step:xstep,
                     align:'right'
                 }
         };
         //内存占用率
         memChartOptions.series=[{
            name: '内存占用率',
            data: data.memPercent,
            marker: {
                enabled: false
            }
         }];    
        
         memChartOptions.tooltip = {
            formatter: function() {
                    return tip + this.x + ' ' + this.series.name +': '+ this.y ;
            }
         };
         memChartOptions.xAxis={ 		 
      		  categories:data.time,
      		  labels:{
                    step:xstep,
                    align:'right'
                }
         };
         //硬盘占用率
         diskChartOptions.series=[{
            name: '硬盘占用率',
            data: data.diskPercent,
            marker: {
                enabled: false
            }
         }];    
        
         diskChartOptions.tooltip = {
            formatter: function() {
                    return tip + this.x + ' ' + this.series.name +': '+ this.y ;
            }
         };
         diskChartOptions.xAxis={ 		 
      		  categories:data.time,
      		  labels:{
                    step:xstep,
                    align:'right'
                }
         };
         //平均负载
         loadChartOptions.series=[{
            name: '平均负载',
            data: data.loadaverage,
            marker: {
                enabled: false
            }
         }];    
        
         loadChartOptions.tooltip = {
            formatter: function() {
                    return tip + this.x + ' ' + this.series.name +': '+ this.y ;
            }
         };
         loadChartOptions.xAxis={ 		 
      		  categories:data.time,
      		  labels:{
                    step:xstep,
                    align:'right'
                }
         };
         //硬盘读写
         ioChartOptions.series=[{
            name: '磁盘读出',
            data: data.diskReadRate,
            marker: {
                enabled: false
            }
         },{
       	    name: '磁盘写入',
            data: data.diskWriteRate,
            marker: {
                enabled: false
            }
        }];    
        
        ioChartOptions.tooltip = {
            formatter: function() {
           	 return tip + this.x + ' ' + this.series.name +': '+ this.y ;
            }
        };
        ioChartOptions.xAxis={ 		 
      		  categories:data.time,
      		  labels:{
                    step:xstep,
                    align:'right'
              }
        };
        //网络收发
        netChartOptions.series=[{
            name: '网络接收',
            data: data.netInPercent,
            marker: {
                enabled: false
            }
        },{
       	    name: '网络发出',
            data: data.netOutPercent,
            marker: {
                enabled: false
            }
        }];    
        
        netChartOptions.tooltip = {
            formatter: function() {
           	 return tip + this.x + ' ' + this.series.name +': '+ this.y ;
            }
        };
        netChartOptions.xAxis={ 		 
      		  categories:data.time,
      		  labels:{
                    step:xstep,
                    align:'right'
                }
        };
         chart = new Highcharts.Chart(cpuChartOptions);
         chart = new Highcharts.Chart(memChartOptions);
         chart = new Highcharts.Chart(diskChartOptions);
         chart = new Highcharts.Chart(loadChartOptions);
         chart = new Highcharts.Chart(ioChartOptions);
         chart = new Highcharts.Chart(netChartOptions);
    });
} 
function configChartOptions(holderid, title) {
	var chartOptions = {
	        chart: {
	            renderTo: holderid,
	            defaultSeriesType: 'line',
	            height:260,
	            margin:[30, 30, 60, 60]
	        },
	        legend: {
	            layout: 'vertical',
	            style: {
	                bottom: '0px',
	                right:'0px'
	            }
	        },
	        credits : {
	            enabled:false
	        },
	        title: {
	            text: '',
	            style: {
	                margin: '10 0 0 0' // center it
	            }
	        },
	        yAxis: {
	            min:0,
	            title: {
	                text: title
	            }
	        },
	        xAxis: {	        	
	            categories:[],
	        },
	        series:[]  
	    };
	    return chartOptions;
}
