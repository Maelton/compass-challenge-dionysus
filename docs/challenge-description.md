# Desafio 03 - Desenvolvimento de Aplicação para Ecommerce com Spring Boot

O projeto consiste no desenvolvimento de uma estrutura “Backend” que compreenda em uma aplicação para o funcionamento de um ecommerce.

# Regras de Negócio

### Visão geral da aplicação

Deverá ser implementado uma aplicação de ecommerce que seja capaz de criar e gerenciar produtos, estoque dos produtos, vendas e usuários, conforme descrito abaixo.

### Requisitos básicos:

- Tecnologias: Java 17+
- Framework: Spring Boot
- Diagrama: Entidade-Relacionamento (ER) ou Unified Modeling Language (UML).
- Banco de dados: O sistema deve utilizar um banco de dados RELACIONAL (PostgreSQL, MySQL...).
- Arquitetura de API: Escolher uma arquitetura de API para organizar o projeto (Layered Architecture, Hexagonal Architecture, Clean Architecture, etc...).
- Boas práticas: O código deve ser desenvolvido seguindo as boas práticas de desenvolvimento de software, para garantir a qualidade e a manutenibilidade.
- Versionamento: O código fonte deve estar no repositório do GitHub.

### Requisitos funcionais:

1. Permitir que os usuários criem, leiam, atualizem e excluam produtos. (PESO 3)
2. Criar validações que façam sentido para os dados de entrada, por exemplo: Preço do produto só pode ser positivo. (PESO 2)
3. Um produto não pode ser DELETADO após ser incluso em uma venda, porém deve ter alguma maneira de INATIVAR ele. (PESO 2)
4. Controlar o estoque do produto de forma que ele não possa ser vendido caso o estoque seja menor do que a quantidade necessária para a venda ou igual a zero. (PESO 3)
5. Permitir que os usuários criem, leiam, atualizem e excluam vendas (Uma venda tem que ter no mínimo 1 produto para ser concluída). (PESO 3)
6. Criar métodos de relatório de vendas por data (informada pelo cliente), por mês e pela semana atual (considerar dias úteis). (PESO 1)
7. Os métodos “GET ALL” de Produtos e Vendas devem salvar as informações no CACHE da aplicação, para que as próximas buscas sejam mais rápidas. Deve ser feito um bom gerenciamento do cache, por exemplo: ao inserir uma nova venda, deletar o cache, para que a informação seja buscada no banco de dados diretamente e venha atualizada. (PESO 1)
8. Todas as EXCEÇÕES devem ser tratadas e seguir o mesmo padrão de resposta. (PESO 1)
9. Todos os campos data, devem seguir o padrão ISO 8601 (exemplo: 2023-07-20T12:00:00Z). (PESO 1)
10. Implementar Autenticação via Token JWT. PESO (3)
11. Implementar Autorização para os usuários autenticados. (PESO 3)
12. Implementar um método para resetar a senha do usuário. (PESO 3)
13. Somente usuários com permissão de ADMIN podem deletar informações do sistema. (PESO 3)
14. Somente usuários com permissão de ADMIN podem cadastrar e atualizar novos produtos e outros usuários ADMIN. (PESO 2)