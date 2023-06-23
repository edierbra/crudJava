<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Lista de Videojuegos</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
                <div>
                    <a href="https://www.unicauca.edu.co" class="navbar-brand"> Aplicación Crud Videojuegos </a>
                </div>
                <ul class="navbar-nav">
                    <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Lista VideoJuegos</a></li>
                </ul>
            </nav>
        </header>
        <br>
        <div class="row">
            <!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->
            <div class="container">
                <h3 class="text-center">Lista de VideoJuegos</h3>
                <hr>
                <div class="container text-left">
                    <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Nuevo VideoJuego</a>
                </div>
                <br>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Titulo</th>
                            <th>Precio</th>
                            <th>Tamaño</th>
                            <th>Categoria</th>
                            <th>Plataforma</th>
                            <th>Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- for (Todo todo: todos) { -->
                        <c:forEach var="videogame" items="${listVideoGame}">
                            <tr>
                                <td>
                                    <c:out value="${videogame.idVideojuegoPk}" />
                                </td>
                                <td>
                                    <c:out value="${videogame.titulo}" />
                                </td>
                                <td>
                                    <c:out value="${videogame.precio}" />
                                </td>
                                <td>
                                    <c:out value="${videogame.tamano}" />
                                </td>
                                <td>
                                    <c:out value="${videogame.idCategoriaFk}" />
                                </td>
                                <td>
                                    <c:out value="${videogame.idPlataformaFk}" />
                                </td>
                                <td><a href="edit?idVideojuegoPk=<c:out value='${videogame.idVideojuegoPk}' />">Editar</a>
                                    &nbsp;&nbsp;&nbsp;&nbsp; <a href="delete?idVideojuegoPk=<c:out value='${videogame.idVideojuegoPk}' />">Eliminar</a></td>
                            </tr>
                        </c:forEach>
                        <!-- } -->
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>

<script>
    // Verificar si el mensaje de eliminación existe en el objeto request
    var deleteMessage = "${requestScope.deleteMessage}";

    if (deleteMessage) {
        // Mostrar mensaje de eliminación utilizando SweetAlert
        if (deleteMessage === "error_1") {
            swal({
                title: "Exito",
                text: "Se elimino correctamente el Videojuego",
                icon: "success"
            });
        } else if(deleteMessage === "error_2"){
            swal({
                title: "Error",
                text: "Error al eliminar el Videojuego, intentelo otra vez",
                icon: "error"
            });
        }
    }
</script>




