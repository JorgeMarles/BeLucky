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
public interface IPuesto {

    public int insertar(Puesto puesto);

    public int borrar(Puesto puesto);

    public int actualizar(Puesto puesto);
    
    public Puesto consultarId(Puesto puesto);

    public List<Puesto> obtenerPuestos(Usuario usuario, Rifa rifa);

    public List<Puesto> getPuestosInscritos(Usuario usuario);

    public List<Puesto> getPuestos(Rifa rifa);

}
