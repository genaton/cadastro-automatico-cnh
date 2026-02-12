package com.bb.cadastro_automatico.cadastro.service;

import com.bb.cadastro_automatico.cadastro.model.Cliente;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CnhExtractorService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Cliente extrair(String ocr) {

        Cliente c = new Cliente();

        c.setNomeCompleto(extrairNome(ocr));
        c.setDataNascimento(parseData(extrairDataNascimento(ocr)));
        c.setLocalNascimento(extrairLocalNascimento(ocr));
        c.setUfNascimento(extrairUfNascimento(ocr));

        c.setRgNumero(extrairRgNumero(ocr));
        c.setRgOrgaoEmissor(extrairRgOrgao(ocr));
        c.setRgUf(extrairRgUf(ocr));

        c.setCpf(extrairCpf(ocr));
        c.setNumeroRegistro(extrairRegistro(ocr));

        c.setDataEmissao(parseData(extrairDataEmissao(ocr)));
        c.setNacionalidade(extrairNacionalidade(ocr));
        c.setFiliacao(extrairFiliacao(ocr));

        return c;
    }

    private LocalDate parseData(String data) {
        if (data == null)
            return null;
        return LocalDate.parse(data, formatter);
    }

    private String extrairNome(String ocr) {
        Pattern p = Pattern.compile("([A-ZÁÉÍÓÚÂÊÔÃÕÇ ]{5,})\\s*\\d{2}/\\d{2}/\\d{4}");
        Matcher m = p.matcher(ocr);
        if (m.find())
            return m.group(1).trim();
        return null;
    }

    private String extrairDataNascimento(String ocr) {
        Pattern p = Pattern.compile("(\\d{2}/\\d{2}/\\d{4}),");
        Matcher m = p.matcher(ocr);
        if (m.find())
            return m.group(1);
        return null;
    }

    private String extrairLocalNascimento(String ocr) {
        Pattern p = Pattern.compile("\\d{2}/\\d{2}/\\d{4},\\s*([A-ZÇÃÕ ]+),");
        Matcher m = p.matcher(ocr);
        if (m.find())
            return m.group(1).trim();
        return null;
    }

    private String extrairUfNascimento(String ocr) {
        Pattern p = Pattern.compile(",\\s*([A-Z]{2})\\s");
        Matcher m = p.matcher(ocr);
        if (m.find())
            return m.group(1);
        return null;
    }

    private String extrairRgNumero(String ocr) {
        Pattern p = Pattern.compile("(\\d{6,12})\\s*[A-Z]");
        Matcher m = p.matcher(ocr);
        if (m.find())
            return m.group(1);
        return null;
    }

    private String extrairRgOrgao(String ocr) {
        Pattern p = Pattern.compile("SSP\\s*([A-Z]{2})");
        Matcher m = p.matcher(ocr);
        if (m.find())
            return "SSP";
        return null;
    }

    private String extrairRgUf(String ocr) {
        Pattern p = Pattern.compile("SSP\\s*([A-Z]{2})");
        Matcher m = p.matcher(ocr);
        if (m.find())
            return m.group(1);
        return null;
    }

    private String extrairCpf(String ocr) {
        Pattern p = Pattern.compile("(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})");
        Matcher m = p.matcher(ocr);
        if (m.find())
            return m.group(1).replaceAll("\\D", "");
        return null;
    }

    private String extrairRegistro(String ocr) {
        Pattern p = Pattern.compile("\\b(\\d{11})\\b");
        Matcher m = p.matcher(ocr);
        while (m.find())
            return m.group(1);
        return null;
    }

    private String extrairDataEmissao(String ocr) {
        // Primeiro tenta encontrar no formato tradicional
        Pattern p1 = Pattern.compile("DATA EMISS[ÃA]O[\\s\\S]{0,20}?(\\d{2}/\\d{2}/\\d{4})");
        Matcher m1 = p1.matcher(ocr);
        if (m1.find())
            return m1.group(1);

        // Se não achar, tenta pegar a primeira data após "DATA EMISSÃO"
        Pattern p2 = Pattern.compile("DATA EMISS[ÃA]O[\\s\\S]*?(\\d{2}/\\d{2}/\\d{4})");
        Matcher m2 = p2.matcher(ocr);
        if (m2.find())
            return m2.group(1);

        return null;
    }

    private String extrairNacionalidade(String ocr) {
        // Captura a palavra NACIONALIDADE e pega a próxima linha com texto em
        // maiúsculas
        // Pattern p = Pattern.compile("NACIONALIDADE[\\s\\r\\n]+([A-ZÁÉÍÓÚÂÊÔÃÕÇ]+\\(?[A-Z]?\\)?)");
        Pattern p = Pattern.compile("Ss r Y /\\s*([A-ZÁÉÍÓÚÂÊÔÃÕÇ]{3,20}\\([A-Z]\\))");
        Matcher m = p.matcher(ocr);
        if (m.find()) {
            return m.group(1).trim();
        }
        return null;
    }

    // private String extrairFiliacao(String ocr) {
    // Pattern p = Pattern.compile("FILIAÇÃO[\\s\\S]*?([A-ZÁÉÍÓÚÂÊÔÃÕÇ
    // ]{5,})[\\s\\S]*?([A-ZÁÉÍÓÚÂÊÔÃÕÇ ]{5,})");
    // Matcher m = p.matcher(ocr);
    // if (m.find())
    // return m.group(1).trim() + " / " + m.group(2).trim();
    // return null;
    // }
    private String extrairFiliacao(String ocr) {

        // 1. Localiza o ponto onde aparece FILIAÇÃO (com ruído)
        Pattern marcador = Pattern.compile("FILIA[ÇC][ÃA]O", Pattern.CASE_INSENSITIVE);
        Matcher m1 = marcador.matcher(ocr);

        if (!m1.find()) {
            return null; // não encontrou o bloco
        }

        // 2. Pega o texto após FILIAÇÃO
        String depois = ocr.substring(m1.end());

        // 3. Captura nomes em maiúsculas (com espaços), ignorando ruído
        Pattern nomes = Pattern.compile("([A-ZÁÉÍÓÚÂÊÔÃÕÇ]{2,}(?: [A-ZÁÉÍÓÚÂÊÔÃÕÇ]{2,})+)");
        Matcher m2 = nomes.matcher(depois);

        String pai = null;
        String mae = null;

        while (m2.find()) {
            String nome = m2.group(1).trim();

            // Ignora palavras curtas ou lixo
            if (nome.length() < 5)
                continue;

            if (pai == null) {
                pai = nome;
            } else {
                mae = nome;
                break;
            }
        }

        if (pai != null && mae != null) {
            return pai + " / " + mae;
        }

        return null;
    }
}