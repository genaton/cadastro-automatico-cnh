package com.bb.cadastro_automatico.cadastro.controller;

import com.bb.cadastro_automatico.cadastro.model.Cliente;
import com.bb.cadastro_automatico.cadastro.service.CnhExtractorService;
import com.bb.cadastro_automatico.cadastro.repository.ClienteRepository;

import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    @Autowired
    private CnhExtractorService extractorService;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/cnh")
    public Cliente lerCnh(@RequestParam("arquivo") MultipartFile arquivo) throws Exception {

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

        // Extrai os dados da CNH e monta o objeto Cliente
        Cliente cliente = extractorService.extrair(textoOcr);

        // Salva no banco
        return clienteRepository.save(cliente);
    }

    private String lerPdf(MultipartFile arquivo, Tesseract tesseract) throws Exception {
        PDDocument document = PDDocument.load(arquivo.getInputStream());
        PDFRenderer renderer = new PDFRenderer(document);

        // Converte a primeira página do PDF para imagem (300 DPI = melhor OCR)
        BufferedImage imagem = renderer.renderImageWithDPI(0, 300);

        document.close();

        return tesseract.doOCR(imagem);
    }
}