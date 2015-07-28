<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Book Zone</title>
<!-- <link rel="shortcut icon" href="<c:url value='/images/icon.ico'/>"> -->
<link rel="stylesheet" href="<c:url value='/css/main.css'/>">
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
</head>
<body>
	<header>
		<img src="<c:url value='/images/logo.jpg'/>"
			alt="Book Zone Logo" width="58">
		<h1>Book Zone</h1>
		<h2>Quality Books For Low Prices!</h2>
	</header>
	<nav id="nav_bar">
		<ul>
			<li><a href="<c:url value='/admin'/>">Admin</a></li>
			<li><a href="<c:url value='/user/deleteCookies'/>">Delete Cookies</a></li>
			<li><a href="<c:url value='/order/showCart'/>">Show Cart</a></li>
		</ul>
	</nav>