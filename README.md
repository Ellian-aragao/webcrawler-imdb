<h1 align="center">
     Webcrawler IMDb
</h1>

<h4 align="center">
	🚧 Concluído 🚧
</h4>

Tabela de conteúdos
=================

* [Sobre o projeto](#-sobre-o-projeto)
* [Como executar o projeto](#-como-executar-o-projeto)
    * [Pré-requisitos](#pré-requisitos)
    * [Rodando o projeto](#-rodando-o-projeto)
* [Tecnologias](#-tecnologias)
* [Autor](#-autor)
* [Licença](#-licença)


## 💻 Sobre o projeto

WebCrawler dos piores 10 filmes segundo [IMDb](https://www.imdb.com/chart/bottom/).

---

## 🚀 Como executar o projeto

### Pré-requisitos

- [Git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/)
- [JDK 16](https://adoptopenjdk.net/?variant=openjdk16&jvmVariant=openj9)

#### 🎲 Rodando o projeto

```bash

# Clone este repositório
$ git clone git@github.com:Ellian-aragao/webcrawler-imdb.git

# Acesse a pasta do projeto no terminal/cmd
$ cd webcrawler-imdb

# Instale as dependências
$ mvn install

# Execute a aplicação
$ mvn compile exec:java

# Ou crie um arquivo jar executável com todas as dependências
$ mvn clean compile assembly:single

$ java -jar target/crawler-1.0.0-jar-with-dependencies.jar

# Ou baixe o jar com executável disponível nos packages do projeto, no https://github.com/Ellian-aragao/webcrawler-imdb/releases/tag/1.0.0v
$ curl -LO https://github.com/Ellian-aragao/webcrawler-imdb/releases/download/1.0.0v/crawler-1.0.0-jar-with-dependencies.jar

$ java -jar crawler-1.0.0-jar-with-dependencies.jar
```
---

## 🛠 Tecnologias

O projeto fora todo desenvolvido apenas com Java, sem utilização de bibliotecas externas e nem framworks com exceção
apenas da biblioteca de manipulação do DOM, [Jsoup](https://jsoup.org/). Frente a isso a estrutura do projeto foi criada
pensando-se na questão de arquitetura limpa, com o objetivo de facilitar a manutenção e a escalabilidade do projeto. Como
não possui muitas features em si, apenas consumo dos dados e ordenação destes, acaba que não é necessário a utilização,
mas fica a utilização como prova de conceito.

A maior parte do código gira em torno do pacote relativo ao WebCrawler de fato, o qual cuida de fazer todo o processamento
da página, de modo a obter seus dados e devolver em entidades de fácil utilização no sistema, do qual poderia ser criado
extensões fácilmente para utilização de outras tecnologias, como para persistência.

Relativos a decisões de estruturas e aplicações de outros conceitos, existe um projeto base utilizado para a organização
deste, [StringsStoreParser](https://github.com/Ellian-aragao/StringsStoreParser/), o qual possúi implementação de recursos
que dado tempo hábio poderiam ser naturalmente implementados no projeto em questão, tal como uso de threads, que poderiam
agir de forma independente consumindo cada filme, tal que durante testes melhoravam o desempenho da aplicação em 30%, quando
em um computador octacore, mas que fora retirado por questões de deadline do projeto.

---

## 🦸 Autor


 <img style="border-radius: 50%;" src="https://avatars1.githubusercontent.com/u/52057913?s=400&u=222dffcab5586f0eb4efcbff06caa868450f6b8a&v=4" width="100px;" alt=""/>
 <br />
 <a><sub><b>Ellian Aragão Dias</b></sub></a>
 <br />
 
[![Linkedin Badge](https://img.shields.io/badge/-Ellian-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/ellian-arag%C3%A3o-dias-22192a159/)](www.linkedin.com/in/ellian-aragao)
[![Gmail Badge](https://img.shields.io/badge/-ellian.aragao@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:ellian.aragao@gmail.com)](mailto:ellian.aragao@gmail.com)

---

## 📝 Licença

Este projeto esta sobe a licença [MIT](./LICENSE).

Feito com ❤️ por Ellian Aragão Dias 👋🏽 [Entre em contato!](www.linkedin.com/in/ellian-aragao)

---
