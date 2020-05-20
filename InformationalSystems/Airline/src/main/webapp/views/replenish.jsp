<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="commons/header.jsp"%>
<h3 class="reg-header">Balance replenish</h3>
<form action="${pageContext.request.contextPath}/user?op=replenishAccount" method="post">
    <table class="reg-table" style="width: 30%; left: 35%">
        <tr class="reg-row">
            <td class="reg-node">Enter amount of money:</td>
            <td>
                <input name="money" class="reg-inp"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Replenish" class="reg-btn"
                       onmouseover="this.style.background= 'dodgerblue'" onmouseout="this.style.background='darkblue'"/>
            </td>
        </tr>
    </table>
</form>
<%@ include file="commons/footer.jsp"%>