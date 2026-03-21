# EMAIL SENDER 📧

Este é um pequeno serviço web para o envio de e-mails a partir de requisições ao servidor, sejam de texto, HTML ou com anexos.

Além de enviar e-mails, o sistema também quarda informações deles em um banco de dados, neste caso em memória com o H2.

## 🚀 Tecnologias utilizadas

- ☕ Java 21 
- 🍃 Spring Boot 
- 💾 Spring Data JPA 
- 🗄 H2 Database 
- 🌐 Spring Web 
- 🐳 Docker 

## 🔀 Endpoints

| endpoint                   | verbo HTTP | descrição                                                  |
|----------------------------|------------|------------------------------------------------------------|
| /health-check              | GET        | para checar se o app está de pé                            |
| /v1/email                  | POST       | endpoint que envia os emails                               |
| /v1/email/{emailId}        | GET        | pegar os detalhes de um email já enviado                   |
| /v1/email                  | GET        | pegar uma lista de emails já enviados                      |
| /v1/email/metrics/delivery | GET        | metricas de envio de email, como a taxa de envios por dias |


## ▶️ Como rodar

1. Clone o repositório
```bash
git clone https://github.com/pedroth07/email-sender.git
cd email-sender
```

2. Faça o build e rode
```bash
docker compose up --build -d
```