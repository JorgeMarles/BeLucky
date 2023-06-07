/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.Dao;

import Model.Entity.Puesto;
import Model.Entity.Rifa;
import Model.Entity.Usuario;
import java.util.List;

/**
 *
 * @author Jorge Marles
 */
public interface IUsuario {

    public int insertar(Usuario usuario);

    public List<Usuario> consultar();

    public Usuario consultarId(Usuario usuario);

    public int borrar(Usuario usuario);

    public int actualizar(Usuario usuario);
    
    public Usuario login(Usuario usuario);

}
