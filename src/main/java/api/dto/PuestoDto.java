/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.dto;

import Model.Entity.Puesto;
import Model.Entity.Rifa;
import Model.Entity.Usuario;

/**
 *
 * @author Jorge Marles
 */
public class PuestoDto {
    private Usuario usuario;
    private Rifa rifa;
    private Puesto puesto;

    public PuestoDto(Usuario usuario, Rifa rifa, Puesto puesto) {
        this.usuario = usuario;
        this.rifa = rifa;
        this.puesto = puesto;
    }

    
    public PuestoDto() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rifa getRifa() {
        return rifa;
    }

    public void setRifa(Rifa rifa) {
        this.rifa = rifa;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
}
