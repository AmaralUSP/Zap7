package main.Java;

import java.time.LocalDateTime;

public class Mensagem {

    public enum tipo {
        TEXTO,
        IMAGEM,
        AUDIO,
        VIDEO
    }

    public tipo tipo_da_mensagem;
    public String conteudo;
    public LocalDateTime data_de_criacao;
    public String remetente;

    public Mensagem(String remetente, tipo tipo_da_mensagem, String conteudo, LocalDateTime data_de_criacao) {
        this.remetente = remetente;
        this.tipo_da_mensagem = tipo_da_mensagem;
        this.conteudo = conteudo;
        this.data_de_criacao = data_de_criacao;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('[').append(data_de_criacao).append("] ").append(remetente).append(": ");
        if(this.tipo_da_mensagem == Mensagem.tipo.TEXTO)
            s.append(conteudo);
        else
            s.append(this.tipo_da_mensagem);
        return s.toString();
    }
}
