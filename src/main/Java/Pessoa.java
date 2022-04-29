package main.Java;

import java.time.LocalDateTime;
import java.util.*;

public class Pessoa {

    private String nome;
    private String telefone;
    private List<Pessoa> contatos;
    private List<Conversa> conversas;
    private List<Grupo> grupos;
    public Pessoa(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
        this.contatos = new ArrayList<>();
        this.conversas = new ArrayList<>();
        this.grupos = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Pessoa)) {
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(nome, pessoa.getNome()) &&
                Objects.equals(telefone, pessoa.getTelefone());
    }
    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone);
    }
    public String getTelefone() {
        return this.telefone;
    }
    public String getNome() { return this.nome; }
    public List<Grupo> getGrupos() { return this.grupos; }
    public List<Pessoa> getContatos() { return this.contatos; }
    private boolean possuiContato(Pessoa destinatario){
        return this.contatos.contains(destinatario);
    }
    private boolean pertenceAoGrupo(Grupo grupo){
        return this.grupos.contains(grupo);
    }
    private boolean ehAdministrador(Grupo grupo){
        return grupo.administradores.contains(this);
    }
    private void iniciaConversa(Pessoa destinatario){
        Conversa nova_conversa = new Conversa(this.getNome(), destinatario.getNome());
        this.conversas.add(nova_conversa);
    }
    public void criaGrupo(String grupo) throws What7Exceptions{
        Grupo novo_grupo = grupos_da_plataforma.get(grupo);
        if(novo_grupo == null) {
            novo_grupo = new Grupo(this, grupo);
            grupos_da_plataforma.put(grupo, novo_grupo);
            this.grupos.add(novo_grupo);
        }
        else if(this.pertenceAoGrupo(novo_grupo))
            throw new What7Exceptions("O usuario ja pertence a esse grupo!");
        else
            throw new What7Exceptions("O usuario ainda nao eh membro do grupo, peca ajuda para o administrador do grupo!");
    }
    public boolean enviarMensagemChat(String telefone, Mensagem nova_mensagem) throws What7Exceptions {
        Pessoa destinatario = usuarios_da_plataforma.get(telefone);
        if(destinatario != null) {
            if (!possuiContato(destinatario)) {
                try {
                    this.adicionaContato(destinatario.getTelefone());
                    this.iniciaConversa(destinatario);
                } catch (Exception e){
                    throw e;
                }
            }
            if (!possuiContato(this)) {
                try {
                    destinatario.adicionaContato(this.getTelefone());
                    destinatario.iniciaConversa(this);
                } catch (Exception e){
                    throw e;
                }
            }

            Conversa conversa_remetente = this.conversas.stream()
                    .filter(conversa -> destinatario.getNome().equals(conversa.getDestinatario()))
                    .findAny()
                    .orElse(null);
            Conversa conversa_destinatario = destinatario.conversas.stream()
                    .filter(conversa -> this.getNome().equals(conversa.getDestinatario()))
                    .findAny()
                    .orElse(null);

            conversa_remetente.novaMensagem(nova_mensagem);
            conversa_destinatario.novaMensagem(nova_mensagem);

            return true;
        }
        return false;
    }
    public void enviarMensagemGrupo(String nome_do_grupo, Mensagem nova_mensagem) throws What7Exceptions {
        Grupo grupo = grupos_da_plataforma.get(nome_do_grupo);
        if (grupo != null) {
            if (!pertenceAoGrupo(grupo)) {
                throw new What7Exceptions("O usuario nao pertence a esse grupo!");
            }

            Grupo grupo_atual = this.grupos.stream()
                    .filter(g -> grupo.getNomeDoGrupo().equals(g.getNomeDoGrupo()))
                    .findAny()
                    .orElse(null);

            grupo_atual.novaMensagem(nova_mensagem);
        }
        else throw new What7Exceptions("O grupo nao existe!");
    }
    public void adicionaContato(String telefone) throws What7Exceptions {
        Pessoa novo_contato = usuarios_da_plataforma.get(telefone);
        if(novo_contato != null){
            this.contatos.add(novo_contato);
            this.iniciaConversa(novo_contato);
        }
        else
            throw new What7Exceptions("O contato nao existe!");
    }
    public void adicionarMembroAoGrupo(String telefone, String grupo) throws What7Exceptions {
        Grupo novo_grupo = grupos_da_plataforma.get(grupo);
        Pessoa pessoa = usuarios_da_plataforma.get(telefone);
        if(novo_grupo == null)
            throw new What7Exceptions("O grupo nao existe!");
        if(pessoa == null)
            throw new What7Exceptions("O usuario nao existe!");
        if(pessoa.pertenceAoGrupo(novo_grupo))
            throw new What7Exceptions("O usuario ja pertence ao grupo!");
        if(!(this.ehAdministrador(novo_grupo) || this.pertenceAoGrupo(novo_grupo)))
            throw new What7Exceptions("O usuario nao possui permissao suficiente!");
        novo_grupo.incluirNovoMembro(pessoa);
        pessoa.grupos.add(novo_grupo);
    }
    static Hashtable<String, Pessoa> usuarios_da_plataforma = new Hashtable<String, Pessoa>();
    static Hashtable<String, Grupo> grupos_da_plataforma = new Hashtable<String, Grupo>();

    public static void main(String args[]) throws What7Exceptions {
        usuarios_da_plataforma.put("123456789", new Pessoa("Joao", "40028922"));
        usuarios_da_plataforma.put("40028922", new Pessoa("Joao", "40028922"));
        usuarios_da_plataforma.put("11953958755", new Pessoa("feliz", "11953958755"));
        usuarios_da_plataforma.put("39416956", new Pessoa("cleber", "39416956"));

        LocalDateTime dtm = LocalDateTime.now();
        Mensagem nova_mensagem = new Mensagem(usuarios_da_plataforma.get("11953958755").getNome(), Mensagem.tipo.TEXTO, "seras se funciona?", dtm);
        dtm = LocalDateTime.now();
        Mensagem nova_mensagem2 = new Mensagem(usuarios_da_plataforma.get("39416956").getNome(), Mensagem.tipo.TEXTO, "Sim funciona", dtm);
        dtm = LocalDateTime.now();
        Mensagem nova_mensagem3 = new Mensagem(usuarios_da_plataforma.get("11953958755").getNome(), Mensagem.tipo.TEXTO, "Ok tks", dtm);

//        usuarios_da_plataforma.get("11953958755").adicionaContato("40028922");
//        usuarios_da_plataforma.get("11953958755").enviarMensagem_chat("40028922", nova_mensagem);
//        System.out.print(usuarios_da_plataforma.get("11953958755").conversas.get(0).listarMensagens());

        try{
            usuarios_da_plataforma.get("11953958755").criaGrupo("Um grupo foda ao som de Mandelao");
        } catch (Exception e){
            System.out.print(e);
        }
//        try{
//            usuarios_da_plataforma.get("11953958755").criaGrupo("Um grupo foda ao som de Mandelao");
//        } catch (Exception e){
//            System.out.print(e);
//        }
        try{
            usuarios_da_plataforma.get("11953958755").adicionarMembroAoGrupo("39416956", "Um grupo foda ao som de Mandelao");
        } catch (Exception e){
            System.out.print(e);
        }
//        try{
//            usuarios_da_plataforma.get("39416956").adicionaMembroAoGrupo("123456789", "Um grupo foda ao som de Mandelao");
//        } catch (Exception e){
//            System.out.print(e);
//        }

        try{
            usuarios_da_plataforma.get("11953958755").enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem);
            usuarios_da_plataforma.get("39416956").enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem2);
            usuarios_da_plataforma.get("11953958755").enviarMensagemGrupo("Um grupo foda ao som de Mandelao", nova_mensagem3);
        } catch (Exception e){
            System.out.print(e);
        }

        System.out.print(usuarios_da_plataforma.get("11953958755").grupos.get(0).listarMensagens());
    }


}
