<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 模态框（创建快照） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header" style="background-color:#f5f5f5;border-top-left-radius: 10px;
   				 border-top-right-radius: 10px;">
            <button type="button" class="close" 
            	aria-hidden="true" onclick="shotmodalclear()">&times;</button>
            <h4 class="modal-title" id="myModalLabel">创建快照</h4>
         </div>
         <div class="modal-body">  
        	<input type="hidden" id="diskId" />
			<input type="hidden" id="providerEn" />
			<input type="hidden" id="appName"/>
			<input type="hidden" id="userIds"/>
			<input type="hidden" id="regionId" />
			<input type="hidden" id="userEmail" />
			 <input type="hidden" id="zoneId"/>
			 <div id="newNameTr" class="form-group">
				 <label>快照名称</label>
				 <input class="form-control" type="text"  id="displayName" placeholder="请输入1-20个字"/>
			 </div>
			 <div id="newDesciptionTr" class="form-group">
				 <label>快照描述</label>
				 <textarea class="form-control" id="displayDescription" placeholder="请输入1-50个字" rows="3"></textarea>

			 </div>
            <%--<table>--%>
				<%--&lt;%&ndash;<tr id="newNameTr">&ndash;%&gt;--%>
					<%--&lt;%&ndash;<td width="120px" style="padding-left:20px">快照名称</td>&ndash;%&gt;--%>
					<%--&lt;%&ndash;<td><input type="text" class="editinput" id="displayName" placeholder="请输入1-20个字"/>&ndash;%&gt;--%>
					<%--&lt;%&ndash;<span class="redletter leftmargin_5" id="error1_edit"></span></td>&ndash;%&gt;--%>
				<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
				<%--&lt;%&ndash;<tr id="newDesciptionTr">&ndash;%&gt;--%>
					<%--&lt;%&ndash;<td width="120px" style="padding-left:20px">快照描述</td>&ndash;%&gt;--%>
					<%--&lt;%&ndash;<td><input type="text" class="editinput" style="height:100px" id="displayDescription" placeholder="请输入1-50个字"/>&ndash;%&gt;--%>
					<%--&lt;%&ndash;<span class="redletter leftmargin_5" id="error2_edit"></span></td>&ndash;%&gt;--%>
				<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
			<%--</table>--%>
         </div>
         <div class="modal-footer" style="background-color:#f5f5f5;border-bottom-left-radius: 10px;
  				  border-bottom-right-radius: 10px;">
            <button type="button" class="btn btn-default" onclick="shotmodalclear()">关闭</button>
            <button type="button" class="btn btn-primary" onclick="submitCheck()"> 确定</button>
         </div>
      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>