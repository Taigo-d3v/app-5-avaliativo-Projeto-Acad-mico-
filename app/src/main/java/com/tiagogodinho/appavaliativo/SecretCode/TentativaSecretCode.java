package com.tiagogodinho.appavaliativo.SecretCode;

public class TentativaSecretCode {
    public char[] digitos;
    public int[] cores; // 0 = vermelho, 1 = amarelo, 2 = verde

    public TentativaSecretCode(char[] digitos, int[] cores) {
        this.digitos = digitos;
        this.cores = cores;
    }
}
