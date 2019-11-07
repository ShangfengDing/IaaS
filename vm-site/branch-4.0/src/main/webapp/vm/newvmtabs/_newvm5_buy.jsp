<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

      <table class="formtable" cellpadding="0" cellspacing="0"><tr>
           <td>虚拟机名称：</td>
           <td><span id="vmNameHtml"></span></td>
           <td></td>
      </tr><tr>
           <td>硬件配置：</td>
           <td><span id="hdConfigHtml"></span></td>
           <td></td>
      </tr><tr>
            <td>付费套餐：</td>
            <td><span id="packageMoneyHtml"></span></td>
            <td></td>
      </tr><tr>
            <td>镜像列表：</td>
            <td><span id="imgIsoHtml"></span></td>
            <td></td>
      </tr><tr>
            <td>数据中心：</td>
            <td><span id="clusterHtml"></span></td>
            <td></td>
      </tr></table>
<div class="topmargin_20 centeralign">
<input type="button" class="sgraybutton" value="上一步" onclick="changeToTab('3')"/>
<input type="button" class="sgraybutton" value="提交" onclick=""/>
</div>
</html>