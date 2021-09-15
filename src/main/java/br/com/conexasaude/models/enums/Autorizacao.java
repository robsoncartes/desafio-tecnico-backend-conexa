package br.com.conexasaude.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Autorizacao {

    MEDICO(1, "ROLE_MEDICO");

    public int codigo;
    private String descricao;

    public static Autorizacao toEnum(Integer codigo) {

        if (codigo == null)
            return null;

        for (Autorizacao autorizacao : Autorizacao.values()) {
            if (codigo.equals(autorizacao.getCodigo()))
                return autorizacao;
        }

        throw new IllegalArgumentException("Id " + codigo + " inválido.");
    }
}
