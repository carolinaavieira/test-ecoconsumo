#language: pt

  @regressivo
  Funcionalidade: Recuperar Token
    Como usuario da api
    Quero recuperar o token
    Para que eu consiga acessar o restante das apis do projeto

  Cenario: Autorizacao Login bem-sucedida
    Dado que eu tenha o seguinte usuario:
      | campo | valor              |
      | email | lunavieira@cao.com |
      | senha | 124L@m             |
    Quando eu enviar a requisição para o endpoint "/auth/login" de auth login
    Entao status code deve ser 200

  Cenario: Erro ao fazer login
    Dado que eu tenha o seguinte usuario:
      | campo | valor         |
      | email | dobby@cao.com |
      | senha | 132D@m        |
    Quando eu enviar a requisição para o endpoint "/auth/login" de auth login
    Entao status code deve ser 403


