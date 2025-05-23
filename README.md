# API-Odonto

API RESTful para gerenciamento de agendamento entre pacientes e dentistas, construída com foco em segurança, desempenho e facilidade de manutenção.

## Tecnologias e Dependências

- **Spring Boot**: Framework principal para construção da aplicação backend em Java.
- **Java**: Linguagem de programação utilizada.
- **JWT (JSON Web Token)**: Para autenticação e autorização segura dos usuários.
- **Flyway**: Gerenciamento e versionamento das migrations do banco de dados.
- **BCrypt**: Hashing seguro de senhas para proteção dos dados dos usuários.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar os dados da aplicação.
- **Lombok**: Biblioteca para reduzir boilerplate code no Java (getters, setters, construtores, etc).

## Funcionalidades Principais

- Cadastro e autenticação de usuários (pacientes e dentistas).
- Criação, consulta, edição e cancelamento de agendamentos.
- Segurança via JWT para garantir que apenas usuários autenticados possam acessar recursos protegidos.
- Versionamento do banco de dados através do Flyway para facilitar o controle das alterações de schema.

## Estrutura do Projeto

- Módulos organizados para separar entidades, serviços, controladores e repositórios.
- Configurações para diferentes ambientes (produção, teste).
- Migrations para versionar alterações do banco de dados.

---

Sinta-se à vontade para contribuir, abrir issues ou sugerir melhorias!

---

### Como rodar a aplicação

1. Configure as variáveis do banco no `application.properties` ou `application.yml`.
2. Execute a aplicação via Spring Boot.
3. Use ferramentas como Postman para testar os endpoints da API.

---

Feito  por João Vitor Santos Policarpo
