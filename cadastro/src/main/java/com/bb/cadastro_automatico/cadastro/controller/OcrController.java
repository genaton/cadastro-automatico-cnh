package com.bb.cadastro_automatico.cadastro.controller;

import com.bb.cadastro_automatico.cadastro.model.Cliente;
import com.bb.cadastro_automatico.cadastro.model.Agencia;
import com.bb.cadastro_automatico.cadastro.service.CnhExtractorService;
import com.bb.cadastro_automatico.cadastro.repository.ClienteRepository;
import com.bb.cadastro_automatico.cadastro.repository.AgenciaRepository;

import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/ocr")
public class OcrController {

    @Autowired
    private CnhExtractorService extractorService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    @PostMapping("/cnh")
    public ResponseEntity<?> lerCnh(@RequestParam("arquivo") MultipartFile arquivo) throws Exception {

        // Configuração do Tesseract
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:/TI/Dev_Foundation/desafio_final_bck/tessdata");
        tesseract.setLanguage("por");

        String contentType = arquivo.getContentType();
        String textoOcr;

        // PDF → converter para imagem
        if (contentType != null && contentType.equals("application/pdf")) {
            textoOcr = lerPdf(arquivo, tesseract);
        }
        // Imagem → OCR direto
        else {
            BufferedImage imagem = ImageIO.read(arquivo.getInputStream());
            textoOcr = tesseract.doOCR(imagem);
        }

        System.out.println("\n================ TEXTO OCR LIDO ================\n");
        System.out.println(textoOcr);
        System.out.println("\n================================================\n");

        // Extrai os dados da CNH e monta o objeto Cliente
        Cliente cliente = extractorService.extrair(textoOcr);
        cliente.setDocumentoIdentidade(arquivo.getBytes());

        // Salva o cliente
        Cliente clienteSalvo = clienteRepository.save(cliente);

        // Criar agência e conta vinculadas ao cliente
        Agencia agencia = new Agencia();
        agencia.setNumeroAgencia(gerarNumeroAgencia());
        agencia.setNumeroConta(gerarNumeroConta());
        agencia.setCliente(clienteSalvo);

        agenciaRepository.save(agencia);

        // Mensagens separadas
        String mensagemValidacao = "Documento validado com sucesso pelo órgão emissor!";

        String mensagemConta = String.format(
            "Sua conta foi aberta com sucesso! Agência: %s, Conta: %s. %s seja bem-vindo ao BB!",
            agencia.getNumeroAgencia(),
            agencia.getNumeroConta(),
            clienteSalvo.getNomeCompleto()
        );

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagemValidacao", mensagemValidacao);
        resposta.put("mensagemConta", mensagemConta);

        return ResponseEntity.ok(resposta);
    }

    private String lerPdf(MultipartFile arquivo, Tesseract tesseract) throws Exception {
        PDDocument document = PDDocument.load(arquivo.getInputStream());
        PDFRenderer renderer = new PDFRenderer(document);

        renderer.setSubsamplingAllowed(false);

        // Converte a primeira página do PDF para imagem (300 DPI = melhor OCR)
        BufferedImage imagem = renderer.renderImageWithDPI(0, 300);

        document.close();

        return tesseract.doOCR(imagem);
    }

    private String gerarNumeroAgencia() {
        return (int)(Math.random() * 9000) + 1000 + "-" + (int)(Math.random() * 9);
    }

    private String gerarNumeroConta() {
        return (int)(Math.random() * 90000) + 10000 + "-" + (int)(Math.random() * 9);
    }
}