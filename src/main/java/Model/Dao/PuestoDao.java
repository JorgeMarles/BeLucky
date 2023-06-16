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
public class PuestoDao implements IPuesto {

    final static String SQL_INSERTAR = "INSERT INTO puesto (id, uid_usuario, id_rifa, num_puesto) VALUES (NULL, ?, ?, ?);";
    final static String SQL_BORRAR = "DELETE FROM puesto where id = ?";
    final static String SQL_ACTUALIZAR = "UPDATE puesto SET num_puesto = ? WHERE id = ?";
    final static String SQL_GET_PUESTOS = "SELECT * FROM usuario u, rifa r, puesto p WHERE u.uid = ? AND r.id = ? AND p.uid_usuario = u.uid AND p.id_rifa = r.id";
    final static String SQL_GET_RIFAS_INSCRITAS = "SELECT * FROM puesto p, usuario u, rifa r WHERE p.uid_usuario = u.uid AND u.uid = ? AND p.id_rifa = r.id";
    final static String SQL_PUESTOS = "SELECT * FROM usuario u, rifa r, puesto p WHERE r.id = ? AND p.id_rifa = r.id AND p.uid_usuario = u.uid";
    final static String SQL_CONSULTAR_ID = "SELECT * FROM usuario u, rifa r, puesto p WHERE p.id_rifa = r.id AND p.uid_usuario = u.uid AND p.id = ?";

    @Override
    public int insertar(Puesto puesto) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_INSERTAR, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, puesto.getUsuario().getUid());
            sentencia.setInt(2, puesto.getRifa().getId());
            sentencia.setInt(3, puesto.getNumPuesto());
            resultado = sentencia.executeUpdate();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs.next()) {
                resultado = rs.getInt(1);
            }

            puesto.setId(resultado);
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
    public int borrar(Puesto puesto) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_BORRAR);
            sentencia.setInt(1, puesto.getId());
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
    public int actualizar(Puesto puesto) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_ACTUALIZAR);
            sentencia.setInt(2, puesto.getId());
            sentencia.setInt(1, puesto.getNumPuesto());
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
    public List<Puesto> obtenerPuestos(Usuario usuario, Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Puesto> puestos = new ArrayList<>();

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_GET_PUESTOS);
            sentencia.setString(1, usuario.getUid());
            sentencia.setInt(2, rifa.getId());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                /*
                user : id nombre uid usuario password telefono foto registro
                rifa : id nombre descripcion premio inicio fin puestos valor_puesto uid_usuario
                puesto : id uid_usuario id_rifa num_puesto
                 */
                String uid = resultado.getString("u.uid");
                String nomUser = resultado.getString("u.nombre");
                String emailUser = resultado.getString("u.correo");
                String telfUser = resultado.getString("u.telefono");
                LocalDateTime regUser = resultado.getTimestamp("u.registro").toLocalDateTime();

                Usuario user = new Usuario(uid, nomUser, emailUser, telfUser, regUser);

                //            id nombre descripcion premio inicio fin puestos valor_puesto uid_usuario
                int idRifa = resultado.getInt("r.id");
                String nomRifa = resultado.getString("r.nombre");
                String descRifa = resultado.getString("r.descripcion");
                String premioRifa = resultado.getString("r.premio");
                LocalDateTime inicioRifa = resultado.getTimestamp("r.inicio").toLocalDateTime();
                LocalDateTime finRifa = resultado.getTimestamp("r.fin").toLocalDateTime();
                int numPuestos = resultado.getInt("r.puestos");
                int valorPuesto = resultado.getInt("r.valor_puesto");
                String uidUsuario = resultado.getString("r.uid_usuario");

                UsuarioDao usuarioDao = new UsuarioDao();
                Usuario u = new Usuario(uidUsuario);

                u = usuarioDao.consultarId(u);

                Rifa rifaArg = new Rifa(idRifa, nomRifa, descRifa, premioRifa, inicioRifa, finRifa, numPuestos, valorPuesto, u);

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

    @Override
    public List<Puesto> getPuestosInscritos(Usuario usuario) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Puesto> puestos = new ArrayList<>();

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_GET_RIFAS_INSCRITAS);
            sentencia.setString(1, usuario.getUid());

            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                /*
                user : id nombre uid usuario password telefono foto registro
                rifa : id nombre descripcion premio inicio fin puestos valor_puesto uid_usuario
                puesto : id uid_usuario id_rifa num_puesto
                 */

                String uid = resultado.getString("u.uid");
                String nomUser = resultado.getString("u.nombre");
                String emailUser = resultado.getString("u.correo");
                String telfUser = resultado.getString("u.telefono");
                LocalDateTime regUser = resultado.getTimestamp("u.registro").toLocalDateTime();

                Usuario user = new Usuario(uid, nomUser, emailUser, telfUser, regUser);

                //            id nombre descripcion premio inicio fin puestos valor_puesto uid_usuario
                int idRifa = resultado.getInt("r.id");
                String nomRifa = resultado.getString("r.nombre");
                String descRifa = resultado.getString("r.descripcion");
                String premioRifa = resultado.getString("r.premio");
                LocalDateTime inicioRifa = resultado.getTimestamp("r.inicio").toLocalDateTime();
                LocalDateTime finRifa = resultado.getTimestamp("r.fin").toLocalDateTime();
                int numPuestos = resultado.getInt("r.puestos");
                int valorPuesto = resultado.getInt("r.valor_puesto");
                String uidUsuario = resultado.getString("r.uid_usuario");
                UsuarioDao usuarioDao = new UsuarioDao();
                Usuario u = new Usuario(uidUsuario);

                u = usuarioDao.consultarId(u);

                Rifa rifa = new Rifa(idRifa, nomRifa, descRifa, premioRifa, inicioRifa, finRifa, numPuestos, valorPuesto, u);

                int idPuesto = resultado.getInt("p.id");
                int puesto = resultado.getInt("p.num_puesto");

                Puesto p = new Puesto(idPuesto, user, rifa, puesto);

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
                user : id nombre uid usuario password telefono foto registro
                rifa : id nombre descripcion premio inicio fin puestos valor_puesto uid_usuario
                puesto : id uid_usuario id_rifa num_puesto
                 */

                String uid = resultado.getString("u.uid");
                String nomUser = resultado.getString("u.nombre");
                String emailUser = resultado.getString("u.correo");
                String telfUser = resultado.getString("u.telefono");
                LocalDateTime regUser = resultado.getTimestamp("u.registro").toLocalDateTime();

                Usuario user = new Usuario(uid, nomUser, emailUser, telfUser, regUser);

                //            id nombre descripcion premio inicio fin puestos valor_puesto uid_usuario
                int idRifa = resultado.getInt("r.id");
                String nomRifa = resultado.getString("r.nombre");
                String descRifa = resultado.getString("r.descripcion");
                String premioRifa = resultado.getString("r.premio");
                LocalDateTime inicioRifa = resultado.getTimestamp("r.inicio").toLocalDateTime();
                LocalDateTime finRifa = resultado.getTimestamp("r.fin").toLocalDateTime();
                int numPuestos = resultado.getInt("r.puestos");
                int valorPuesto = resultado.getInt("r.valor_puesto");
                String uidUsuario = resultado.getString("r.uid_usuario");
                UsuarioDao usuarioDao = new UsuarioDao();
                Usuario u = new Usuario(uidUsuario);

                u = usuarioDao.consultarId(u);
                Rifa rifaArg = new Rifa(idRifa, nomRifa, descRifa, premioRifa, inicioRifa, finRifa, numPuestos, valorPuesto, u);

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

    @Override
    public Puesto consultarId(Puesto puesto) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Puesto puestoRet = null;

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_CONSULTAR_ID);
            sentencia.setInt(1, puesto.getId());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                /*
                user : id nombre uid usuario password telefono foto registro
                rifa : id nombre descripcion premio inicio fin puestos valor_puesto uid_usuario
                puesto : id uid_usuario id_rifa num_puesto
                 */

                String uid = resultado.getString("u.uid");
                String nomUser = resultado.getString("u.nombre");
                String emailUser = resultado.getString("u.correo");
                String telfUser = resultado.getString("u.telefono");
                LocalDateTime regUser = resultado.getTimestamp("u.registro").toLocalDateTime();

                Usuario user = new Usuario(uid, nomUser, emailUser, telfUser, regUser);

                //            id nombre descripcion premio inicio fin puestos valor_puesto uid_usuario
                int idRifa = resultado.getInt("r.id");
                String nomRifa = resultado.getString("r.nombre");
                String descRifa = resultado.getString("r.descripcion");
                String premioRifa = resultado.getString("r.premio");
                LocalDateTime inicioRifa = resultado.getTimestamp("r.inicio").toLocalDateTime();
                LocalDateTime finRifa = resultado.getTimestamp("r.fin").toLocalDateTime();
                int numPuestos = resultado.getInt("r.puestos");
                int valorPuesto = resultado.getInt("r.valor_puesto");
                String uidUsuario = resultado.getString("r.uid_usuario");

                UsuarioDao usuarioDao = new UsuarioDao();
                Usuario u = new Usuario(uidUsuario);

                u = usuarioDao.consultarId(u);

                Rifa rifaArg = new Rifa(idRifa, nomRifa, descRifa, premioRifa, inicioRifa, finRifa, numPuestos, valorPuesto, u);

                int idPuesto = resultado.getInt("p.id");
                int numPuesto = resultado.getInt("p.num_puesto");

                puestoRet = new Puesto(idPuesto, user, rifaArg, numPuesto);

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
        return puestoRet;
    }
}
