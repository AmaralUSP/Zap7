package main.Java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class Mensagem {
    public enum tipo {
        TEXTO,
        IMAGEM,
        AUDIO,
        VIDEO
    }

    public Mensagem(String remetenteNome, String remetenteTelefone, tipo tipoDaMensagem, String conteudo, LocalDateTime data_de_criacao) throws SQLException {
        PostgreSQLJDBC app = new PostgreSQLJDBC();
        Connection conn = app.connect();
        Statement st = conn.createStatement();

        try {
            st.executeUpdate("INSERT INTO mensagens (conteudo, tipo_mensagem, remetente_nome, remetente_telefone) " +
                    "values ('" + conteudo + "', " + tipoDaMensagem + ", '" + remetenteNome + "', '" + remetenteTelefone + "');");
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
