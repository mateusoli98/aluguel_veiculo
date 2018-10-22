package modelDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Veiculo;

public class VeiculoDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection conn;

    public ArrayList<Veiculo> exibeVeiculos() {
        ArrayList<Veiculo> listVeiculos = new ArrayList<>();
        try {
            conn = ConexaoDAO.abreConexao();
            String buscaCliente = "SELECT * FROM veiculo";
            ps = conn.prepareStatement(buscaCliente);
            rs = ps.executeQuery();

            while (rs.next()) {
                Veiculo objVeiculo = new Veiculo();
                objVeiculo.setNome(rs.getString("nome"));
                objVeiculo.setCombustivel(rs.getString("combustivel"));
                objVeiculo.setModelo(rs.getString("modelo"));
                objVeiculo.setMarca(rs.getString("marca"));
                objVeiculo.setAno(rs.getInt("ano"));
                listVeiculos.add(objVeiculo);
                objVeiculo = null;
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
}