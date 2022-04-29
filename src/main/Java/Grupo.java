package main.Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Grupo implements What7Interface{
    public String nome_do_grupo;
    public List<Pessoa> participantes;
    public List<Pessoa> administradores;
    public List<Mensagem> mensagens;

    public Grupo(Pessoa administrador, String nome_do_grupo) {
        this.nome_do_grupo = nome_do_grupo;
        this.participantes = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.administradores.add(administrador);
        this.mensagens = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Grupo)) {
            return false;
        }
        Grupo grupo = (Grupo) o;
        return Objects.equals(nome_do_grupo, grupo.getNomeDoGrupo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome_do_grupo);
    }

    public List<Pessoa> getParticipantes() {
        return participantes;
    }

    public List<Pessoa> getAdministradores() {
        return administradores;
    }

    public String listarMensagens() {
        StringBuilder lista_de_mensagens = new StringBuilder();
        for (Mensagem mensagem : this.mensagens) {
            lista_de_mensagens.append(mensagem.toString()).append('\n');
        }
        return lista_de_mensagens.toString();
    }

    public String getNomeDoGrupo() { return this.nome_do_grupo; }

    public void novaMensagem(Mensagem nova_mensagem) {
        this.mensagens.add(nova_mensagem);
    }

    public void incluirNovoMembro(Pessoa pessoa) {
        this.participantes.add(pessoa);
    }
}
