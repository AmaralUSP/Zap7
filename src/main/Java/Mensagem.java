package main.Java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class Mensagem {
    private String remetenteNome;
    private String remetenteTelefone;
    private int tipoDaMensagem;
    private String conteudo;

    public Mensagem(String remetenteNome, String remetenteTelefone, int tipoDaMensagem, String conteudo) {
        this.remetenteNome = remetenteNome;
        this.remetenteTelefone = remetenteTelefone;
        this.tipoDaMensagem = tipoDaMensagem;
        this.conteudo = conteudo;
    }
    public Statement inserirMensagem() throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        try {
            st.execute("INSERT INTO mensagens (conteudo, tipo_mensagem, remetente_nome, remetente_telefone) " +
                    "values ('" + this.conteudo + "', " + this.tipoDaMensagem + ", '" + this.remetenteNome + "', '" + this.remetenteTelefone + "') RETURNING id;", Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e){
            throw e;
        }

        return st;
    }

//    @Override
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        s.append('[').append(data_de_criacao).append("] ").append(remetente).append(": ");
//        if(this.tipo_da_mensagem == Mensagem.tipo.TEXTO)
//            s.append(conteudo);
//        else
//            s.append(this.tipo_da_mensagem);
//        return s.toString();
//    }
}
