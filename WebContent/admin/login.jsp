<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
  <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http=//www.w3.org/TR/html4/loose.dtd">
  <html>
    
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <%@include file="/admin/included.jsp" %>
        <title>
          系统管理登陆
        </title>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/admin/login.js"
        charset=UTF-8>
        </script>
    </head>
    
    <body>
      <div id="login">
        <div id="login_panel">
          <form id="login_form">
            <table style="margin-top:20px;">
              <tr>
                <td height="50" width="40%" align="center">
                  帐&nbsp;&nbsp;&nbsp;&nbsp;号:
                </td>
                <td>
                  <input type="text" class="easyui-validatebox" required="true" id="userId"
                  size="25" />
                </td>
              </tr>
              <tr>
                <td height="50" width="40%" align="center">
                  密&nbsp;&nbsp;&nbsp;&nbsp;码:
                </td>
                <td>
                  <input type="password" class="easyui-validatebox" required="true" id="password"
                  size="25" />
                </td>
              </tr>
              <tr>
                <td height="70" width="40%" align="right">
                  <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok"
                  onclick="login();">
                    登陆
                  </a>
                </td>
                <td align="right">
                  <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
                  onclick="reset();">
                    清空
                  </a>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </div>
    </body>
  
  </html>