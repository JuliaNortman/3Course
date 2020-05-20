<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="commons/header.jsp"%>
<h3 class="reg-header">Log in</h3>
<form action="${pageContext.request.contextPath}/account?op=login" method="post">
    <table class="reg-table" style="width: 30%; left: 35%">
        <tr class="reg-row">
            <td class="reg-node">Login:</td>
            <td>
                <input name="username" class="reg-inp"/>
            </td>
        </tr>
        <tr class="reg-row">
            <td class="reg-node">Password:</td>
            <td>
                <input type="password" name="password" class="reg-inp"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Log in" class="reg-btn"
                       onmouseover="this.style.background= 'dodgerblue'" onmouseout="this.style.background='darkblue'"/>
            </td>
        </tr>
    </table>
</form>
<%@ include file="commons/footer.jsp"%>