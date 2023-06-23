<%-- 
    Document   : edit_games
    Created on : 22/06/2023, 10:05:56 p. m.
    Author     : juan0
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Formulario Cliente</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<header>
<nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
<div>
<a href="https://www.unicauca.edu.co" class="navbar-brand"> Aplicación Crud VideoGame </a>
</div>
<ul class="navbar-nav">
<li><a href="<%=request.getContextPath()%>/list" class="nav-link">Lista Videojuegos</a></li>
</ul>
</nav>
</header>
<br>
<div class="container col-md-5">
<div class="card">
<div class="card-body">
<c:if test="${videojuego != null}">
<form action="update" method="post">
</c:if>

<c:if test="${videojuego == null}">
<form action="insert" method="post">
</c:if>
<caption>
<h2>
<c:if test="${videojuego != null}">
Editar Videojuego
</c:if>

<c:if test="${videojuego == null}">

Nuevo Videojuego
</c:if>
</h2>
</caption>

<c:if test="${videojuego != null}">

<input type="hidden" name="id" value="<c:out value='${videojuego.idVideojuegoPk}' />" />
</c:if>
<fieldset class="form-group">
<label>Titulo</label> <input type="text" value="<c:out value='${videojuego.titulo}' />"
class="form-control" name="titulo" required="required">
</fieldset>
<fieldset class="form-group">
<label>Precio</label> <input type="text" value="<c:out value='${videojuego.precio}' />"
class="form-control" name="precio">
</fieldset>
<fieldset class="form-group">
<label>Tamaño</label> <input type="text" value="<c:out value='${videojuego.tamano}'
/>" class="form-control" name="tamano">
</fieldset>
<c:if test="${videojuego != null}">
<fieldset>
    <label>Categoría</label>
        <select class="form-control" name="categoria">
            <c:forEach var="category" items="${listCategories}">
                <c:choose>
                    <c:when test="${category.nombre == videojuego.idCategoriaFk}">
                        <option value="${category.idCategoriaPk}" selected>${videojuego.idCategoriaFk}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${category.idCategoriaPk}">${category.nombre}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
</fieldset>
<fieldset>
    <label>Plataforma</label>
    <select class="form-control" name="plataforma">
        <c:forEach var="platform" items="${listPlatforms}">
            <c:choose>
                <c:when test="${platform.nombre == videojuego.idPlataformaFk}">
                    <option value="${platform.idPlataformaPk}" selected>${videojuego.idPlataformaFk}</option>                
                </c:when>
                <c:otherwise>
                    <option value="${platform.idPlataformaPk}">${platform.nombre}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
</fieldset>
</c:if>

<c:if test="${videojuego == null}">
<fieldset>
    <label>Categoría</label>
        <select class="form-control" name="categoria">            
        <c:forEach var="category" items="${listCategories}">
            <option value="${category.idCategoriaPk}">${category.nombre}</option>
        </c:forEach>    
        </select>
</fieldset>
<fieldset>
    <label>Plataforma</label>
    <select class="form-control" name="plataforma">
        <c:forEach var="platform" items="${listPlatforms}">
            <option value="${platform.idPlataformaPk}">${platform.nombre}</option>
        </c:forEach>
    </select>
</fieldset>
</c:if>
<br>
<div class="container">
  <div class="row">
    <div class="col-md-6">
      <button type="submit" class="btn btn-success">Guardar</button>
    </div>
    <div class="col-md-6 text-right">
      <button type="button" class="btn btn-secondary" onclick="window.history.back()">Devolver</button>
    </div>
  </div>
</div>

</form>
</div>
</div>
</div>
</body>
</html>