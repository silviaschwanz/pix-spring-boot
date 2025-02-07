# Projeto para estudo - API REST em Spring Boot referente a transações PIX

### **Descrição do desafio:**

Crie uma API RESTful, em Spring Boot, para gerenciar transações PIX.

A API deve atender aos seguintes requisitos:

### **Funcionalidades:**

1. **Registrar Transação PIX**
    - Endpoint para criar uma transação.
    - Campos:
        - `id` (UUID gerado automaticamente).
        - `chavePix` (chave de origem do PIX, válida).
        - `valor` (deve ser positivo e maior que zero).
        - `destinatario` (chave PIX do destinatário).
        - `dataHora` (timestamp gerado no momento da criação).
    - Validações:
        - A chave PIX deve ter formato válido (CPF, e-mail, telefone ou chave aleatória).
        - Não permitir transações para a mesma chave de origem e destinatário.
2. **Listar Transações**
    - Endpoint para listar todas as transações registradas para uma chave pix origem.
3. **Buscar Transação por ID**
    - Endpoint para buscar os detalhes de uma transação específica.

### **Regras de Negócio:**

- O sistema deve garantir que as transações sejam persistidas corretamente no banco.
- As validações devem ser implementadas com Bean Validation.
- Use um banco de dados H2 para facilitar o desenvolvimento.

________________________________________________________________________________________________________________________

## Configuração do Ambiente:

Necessário: Java 17 e docker.  
Pode ser utilizado o maven wrapper existente no próprio projeto, para isso usar `./mvnw` . 
Projeto com docker compose contendo dois serviços de banco de dados, um da aplicação e outro para testes.  

### Arquivo .env docker:
É necessário copiar o arquivo .exemplo.env com o nome .env, nesse novo arquivo preencha os dados conforme necessário.

### Arquivo .env SpringBoot:
No diretório resources da aplicação, copiar o arquivo .exemplo.env com nome .env. Nele devem ser preenchidos valores iguais aos colocados no arquivo .env do docker.

________________________________________________________________________________________________________________________
## Execução de Testes Unitários:
Os testes unitários deverão ficar no diretório padrão de testes e não deverão conter dependências Spring.  
Assim eles rodam sem subir o contexto spring o que os torna mais rápidos.  
Para executá-los via linha de comando:
`./mvnw test`

## Execução de Testes de Integração:
Foi feita configuração no maven para que os testes de integração possam ser executados de forma separada dos testes unitários.  
Todos os testes que contiverem anotações SpringBoot deverão ser considerados testes de integração e deverão ser colocados no diretório integration-test, seguindo a mesma organização de pacotes do projeto java.
Para executá-los via linha de comando:
`./mvnw verify`

## Modelagem
https://miro.com/app/board/uXjVLlYfY-Q=/?moveToWidget=3458764615540821694&cot=14
![diagrama_ER_transacoes_pix ](https://github.com/user-attachments/assets/c6728ae6-f197-4634-a1f7-b9e99603a950)

## Swagger
http://localhost:8080/swagger-ui.html

## Modelagem para refatoração em microsserviços - relação de tópicos
![pix_microsservices_topics drawio](https://github.com/user-attachments/assets/f3866d84-3071-4e5b-b3ef-7fb4c0ba49dc)

