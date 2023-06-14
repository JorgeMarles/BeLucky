/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Model.Dao.PuestoDao;
import Model.Entity.Puesto;
import Model.Entity.Rifa;
import Model.Entity.Usuario;
import java.util.List;

/**
 *
 * @author Jorge Marles
 */
public class PuestoTest {
    public static void main(String[] args) {
        Usuario u = new Usuario("1");
        Rifa r = new Rifa(1);
        
        PuestoDao pd = new PuestoDao();
        
        List<Puesto> lp = pd.obtenerPuestos(u, r);
        
        for(Puesto p : lp){
            System.out.println(p);
        }
    }
}
