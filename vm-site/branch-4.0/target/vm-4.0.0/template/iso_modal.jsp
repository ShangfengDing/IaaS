<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>
    ISO装机
</title>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
    <s:include value="../template/_head.jsp" />
</head>
<body>
<div class="modal-body">
    <select id="myselect" class="form-control">
    
    </select>
</div>
<div class="modal-footer">
    <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
    <a id="confirm" href="javascript:void(0)" data-dismiss="modal" class="btn btn-primary">确定</a> <!-- 注意按钮换行会导致多余的外补(margin) -->
</div>

</body>
</html>