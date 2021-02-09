<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>--%>

<%--<page:applyDecorator name="main">--%>

<h2>An unexpected error has occurred</h2>

<p>
    Please report this error to your system administrator
    or appropriate technical support personnel.
    Thank you for your cooperation.
</p>

<img src="gfx/line-thin-black.png" width="100%" height="1" alt="" class="hr" />

<h3>Error Message</h3>

<p>{$exceptionMessage}</p>

<img src="gfx/line-thin-black.png" width="100%" height="1" alt="" class="hr" />

<h3>Technical Details</h3>

<!-- <p><textarea rows="50" cols="1000" style="width:100%"><s:property value="%{exceptionStack}"/></textarea></p> -->
