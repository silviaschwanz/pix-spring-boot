package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class CnpjPix extends ChavePix {

    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");
    private static final String PREFIXO_ERROR_MESSAGE = "Chave Pix do tipo CNPJ inválida";

    private CnpjPix(String chave, TipoChavePix tipo) {
        super(chave, tipo);
        validarFormato();
        validarTodosDigitosIguais();
        validarDigitosVerificadores();
    }

    private CnpjPix(UUID uuid ,String chave, TipoChavePix tipo) {
        super(uuid, chave, tipo);
    }

    public static ChavePix criar(String chave, TipoChavePix tipo) {
        String chaveLimpa = limparFormatacao(chave);
        return new CnpjPix(chaveLimpa, tipo);
    }

    public static ChavePix restaurar(UUID uuid,String chave, TipoChavePix tipo) {
        return new CnpjPix(uuid, chave, tipo);
    }

    private static String limparFormatacao(String chave) {
        return chave.replaceAll("\\D", "");
    }

    private void validarFormato() {
        if (!CNPJ_PATTERN.matcher(chave).matches()) {
            throw new IllegalArgumentException(PREFIXO_ERROR_MESSAGE + " - formato incorreto.");
        }
    }

    private void validarTodosDigitosIguais() {
        if (chave.chars().allMatch(ch -> ch == chave.charAt(0))) {
            throw new IllegalArgumentException(PREFIXO_ERROR_MESSAGE + " - todos os digítos são iguais.");
        }
    }

    private void validarDigitosVerificadores() {
        String cnpjBase = chave.substring(0, 12);
        int digito1Informado = Character.getNumericValue(chave.charAt(12));
        int digito2Informado = Character.getNumericValue(chave.charAt(13));

        int[] pesosPrimeiro = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundo = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int digito1Calculado = calcularDigito(cnpjBase, pesosPrimeiro);

        String cnpjBaseComDigito1 = cnpjBase + digito1Calculado;

        int digito2Calculado = calcularDigito(cnpjBaseComDigito1, pesosSegundo);

        if (digito1Informado != digito1Calculado || digito2Informado != digito2Calculado) {
            throw new IllegalArgumentException(PREFIXO_ERROR_MESSAGE + " - dígitos verificadores inválidos.");
        }
    }

    private int calcularDigito(String cnpjBase, int[] pesos) {
        int soma = IntStream.range(0, cnpjBase.length())
                .map(i -> Character.getNumericValue(cnpjBase.charAt(i)) * pesos[i])
                .sum();
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

}
