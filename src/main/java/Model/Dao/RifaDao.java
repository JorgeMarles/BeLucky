/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Dao;

import Model.Entity.Puesto;
import Model.Entity.Rifa;
import Model.Entity.Usuario;
import Red.BaseDeDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Marles
 */
public class RifaDao implements IRifa {

    final static String SQL_INSERTAR = "INSERT INTO rifa (id, nombre, descripcion, premio, inicio, fin, puestos, valor_puesto, id_usuario) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
    final static String SQL_CONSULTAR = "SELECT * FROM rifa r, usuario u WHERE r.id_usuario = u.id";
    final static String SQL_BORRAR = "DELETE FROM rifa WHERE id = ?";
    final static String SQL_CONSULTARID = "SELECT * FROM rifa r, usuario u WHERE r.id = ? AND r.id_usuario = u.id";
    final static String SQL_ACTUALIZAR = "UPDATE rifa SET nombre = ?, descripcion = ?, premio = ?, inicio = ?, fin = ?, puestos = ?, valor_puesto = ? WHERE rifa.id = ?";
    final static String SQL_PUESTOS = "SELECT * FROM usuario u, rifa r, puesto p WHERE r.id = ? AND p.id_rifa = r.id AND p.id_usuario = u.id";

    @Override
    public int insertar(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_INSERTAR);
            sentencia.setString(1, rifa.getNombre());
            sentencia.setString(2, rifa.getDescripcion());
            sentencia.setString(3, rifa.getPremio());
            sentencia.setTimestamp(4, Timestamp.valueOf(rifa.getInicio()));
            sentencia.setTimestamp(5, Timestamp.valueOf(rifa.getFin()));
            sentencia.setInt(6, rifa.getPuestos());
            sentencia.setInt(7, rifa.getValorPuesto());
            sentencia.setInt(8, rifa.getUsuario().getId());

            resultado = sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public List<Rifa> consultar() {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Rifa> rifas = new ArrayList<>();

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_CONSULTAR);

            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                //id nombre descripcion premio inicio fin puestos valor_puesto id_usuario
                int id = resultado.getInt("r.id");
                String nombre = resultado.getString("r.nombre");
                String descripcion = resultado.getString("descripcion");
                String premio = resultado.getString("premio");
                LocalDateTime inicio = resultado.getTimestamp("inicio").toLocalDateTime();
                LocalDateTime fin = resultado.getTimestamp("fin").toLocalDateTime();
                int puestos = resultado.getInt("puestos");
                int valorPuesto = resultado.getInt("valor_puesto");

                //id nombre correo usuario telefono foto registro
                int idUsuario = resultado.getInt("u.id");
                String nomUsuario = resultado.getString("u.nombre");
                String correo = resultado.getString("correo");
                String usuario = resultado.getString("usuario");
                String telefono = resultado.getString("telefono");
                String foto = resultado.getString("foto");
                LocalDateTime registro = resultado.getTimestamp("registro").toLocalDateTime();

                Usuario u = new Usuario(idUsuario, nomUsuario, correo, usuario, telefono, foto, registro);

                Rifa r = new Rifa(id, nombre, descripcion, premio, inicio, fin, puestos, valorPuesto, u);

                rifas.add(r);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                BaseDeDatos.close(resultado);
                BaseDeDatos.close(sentencia);
                BaseDeDatos.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rifas;
    }

    @Override
    public Rifa consultarId(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Rifa rifaReturn = null;

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_CONSULTARID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
            sentencia.setInt(1, rifa.getId());
            resultado = sentencia.executeQuery();
            resultado.absolute(1);

            //id nombre descripcion premio inicio fin puestos valor_puesto id_usuario
            int id = resultado.getInt("r.id");
            String nombre = resultado.getString("r.nombre");
            String descripcion = resultado.getString("descripcion");
            String premio = resultado.getString("premio");
            LocalDateTime inicio = resultado.getTimestamp("inicio").toLocalDateTime();
            LocalDateTime fin = resultado.getTimestamp("fin").toLocalDateTime();
            int puestos = resultado.getInt("puestos");
            int valorPuesto = resultado.getInt("valor_puesto");

            //id nombre correo usuario telefono foto registro
            int idUsuario = resultado.getInt("u.id");
            String nomUsuario = resultado.getString("u.nombre");
            String correo = resultado.getString("correo");
            String usuario = resultado.getString("usuario");
            String telefono = resultado.getString("telefono");
            String foto = resultado.getString("foto");
            LocalDateTime registro = resultado.getTimestamp("registro").toLocalDateTime();

            Usuario u = new Usuario(idUsuario, nomUsuario, correo, usuario, telefono, foto, registro);

            rifaReturn = new Rifa(id, nombre, descripcion, premio, inicio, fin, puestos, valorPuesto, u);

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                BaseDeDatos.close(resultado);
                BaseDeDatos.close(sentencia);
                BaseDeDatos.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rifaReturn;
    }

    @Override
    public int borrar(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_BORRAR);
            sentencia.setInt(1, rifa.getId());
            resultado = sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public int actualizar(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_ACTUALIZAR);
            sentencia.setInt(9, rifa.getId());
            sentencia.setString(1, rifa.getNombre());
            sentencia.setString(2, rifa.getDescripcion());
            sentencia.setString(3, rifa.getPremio());
            sentencia.setTimestamp(4, Timestamp.valueOf(rifa.getInicio()));
            sentencia.setTimestamp(5, Timestamp.valueOf(rifa.getFin()));
            sentencia.setInt(6, rifa.getPuestos());
            sentencia.setInt(7, rifa.getValorPuesto());
            resultado = sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public List<Puesto> getPuestos(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Puesto> puestos = new ArrayList<>();

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_PUESTOS);
            sentencia.setInt(1, rifa.getId());

            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                /*
                user : id nombre correo usuario password telefono foto registro
                rifa : id nombre descripcion premio inicio fin puestos valor_puesto id_usuario
                puesto : id id_usuario id_rifa num_puesto
                 */

                int idUser = resultado.getInt("u.id");
                String nomUser = resultado.getString("u.nombre");
                String emailUser = resultado.getString("u.correo");
                String userUser = resultado.getString("u.usuario");
                String telfUser = resultado.getString("u.telefono");
                String fotoUser = resultado.getString("u.foto");
                LocalDateTime regUser = resultado.getTimestamp("u.registro").toLocalDateTime();

                Usuario user = new Usuario(idUser, nomUser, emailUser, userUser, telfUser, fotoUser, regUser);

                //            id nombre descripcion premio inicio fin puestos valor_puesto id_usuario
                int idRifa = resultado.getInt("r.id");
                String nomRifa = resultado.getString("r.nombre");
                String descRifa = resultado.getString("r.descripcion");
                String premioRifa = resultado.getString("r.premio");
                LocalDateTime inicioRifa = resultado.getTimestamp("r.inicio").toLocalDateTime();
                LocalDateTime finRifa = resultado.getTimestamp("r.fin").toLocalDateTime();
                int numPuestos = resultado.getInt("r.puestos");
                int valorPuesto = resultado.getInt("r.valor_puesto");

                Rifa rifaArg = new Rifa(idRifa, nomRifa, descRifa, premioRifa, inicioRifa, finRifa, numPuestos, valorPuesto, user);

                int idPuesto = resultado.getInt("p.id");
                int puesto = resultado.getInt("p.num_puesto");

                Puesto p = new Puesto(idPuesto, user, rifaArg, puesto);

                puestos.add(p);

                //System.out.println(numero);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                BaseDeDatos.close(resultado);
                BaseDeDatos.close(sentencia);
                BaseDeDatos.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return puestos;
    }
}
