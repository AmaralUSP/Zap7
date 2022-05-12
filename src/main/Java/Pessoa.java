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

//    @Override
//    public boolean equals(Object o) {
//
//        if (o == this) return true;
//        if (!(o instanceof Pessoa)) {
//            return false;
//        }
//        Pessoa pessoa = (Pessoa) o;
//        return Objects.equals(nome, pessoa.getNome()) &&
//                Objects.equals(telefone, pessoa.getTelefone());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(nome, telefone);
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder imprimir = new StringBuilder();
//
//        return imprimir.append("Nome: ").append(this.nome).append(" Telefone: ").append(this.telefone).toString();
//    }

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
                "' AND destinatario_nome = '" + this.nome + "' AND " + this.telefone + " = '11953958755';");

        if (!rs.next()) {
            st.executeUpdate("INSERT INTO contatos (remetente_nome, remetente_telefone, destinatario_nome, destinatario_telefone)" +
                    "values ('" + nome + "', '" + telefone + "', '" + this.nome + "', '" + this.telefone + "');");
        }
    }

    private boolean possuiContato(String nome, String telefone) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM contatos WHERE remetente_nome = '" + nome + "' AND remetente_telefone = '" + telefone +
                "' AND destinatario_nome = '" + this.nome + "' AND " + this.telefone + " = '11953958755';");

        return rs.next();
    }

    public void enviarMensagemChat(String nome, String telefone, String conteudo, int tipo) throws What7Exceptions, SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet keys;

        if (!this.possuiContato(nome, telefone)) this.adicionaContato(nome, telefone);

        try {
            st.executeUpdate("INSERT INTO mensagens (conteudo, tipo_mensagem, remetente_nome, remetente_telefone) " +
                    "values ('" + conteudo + "', " + tipo + ", '" + this.nome + "', '" + this.telefone + "');");
            keys = st.getGeneratedKeys();
            st.executeUpdate("INSERT INTO mensagens_pessoa (destinatario_nome, destinatario_telefone, mensagem) " +
                    "values ('" + nome + "', '" + telefone + "', " + keys.getInt("id") + ");");
        } catch (Exception e) {
            throw e;
        }

    }

    public String listarConversas() throws SQLException {
        StringBuilder novaString = new StringBuilder();
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM mensages m JOIN mensagens_pessoa mp ON m.id = mp.mensagem" +
                        "WHERE remetente_nome = " + this.nome + " AND remetente_telefone = " + this.telefone ";");

        novaString.append("Remetente: ").append(this.nome).append("\nDestinatarios: \n");
        novaString.append("Nome:\tTelefone:\n");
        while (rs.next()) {
            novaString.append(rs.getString(1)).append(rs.getString(2));
        }

        return novaString.toString();
    }

    public void criaGrupo(String grupo) throws What7Exceptions, SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        try {
            st.executeUpdate("INSERT INTO grupo (nome_do_grupo) values ('"+ grupo + "');");
            st.executeUpdate("INSERT INTO grupo_participantes (grupo, participante_nome, participante_telefone)" +
                    "VALUES ('"+ grupo + "', '" + this.nome + "', '" + this.telefone + "');");
            st.executeUpdate("INSERT INTO grupo_adms (grupo, adm_nome, adm_telefone)" +
                    "VALUES ('"+ grupo + "', '" + this.nome + "', '" + this.telefone + "');");

        } catch (Exception e) {
            throw e;
        }
    }

    private boolean ehAdministrador(String grupo) {
        Grupo novoGrupo = new Grupo();
        return novoGrupo.ehAdministrador(grupo, this.nome, this.telefone);
    }
//
//    private boolean pertenceAoGrupo(Grupo grupo) {
//        return this.grupos.contains(grupo);
//    }
//
//    public String listarPessoasDoGrupo(String nomeDoGrupo, String tipo) throws What7Exceptions {
//        Grupo novo_grupo = grupos_da_plataforma.get(nomeDoGrupo);
//        if (novo_grupo == null)
//            throw new What7Exceptions("O grupo nao existe!");
//        if (!this.pertenceAoGrupo(novo_grupo))
//            throw new What7Exceptions("O usuario nao pertence ao grupo!");
//        if (tipo.equals("Participantes")) return novo_grupo.listarPessoas(tipo);
//        else if (tipo.equals("Administradores")) return novo_grupo.listarPessoas(tipo);
//        else return "";
//    }
//
//    public void removerMembroDoGrupo(String telefone, String grupo) throws What7Exceptions {
//        Grupo novo_grupo = grupos_da_plataforma.get(grupo);
//        Pessoa pessoa = usuarios_da_plataforma.get(telefone);
//        if (novo_grupo == null)
//            throw new What7Exceptions("O grupo nao existe!");
//        if (pessoa == null)
//            throw new What7Exceptions("O usuario nao existe!");
//        if (!(this.ehAdministrador(novo_grupo) || this.pertenceAoGrupo(novo_grupo)))
//            throw new What7Exceptions("O usuario nao possui permissao suficiente!");
//        novo_grupo.removerMembro(pessoa);
//        pessoa.grupos.remove(novo_grupo);
//    }
//
//    public void adicionarMembroAoGrupo(String telefone, String grupo) throws What7Exceptions {
//        Grupo novo_grupo = grupos_da_plataforma.get(grupo);
//        Pessoa pessoa = usuarios_da_plataforma.get(telefone);
//        if (novo_grupo == null)
//            throw new What7Exceptions("O grupo nao existe!");
//        if (pessoa == null)
//            throw new What7Exceptions("O usuario nao existe!");
//        if (pessoa.pertenceAoGrupo(novo_grupo))
//            throw new What7Exceptions("O usuario ja pertence ao grupo!");
//        if (!(this.ehAdministrador(novo_grupo) || this.pertenceAoGrupo(novo_grupo)))
//            throw new What7Exceptions("O usuario nao possui permissao suficiente!");
//        novo_grupo.incluirNovoMembro(pessoa);
//        pessoa.grupos.add(novo_grupo);
//    }
//
//    public void promoveADMdoGrupo(String telefone, String grupo) throws What7Exceptions {
//        Grupo novo_grupo = grupos_da_plataforma.get(grupo);
//        Pessoa pessoa = usuarios_da_plataforma.get(telefone);
//        if (novo_grupo == null)
//            throw new What7Exceptions("O grupo nao existe!");
//        if (pessoa == null)
//            throw new What7Exceptions("O usuario nao existe!");
//        if (!(this.ehAdministrador(novo_grupo) || this.pertenceAoGrupo(novo_grupo)))
//            throw new What7Exceptions("O usuario nao possui permissao suficiente!");
//        if (!pessoa.pertenceAoGrupo(novo_grupo)) {
//            novo_grupo.incluirNovoMembro(pessoa);
//            pessoa.grupos.add(novo_grupo);
//        }
//        novo_grupo.adicionaADM(pessoa);
//    }
//
//    public void sairDoGrupo(String grupo) throws What7Exceptions {
//        Grupo sairDoGrupo = grupos_da_plataforma.get(grupo);
//        if (sairDoGrupo == null)
//            throw new What7Exceptions("O grupo nao existe!");
//        if (!this.pertenceAoGrupo(sairDoGrupo))
//            throw new What7Exceptions("O usuario nao pertence ao grupo!");
//        sairDoGrupo.sairDoGrupo(this);
//    }
//
//    public void enviarMensagemGrupo(String nome_do_grupo, Mensagem nova_mensagem) throws What7Exceptions {
//        Grupo grupo = grupos_da_plataforma.get(nome_do_grupo);
//        if (grupo != null) {
//            if (!pertenceAoGrupo(grupo)) {
//                throw new What7Exceptions("O usuario nao pertence a esse grupo!");
//            }
//
//            Grupo grupo_atual = this.grupos.stream()
//                    .filter(g -> grupo.getNomeDoGrupo().equals(g.getNomeDoGrupo()))
//                    .findAny()
//                    .orElse(null);
//
//            grupo_atual.novaMensagem(nova_mensagem);
//        } else throw new What7Exceptions("O grupo nao existe!");
//    }
//
//    public String listarGrupos() {
//        StringBuilder novaString = new StringBuilder();
//        novaString.append("Pessoa: ").append(this.nome).append("\nGrupos: \n");
//        for (Grupo atual : this.grupos) {
//            novaString.append(atual.nome_do_grupo).append('\n');
//        }
//        return novaString.toString();
//    }
}