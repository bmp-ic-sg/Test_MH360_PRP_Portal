<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="application" var="bundle" scope="session" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">
<link rel="icon" href="<fmt:message key="context.path" bundle="${bundle}" />/assets/images/favicon.ico">
<title>Patient Registration</title>
<link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/bootstrap.min.css">
<link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/app.css?deploy=<fmt:message key="static.suffix" bundle="${bundle}" />">
<link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/countrySelect.min.css">
<link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/intlTelInput.min.css">
<script>
	var CONTEXT_URL = "<fmt:message key="context.path" bundle="${bundle}" />";
</script>