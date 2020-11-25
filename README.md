# KBOARD-API
Api para para gerenciamento de tarefas utilizando o sistema kanban.

## Guia de inicio rápido
### Pré-requisitos
[Java SDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)  
[MySQL Community Server](https://www.mysql.com/downloads/)  
[Apache Maven](http://maven.apache.org/download.cgi)

### Instalação
Clone este repositório em seu diretório de preferência, digitando o comando á seguir em uma interface de linha de comando.
```
git clone https://github.com/hvini/kboard-api
```

Depois de configurar adequadamente as variáveis de ambiente em relação ao banco de dados, ao json web token e ao sendgrid, no arquivo 'application-dev.properties', na pasta resources, caminhe até a pasta raiz da api por meio de uma interface de linha de comando e rode á mesma com o comando
```
mvn spring-boot:run
```

## Desenvolvido com
- [Spring boot](https://start.spring.io/) - Framework Web utilizado.
- [Sendgrid](https://sendgrid.com/) - Serviço utilizado para envio de emails.
- [JSON Web Token](https://jwt.io/) - Utilizado para geração de jwt na autenticação de usuários.

## Licença
Este projeto é licenciado sobre a licença MIT - veja o arquivo [LICENSE.md](LICENSE.md) para mais detalhes.

## Reconhecimentos
[kanban-board](https://github.com/mariorez/kanban-board) - De onde surgiu a idéia.  
[trello-clone](https://github.com/CodingGarden/trello-clone) - Projeto que ajudou na definição de parte da estrutura do banco.