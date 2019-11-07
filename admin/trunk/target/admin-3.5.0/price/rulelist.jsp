<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" 
        + request.getServerPort() + request.getContextPath() %>/" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>计费设置 - 云海IaaS</title>
	<s:include value="/template/_head.jsp" />
</head>
<body>
    <div id="container">
        <s:include value="/template/_banner.jsp?menu=price" />
        <div id="inner">
            <s:include value="/price/_left.jsp?menu=price" />
            <div class="right">
                <div class="divline bottommargin_20">计费设置</div>
	            <div class="contentline">
	            <div class="tabcontainer">
	                <ul>
	                    <li>CPU计费</li><li>内存计费</li><li>硬盘计费</li>
	                    <li>带宽计费</li><li>套餐计费</li><li>手续费计费</li>
	                    <li>第三方计费</li>
					</ul>
                    <div class="tabdiv">
					    <s:if test="cpus == null or cpus.size() == 0">
					    <h2 class="centeralign">没有计费规则，快去创建吧</h2>
		                </s:if>
		                <s:else>
		                <table class="datatable" id="cpus">
		                    <tr>
		                        <td>集群</td>
		                        <td>规格</td>
		                        <td>包年（元）</td>
		                        <td>包月（元）</td>
		                        <td>包日（元）</td>
		                        <td>按小时（元）</td>
		                        <td>操作（元）</td>
		                    </tr>
		                    <s:iterator id="price" value="cpus" status="st">
		                    <tr id="tr<s:property value="#price.id"/>" >
		                        <td id="td_cluster_<s:property value="#price.clusterid"/>"><s:property value="clusterMap[#price.clusterid]"/></td>
		                        <td><s:property value="#price.cpu"/>核</td>
		                        <td><s:property value="#price.yearPrice"/></td>
		                        <td><s:property value="#price.monthPrice"/></td>
		                        <td><s:property value="#price.dayPrice"/></td>
		                        <td><s:property value="#price.hourPrice"/></td>
		                        <td><a class="blueletter" href="price/preupdaterule?pid=<s:property value="#price.id"/>&name=cpu" title="修改计费规则" rel="facebox">修改</a>
		                        <a class="blueletter leftmargin_10" href="javascript:void(0);" onclick="delRule(<s:property value="#price.id"/>)">删除</a>
		                        </td>
		                    </tr>
		                    </s:iterator>
		                </table>
		                <br />
		                </s:else>
		                <div class="rightalign">
		                    <a class="button" href="price/preaddrule?name=cpu" rel="facebox" title="新增配置">新增配置</a>
		                </div>
                    </div>
					<div class="tabdiv">
					    <s:if test="mems == null or mems.size() == 0">
                        <h2 class="centeralign">没有计费规则，快去创建吧</h2>
                        </s:if>
                        <s:else>
                        <table class="datatable" id="mems">
                            <tr>
                                <td>集群</td>
                                <td>规格</td>
                                <td>包年（元）</td>
                                <td>包月（元）</td>
                                <td>包日（元）</td>
                                <td>按小时（元）</td>
                                <td>操作（元）</td>
                            </tr>
                            <s:iterator id="price" value="mems" status="st">
                            <tr id="tr<s:property value="#price.id"/>" >
                                <td id="td_cluster_<s:property value="#price.clusterid"/>"><s:property value="clusterMap[#price.clusterid]"/></td>
                                <td><s:property value="#price.memory"/>G</td>
                                <td><s:property value="#price.yearPrice"/></td>
                                <td><s:property value="#price.monthPrice"/></td>
                                <td><s:property value="#price.dayPrice"/></td>
                                <td><s:property value="#price.hourPrice"/></td>
                                <td><a class="blueletter" href="price/preupdaterule?pid=<s:property value="#price.id"/>&name=mem" title="修改计费规则" rel="facebox">修改</a>
                                <a class="blueletter leftmargin_10" href="javascript:void(0);" onclick="delRule(<s:property value="#price.id"/>)">删除</a>
                                </td>
                            </tr>
                            </s:iterator>
                        </table>
                        <br />
                        </s:else>
                        <div class="rightalign">
                            <a class="button" href="price/preaddrule?name=mem" rel="facebox" title="新增配置">新增配置</a>
                        </div>
					</div>
					<div class="tabdiv">
					    <s:if test="hds == null or hds.size() == 0">
                        <h2 class="centeralign">没有计费规则，快去创建吧</h2>
                        </s:if>
                        <s:else>
                        <table class="datatable" id="hds">
                            <tr>
                                <td>集群</td>
                                <td>规格</td>
                                <td>包年（元）</td>
                                <td>包月（元）</td>
                                <td>包日（元）</td>
                                <td>按小时（元）</td>
                                <td>操作（元）</td>
                            </tr>
                            <s:iterator id="price" value="hds" status="st">
                            <tr id="tr<s:property value="#price.id"/>" >
                                <td id="td_cluster_<s:property value="#price.clusterid"/>"><s:property value="clusterMap[#price.clusterid]"/></td>
                                <td><s:property value="#price.harddisk"/>G</td>
                                <td><s:property value="#price.yearPrice"/></td>
                                <td><s:property value="#price.monthPrice"/></td>
                                <td><s:property value="#price.dayPrice"/></td>
                                <td><s:property value="#price.hourPrice"/></td>
                                <td><a class="blueletter" href="price/preupdaterule?pid=<s:property value="#price.id"/>&name=hd" title="修改计费规则" rel="facebox">修改</a>
                                <a class="blueletter leftmargin_10" href="javascript:void(0);" onclick="delRule(<s:property value="#price.id"/>)">删除</a>
                                </td>
                            </tr>
                            </s:iterator>
                        </table>
                        <br />
                        </s:else>
                        <div class="rightalign">
                            <a class="button" href="price/preaddrule?name=hd" rel="facebox" title="新增配置">新增配置</a>
                        </div>
					</div>
					<div class="tabdiv">
					    <s:if test="bds == null or bds.size() == 0">
                        <h2 class="centeralign">没有计费规则，快去创建吧</h2>
                        </s:if>
                        <s:else>
                        <table class="datatable" id="bws">
                            <tr>
                                <td>集群</td>
                                <td>规格</td>
                                <td>包年（元）</td>
                                <td>包月（元）</td>
                                <td>包日（元）</td>
                                <td>按小时（元）</td>
                                <td>操作（元）</td>
                            </tr>
                            <s:iterator id="price" value="bds" status="st">
                            <tr id="tr<s:property value="#price.id"/>" >
                                <td id="td_cluster_<s:property value="#price.clusterid"/>"><s:property value="clusterMap[#price.clusterid]"/></td>
                                <td><s:property value="#price.bandwidth"/>M</td>
                                <td><s:property value="#price.yearPrice"/></td>
                                <td><s:property value="#price.monthPrice"/></td>
                                <td><s:property value="#price.dayPrice"/></td>
                                <td><s:property value="#price.hourPrice"/></td>
                                <td><a class="blueletter" href="price/preupdaterule?pid=<s:property value="#price.id"/>&name=bw" title="修改计费规则" rel="facebox">修改</a>
                                <a class="blueletter leftmargin_10" href="javascript:void(0);" onclick="delRule(<s:property value="#price.id"/>)">删除</a>
                                </td>
		                    </tr>
                            </s:iterator>
                        </table>
                        <br />
                        </s:else>
                        <div class="rightalign">
                            <a class="button" href="price/preaddrule?name=bw" rel="facebox" title="新增配置">新增配置</a>
                        </div>
					</div>
					<div class="tabdiv">
					    <s:if test="packages == null or packages.size() == 0">
                        <h2 class="centeralign">没有计费规则，快去创建吧</h2>
                        </s:if>
                        <s:else>
                        <table class="datatable">
                            <tr>
                                <td width="60px">集群</td>
                                <td width="80px">名称</td>
		                        <td>描述</td>
		                        <td width="70px">配置</td>
		                        <td width="105px">价格</td>
		                        <td width="61px">操作</td>
                            </tr>
                            <s:iterator id="price" value="packages" status="st">
                            <tr id="tr<s:property value="#price.id"/>" >
                                <td id="td_cluster_<s:property value="#price.clusterid"/>"><s:property value="clusterMap[#price.clusterid]"/></td>
                                <td><s:property value="#price.name"/></td>
		                        <td><s:property value="#price.description" escape="false"/></td>
		                        <td><s:property value="#price.cpu"/>核CPU，<br><s:property value="#price.memory"/>G内存，<br><s:property value="#price.harddisk"/>G硬盘，<br><s:property value="#price.bandwidth"/>M带宽。</td>
		                        <td><s:property value="#price.hourPrice"/>元/小时，<br><s:property value="#price.dayPrice"/>元/天，<br><s:property value="#price.monthPrice"/>元/月，<br><s:property value="#price.yearPrice"/>元/年。</td>
                                <td><a class="blueletter" href="price/preupdaterule?pid=<s:property value="#price.id"/>&name=vmpackage" title="修改计费规则" rel="facebox">修改</a>
                                <a class="blueletter leftmargin_10" href="javascript:void(0);" onclick="delRule(<s:property value="#price.id"/>)">删除</a>
                            	</td>
							</tr>
                            </s:iterator>
                        </table>
                        <br />
                        </s:else>
                        <div class="rightalign">
                            <a class="button" href="price/preaddrule?name=vmpackage" rel="facebox" title="新增配置">新增配置</a>
                        </div>
					</div>
					<div class="tabdiv">
                        <s:if test="charges == null or charges.size() == 0">
                        <h2 class="centeralign">没有计费规则，快去创建吧</h2>
                        </s:if>
                        <s:else>
                        <table class="datatable" id="charges">
                            <tr>
                                <td>集群</td>
                                <td>包年（元）</td>
                                <td>包月（元）</td>
                                <td>包日（元）</td>
                                <td>按小时（元）</td>
                                <td>操作（元）</td>
                            </tr>
                            <s:iterator id="price" value="charges" status="st">
                            <tr id="tr<s:property value="#price.id"/>" >
                                <td id="td_cluster_<s:property value="#price.clusterid"/>"><s:property value="clusterMap[#price.clusterid]"/></td>
                                <td><s:property value="#price.yearPrice"/></td>
                                <td><s:property value="#price.monthPrice"/></td>
                                <td><s:property value="#price.dayPrice"/></td>
                                <td><s:property value="#price.hourPrice"/></td>
                                <td><a class="blueletter" href="price/preupdaterule?pid=<s:property value="#price.id"/>&name=charge" title="修改计费规则" rel="facebox">修改</a>
                                <a class="blueletter leftmargin_10" href="javascript:void(0);" onclick="delRule(<s:property value="#price.id"/>)">删除</a>
                                </td>
                            </tr>
                            </s:iterator>
                        </table>
                        <br />
                        </s:else>
                        <div class="rightalign">
                            <a class="button" href="price/preaddrule?name=charge" rel="facebox" title="新增配置">新增配置</a>
                        </div>
                    </div>
                    <div class="tabdiv">
					    <s:if test="instancetype == null or instancetype.size() == 0">
                        <h2 class="centeralign">没有计费规则，快去创建吧</h2>
                        </s:if>
                        <s:else>
                        <table class="datatable">
                            <tr>
                                <td width="60px">集群</td>
                                <td width="80px">名称</td>
		                        <td>描述</td>
		                        <td width="70px">配置</td>
		                        <td width="105px">价格</td>
		                        <td width="61px">操作</td>
                            </tr>
                            <s:iterator id="price" value="instancetype" status="st">
                            <tr id="tr<s:property value="#price.id"/>" >
                                <td id="td_cluster_<s:property value="#price.clusterid"/>"><s:property value="clusterMap[#price.clusterid]"/></td>
                                <td><s:property value="#price.name"/></td>
		                        <td><s:property value="#price.description" escape="false"/></td>
		                        <td><s:property value="#price.cpu"/>核CPU，<br><s:property value="#price.memory"/>G内存，<br><s:property value="#price.harddisk"/>G硬盘，<br><s:property value="#price.bandwidth"/>M带宽。</td>
		                        <td><s:property value="#price.hourPrice"/>元/小时，<br><s:property value="#price.dayPrice"/>元/天，<br><s:property value="#price.monthPrice"/>元/月，<br><s:property value="#price.yearPrice"/>元/年。</td>
                                <td><a class="blueletter" href="price/preupdaterule?pid=<s:property value="#price.id"/>&name=instancetype" title="修改计费规则" rel="facebox">修改</a>
                                <a class="blueletter leftmargin_10" href="javascript:void(0);" onclick="delRule(<s:property value="#price.id"/>)">删除</a>
                            	</td>
							</tr>
                            </s:iterator>
                        </table>
                        <br />
                        </s:else>
                        <div class="rightalign">
                            <a class="button" href="price/preaddrule?name=instancetype" rel="facebox" title="新增配置">新增配置</a>
                        </div>
					</div>
	            </div>
	            </div>
            </div>
        </div>
        <s:include value="/template/_footer.jsp"></s:include>
    </div>
    <s:include value="/template/_common_js.jsp"></s:include>
<script>
function delRule(pid){
	//alert(rids);
    if(confirm("确认删除该计费规则？")){
        showLoading();
        $.ajax({
            url:"price/delrule",  
            type:"post",
            //async:false,
            data:{pid:pid},  
            success:function(data) {
            	//alert(data.result);
                if(data.result=="success"){
                    $("#tr"+data.pid).remove();
                    hideLoading();
                    fillTipBox("success","删除计费规则成功");
                }else{
                    hideLoading();
                    fillTipBox("error","删除计费规则失败");
                }   
            },
            error:function(data){
                hideLoading();
                fillTipBox("error","删除计费规则失败");
            }
        });
    };
};
</script>
</body>
</html>