/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Model.Dao.UsuarioDao;
import Model.Entity.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jorge Marles
 */
public class UsuarioTest {
    public static void main(String[] args) {
        UsuarioDao ud = new UsuarioDao();
        //Random rand = new Random();
        
        Usuario u = new Usuario("jorge andre", "a@a", "555",LocalDateTime.now());
        System.out.println("Insercion: "+ud.insertar(u));
        List<Usuario> lu = ud.consultar();
        for(Usuario us : lu){
            System.out.println(us);
        }
    }
}
