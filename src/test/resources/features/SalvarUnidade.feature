#language: pt
@regressivo
Funcionalidade: Salvar dados de um dispositivo
  Contexto: Autorizacao Login bem-sucedida
    Dado que eu tenha o seguinte usuario:
      | campo | valor              |
      | email | lunavieira@cao.com |
      | senha | 124L@m             |
    Quando eu enviar a requisição para o endpoint "/auth/login" de auth login
    Entao status code deve ser 200

  Cenario: Deve ser possivel cadastrar uma unidade
    Dado que eu recupere o token retornado no contexto e salve a unidade:
      | campo     | valor   |
      | nome      | Matriz  |
      | tpUnidade | Empresa |
    Quando eu enviar a requisição para o endpoint "/api/unidades" para salvar os dados de uma unidade
    Entao status code da unidade criada deve ser 201
