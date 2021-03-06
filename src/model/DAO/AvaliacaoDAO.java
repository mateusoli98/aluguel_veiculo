package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.ConexaoDAO;

public class AvaliacaoDAO {

    String query;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public boolean resgistarAvaliacao(Avaliacao a, HistoricoLocacao hl) {
        try {
            conn = ConexaoDAO.abreConexao();
            query = "UPDATE avaliacao SET numAvaliacao = " + a.getNumAvaliacao() + ", "
                    + "comentario = '" + a.getComentario() + "',"
                    + "status = 1 "
                    + "WHERE codVeiculo = " + hl.getVeiculo().getCodigo() + " AND "
                    + "codPessoa = " + ConexaoDAO.getCliente().getCodigo() + ";";

            ps = conn.prepareStatement(query);
            ps.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {

        } finally {
            query = "";
            try {
                conn.close();
                ps.close();
            } catch (SQLException ex) {
            }
        }
        return false;
    }
    
    public ArrayList<Avaliacao> maiorNota() {

        ArrayList<Avaliacao> listAvaliacao = new ArrayList<>();

        try {
            conn = ConexaoDAO.abreConexao();
 
            query = "SELECT COUNT(numAvaliacao) as qntNota, max(numAvaliacao) as maiorNota from avaliacao";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setQtdAvaliacao(rs.getInt("qntNota"));
                avaliacao.setNumAvaliacao(rs.getInt("maiorNota"));
                listAvaliacao.add(avaliacao);
            }

        } catch (SQLException e) {

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvaliacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAvaliacao;
    }

    public String comentariosMelhorNota() {
        String comentarios = "";
        try {
            conn = ConexaoDAO.abreConexao();

            query = "SELECT pessoa.nome, avaliacao.numAvaliacao, veiculo.nome, avaliacao.comentario FROM pessoa "
                    + "JOIN avaliacao ON pessoa.codigo = avaliacao.codPessoa "
                    + "JOIN veiculo ON veiculo.codigo = avaliacao.codVeiculo "
                    + "WHERE avaliacao.numAvaliacao = (SELECT max(numAvaliacao) from avaliacao) AND avaliacao.status = 1";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                comentarios += "<br>Cliente: <b>" + rs.getString("pessoa.nome") + "</b> avaliou o "
                        + "veículo: <b>" + rs.getString("veiculo.nome") + "</b> com nota: <b>"
                        + +rs.getInt("avaliacao.numAvaliacao")
                        + "</b><br><br>"
                        + "<b>Experiencia com nossos serviços:</b><br>"
                        + "<i>\"" + rs.getString("avaliacao.comentario") + "\"</i>"
                        + "<br><br><hr>";
            }

        } catch (SQLException | ClassNotFoundException e) {

        } finally {
            try {
                query = "";
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
            }
        }

        return comentarios;
    }
    

    public ArrayList<Avaliacao> menorNota() {

        ArrayList<Avaliacao> listAvaliacao = new ArrayList<>();

        try {
            conn = ConexaoDAO.abreConexao();
 
            query = "SELECT COUNT(numAvaliacao) as qntNota, min(numAvaliacao) as menorNota from avaliacao";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setQtdAvaliacao(rs.getInt("qntNota"));
                avaliacao.setNumAvaliacao(rs.getInt("menorNota"));
                listAvaliacao.add(avaliacao);
            }

        } catch (SQLException e) {

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvaliacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAvaliacao;
    }

    public String comentariosMenorNota() {
        String comentarios = "";
        try {
            conn = ConexaoDAO.abreConexao();

            query = "SELECT pessoa.nome, avaliacao.numAvaliacao, veiculo.nome, avaliacao.comentario FROM pessoa "
                    + "JOIN avaliacao ON pessoa.codigo = avaliacao.codPessoa "
                    + "JOIN veiculo ON veiculo.codigo = avaliacao.codVeiculo "
                    + "WHERE avaliacao.numAvaliacao = (SELECT min(numAvaliacao) from avaliacao) AND avaliacao.status = 1";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                comentarios += "<br>Cliente: <b>" + rs.getString("pessoa.nome") + "</b> avaliou o "
                        + "veículo: <b>" + rs.getString("veiculo.nome") + "</b> com nota: <b>"
                        + +rs.getInt("avaliacao.numAvaliacao")
                        + "</b><br><br>"
                        + "<b>Experiencia com nossos serviços:</b><br>"
                        + "<i>\"" + rs.getString("avaliacao.comentario") + "\"</i>"
                        + "<br><br><hr>";
            }

        } catch (SQLException | ClassNotFoundException e) {

        } finally {
            try {
                query = "";
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
            }
        }

        return comentarios;
    }
    
    public String comentarios(String veiculo) {
        String comentarios = "";
        try {
            conn = ConexaoDAO.abreConexao();
            if (veiculo.equals("Geral")) {
                query = "SELECT DISTINCT pessoa.nome, avaliacao.numAvaliacao, veiculo.nome, avaliacao.comentario FROM pessoa "
                        + "JOIN avaliacao ON pessoa.codigo = avaliacao.codPessoa "
                        + "JOIN veiculo ON veiculo.codigo = avaliacao.codVeiculo "
                        + "WHERE avaliacao.status = 1 ORDER BY avaliacao.codigo DESC;";
            } else {
                query = "SELECT DISTINCT pessoa.nome, avaliacao.numAvaliacao, veiculo.nome, avaliacao.comentario FROM pessoa "
                        + "JOIN avaliacao ON pessoa.codigo = avaliacao.codPessoa "
                        + "JOIN veiculo ON veiculo.codigo = avaliacao.codVeiculo "
                        + "WHERE veiculo.nome = '" + veiculo + "' AND avaliacao.status = 1 ORDER BY avaliacao.codigo DESC;";
            }
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                comentarios += "<br>Cliente: <b>" + rs.getString("pessoa.nome") + "</b> avaliou o "
                        + "veículo: <b>" + rs.getString("veiculo.nome") + "</b> com nota: <b>"
                        + +rs.getInt("avaliacao.numAvaliacao")
                        + "</b><br><br>"
                        + "<b>Experiencia com nossos serviços:</b><br>"
                        + "<i>\"" + rs.getString("avaliacao.comentario") + "\"</i>"
                        + "<br><br><hr>";
            }

        } catch (SQLException | ClassNotFoundException e) {

        } finally {
            try {
                query = "";
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
            }
        }

        return comentarios;
    }

    public ArrayList<Avaliacao> notas() {

        ArrayList<Avaliacao> listAvaliacao = new ArrayList<>();

        try {
            conn = ConexaoDAO.abreConexao();

            query = "SELECT COUNT(numAvaliacao) as nota, numAvaliacao from avaliacao GROUP BY numAvaliacao";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setQtdAvaliacao(rs.getInt("nota"));
                avaliacao.setNumAvaliacao(rs.getInt("numAvaliacao"));
                listAvaliacao.add(avaliacao);
            }

        } catch (SQLException e) {

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvaliacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAvaliacao;
    }

    public ArrayList<Avaliacao> notasVeiculo(String veiculo) {

        ArrayList<Avaliacao> listAvaliacao = new ArrayList<>();

        try {
            conn = ConexaoDAO.abreConexao();

            query = "SELECT COUNT(avaliacao.numAvaliacao) as nota, avaliacao.numAvaliacao from avaliacao "
                    + "join veiculo on veiculo.codigo = avaliacao.codVeiculo "
                    + "where veiculo.nome = '" + veiculo + "' GROUP BY numAvaliacao ;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setQtdAvaliacao(rs.getInt("nota"));
                avaliacao.setNumAvaliacao(rs.getInt("numAvaliacao"));
                listAvaliacao.add(avaliacao);
            }

        } catch (SQLException e) {

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvaliacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAvaliacao;
    }
}
