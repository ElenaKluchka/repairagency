<%@ attribute name="dat" type="java.time.LocalDateTime"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
	
<fmt:parseDate value="${dat}" type="date" pattern="yyyy-MM-dd"
	var="parsedDate" />
<fmt:formatDate value="${parsedDate}" type="date" pattern="dd.MM.yyyy"
	var="stdDatum" />
${stdDatum }
