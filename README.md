
# QR Code Generator with Java, Spring Boot, Docker, and AWS S3

Este projeto é um **gerador de QR Code** desenvolvido em **Java 24** com **Spring Boot**, utilizando **Docker** para facilitar o ambiente de desenvolvimento e implantação. Além disso, o projeto integra-se com o **AWS S3**, onde as imagens geradas dos QR Codes são armazenadas.

## Visão Geral

O **Gerador de QR Code** é uma aplicação que recebe um texto via API e gera um QR Code correspondente. O QR Code gerado é então enviado para um bucket **AWS S3**, e a URL do arquivo armazenado é retornada ao usuário. O projeto foi desenvolvido para ser fácil de usar, altamente escalável e eficiente, aproveitando as tecnologias Spring Boot, Docker e AWS.

### Funcionalidades

- **Geração de QR Codes** a partir de texto (como URLs).
- **Armazenamento no AWS S3**: As imagens dos QR Codes gerados são enviadas e armazenadas de forma segura na nuvem.
- **API Restful** para integrar com outras aplicações ou sistemas.

## Requisitos

- **Java 24**
- **Docker**
- **AWS S3** (você precisa de uma conta AWS e configurar as credenciais para acesso ao S3).

## Como Rodar o Projeto

### 1. Criação do arquivo `.env`

O projeto usa variáveis de ambiente para configurar a integração com o AWS S3. Para começar, crie um arquivo `.env` na raiz do seu projeto e insira as variáveis de ambiente necessárias. Um exemplo de como fazer isso pode ser encontrado no arquivo `.env-example`.

Exemplo de conteúdo para o arquivo `.env`:

```env
AWS_ACCESS_KEY_ID=your-aws-access-key
AWS_SECRET_ACCESS_KEY=your-aws-secret-key
AWS_REGION=us-east-1
S3_BUCKET_NAME=qrcode-storagebp
```

### 2. Comandos para Gerar a Imagem e Rodar o Contêiner

Com o Docker instalado, siga os seguintes passos:

1. **Construir a imagem Docker**:

```bash
docker build -t qrcode-generator:1.0 .
```

- Este comando vai construir a imagem do Docker com o nome `qrcode-generator:1.0` com base no `Dockerfile` presente na raiz do seu projeto.

2. **Rodar o contêiner Docker**:

```bash
docker run -d -p 7777:8080 --env-file .env --name qrcode-generator qrcode-generator:1.0
```

- O comando acima irá rodar a aplicação na porta `7777` da sua máquina local, enquanto o contêiner usará as variáveis definidas no arquivo `.env` para se conectar ao AWS S3.

### 3. Teste a API

Após o contêiner estar rodando, você pode testar a API de geração de QR Code. Para isso, envie uma **requisição POST** para o seguinte endpoint:

```http
POST http://[sua_url]:7777/api/qrcode/generate
```

#### Corpo da Requisição (JSON):

```json
{
    "text": "https://www.google.com"
}
```

#### Resposta Esperada:

```json
{
    "url": "https://qrcode-storagebp.s3.us-east-1.amazonaws.com/287691892376"
}
```

A resposta conterá a URL onde a imagem gerada do QR Code pode ser acessada no **AWS S3**.
