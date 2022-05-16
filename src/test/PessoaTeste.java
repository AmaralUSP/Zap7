package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Java.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class testeDeFuncionalidades {

    @Test
    public void deveAdicionarContatoComSucesso() throws SQLException, What7Exceptions {
        // como tratar isso?
        try {
            Pessoa novaPessoa = new Pessoa("teste", "23456789456123");
            novaPessoa.adicionaContato("Feliz", "11953958755");
        } catch (Exception e){
            System.out.print(e);
        }
    }
    @Test
    public void erroAoAdicionarContatoUsuarioInexistente() throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM contatos WHERE remetente_nome = 'Jon Doe' AND remetente_telefone = '4098234908239' AND " +
                "destinatario_nome = 'Feliz' AND destinatario_telefone = '11953958755';");

        assertFalse(rs.next());
    }
    @Test
    public void enviaMensagemComSucesso(){
        try {
            Pessoa novaPessoa = new Pessoa("teste5", "1234567890");
            novaPessoa.enviarMensagemChat("Feliz", "11953958755", "Uma mensagem de teste", 1);
        } catch (Exception e){
            System.out.print(e);
        }
    }
    @Test
    public void criaGrupoComSucesso() {
        try {
            Pessoa novaPessoa = new Pessoa("teste12`", "1234567890");
            novaPessoa.criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    @Test
    public void erroAoCriarUmGrupoJaExistente() throws SQLException {
        Pessoa novaPessoa = new Pessoa("teste13`", "1234567890");

        assertThrows(
                SQLException.class,
                () -> novaPessoa.criaGrupo("Um grupo foda ao som de Mandelao")
        );
    }
    @Test
    public void adicionaMembroAoGrupoComSucesso() {
        try {
            Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");
            novoGrupo.incluirNovoMembro("teste12`", "1234567890");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    @Test
    public void erroAoCriaGrupoMembroJaPertenceAoGrupo() {
        Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");

        assertThrows(
                SQLException.class,
                () -> novoGrupo.incluirNovoMembro("Feliz", "11953958755")
        );
    }
    @Test
    public void erroAoAdicionarUmMembroInexistenteAoGrupo() {
        Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");

        assertThrows(
                SQLException.class,
                () -> novoGrupo.incluirNovoMembro("Feliz", "11953958755")
        );
    }
    @Test
    public void removerMembroDoGrupoComSucesso() throws SQLException {
        Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");
        novoGrupo.removerMembro("teste12`", "1234567890");
    }
    @Test
    public void promoveADMdoGrupo() throws What7Exceptions, SQLException {
        Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");
        novoGrupo.incluirNovoMembro("teste12`", "1234567890");
        Grupo novoGrupo = new Grupo(grupo);
        novoGrupo.incluirNovoMembro(nomeMembro, telefoneMembro);
    }
//    public void sairDoGrupo(String grupo) throws What7Exceptions, SQLException {
//        Grupo novoGrupo = new Grupo(grupo);
//        novoGrupo.removerMembro(this.nome, this.telefone);
//    }
//    public void enviarMensagemGrupo(String nomeDoGrupo, String conteudo, int tipo) throws What7Exceptions, SQLException {
//        PostgreSQLJDBC app = new PostgreSQLJDBC();
//        Connection conn = app.connect();
//        Statement st = conn.createStatement();
//        Statement keys;
//
//        if (!this.pertenceAoGrupo(nomeDoGrupo)) throw new What7Exceptions("O usuario nao pertence ao grupo!\n");
//
//        try {
//            Mensagem novaMensagem = new Mensagem(this.nome, this.telefone, tipo, conteudo);
//            keys = novaMensagem.inserirMensagem();
//        } catch (Exception e) {
//            throw e;
//        }
//
//        try (ResultSet generatedKeys = keys.getGeneratedKeys()) {
//            if (generatedKeys.next()) {
//                st.executeUpdate("INSERT INTO  mensagens_grupo (grupo, mensagem) " +
//                        "values ('" + nomeDoGrupo + "', " + generatedKeys.getLong(1) + ");");
//            }
//            else {
//                throw new SQLException("Creating user failed, no ID obtained.");
//            }
//        }
//    }

}
