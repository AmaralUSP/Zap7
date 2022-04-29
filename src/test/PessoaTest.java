package test;

import org.junit.Before;

import java.time.LocalDateTime;
import main.Java.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PessoaTest {
    private static final Pessoa pessoaTest1 = new Pessoa("Joao", "40028922");
    private static final Pessoa pessoaTest2 = new Pessoa("feliz", "11953958755");
    private static final Mensagem nova_mensagem = new Mensagem("feliz", Mensagem.tipo.TEXTO, "seras se funciona?", LocalDateTime.now());
    private static final Mensagem nova_mensagem2 = new Mensagem("Joao", Mensagem.tipo.TEXTO, "Sim funciona", LocalDateTime.now());
    private static final Mensagem nova_mensagem3 = new Mensagem("Feliz", Mensagem.tipo.TEXTO, "Ok tks", LocalDateTime.now());

    @Test
    public void deveAdicionarContatoComSucesso(){
        try {
            pessoaTest1.adicionaContato("11953958755");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(pessoaTest2, pessoaTest1.getContatos().stream()
                                        .filter(contatos -> pessoaTest2.getNome().equals(contatos.getNome()))
                                        .findAny()
                                        .orElse(null));
    }
    @Test
    public void erroAoAdicionarContatoInexistente(){
        Throwable exception = assertThrows(What7Exceptions.class, () -> pessoaTest1.adicionaContato("11953958755"));
        assertEquals("O contato nao existe!", exception.getMessage());
    }
    @Test
    public void enviaMensagemComSucesso(){
        try{
            pessoaTest1.enviarMensagemChat("11953958755", nova_mensagem);
        } catch (Exception e){
            System.out.print(e);
        }
    }

    @Test
    public void criaGrupoComSucesso() {
        try {
            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    @Test
    public void erroAoCriarUmGrupoJaExistente() {
        try {
            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    @Test
    public void erroAoCriaGrupoMembroJaPertenceAoGrupo() {
        try {
            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            pessoaTest1.adicionarMembroAoGrupo("11953958755", "Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            pessoaTest2.criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    @Test
    public void adicionaMembroAoGrupoComSucesso() {
        try {
            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            pessoaTest1.adicionarMembroAoGrupo("39416956", "Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    @Test
    public void erroAoAdicionarUmMembroInexistenteAoGrupo() {
        try {
            pessoaTest1.adicionarMembroAoGrupo("123456789", "Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    @Test
    public void listarMensagensDeUmGrupo() {
        try {
            pessoaTest1.criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            pessoaTest1.adicionarMembroAoGrupo("39416956", "Um grupo foda ao som de Mandelao");
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            pessoaTest1.enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem);
            pessoaTest2.enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem2);
            pessoaTest1.enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem3);
        } catch (Exception e) {
            System.out.print(e);
        }
        System.out.print(pessoaTest1.getGrupos().get(0).listarMensagens());
    }



}
