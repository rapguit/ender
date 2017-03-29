# ender
## Description
Software de cadastro de endereços.

Dispõe de uma interface web e uma API Rest para gerenciar os endereços.

* API Guide: https://rapguit.github.io/ender/api-guide.html

## Install
### Com Docker =)
Rodar os comandos:
* baixar a imagem:
```
docker pull rapguit/ender
```
* rodar o container:
```
docker run -p 8080:8080 rapguit/ender
```
dockerhub: https://hub.docker.com/r/rapguit/ender/

O conteiner é construído sobre java8 tomcat 8, e roda uma base de dados mongo embed.

* Não tem docker instalado?
  * https://www.docker.com/community-edition
   
### Sem Docker =/
Primeiro passo é clonar o repositório:
```
$ git clone https://github.com/rapguit/ender.git
```

Agora vá no diretório raiz do projeto e execute:
```
$ ./mvnw package
```
Quando o build terminar, você está pronto rodar o projeto:
```
$ ./mvnw spring-boot:run

```
### Usando
* Acessar o sistema pelo browser:
  * http://localhost:8080/ender/address/list
  
* Acessar API Rest
  * https://rapguit.github.io/ender/api-guide.html
  
