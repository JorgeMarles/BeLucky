/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.Dao;

import Model.Entity.Puesto;
import Model.Entity.Rifa;
import java.util.List;

/**
 *
 * @author Jorge Marles
 */
public interface IRifa {
    public int insertar(Rifa rifa);
    
    public List<Rifa> consultar();
    
    public Rifa consultarId(Rifa rifa);
    
    public int borrar(Rifa rifa);
    
    public int actualizar(Rifa rifa);
    
    public List<Puesto> getPuestos(Rifa rifa);
}
