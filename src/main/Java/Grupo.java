package main.Java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Grupo implements What7Interface{
    public String nome_do_grupo;

    public Grupo(Pessoa administrador, String nome_do_grupo) {
        this.nome_do_grupo = nome_do_grupo;
        this.participantes = new ArrayList<>();
        this.participantes.add(administrador);
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
    public void adicionaADM(Pessoa pessoa) { this.administradores.add(pessoa); }
    public void removerMembro(Pessoa pessoa) {
        this.participantes.remove(pessoa);
        this.administradores.remove(pessoa);
    }
    public void promoverADM(Pessoa pessoa) { this.administradores.add(pessoa); }
    public int quantidadeDeMembros() { return this.participantes.size(); }
    public int quantidadeDeAdministradores() { return this.administradores.size(); }
    public String listarPessoas(String tipo) {
        List<Pessoa> listaPessoas = new ArrayList<>();
        StringBuilder lista = new StringBuilder();

        if (tipo.equals("Administradores")) listaPessoas = this.administradores;
        else if (tipo.equals("Participantes")) listaPessoas = this.participantes;

        for (Pessoa adm : listaPessoas) {
            lista.append(adm.toString()).append('\n');
        }
        return lista.toString();
    }
    public void sairDoGrupo(Pessoa p) throws What7Exceptions{
        this.participantes.remove(p);
        this.administradores.remove(p);

        if(this.administradores.size() == 0){
            if(this.participantes.size() == 0){
//                excluir o banco
                throw new What7Exceptions("O grupo nao possui mais membros e nem participantes, entao ele foi excluido!");
            } else {
                this.administradores.add(this.participantes.get(0));
//                throw new What7Exceptions("O grupo nao possui mais administradores, entao o primeiro membro a ser adicionado ao grupo se tornou administrador!");
            }
        }
    }
    public boolean ehAdministrador(String nome_do_grupo, String nome, String telefone) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM grupo_adms WHERE grupo = " + this.nome_do_grupo + "adm_nome = '" + nome +
                                                                    "' AND adm_telefone = '" + telefone + "';");

        return rs.next();
    }
}
