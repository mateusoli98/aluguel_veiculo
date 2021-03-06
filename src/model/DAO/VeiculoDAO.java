package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Locacao;
import model.Veiculo;

public class VeiculoDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection conn;
    String query = "";
    boolean retorno = false;

    public ArrayList<Veiculo> exibeVeiculos() {
        ArrayList<Veiculo> listVeiculos = new ArrayList<>();
        try {
            conn = ConexaoDAO.abreConexao();
            query = "SELECT * FROM exibeveiculos";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Veiculo objVeiculo = new Veiculo();
                objVeiculo.setCodigo(rs.getInt("codigo"));
                objVeiculo.setNome(rs.getString("nome"));
                objVeiculo.setTipo(rs.getString("tipo"));
                objVeiculo.setCombustivel(rs.getString("combustivel"));
                objVeiculo.setModelo(rs.getString("modelo"));
                objVeiculo.setMarca(rs.getString("marca"));
                objVeiculo.setAno(rs.getInt("ano"));
                objVeiculo.setValor(rs.getDouble("valor"));
                listVeiculos.add(objVeiculo);
                objVeiculo = null;
                query = "";
            }
            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException erroSQL) {
            erroSQL.printStackTrace();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return listVeiculos;
    }

    public ArrayList<Veiculo> exibeNomeVeiculos() {
        ArrayList<Veiculo> listVeiculos = new ArrayList<>();
        try {
            conn = ConexaoDAO.abreConexao();
            query = "SELECT * FROM veiculo";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Veiculo objVeiculo = new Veiculo();
                objVeiculo.setNome(rs.getString("nome"));
                listVeiculos.add(objVeiculo);
                objVeiculo = null;
                query = "";
            }
            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException erroSQL) {
            erroSQL.printStackTrace();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return listVeiculos;
    }

    public ArrayList<Veiculo> exibeTipoVeiculos(String tipo) {
        ArrayList<Veiculo> listVeiculos = new ArrayList<>();
        try {
            conn = ConexaoDAO.abreConexao();
            query = "SELECT * FROM veiculo WHERE tipo = '" + tipo + "' AND alugado = 0";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Veiculo objVeiculo = new Veiculo();
                objVeiculo.setCodigo(rs.getInt("codigo"));
                objVeiculo.setNome(rs.getString("nome"));
                objVeiculo.setCombustivel(rs.getString("combustivel"));
                objVeiculo.setModelo(rs.getString("modelo"));
                objVeiculo.setMarca(rs.getString("marca"));
                objVeiculo.setAno(rs.getInt("ano"));
                listVeiculos.add(objVeiculo);
                objVeiculo = null;
                query = "";
            }
            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException erroSQL) {
            erroSQL.printStackTrace();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return listVeiculos;
    }

    public ArrayList<Veiculo> exibeVeiculoNome(String nome) {
        ArrayList<Veiculo> listVeiculos = new ArrayList<>();
        try {
            conn = ConexaoDAO.abreConexao();
            query = "SELECT * FROM veiculo WHERE nome LIKE '%" + nome + "%' AND alugado = 0";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Veiculo objVeiculo = new Veiculo();
                objVeiculo.setCodigo(rs.getInt("codigo"));
                objVeiculo.setNome(rs.getString("nome"));
                objVeiculo.setCombustivel(rs.getString("combustivel"));
                objVeiculo.setModelo(rs.getString("modelo"));
                objVeiculo.setMarca(rs.getString("marca"));
                objVeiculo.setAno(rs.getInt("ano"));
                objVeiculo.setValor(rs.getDouble("valor"));
                listVeiculos.add(objVeiculo);
                objVeiculo = null;
                query = "";
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (SQLException erroSQL) {
            erroSQL.printStackTrace();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return listVeiculos;
    }

    public byte[] retornaFotoVeiculo(int codigoVeiculo) {
        byte[] fotoVeiculo = null;
        try {
            conn = ConexaoDAO.abreConexao();
            query = "SELECT veiculo.foto FROM veiculo WHERE veiculo.codigo = '" + codigoVeiculo + "'";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                fotoVeiculo = rs.getBytes("veiculo.foto");
                query = "";
            }
            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException | ClassNotFoundException error) {
            error.printStackTrace();
        }
        return fotoVeiculo;
    }

    public boolean cadastro(Veiculo v) {
        try {
            conn = ConexaoDAO.abreConexao();
            query = " INSERT INTO veiculo (`nome`, `tipo`, `combustivel`, `modelo`, `marca`, `ano`,`alugado`, foto,`valor`) VALUES ( ?, ?, ?, ?, ?, ?, 0, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, v.getNome());
            ps.setString(2, v.getTipo());
            ps.setString(3, v.getCombustivel());
            ps.setString(4, v.getModelo());
            ps.setString(5, v.getMarca());
            ps.setInt(6, v.getAno());
            ps.setBytes(7, v.getFoto());
            ps.setDouble(8, v.getValor());
            ps.executeUpdate();
            return retorno = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            retorno = false;
            query = "";
            try {
                conn.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return retorno;
    }

    public boolean alterarVeiculo(Veiculo v) {
        try {
            conn = ConexaoDAO.abreConexao();
            query = "UPDATE veiculo SET nome = '" + v.getNome() + "', "
                    + "tipo = '" + v.getTipo() + "', "
                    + "combustivel = '" + v.getCombustivel() + "', "
                    + "modelo = '" + v.getModelo() + "',"
                    + "marca = '" + v.getMarca() + "', "
                    + "ano = '" + v.getAno() + "', "
                    + "valor = '" + v.getValor() + "',"
                    + "foto = ? "
                    + "WHERE codigo = " + v.getCodigo() + ";";

            ps = conn.prepareStatement(query);
            ps.setBytes(1, v.getFoto());
            ps.executeUpdate();
            return retorno = true;
        } catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                retorno = false;
                query = "";
                conn.close();

            } catch (Exception e) {
            }
        }
        return retorno;
    }

    public boolean deletarVeiculo(Veiculo v) {
        try {

            conn = ConexaoDAO.abreConexao();
            query = "DELETE FROM `veiculo` WHERE `codigo` = " + v.getCodigo() + ";";
            ps = conn.prepareStatement(query);
            ps.executeUpdate();
            return retorno = true;
        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Veiculo em uso!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                retorno = false;
                query = "";
                conn.close();

            } catch (Exception e) {
            }
        }
        return retorno;
    }

    public boolean realizaLocacao(Locacao loc) {
        String queryAvaliacao = "INSERT INTO avaliacao(codPessoa,codVeiculo) "
                + "VALUES (" + loc.getCodCliente() + "," + loc.getCodVeiculo() + ");";
        try {
            conn = ConexaoDAO.abreConexao();
            query = "INSERT INTO locacao (`codVeiculo`, `codPessoa`, `dtInicio`, `dtTermino`, `total`) "
                    + "VALUES (?, ?, ?, ?,?);";

            ps = conn.prepareStatement(query);
            ps.setInt(1, loc.getCodVeiculo());
            ps.setInt(2, loc.getCodCliente());
            ps.setString(3, loc.getDtInicio());
            ps.setString(4, loc.getDtTermino());
            ps.setDouble(5, loc.getTotal());
            ps.executeUpdate();
            ps = null;
            ps = conn.prepareStatement(queryAvaliacao);
            ps.executeUpdate();
            ps = null;
            query = "";
            query = "UPDATE veiculo SET alugado = 1 WHERE codigo = " + loc.getCodVeiculo() + ";";
            ps = conn.prepareStatement(query);
            ps.executeUpdate();
            return retorno = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            retorno = false;
            query = "";
            try {
                conn.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return retorno;
    }

    public boolean mudaStatusVeiculo(int alugado, Locacao loc) {
        try {
            conn = ConexaoDAO.abreConexao();
            query = "UPDATE veiculo SET alugado = '" + alugado + "' WHERE codigo = " + loc.getCodVeiculo() + ";";
            ps = conn.prepareStatement(query);
            ps.executeUpdate();
            return retorno = true;
        } catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                retorno = false;
                query = "";
                conn.close();

            } catch (Exception e) {
            }
        }
        return retorno;
    }

}
