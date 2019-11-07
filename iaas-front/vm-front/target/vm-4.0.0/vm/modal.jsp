<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<!-- 模态框（续费） -->
<div class="modal fade" id="priceModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:-1;display:block">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header modalheader">
            <button type="button" class="close" 
            	aria-hidden="true" onclick="modalclear()">&times;</button>
            <h4 class="modal-title" id="priceModalLabel">续费</h4>
         </div>
         <div class="modal-body">  
        	<div class="panel-body front-last-no-margin">
				<div class="row">
					<div class="col-md-2 col-sm-3 col-xs-4">
						<h5 class="label-pos2">付费方式</h5>
					</div>
					<div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
						<div class="front-toolbar other">
							<div class="front-toolbar-header clearfix">
								<button type="button"
									class="front-toolbar-toggle navbar-toggle"
									data-toggle="collapse" data-target="#payment">
									<span class="icon-bar"></span> <span class="icon-bar"></span>
									<span class="icon-bar"></span>
								</button>
							</div>
							<div id="payment" class="front-btn-group collapse">
								<button id="yearbtn" value="PayByYear" class="btn btn-default front-no-box-shadow active" >包年</button>
								<button id="monthbtn" value="PayByMonth" class="btn btn-default front-no-box-shadow">包月</button> 
								<button id="daybtn" value="PayByDay" class="btn btn-default front-no-box-shadow">包天</button> 
							</div>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top: 20px">
					<div class="col-md-2 label-gutter">
						<h5 class="label-pos2">购买时长</h5>
					</div>
					<div id="slider_parent" class="col-md-10" style="padding-left: 26px;height:40px">
						<!-- 按年续费 -->
						<div id="year-slider" class="front-slider noselect">
						    <input id="year-ex" style="width:80%;margin-bottom: 24px" data-slider-handle="round" type="text" 
						    	data-slider-ticks="[1,2,3]"
						    	data-slider-ticks-labels='["1", "2", "3"]'
						    	data-slider-min="1" data-slider-max="3" data-slider-step="1" data-slider-value="0" data-slider-tooltip="hide"/>
						    <span id="year-plus" style="cursor: pointer" class="glyphicon glyphicon-plus"></span>
						    <div style="display: inline-block; margin-left: 4px; margin-right: 4px;">
						        <span class="slider-label" id="year-label">1</span><span id="unit">年</span>
						    </div>
						    <span id="year-minus" style="cursor: pointer" class="glyphicon glyphicon-minus"></span>
						</div>
						<!-- 按月续费 -->
						<div id="month-slider" class="front-slider noselect">
						    <input id="month-ex" style="width:80%;margin-bottom: 24px" data-slider-handle="round" type="text" 
						    	data-slider-ticks="[1,2,3,4,5,6,7,8,9,10,11,12]"
						    	data-slider-ticks-labels='["1", "2", "3","4","5","6","7","8","9","10","11","12"]'
						    	data-slider-min="1" data-slider-max="12" data-slider-step="1" data-slider-value="0" data-slider-tooltip="hide"/>
						    <span id="month-plus" style="cursor: pointer" class="glyphicon glyphicon-plus"></span>
						    <div style="display: inline-block; margin-left: 4px; margin-right: 4px;">
						        <span class="slider-label" id="month-label">1</span><span id="unit">月</span>
						    </div>
						    <span id="month-minus" style="cursor: pointer" class="glyphicon glyphicon-minus"></span>
						</div>	
						<!-- 按日续费-->
						<div id="day-slider" class="front-slider noselect">
						    <input id="day-ex" style="width:80%;margin-bottom: 24px" data-slider-handle="round" type="text"
						    	data-slider-ticks="[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]" 
						    	data-slider-ticks-labels='["1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"]'
						    	data-slider-min="1" data-slider-max="20" data-slider-step="1" data-slider-value="0" data-slider-tooltip="hide"/>
						    <span id="day-plus" style="cursor: pointer" class="glyphicon glyphicon-plus"></span>
						    <div style="display: inline-block; margin-left: 4px; margin-right: 4px;">
						        <span class="slider-label" id="day-label">1</span><span id="unit">日</span>
						    </div>
						    <span id="day-minus" style="cursor: pointer" class="glyphicon glyphicon-minus"></span>
						</div>					
					</div>			
				</div>
			</div>
			<div style="text-align:center">
				<div>已选择：<s:property value="instanceType[0]"/>个CPU,<s:property value="instanceType[1]"/>G内存,<s:property value="instanceType[2]"/>G硬盘,<s:property value="instanceType[3]"/>M带宽,<span>共计</span><span class="slider-label" id="price"></span><span>元</span></div>			</div>
        	</div>
         <div class="modal-footer modalfooter">
            <button type="button" class="btn btn-default" onclick="modalclear()">关闭</button>
            <button type="button" class="btn btn-primary" onclick="comfirmBtn()"> 确定</button>
         </div>
      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>