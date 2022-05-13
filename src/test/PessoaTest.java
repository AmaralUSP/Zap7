package test;

import org.junit.Before;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import main.Java.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class PessoaTest {

    private static String NOME_FELIZ = "FELIZ";
    private static String TEL_FELIZ = "11953958755";

    @Test
    public void deveAdicionarContatoComSucesso() throws SQLException, What7Exceptions {
        try {
            Pessoa novaPessoa = new Pessoa("teste", "23456789456123");
            novaPessoa.adicionaContato("Feliz", "11953958755");
        } catch (Exception e){
            System.out.print(e);
        }
    }
    @Test
    public void erroAoAdicionarContatoUsuarioInexistente() throws SQLException {
        assertThrows(SQLException.class,
                ()->{
                    PostgreSQLJDBC app = new PostgreSQLJDBC();
                    Connection conn = app.connect();
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM contatos WHERE remetente_nome = 'Jon Doe' AND remetente_telefone = '4098234908239' AND " +
                            "destinatario_nome = 'Feliz' AND destinatario_telefone = '11953958755';");
                });
    }

    @Test
    public void enviaMensagemComSucesso(){
        try {
            Pessoa novaPessoa = new Pessoa("teste", "23456789456123");
            novaPessoa.enviarMensagemChat(novaPessoa.getNome(), novaPessoa.getTelefone(), "Uma mensagem de teste", 1);
        } catch (Exception e){
            System.out.print(e);
        }
    }
//
//    @Test
//    public void criaGrupoComSucesso() {
//        try {
//            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//    }
//    @Test
//    public void erroAoCriarUmGrupoJaExistente() {
//        try {
//            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//        try {
//            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//    }
//    @Test
//    public void erroAoCriaGrupoMembroJaPertenceAoGrupo() {
//        try {
//            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//        try {
//            pessoaTest1.adicionarMembroAoGrupo("11953958755", "Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//        try {
//            pessoaTest2.criaGrupo("Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//    }
//    @Test
//    public void adicionaMembroAoGrupoComSucesso() {
//        try {
//            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//        try {
//            pessoaTest1.adicionarMembroAoGrupo("39416956", "Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//    }
//    @Test
//    public void erroAoAdicionarUmMembroInexistenteAoGrupo() {
//        try {
//            pessoaTest1.adicionarMembroAoGrupo("123456789", "Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//    }
//    @Test
//    public void listarMensagensDeUmGrupo() {
//        try {
//            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//        try {
//            pessoaTest1.adicionarMembroAoGrupo("39416956", "Um grupo foda ao som de Mandelao");
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//        try {
//            pessoaTest1.enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem);
//            pessoaTest2.enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem2);
//            pessoaTest1.enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem3);
//        } catch (Exception e) {
//            System.out.print(e);
//        }
//        System.out.print(pessoaTest1.getGrupos().get(0).listarMensagens());
//    }
//
//
//
}
