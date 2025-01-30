package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.stream.IntStream;

public record CnpjPix(String valorChave) implements ChavePix {

    public CnpjPix {
        todosDigitosIguais(valorChave);
        validarDigitosVerificadores(valorChave);
    }

    @Override
    public TipoChavePix getTipo() {
        return TipoChavePix.CNPJ;
    }

    private void todosDigitosIguais(String cnpj) {
        if (cnpj.chars().allMatch(ch -> ch == cnpj.charAt(0))) {
            throw new IllegalArgumentException("CNPJ inválido - todos os digítos são iguais.");
        }
    }

    private void validarDigitosVerificadores(String cnpj) {
        String cnpjBase = cnpj.substring(0, 12);
        int digito1Informado = Character.getNumericValue(cnpj.charAt(12));
        int digito2Informado = Character.getNumericValue(cnpj.charAt(13));

        int[] pesosPrimeiro = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundo = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int digito1Calculado = calcularDigito(cnpjBase, pesosPrimeiro);
        int digito2Calculado = calcularDigito(cnpjBase + digito1Calculado, pesosSegundo);

        if (!(digito1Informado == digito1Calculado && digito2Informado == digito2Calculado)) {
            throw new IllegalArgumentException("CNPJ inválido - dígitos verificadores calculados diferente dos fornecidos.");
        }
    }

    private int calcularDigito(String cnpj, int[] pesos) {
        int soma = IntStream.range(0, pesos.length)
                .map(i -> Character.getNumericValue(cnpj.charAt(i)) * pesos[i])
                .sum();
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

}
