/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import Model.Dao.UsuarioDao;
import Model.Entity.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Marles
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
@Path("/apiusuario")
public class UsuarioResource {

    UsuarioDao usuarioDao = new UsuarioDao();

    @GET
    @Path("/usuario")
    @Produces(MediaType.APPLICATION_JSON)

    public Response consultar() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioDao.consultar();
        return Response
                .status(200)
                .entity(usuarios)
                .build();
    }

    @GET
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarId(@PathParam("id") String id) {
        try {
            Usuario usuario = new Usuario(id);
            usuario = usuarioDao.consultarId(usuario);
            return Response
                    .status(200)
                    .entity(usuarioDao.consultarId(usuario))
                    .build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }

    }

    @POST
    @Path("/usuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(Usuario usuario) {
        try {
            usuarioDao.insertar(usuario);
            return Response.status(Response.Status.CREATED).entity(usuario).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrar(@PathParam("id") String id) {
        Usuario usuario = new Usuario(id);
        int i = usuarioDao.borrar(usuario);
        if (i == 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Usuario not found")
                    .build();
        } else {
            return Response.ok("Correcto").build();
        }
    }
    
    @PUT
    @Path("/usuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(Usuario usuario) {
        try {
            usuarioDao.actualizar(usuario);
            return Response.status(Response.Status.CREATED).entity(usuario).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
