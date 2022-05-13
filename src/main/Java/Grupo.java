package main.Java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Grupo implements What7Interface{
    public String nomeDoGrupo;

    public Grupo(String nomeDoGrupo, String nomeAdm, String telefoneAdm) throws SQLException {
        this.nomeDoGrupo = nomeDoGrupo;
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        try {
            st.executeUpdate("INSERT INTO grupo (nomeDoGrupo) values ('"+ this.nomeDoGrupo + "');");
            st.executeUpdate("INSERT INTO grupo_participantes (grupo, participante_nome, participante_telefone)" +
                    "VALUES ('"+ this.nomeDoGrupo + "', '" + nomeAdm + "', '" + telefoneAdm + "');");
            st.executeUpdate("INSERT INTO grupo_adms (grupo, adm_nome, adm_telefone)" +
                    "VALUES ('"+ this.nomeDoGrupo + "', '" + nomeAdm + "', '" + telefoneAdm + "');");

        } catch (Exception e) {
            throw e;
        }
    }

    public Grupo(String nomeDoGrupo){
        this.nomeDoGrupo = nomeDoGrupo;
    }
    @Override
    public String listarMensagens() throws SQLException {
        StringBuilder novaString = new StringBuilder();
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM mensagens_grupo mg JOIN mensagens m ON mg.mensagem = m.id " +
                "WHERE grupo = '" + this.nomeDoGrupo + "';");

        novaString.append("Grupo: ").append(this.nomeDoGrupo).append("\n");
        novaString.append("Remetente:\tTelefone:\n");
        while (rs.next()) {
            novaString.append(rs.getString(5)).append('\t').append(rs.getString(6));
        }

        return novaString.toString();
    }
    public void incluirNovoMembro(String nomeMembro, String telefoneMembro) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("INSERT INTO grupo_participantes(grupo, participante_nome, participante_telefone)" +
                "VALUES ('" + this.nomeDoGrupo + "', '" + nomeMembro + "', '" + telefoneMembro + "');");
    }
    public void adicionaADM(String nomeMembro, String telefoneMembro) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("INSERT INTO grupo_adms(grupo, adm_nome, adm_telefone)" +
                "VALUES ('" + this.nomeDoGrupo + "', '" + nomeMembro + "', '" + telefoneMembro + "');");
    }
    public void removerMembro(String nomeMembro, String telefoneMembro) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("DELETE FROM grupo_participantes WHERE grupo = '" + this.nomeDoGrupo +
                " 'AND participante_nome = '" + nomeMembro + "'AND participante_telefone = '" + telefoneMembro + "';");
    }
    public int quantidadeDeMembros() throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT coutn(*) FROM grupo_participantes gp WHERE grupo = '" + this.nomeDoGrupo + "';");
        rs.next();
        return rs.getInt("count(*)");
    }
    public int quantidadeDeAdministradores() throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT coutn(*) FROM grupo_adms gp WHERE grupo = '" + this.nomeDoGrupo + "';");
        rs.next();
        return rs.getInt("count(*)");
    }
    public String listarMembros(String tipo) throws SQLException {
        StringBuilder novaString = new StringBuilder();
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM mensages m JOIN mensagens_grupo mg ON m.id = mp.mensagem" +
                "WHERE grupo = '" + this.nomeDoGrupo + "';");

        novaString.append("Grupo: ").append(this.nomeDoGrupo).append("\nMembros: \n");
        novaString.append("Nome:\tTelefone:\n");
        while (rs.next()) {
            novaString.append(rs.getString(3)).append(rs.getString(4));
        }

        return novaString.toString();
    }

}
