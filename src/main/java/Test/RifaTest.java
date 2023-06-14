/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Model.Dao.*;
import Model.Entity.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jorge Marles
 */
public class RifaTest {
    public static void main(String[] args) {
        Usuario u = new Usuario("1");
        UsuarioDao ud = new UsuarioDao();
        u = ud.consultarId(u);
        Rifa r = new Rifa("rifa prueba", "desc rifa prueva", "un peso", LocalDateTime.now(), LocalDateTime.now(), 10, 5000, u);
        RifaDao rd = new RifaDao();
        rd.insertar(r);
        System.out.println("la consulta retorno: "+rd.consultar());
        
        List<Rifa> lr = rd.consultar();
        
        for(Rifa rr : lr){
            System.out.println(rr);
        }
    }   
}
