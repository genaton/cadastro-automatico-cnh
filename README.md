# Cadastro Automático de Dados de Documento de Identidade e Abertura Automática de Conta-Corrente

Um sistema de cadastro automático de dados do Documento de Identidade e abertura automática de conta-corrente desenvolvido em Java.

## 📋 Descrição

Este projeto implementa uma solução automatizada para o cadastro e processamento de informações de Documento de Identificação, utilizando tecnologias de OCR (Optical Character Recognition) e processamento de imagens.

## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem de programação principal
- **Tesseract**: Engine de OCR para reconhecimento de texto em documentos
- **tessdata**: Dados de treinamento para o Tesseract

## 📁 Estrutura do Projeto

```
cadastro-automatico-cnh/
├── cadastro/          # Módulo de cadastro e processamento
├── tessdata/          # Dados de treinamento do Tesseract
└── README.md          # Este arquivo
```

## 🚀 Como Utilizar

### Pré-requisitos

- Java 8 ou superior
- Maven (para gerenciamento de dependências)
- Tesseract OCR instalado no sistema

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/genaton/cadastro-automatico-cnh.git
cd cadastro-automatico-cnh
```

2. Instale as dependências:
```bash
mvn install
```

3. Execute o projeto:
```bash
mvn run
```

## 📝 Funcionalidades

- Captura automática de dados de Documento de Identificação
- Reconhecimento ótico de caracteres (OCR)
- Validação de informações extraídas
- Armazenamento seguro de dados

## 🔧 Configuração

Para rodar a aplicação faça as seguintes alterações e digite os seguintes comandos:

> no mysql crie o schema 'cadastro'
> em application.properties altere as informações:
spring.datasource.url=jdbc:mysql://localhost:3306/cadastro
spring.datasource.username=[usuario]
spring.datasource.password=[senha_usuario]

> em controller> OcrController na linha 42 altere o local para o arquivo tessdata> por.traineddata
tesseract.setDatapath("[local do arquivo]");

> cd .\cadastro\
> spring-boot:run

## 📚 Documentação

Para mais informações sobre como contribuir ou usar o projeto, consulte a documentação adicional nos issues e wikis do repositório.

## 👨‍💻 Autor

**genaton** - [GitHub Profile](https://github.com/genaton)

## 📄 Licença

Este projeto não possui licença especificada. Consulte a política de uso antes de utilizar.

## 📞 Contato e Suporte

Para dúvidas ou sugestões, abra uma [issue](https://github.com/genaton/cadastro-automatico-cnh/issues) no repositório.

---

**Última atualização**: 2026-03-03 23:14:05
