package test;

import main.Java.Grupo;
import main.Java.What7Exceptions;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertThrows;

public class GrupoTeste {
    @Test
    public void adicionaMembroAoGrupoComSucesso() {
        try {
            Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");
            novoGrupo.incluirNovoMembro("teste12`", "1234567890");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    @Test
    public void erroAoCriaGrupoMembroJaPertenceAoGrupo() {
        Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");

        assertThrows(
                SQLException.class,
                () -> novoGrupo.incluirNovoMembro("Feliz", "11953958755")
        );
    }
    @Test
    public void erroAoAdicionarUmMembroInexistenteAoGrupo() {
        Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");

        assertThrows(
                SQLException.class,
                () -> novoGrupo.incluirNovoMembro("Feliz", "11953958755")
        );
    }
    @Test
    public void removerMembroDoGrupoComSucesso() throws SQLException {
        Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");
        novoGrupo.removerMembro("teste12`", "1234567890");
    }
    @Test
    public void promoveADMdoGrupo() throws What7Exceptions, SQLException {
        Grupo novoGrupo = new Grupo("Um grupo foda ao som de Mandelao");
        novoGrupo.adicionaADM("Ana", "15400289220");
    }
}
