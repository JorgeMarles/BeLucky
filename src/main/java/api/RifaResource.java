/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import Model.Dao.RifaDao;
import Model.Entity.Rifa;
import Model.Entity.Usuario;
import api.dto.RifaDto;
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
@Path("/apirifa")
public class RifaResource {

    RifaDao rifaDao = new RifaDao();

    @GET
    @Path("/rifa")
    @Produces(MediaType.APPLICATION_JSON)

    public Response consultar() {
        List<Rifa> rifas = new ArrayList();
        rifas = rifaDao.consultar();
        List<RifaDto> rifasDto = new ArrayList();
        for (Rifa rifa : rifas) {
            RifaDto rdto = new RifaDto(rifa, rifa.getUsuario());
            rifasDto.add(rdto);
        }
        return Response
                .status(200)
                .entity(rifasDto)
                .build();
    }

    @GET
    @Path("/rifa/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarId(@PathParam("id") int id) {
        Rifa rifa = new Rifa(id);
        return Response
                .status(200)
                .entity(rifaDao.consultarId(rifa))
                .build();
    }

    @POST
    @Path("/rifa")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(RifaDto rifadto) {
        try {
            Rifa rifa = rifadto.getRifa();
            rifa.setUsuario(rifadto.getUsuario());
            rifaDao.insertar(rifa);
            return Response.status(Response.Status.CREATED).entity(rifa).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/rifa/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrar(@PathParam("id") int id) {
        Rifa rifa = new Rifa(id);
        int i = rifaDao.borrar(rifa);
        if (i == 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Rifa not found")
                    .build();
        } else {
            return Response.ok("Correcto").build();
        }
    }

    @Path("/rifa")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(Rifa rifa) {
        try {
            rifaDao.actualizar(rifa);
            return Response.status(Response.Status.CREATED).entity(rifa).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/rifa/of/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRifasDe(@PathParam("id") String id) {
        List<Rifa> rifas = new ArrayList();
        Usuario u = new Usuario(id);
        rifas = rifaDao.getRifasCreadas(u);
        return Response
                .status(200)
                .entity(rifas)
                .build();
    }

}
