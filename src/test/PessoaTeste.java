package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Java.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class PessoaTeste {

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
    public void enviarMensagemGrupo() {
        try {
            Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");
            Pessoa novaPessoa = new Pessoa("teste27", "1234567890");
            novoGrupo.incluirNovoMembro(novaPessoa.getNome(), novaPessoa.getTelefone());
            novaPessoa.enviarMensagemGrupo("Um grupo foda ao som de Mandelao", "Vamobora Imperial", 1);
        } catch (Exception e){
            System.out.print(e);
        }
    }

}
