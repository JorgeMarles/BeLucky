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
public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String usuario;
    private String telefono;
    private String foto;
    private LocalDateTime registro;

    private String password;

    public Usuario(int id, String nombre, String correo, String usuario, String telefono, String foto, LocalDateTime registro) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.usuario = usuario;
        this.telefono = telefono;
        this.foto = foto;
        this.registro = registro;
    }

    public Usuario(String nombre, String correo, String usuario, String password, String telefono, String foto, LocalDateTime registro) {
        this.nombre = nombre;
        this.correo = correo;
        this.usuario = usuario;
        this.telefono = telefono;
        this.foto = foto;
        this.registro = registro;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario(int id) {
        this.id = id;
    }

    public Usuario() {
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public LocalDateTime getRegistro() {
        return registro;
    }

    public void setRegistro(LocalDateTime registro) {
        this.registro = registro;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", usuario=" + usuario + ",  telefono=" + telefono + ", foto=" + foto + ", registro=" + registro + '}';
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
        final Usuario other = (Usuario) obj;
        return this.id == other.id;
    }

}
