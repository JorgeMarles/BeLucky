/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entity;

/**
 *
 * @author Jorge Marles
 */
public class Puesto {
    private int id;
    private Usuario usuario;
    private Rifa rifa;
    private int numPuesto;

    public Puesto(int id, Usuario usuario, Rifa rifa, int numPuesto) {
        this.id = id;
        this.usuario = usuario;
        this.rifa = rifa;
        this.numPuesto = numPuesto;
    }
    
    public Puesto(Usuario usuario, Rifa rifa, int numPuesto) {
        this.usuario = usuario;
        this.rifa = rifa;
        this.numPuesto = numPuesto;
    }

    public Puesto(int id) {
        this.id = id;
    }

    public Puesto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNumPuesto() {
        return numPuesto;
    }

    public void setNumPuesto(int numPuesto) {
        this.numPuesto = numPuesto;
    }

    @Override
    public String toString() {
        return "Puesto{" + "id=" + id + ", usuario=" + usuario + ", rifa=" + rifa + ", numPuesto=" + numPuesto + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Puesto other = (Puesto) obj;
        return this.id == other.id;
    }
    
    
}
