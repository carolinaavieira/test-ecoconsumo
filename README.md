# Testes Automatizados — EcoConsumo API

Projeto de testes automatizados da API EcoConsumo, desenvolvido com Java, Maven, Cucumber e Rest Assured. Os cenários são descritos em Gherkin (português) e incluem testes de autenticação, cadastro e validação de contrato JSON.

## Objetivo

Validar o comportamento dos principais endpoints da API EcoConsumo por meio de testes de integração HTTP, verificando códigos de resposta e, no cenário de cadastro de dispositivo, a estrutura do JSON retornado.

## Tecnologias

- Java 21 (versão configurada como `maven.compiler.source` e `maven.compiler.target` no `pom.xml`).
- Maven — gerenciamento de dependências e execução.
- Cucumber 7.18.1 e Cucumber JUnit — especificação e execução dos cenários Gherkin.
- Rest Assured 5.5.0 — envio e validação de requisições HTTP.
- Gson 2.11.0 — serialização e desserialização JSON.
- NetworkNT JSON Schema Validator 1.5.1 — validação de contrato JSON.
- JSON-java 20240303 — manipulação de objetos JSON.
- Lombok 1.18.34 — redução de código repetitivo nos Models.
- IntelliJ IDEA — ambiente utilizado para executar os testes manualmente.
- Docker — execução local da API EcoConsumo.
- Microsoft Azure App Service — hospedagem da API utilizada pelo projeto de testes.
- GitHub — versionamento do projeto.

## Ambiente da API

A configuração atual das Services aponta para a API publicada no Azure:

`https://web-ecoconsumo-fiap-caawamh6gvc5e8cj.brazilsouth-01.azurewebsites.net`

O projeto também possui uma imagem Docker da API para execução local. A imagem utilizada no ambiente local foi `carolinaavieira/ecoconsumo-fiap:v3`, com a porta `8080` publicada no host.

> A URL base está definida diretamente nas classes de Service. Caso seja necessário executar os testes contra outro ambiente, atualize a URL nas Services correspondentes antes da execução.

## Estrutura do projeto

```text
test-ecoconsumo/
├── src/
│   ├── main/java/                 # Estrutura principal do projeto
│   └── test/
│       ├── java/
│       │   ├── hook/              # Hooks de preparação e finalização
│       │   ├── model/             # Models usados nas requisições/respostas
│       │   ├── runner/            # TestRunner do Cucumber
│       │   ├── services/          # Comunicação HTTP e validações
│       │   └── steps/             # Implementação dos passos Gherkin
│       └── resources/
│           ├── features/          # Cenários escritos em Gherkin
│           └── schemas/           # Contratos JSON
├── pom.xml                        # Dependências e configuração Maven
└── README.md
```

## Cenários automatizados

Os arquivos de funcionalidades estão em `src/test/resources/features`.

### AuthLogin.feature — Recuperar Token

- **Autorização Login bem-sucedida:** envia email e senha ao endpoint `POST /auth/login` e verifica HTTP 200.
- **Erro ao fazer login:** envia credenciais inválidas e verifica HTTP 403.

### SalvarDispositivo.feature — Salvar dados de um dispositivo

- Realiza login para obter o token JWT.
- Envia os dados de um dispositivo ao endpoint `POST /api/dispositivos` com autenticação Bearer.
- Verifica HTTP 201.
- Valida a resposta com o contrato `schemas/salvar-dispositivo-sucesso.json`.

### SalvarUnidade.feature — Salvar dados de uma unidade

- Realiza login para obter o token JWT.
- Envia os dados da unidade ao endpoint `POST /api/unidades` com autenticação Bearer.
- Verifica HTTP 201.

### BuscarDispositivos.feature

Contém um cenário que executa autenticação e chama `POST /api/dispositivos`. Apesar do nome do arquivo e da descrição do cenário indicarem recuperação/busca, a requisição implementada é de cadastro (`POST`). Esse arquivo não está selecionado pelo filtro `@regressivo` configurado no Runner.

## Organização técnica

- **Steps:** conectam as frases Gherkin aos métodos de serviço.
- **Services:** montam as requisições HTTP, enviam o JWT quando necessário e guardam as respostas.
- **Models:** representam os dados enviados e recebidos. O Gson está configurado para considerar os campos anotados com `@Expose`.
- **Hooks:** executam rotinas de preparação e finalização antes/depois dos cenários e da suíte.
- **Runner:** configura diretório de features, pacotes de Steps/Hooks, filtro de tags e relatório HTML.

## Pré-requisitos

- JDK 21 instalado.
- IntelliJ IDEA.
- Maven integrado ao IntelliJ (ou Maven Wrapper, caso incluído na versão do projeto entregue).
- Acesso à internet para baixar dependências e acessar a API publicada.
- API EcoConsumo disponível no ambiente selecionado.

## Como abrir e executar no IntelliJ IDEA

1. Extraia o projeto para uma pasta local.
2. Abra a pasta do projeto no IntelliJ IDEA.
3. Aguarde a importação do projeto Maven pelo arquivo `pom.xml`.
4. Em **File → Project Structure → Project**, selecione o JDK 21 e o nível de linguagem 21.
5. Em **Settings → Build Tools → Maven**, confirme que **Work offline** está desmarcado.
6. Recarregue o projeto Maven pelo painel Maven (**Reload All Maven Projects**).
7. Abra `src/test/java/runner/TestRunner.java`.
8. Clique com o botão direito em `TestRunner` e selecione **Run 'TestRunner'**.
9. Consulte o resultado no painel **Run / Test Results** do IntelliJ.

## Filtro de execução e relatório

O `TestRunner` está configurado com a tag `@regressivo`:

```java
tags = "@regressivo",
plugin = {"html:target/cucumber-reports.html"}
```

Assim, a execução pelo Runner seleciona somente os cenários marcados com `@regressivo`. O arquivo `BuscarDispositivos.feature`, que não possui essa tag, não é incluído nessa execução.

O Cucumber está configurado para gerar o relatório HTML em:

`target/cucumber-reports.html`

O relatório é criado após a execução do Runner. A pasta `target` é gerada pelo Maven/IDE e não precisa ser versionada; execute os testes novamente para regenerar os resultados.

## Execução pelo Maven (opcional)

Na raiz do projeto, também é possível executar a suíte com:

```bash
mvn test
```

Esse comando utiliza a configuração do projeto e o filtro de tags definido no `TestRunner`.

## Docker — API local

A imagem Docker da API pode ser executada localmente. Exemplo com a imagem utilizada no ambiente de desenvolvimento:

```bash
docker run -d --name ecoconsumo-v3 -p 8080:8080 carolinaavieira/ecoconsumo-fiap:v3
```

Para verificar o container:

```bash
docker ps
```

Para consultar os logs:

```bash
docker logs ecoconsumo-v3
```

Quando os testes forem direcionados à API local, configure a URL base das Services para `http://localhost:8080`. Na configuração entregue analisada, as Services estão apontadas para o Azure.
