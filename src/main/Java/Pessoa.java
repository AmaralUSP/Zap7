package main.Java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

public class Pessoa {
    private String nome;
    private String telefone;
    public Pessoa(String nome, String telefone) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        this.nome = nome;
        this.telefone = telefone;

        try {
            st.executeUpdate("INSERT INTO pessoa(nome, telefone) VALUES ('" + nome + "', '" + telefone + "');");
        } catch (Exception e) {
            throw e;
        }
    }
    // criar um construtor so pra usar um metodo?
    public Pessoa() {

    }
    public String getNome() {
        return this.nome;
    }
    public String getTelefone() {
        return this.telefone;
    }
    public void adicionaContato(String nome, String telefone) throws What7Exceptions, SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        try {
            st.executeUpdate("INSERT INTO contatos (remetente_nome, remetente_telefone, destinatario_nome, destinatario_telefone)" +
                    "values ('" + this.nome + "', '" + this.telefone + "', '" + nome + "', '" + telefone + "');");
        } catch (Exception e) {
            throw new What7Exceptions("Nao foi possivel adicionar o contato!");
        }

        ResultSet rs = st.executeQuery("SELECT * FROM contatos WHERE remetente_nome = '" + nome + "' AND remetente_telefone = '" + telefone +
                "' AND destinatario_nome = '" + this.nome + "' AND destinatario_telefone = '" + this.telefone + "';");

        if (!rs.next()) {
            st.executeUpdate("INSERT INTO contatos (remetente_nome, remetente_telefone, destinatario_nome, destinatario_telefone)" +
                    "values ('" + nome + "', '" + telefone + "', '" + this.nome + "', '" + this.telefone + "');");
        } else{
            throw new What7Exceptions("Usuario ja inserido ja base!");
        }
    }
    private boolean possuiContato(String nome, String telefone) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM contatos WHERE remetente_nome = '" + this.nome + "' AND remetente_telefone = '" + this.telefone +
                "' AND destinatario_nome = '" + nome + "' AND destinatario_telefone = '" + telefone + "';");

        return rs.next();
    }
    public void enviarMensagemChat(String nome, String telefone, String conteudo, int tipo) throws What7Exceptions, SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        Statement keys;

        if (!this.possuiContato(nome, telefone)) this.adicionaContato(nome, telefone);

        try {
            Mensagem novaMensagem = new Mensagem(this.nome, this.telefone, tipo, conteudo);
            keys = novaMensagem.inserirMensagem();
        } catch (Exception e) {
            throw e;
        }
        try (ResultSet generatedKeys = keys.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                st.executeUpdate("INSERT INTO mensagens_pessoa (destinatario_nome, destinatario_telefone, mensagem) " +
                        "values ('" + nome + "', '" + telefone + "', '" + generatedKeys.getLong(1) + "');");
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }

    }
    public void criaGrupo(String grupo) throws What7Exceptions, SQLException {
        Grupo novoGrupo = new Grupo(grupo, this.nome, this.telefone);

    }
    private boolean ehAdministrador(String grupo) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM grupo_adms WHERE grupo = '" + grupo + " ', adm_nome = '" + this.nome +
                "', adm_telefone = '" + this.telefone + "';");

        return rs.next();
    }
    private boolean pertenceAoGrupo(String grupo) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM grupo_participantes WHERE grupo = '" + grupo +
                " 'AND participante_nome = '" + this.nome + "'AND participante_telefone = '" + this.telefone + "';");

        return rs.next();
    }
    public void removerMembroDoGrupo(String nomeMembro, String telefoneMembro, String grupo) throws What7Exceptions, SQLException {
        if(this.ehAdministrador(grupo)){
            Grupo novoGrupo = new Grupo(grupo);
            novoGrupo.removerMembro(nomeMembro, telefoneMembro);
        }
    }
    public void adicionarMembroAoGrupo(String nomeMembro, String telefoneMembro, String telefone, String grupo) throws What7Exceptions, SQLException {
        if(this.ehAdministrador(grupo)){
            Grupo novoGrupo = new Grupo(grupo);
            novoGrupo.incluirNovoMembro(nomeMembro, telefoneMembro);
        }
    }
    public void promoveADMdoGrupo(String nomeMembro, String telefoneMembro, String telefone, String grupo) throws What7Exceptions, SQLException {
        if(this.ehAdministrador(grupo)){
            Grupo novoGrupo = new Grupo(grupo);
            novoGrupo.adicionaADM(nomeMembro, telefoneMembro);
        }
    }
    public void sairDoGrupo(String grupo) throws What7Exceptions, SQLException {
        Grupo novoGrupo = new Grupo(grupo);
        novoGrupo.removerMembro(this.nome, this.telefone);
    }
    public void enviarMensagemGrupo(String nomeDoGrupo, String conteudo, int tipo) throws What7Exceptions, SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        Statement keys;

        if (!this.pertenceAoGrupo(nomeDoGrupo)) throw new What7Exceptions("O usuario nao pertence ao grupo!\n");

        try {
            Mensagem novaMensagem = new Mensagem(this.nome, this.telefone, tipo, conteudo);
            keys = novaMensagem.inserirMensagem();
        } catch (Exception e) {
            throw e;
        }
        try (ResultSet generatedKeys = keys.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                st.executeUpdate("INSERT INTO mensagens_grupo (grupo, mensagem) VALUES ('" + nomeDoGrupo + "', " + generatedKeys.getLong(1) + ");");
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }
    public String listarGrupos() throws SQLException {
        StringBuilder novaString = new StringBuilder();
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM grupo_participantes gp WHERE participante_nome = '" + this.nome + "' AND participante_telefone = '" + this.telefone + "';");

        novaString.append("Membro: ").append(this.nome).append("\n");
        novaString.append("Nome do grupo:\n");
        while (rs.next()) {
            novaString.append(rs.getString(1)).append('\n');
        }

        return novaString.toString();
    }
    public String listarConversas() throws SQLException {
        StringBuilder novaString = new StringBuilder();
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM mensages m JOIN mensagens_pessoa mp ON m.id = mp.mensagem" +
                "WHERE remetente_nome = '" + this.nome + "' AND remetente_telefone = '" + this.telefone + "';");

        novaString.append("Remetente: ").append(this.nome).append("\nDestinatarios: \n");
        novaString.append("Nome:\tTelefone:\n");
        while (rs.next()) {
            novaString.append(rs.getString(3)).append(rs.getString(4));
        }

        return novaString.toString();
    }
}