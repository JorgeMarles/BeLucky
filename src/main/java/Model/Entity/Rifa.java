/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entity;

import java.time.LocalDateTime;

/**
 *
 * @author Jorge Marles
 */
public class Rifa {

    private int id;
    private String nombre;
    private String descripcion;
    private String premio;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private int puestos;
    private int valorPuesto;
    private Usuario usuario;

    public Rifa(int id, String nombre, String descripcion, String premio, LocalDateTime inicio, LocalDateTime fin, int puestos, int valorPuesto, Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.premio = premio;
        this.inicio = inicio;
        this.fin = fin;
        this.puestos = puestos;
        this.valorPuesto = valorPuesto;
        this.usuario = usuario;
    }

    public Rifa(String nombre, String descripcion, String premio, LocalDateTime inicio, LocalDateTime fin, int puestos, int valorPuesto, Usuario usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.premio = premio;
        this.inicio = inicio;
        this.fin = fin;
        this.puestos = puestos;
        this.valorPuesto = valorPuesto;
        this.usuario = usuario;
    }

    public Rifa(int id) {
        this.id = id;
    }

    public Rifa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public int getPuestos() {
        return puestos;
    }

    public void setPuestos(int puestos) {
        this.puestos = puestos;
    }

    public int getValorPuesto() {
        return valorPuesto;
    }

    public void setValorPuesto(int valorPuesto) {
        this.valorPuesto = valorPuesto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Rifa{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", premio=" + premio + ", inicio=" + inicio + ", fin=" + fin + ", puestos=" + puestos + ", valorPuesto=" + valorPuesto + ", usuario=" + usuario + '}';
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
        final Rifa other = (Rifa) obj;
        return this.id == other.id;
    }

    
    
}
