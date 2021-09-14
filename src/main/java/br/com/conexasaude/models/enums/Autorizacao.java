package br.com.conexasaude.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Autorizacao {

    MEDICO(1, "ROLE_MEDICO");

    private int codigo;
    private String descricao;

    public static Autorizacao toEnum(Integer code) {

        if (code == null)
            return null;

        for (Autorizacao autorizacao : Autorizacao.values()) {
            if (code.equals(autorizacao.getDescricao()))
                return autorizacao;
        }

        throw new IllegalArgumentException("Id " + code + " inválido.");
    }
}
