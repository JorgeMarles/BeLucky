/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import Model.Dao.PuestoDao;
import Model.Dao.RifaDao;
import Model.Entity.Puesto;
import Model.Entity.Rifa;
import Model.Entity.Usuario;
import api.dto.PuestoDto;
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
@Path("/apipuesto")
public class PuestoResource {

    PuestoDao puestoDao = new PuestoDao();

    @GET
    @Path("/puesto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarId(@PathParam("id") int id) {
        Puesto puesto = new Puesto(id);
        return Response
                .status(200)
                .entity(puestoDao.consultarId(puesto))
                .build();
    }

    @POST
    @Path("/puesto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(PuestoDto puestoDto) {
        try {
            Puesto puesto = puestoDto.getPuesto();
            Usuario usuario = puestoDto.getUsuario();
            Rifa rifa = puestoDto.getRifa();
            
            puesto.setUsuario(usuario);
            puesto.setRifa(rifa);
            puestoDao.insertar(puesto);
            return Response.status(Response.Status.CREATED).entity(puesto).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/puesto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrar(@PathParam("id") int id) {
        Puesto puesto = new Puesto(id);
        int i = puestoDao.borrar(puesto);
        if (i == 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Puesto not found")
                    .build();
        } else {
            return Response.ok("Correcto").build();
        }
    }

    @Path("/puesto")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(Puesto puesto) {
        try {
            puestoDao.actualizar(puesto);
            return Response.status(Response.Status.CREATED).entity(puesto).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("puesto/rifa/{idr}/user/{idu}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuestosRifaUsuario(@PathParam("idr") int idr, @PathParam("idu") int idu) {
        List<Puesto> puestos = new ArrayList();
        Usuario u = new Usuario(idu);
        Rifa r = new Rifa(idr);
        puestos = puestoDao.obtenerPuestos(u, r);
        return Response
                .status(200)
                .entity(puestos)
                .build();
    }

    @GET
    @Path("puesto/rifa/{idr}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuestosRifa(@PathParam("idr") int idr) {
        List<Puesto> puestos = new ArrayList();
        Rifa r = new Rifa(idr);
        puestos = puestoDao.getPuestos(r);
        return Response
                .status(200)
                .entity(puestos)
                .build();
    }

    @GET
    @Path("puesto/user/{idu}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuestosUsuario(@PathParam("idu") int idu) {
        List<Puesto> puestos = new ArrayList();
        Usuario u = new Usuario(idu);
        puestos = puestoDao.getPuestosInscritos(u);
        return Response
                .status(200)
                .entity(puestos)
                .build();
    }

}
