/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.dto;

import Model.Entity.Rifa;
import Model.Entity.Usuario;

/**
 *
 * @author Jorge Marles
 */
public class RifaDto {
    private Rifa rifa;
    private Usuario usuario;

    public RifaDto(Rifa rifa, Usuario usuario) {
        this.rifa = rifa;
        this.usuario = usuario;
    }

    public RifaDto() {
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

    
}
