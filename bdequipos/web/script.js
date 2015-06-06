//script desplegable formulario estadisticas
$(document).ready(function () {
    var location = window.location;
    file = location['pathname'].split("/")
    file = file[file.length - 1]
    console.debug(file)
    var found = false;
    $("#tab-container a").each(function(){
        var href = $(this).attr("href");
        if(href==file){
            $(this).parent().addClass("selected");
            found = true;
        }
    });
});
//funcion alerta confirmacion borrar usuario
function confirmdelete(e) {
    var r = confirm("¿Desea borrar su cuenta?");
    if (r == true) {
        window.location.href = e.attr('href')
    }
}
