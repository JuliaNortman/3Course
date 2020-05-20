<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="commons/header.jsp"%>
<c:if test="${empty sessionScope.user}">
    <h3 class="reg-header">Registration</h3>
    <form action="${pageContext.request.contextPath}/account?op=registration" method="post">
        <table class="reg-table">
            <tr class="reg-row">
                <td class="reg-node">Login:</td>
                <td>
                    <input name="username" class="reg-inp"/>
                </td>
            </tr>
            <tr class="reg-row">
                <td class="reg-node">Password:</td>
                <td>
                    <input class="reg-inp" type="password" name="password"/>
                </td>
            </tr>
            <tr class="reg-row">
                <td class="reg-node">Phone:</td>
                <td>
                    <input name="phone" class="reg-inp"/>
                </td>
            </tr>
            <tr class="reg-row">
                <td class="reg-node">Address:</td>
                <td><input name="address" class="reg-inp"/></td>
            </tr>
            <tr class="reg-row">
                <td class="reg-node">Email:</td>
                <td>
                    <input name="email" class="reg-inp"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input class="reg-btn" type="submit" value="Register"
                           onmouseover="this.style.background= 'dodgerblue'" onmouseout="this.style.background='darkblue'"/>
                </td>
            </tr>
        </table>
    </form>
</c:if>
<%@ include file="commons/footer.jsp"%>