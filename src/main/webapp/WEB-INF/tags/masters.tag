<%@ attribute name="par" type="com.elenakliuchka.repairagency.entity.Order"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items="${par.masters}" var="master">
       ${master.name}<br>
</c:forEach>