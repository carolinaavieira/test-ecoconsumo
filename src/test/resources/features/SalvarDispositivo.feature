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

    Cenario: Deve ser possivel cadastrar um dispositivo
      Dado que eu recupere o token retornado no contexto e salve o dispositivo:
        | campo       | valor                     |
        | nome        | Ar-Condicionado           |
        | dispositivo | Equipamento               |
        | status      | Ligado                    |
        | nomeUnidade | Escritório Administrativo |
        | setorUnidade | Setor                     |
      Quando eu enviar a requisição para o endpoint "/api/dispositivos" para salvar os dados de um dispositivo
      Entao status code do dispositivo criado deve ser 201
      E o contrato selecionado será "Salvar Dispositivo Sucesso"
      Entao a resposta da requisicao deve estar de acordo com o contrato
