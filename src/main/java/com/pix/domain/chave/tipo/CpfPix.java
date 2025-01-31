package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class CpfPix extends ChavePix {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
    private static final String PREFIXO_ERROR_MESSAGE = "Chave Pix do tipo CPF inválida";

    private CpfPix(String chave, TipoChavePix tipo) {
        super(chave, tipo);
    }

    public static ChavePix criar(String chave, TipoChavePix tipo) {
        String chaveLimpa = limparFormatacao(chave);
        validarFormato(chaveLimpa);
        validarTodosDigitosIguais(chaveLimpa);
        validarDigitosVerificadores(chaveLimpa);
        return new CpfPix(chaveLimpa, tipo);
    }

    public static ChavePix restaurar(String chave, TipoChavePix tipo) {
        return new CpfPix(chave, tipo);
    }


    private static String limparFormatacao(String chave) {
        return chave.replaceAll("\\D", "");
    }

    private static void validarFormato(String chave) {
        if (!CPF_PATTERN.matcher(chave).matches()) {
            throw new IllegalArgumentException(PREFIXO_ERROR_MESSAGE + " - formato incorreto.");
        }
    }

    private static void validarTodosDigitosIguais(String chave) {
        if (chave.chars().distinct().count() == 1) {
            throw new IllegalArgumentException(PREFIXO_ERROR_MESSAGE + " - todos os digítos são iguais.");
        }
    }

    private static void validarDigitosVerificadores(String chave) {
        String cpfBase = chave.substring(0, 9);
        String digitosVerificadoresFornecidos = cpfBase.substring(9);
        StringBuilder digitosCalculados = new StringBuilder();
        digitosCalculados.append(calcularDigito(cpfBase, 10));
        digitosCalculados.append(calcularDigito(cpfBase, 11));
        if (!digitosCalculados.toString().equals(digitosVerificadoresFornecidos)) {
            throw new IllegalArgumentException(PREFIXO_ERROR_MESSAGE + " - dígitos verificadores inválidos.");
        }
    }

    private static int calcularDigito(String cpf, int pesoInicial) {
        int soma = IntStream.range(0, cpf.length())
                .map(i -> Character.getNumericValue(cpf.charAt(i)) * (pesoInicial - i))
                .sum();
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

}
