package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.stream.IntStream;

public record CpfPix(String valorChave) implements ChavePix {

    public CpfPix {
        todosDigitosIguais(valorChave);
        validarDigitosVerificadores(valorChave);
    }

    @Override
    public TipoChavePix getTipo() {
        return TipoChavePix.CPF;
    }

    private void todosDigitosIguais(String cpf) {
        if (cpf.chars().distinct().count() == 1) {
            throw new IllegalArgumentException("CPF inválido - todos os digítos são iguais.");
        }
    }

    private void validarDigitosVerificadores(String cpf) {
        String cpfBase = cpf.substring(0, 9);
        String digitosVerificadoresFornecidos = cpf.substring(9);
        StringBuilder digitosCalculados = new StringBuilder();
        digitosCalculados.append(calcularDigito(cpfBase, 10));
        digitosCalculados.append(calcularDigito(cpfBase, 11));
        if (!digitosCalculados.toString().equals(digitosVerificadoresFornecidos)) {
            throw new IllegalArgumentException("CPF inválido - dígitos verificadores calculados diferente dos fornecidos.");
        }
    }

    private int calcularDigito(String cpf, int pesoInicial) {
        int soma = IntStream.range(0, cpf.length())
                .map(i -> Character.getNumericValue(cpf.charAt(i)) * (pesoInicial - i))
                .sum();
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

}
