<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>云镜像管理</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <s:include value="../template/_head.jsp"/>
</head>
<body class="front-body">

<!--首部标签-->
<s:include value="../template/_banner.jsp"/>

<div class="front-inner">
   <div class="container">
            <!--云镜像管理提示标签-->
            <div class="col-md-12" style="padding:0">
                <div class="col-md-4 front-input-search" style="padding-left: 0">
                    <div style="display: inline-block;padding-left: 0">
                        <label class="head_line"></label>
                        <label>云镜像</label>
                    </div>
                </div>
            </div>
     
			<div class="front-toolbar other">
				<div class="front-toolbar-header clearfix">
					<button type="button" class="front-toolbar-toggle navbar-toggle"
						data-toggle="collapse" data-target="#iass-mygroup"
						aria-expanded="false" aria-controls="freeshare-reource-btns">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
				</div>
				<div id="iass-mygroup" class="front-btn-group collapse"
					data-toggle="buttons">
					<label id="group-all" class="btn btn-default front-no-box-shadow active" onclick="hidesearch()"
						data-group="all"> <input type="radio" name="options"
						autocomplete="off" checked>全部
					</label> 
					<label class="btn btn-default front-no-box-shadow" onclick="hidesearch()"
						data-group="create" style="box-shadow: none"> <input
						type="radio" name="options" autocomplete="off" checked> <span
						class="glyphicon glyphicon-asterisk"></span> 我分享的
					</label> 
					<label class="btn btn-default front-no-box-shadow" onclick="hidesearch()"
						data-group="manage" style="box-shadow: none"> <input
						type="radio" name="options" autocomplete="off" checked> <span
						class="glyphicon glyphicon-king"></span> 自定义的
					</label> 
					<label class="btn btn-default front-no-box-shadow" 
						data-group="search" style="box-shadow: none" onclick="hidetable()"> <input
						type="radio" name="options" autocomplete="off" checked> <span
						class="glyphicon glyphicon-search"></span> 搜索
					</label>			
				</div>
			</div>
			<div>
				<div id="freeshare-grouprel">
					<div class="panel panel-default hidden front-panel " id="back_search"
						style="margin-bottom: 10px" >
						<div class="panel-heading">
							<strong>云镜像搜索</strong>
						</div>
						<div class="panel-body">
							<div class="form-horizontal">
								<div class="form-group">
									<label class="col-md-1 control-label front-label"
										for="groupkeyword">关键字</label>
									<div class="col-md-11">
										<input id="groupkeyword" type="text"
											class="form-control front-no-box-shadow" placeholder="搜索关键字">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-1 control-label front-label"
										for="grouppermission">提供商</label>
									<div class="col-md-3">
										<select id="grouppermission"
											class="form-control front-no-radius front-no-box-shadow">
											<option value="0">--全部--</option>
											<option value="readwrite">亚马逊</option>
											<option value="readonly">阿里云</option>
										</select>
									</div>
									<label class="col-md-1 control-label front-label"
										for="grouptype">可用区</label>
									
									<div class="col-md-3">
										<select id="grouptype"
											class="form-control front-no-radius front-no-box-shadow">
											<option value="0">--全部--</option>
											<option value="private">华北区</option>
											<option value="normal">华东区</option>
										</select>
									</div>
									<label class="col-md-1 control-label front-label"
										for="grouppermission">操作系统</label>
									<div class="col-md-3">
										<select id="grouppermission"
											class="form-control front-no-radius front-no-box-shadow">
											<option value="0">--全部--</option>
											<option value="readwrite">Windows</option>
											<option value="readonly">Linux</option>
											<option value="readonly">Mac</option>
										</select>
									</div>
								</div>
								<div class="form-group">									
									<label class="col-md-1 control-label front-label"
										for="grouptype">系统性质</label>
									
										<div class="col-md-3">
											<select id="grouptype"
												class="form-control front-no-radius front-no-box-shadow">
												<option value="0">--全部--</option>
												<option value="private">64位</option>
												<option value="normal">32位</option>
											</select>
										</div>
									<label class="col-md-1 control-label front-label"
										for="grouptype">镜像类型</label>
									
										<div class="col-md-3">
											<select id="grouptype"
												class="form-control front-no-radius front-no-box-shadow">
												<option value="0">--全部--</option>
												<option value="private">公共镜像</option>
												<option value="normal">私有镜像</option>
											</select>
										</div>
									<div class="text-right col-sm-4">
										<button type="button" id="resetsearch" class="btn btn-default">重置</button>
										<button type="button" id="groupsearch" class="btn btn-primary">查找</button>
									</div>
								</div>
								
							</div>
						</div>
					</div>
					<div id = "show_data_area">
					<!--镜像信息-->
					  <div class="panel panel-default front-panel" id="back_table">
		                <div class="panel-body front-no-padding">
		                    <table class="table table-striped front-table"
		                           style="margin-bottom: 0px;font-size: 15px">
		                    <tr style="background-color: rgba(86,61,124,.15);color: black">
		                        <td class="col-sm-1">云镜像名称</td>
		                        <td class="col-sm-1">镜像容量</td>
		                        <td class="col-sm-2 branch_kind">
		                            <label style="padding-right: 10px">镜像类型</label>
		                        </td>
		                        <td class="col-sm-1">操作系统</td>
		                        <td class="col-sm-1">系统位数</td>
		                        <td class="col-sm-2">创建时间</td>
		                        <td class="col-sm-1">进度</td>
		                        <td class="col-sm-1">状态</td>
		                        <td class="col-sm-2" style="text-align:right;">操作</td>
		                    </tr>
		                        <tr>
		                            <td class="col-sm-1">
		                                <div>
		                                    <label id="mainName">rain</label>
		                                    <a data-toggle="front-modal" data-title="修改云镜像的名称" data-href="template/rename.jsp"><span class="glyphicon glyphicon-edit"></span></a>
		                                </div>
		                            </td>
		                            <td class="col-sm-1">40G</td>
		                            <td class="col-sm-2 branch_kind">
		                                <label>自定义镜像</label>
		                            </td>
		                            <td class="col-sm-1">
		                                <label>CentOS6</label>
		                            </td>
		                            <td class="col-sm-1">
		                                <label>64位</label>
		                            </td>
		                            <td class="col-sm-2">
		                                <div style="display: inline-block">
		                                    <label>2016-03-16</label>
		                                    <%--<label style="width: 5px"></label>--%>
		                                    <label>11:29:59</label>
		                                </div>
		                            </td>
		                            <td class="col-sm-1">
		                                <label>100%</label>
		                            </td>
		                            <td class="col-sm-1">
		                                <label>可用</label>
		                            </td>
		                            <td class="col-sm-1" style="text-align: right">
		                                <div class="btn-group">                                    
		                                    <a href="#">相关实例</a>	                                    
	                                        <a href="#" class="vmlist_op_dis">编辑描述</a><br/>
	                                        <a href="#">共享镜像</a> 
	                                        <a href="#" class="vmlist_op_dis">复制镜像</a>                                
		                                </div>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td class="col-sm-1">
		                                <div>
		                                    <label id="mainName">rain</label>
		                                    <a data-toggle="front-modal" data-title="修改云镜像的名称" data-href="template/rename.jsp"><span class="glyphicon glyphicon-edit"></span></a>
		                                </div>
		                            </td>
		                            <td class="col-sm-1">60G</td>
		                            <td class="col-sm-2 branch_kind">
		                                <label>自定义镜像</label>
		                            </td>
		                            <td class="col-sm-1">
		                                <label>CentOS6.5</label>
		                            </td>
		                            <td class="col-sm-1">
		                                <label>64位</label>
		                            </td>
		                            <td class="col-sm-2">
		                                <div style="display: inline-block">
		                                    <label>2016-03-16</label>
		                                    <label>11:29:59</label>
		                                </div>
		                            </td>
		                            <td class="col-sm-1">
		                                <label>100%</label>
		                            </td>
		                            <td class="col-sm-1">
		                                <label>可用</label>
		                            </td>
		                            <td class="col-sm-1" style="text-align: right">
		                                <div class="btn-group">
		                                    <a href="#">相关实例</a>	                                    
	                                        <a href="#" class="vmlist_op_dis">编辑描述</a><br/>
	                                        <a href="#">共享镜像</a>   
	                                        <a href="#" class="vmlist_op_dis">复制镜像</a>                                  		                                   
		                                </div>
		                            </td>
		                        </tr>
		                    </table>
		                </div>
		            </div>
		        	</div>
				</div>
			</div>
        <!--分页标签-->
        <div style="text-align: center" id="back_divPage">
            <ul id="pages" class="pagination">
                <li class="disabled"><a aria-label="Previous">&laquo;</a></li>
                <li class="active"><a>1</a></li>
                <%--<li><a aria-label="PreMore">...</a></li>--%>
                <li class="disabled"><a aria-label="Next">&raquo;</a></li>
            </ul>
        </div>
   	</div>
   	<!--底部标签-->
    <s:include value="../template/_footer.jsp"/>
</div>

</body>
<s:include value="../template/_global.jsp"/>
<script src="js/searchbox.js"></script>
</html>