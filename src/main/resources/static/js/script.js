$(document).ready(function() {
    "use strict";

    function guardadoOK(data) {
        console.log(data);
        //traerDatosProblemas();
        console.log("lo guarda");
    }

    $("#btnCliente").on("click", function(event) {
        event.preventDefault();
        let idC = $("#idC").val();
        let nombre = $("#nombreCliente").val();
        let objeto = {
            "id": idC,
            "nombre": nombre
        };

        $.ajax({
            "url": "http://localhost:8080/clientes/",
            "method": "POST",
            "contentType": "application/json; charset=utf-8",
            "data": JSON.stringify(objeto),
            "dataType": "JSON",
            "success": guardadoOK,
            "error": function(xmlhr, r, error) {
                alert("Error al enviar");
            }
        })
    })

    $("#btnProducto").on("click", function(event) {
        event.preventDefault();
        let idP = $("#idP").val();
        let nombre = $("#nombreProducto").val();
        let precio = $("#precio").val();
        let objeto = {
            "id": idP,
            "nombre": nombre,
            "precio": precio
        };

        $.ajax({
            "url": "http://localhost:8080/productos/",
            "method": "POST",
            "contentType": "application/json; charset=utf-8",
            "data": JSON.stringify(objeto),
            "dataType": "JSON",
            "success": guardadoOK,
            "error": function(xmlhr, r, error) {
                alert("Error al enviar");
            }
        })
    })

    /*$("#btnRegistro").on("click", function(event) {
        event.preventDefault();
        let libreta = $("#libretaR").val();
        let carrera = $("#nombreR").val();
        let año = $("#anio").val();
        let objeto = {
            "libreta": libreta,
            "carrera": carrera,
            "año": año,
        };

        $.ajax({
            "url": "http://localhost:8080/Entregable3/rest/registro/" + libreta + "/" + carrera + "/" + año,
            "method": "POST",
            "contentType": "application/json; charset=utf-8",
            "data": JSON.stringify(objeto),
            "dataType": "JSON",
            "success": guardadoOK,
            "error": function(xmlhr, r, error) {
                alert("Error al enviar");
            }
        })
    })*/

    function cargarTablaClientes(data) {
        let html = "<tr><th>Id de Cliente</th><th>Nombre</th></tr>";
        for (let i = 0; i < data.length; i++) {
            html += "<tr> <td>" + data[i].id + "</td>";
            html += "<td>" + data[i].nombre + "</td></tr>";

        }
        $(".js-tabla").html(html);
    }

    function cargarTablaProductos(data) {
        let html = "<tr><th>Id de Producto</th><th>Nombre</th><th>Precio</th></tr>";
        for (let i = 0; i < data.length; i++) {
            html += "<tr> <td>" + data[i].id + "</td>";
            html += "<td>" + data[i].nombre + "</td>";
            html += "<td>" + data[i].precio + "</td></tr>";

        }
        $(".js-tabla").html(html);
    }

    $("#clientesGet").on("click", function(event) {
        event.preventDefault();
        $.ajax({
            "url": "http://localhost:8080/clientes/",
            "method": "GET",
            "contentType": "application/json; charset=utf-8",
            "dataType": "JSON",
            "success": cargarTablaClientes,
            "error": function(xmlhr, r, error) {
                console.log(error);
            }
        })
        $(".js-tabla").html("Cargando tabla...");
    })

    $("#productosGet").on("click", function(event) {
        event.preventDefault();
        $.ajax({
            "url": "http://localhost:8080/productos/",
            "method": "GET",
            "contentType": "application/json; charset=utf-8",
            "dataType": "JSON",
            "success": cargarTablaProductos,
            "error": function(xmlhr, r, error) {
                console.log(error);
            }
        })
        $(".js-tabla").html("Cargando tabla...");
    })

});