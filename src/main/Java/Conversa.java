package main.Java;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Conversa implements What7Interface{
    public String destinatario;
    public String remetente;
    public List<Mensagem> mensagens;
    public LocalDateTime ultima_atualizacao;

    public Conversa(String remetente, String destinatario) {
        Date date = new Date();

        this.destinatario = destinatario;
        this.remetente = remetente;
        this.mensagens = new ArrayList<>();
        this.ultima_atualizacao = LocalDateTime.now();
    }
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Conversa)) {
            return false;
        }
        Conversa conversa = (Conversa) o;
        return Objects.equals(destinatario, conversa.getDestinatario()) &&
                Objects.equals(remetente, conversa.getRemetente());
    }
    @Override
    public int hashCode() {
        return Objects.hash(remetente, destinatario);
    }
    public String getDestinatario() {
        return destinatario;
    }
    public String getRemetente() {
        return remetente;
    }
    public String listarMensagens() {
        StringBuilder lista_de_mensagens = new StringBuilder();
        for (Mensagem mensagem : this.mensagens) {
            lista_de_mensagens.append(mensagem.toString()).append('\n');
        }
        return lista_de_mensagens.toString();
    }
    public void novaMensagem(Mensagem mensagem) {
        this.mensagens.add(mensagem);
    }
    public LocalDateTime getUltima_atualizacao() {
        return ultima_atualizacao;
    }

}
