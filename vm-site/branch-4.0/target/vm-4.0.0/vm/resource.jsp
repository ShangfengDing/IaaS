<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<!-- 资源修改的模态框 -->
<div class="modal fade" id="sourceModal" tabinde="-1" role="dialog"
	aria-labelledby="souceModalLabel" aria-hidden="true" style="z-index:-1;display:block;">
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header modalheader">
			<button class="close"
				aria-hidden="true" onclick="sourcemdlClose()">&times;</button>
			<h4 class="modal-title">资源修改(将关闭主机)</h4>
		</div>
		<div class="modal-body front-last-no-margin">
			<!-- 修改主机规格 -->
			<div class="row" id="vmType">
                <div class="col-md-2">
                    <h5 class="label-pos2">主机规格</h5>
                </div>
                <div class="col-md-10">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default"
                                id="instanceType-disp">请选择版本</button>
                        <button type="button"
                                class="btn btn-default dropdown-toggle"
                                data-toggle="dropdown" aria-haspopup="true"
                                aria-expanded="false">
                            <span class="caret"></span> <span class="sr-only">ToggleDropdown</span>
                        </button>
                        <ul class="dropdown-menu" id="instanceType-list">
                            <li><a href="javascript:void(0)">无</a></li>
                        </ul>
                    </div>

                </div>
            </div>
			<!-- 修改带宽 -->
			<div class="row hidden" id="vmBand">
			    <div class="col-md-2 label-gutter">
			        <h5 class="label-pos2">带宽</h5>
			    </div>
			    <div class="col-md-10" style="padding-left: 26px;margin-top:6px">
			        <div class="front-slider noselect">
			            <input id="bandWidth" type="text"
			                   data-slider-handle="round"
			                   data-provide="slider"
			                   data-slider-ticks="[0,2,3,5,10,20,50]"
			                   data-slider-ticks-labels='["0Mbps", "2Mbps", "3Mbps","5Mbps","10Mbps","20Mbps","50Mbps"]'
			                   data-slider-tooltip="hide"
			                   value="0"
			                   onchange="changeBand(this.value)"/>
			        </div>
			    </div>
			</div>
			<div id="showprice" class="row hidden">
				<div class="col-md-2 label-gutter"><h5 class="label-pos2">修改后</h5></div>
				<div class="col-md-10"><h5 id="finalprice"></h5></div>
			</div>
		</div>
		<div class="modal-footer modalfooter">
			<button type="button" class="btn btn-default" onclick="sourcemdlClose()">关闭</button>
            <button type="button" class="btn btn-primary" onclick="modifySource()"> 确定</button>
		</div>
	</div>
</div>
</div>