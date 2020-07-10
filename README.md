# poll-api

Poll-api é um microserviço que permite realizar votações, contabilizar votos e validar votantes apenas com CPFs válidos. Cada CPF pode participar apenas uma vez por votação. Após encerrada, o resultado é enviado para uma fila SQS.

O sistema foi desenvolvido utilizando Java 8, Spring boot, Spring data e mongoDB. A validação de campos obrigatórios utiliza as annotations disponíveis na especificação Bean Validation 2.0.

A estrutura de pacotes busca seguir conceitos do DDD, onde o código fonte está dividido em model, application e infrastructure.

Foram desenvolvidas duas versões, uma inicial utilizando Spring Webflux, que está no branch ***reactive***. Esta primeira versão foi desenvolvida com o intuito de aprofundar o conhecimento em programação reativa, entretanto não foi possível finalizá-la com a qualidade desejada dentro do prazo, sendo disponibilizada separadamente em um branch para posterior aperfeiçoamento. A versão final, já coberta parcialmente com testes unitários, está no branch ***master***.

## Recursos

***@POST /v1/poll*** - permite criar uma sessão de votação com duração conforme especificada no campo **expirationInMinutes**. Caso este campo não seja preenchido, o valor default de duração é 1 minuto. Por default, as respostas são SIM e NÃO, entretanto o sistema foi desenvolvido de forma que isto pode ser flexibilizado em uma futura versão, sendo necessário apenas incluir estes campos na requisição.

```
{
    "question": "string",
    "expirationInMinutes": 0,
    "description": "string"
}
```

O retorno possui o código da sessão, que deve ser utilizado para realizar votos e obter resultados de sessões abertas ou finalizadas.

```
{
    "id": "string"
}
```

***@GET /v1/poll/:id/result*** - permite obter resultado de sessões abertas ou finalizadas.

```
{
    "options": [
        {
            "option": "Sim",
            "alias": "YES",
            "votes": 0
        },
        {
            "option": "Não",
            "alias": "NO",
            "votes": 0
        }
    ],
    "expiresIn": "2020-07-10T03:14:51.712",
    "question": "Você gosta do home office?"
}
```

***@GET /v1/voter*** - permite cadastrar votantes. O CPF deve ser válido.

```
{
    "cpf": "string"
}
```

***@POST /v1/poll/:id/vote*** - permite que seja realizado um voto. O CPF deve estar estar registrado e ser válido. Um mesmo CPF pode realizar apenas um voto por votação. **Option** deve conter o valor **YES** ou **NO**.

```
{
    "cpf": "string",
    "optionAlias": "string"
}
```

## Tratamento de erros

Futuramente pretende-se avaliar a adição no exception handler de um direcionamento dos erros/exceptions não previstos para uma ou mais filas SQS apenas para esta finalizade, de forma que permita monitoramento (caso este não seja feito no log diretamente), análise, e eventual reprocessamento.

A estrutura abaixo refere-se aos erros de campos obrigatórios:

```
{
    "errors": [
        {
            "field": "cpf",
            "message": "invalid Brazilian individual taxpayer registry number (CPF)"
        },
        {
            "field": "optionAlias",
            "message": "must not be blank"
        }
    ]
}
```

Demais mensagens seguem o padrão abaixo, e necessitam ser adaptadas para manter o padrão acima:

```
{
    "timestamp": "2020-07-10T06:09:56.897+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Poll ended.",
    "path": "/v1/poll/5f07cd9acedc75f0d8ce34cc/vote"
}
```

## Persistência

A persistência dos dados é realizada no mongoDB utilizando três collections: polls (para votações), voters (para votantes) e votes (para registrar quem já votou em cada votação). O BD está hospedado utilizando a infraestrutura grátis em cloud.mongodb.com. A escolha por um banco não relacional se deve principalmente ao fato de termos estruturas variáveis para as respostas de cada votação, podendo assim futuramente disponibilizar diversos formatos de respostas. 

Seguem exemplos abaixo dos dados:

**Polls** - conceito de 'alias' para as repostas permite identificá-las por este campo, não sendo necessário validar o texto da resposta em si.
```
[
  {
    "_id": {"$oid": "5f07cd9acedc75f0d8ce34cc"},
    "_class": "com.gustavo.pollapi.model.Poll",
    "description": "Pergunta realizada dia 09/07/2020",
    "expirationInMinutes": 2,
    "isClosed": true,
    "options": [
      {
        "alias": "NO",
        "option": "Não",
        "voteCount": 0
      },
      {
        "alias": "YES",
        "option": "Sim",
        "voteCount": 1
      }
    ],
    "question": "Você gosta de trabalhar no modelo de home office?",
    "startedAt": {"$date": "2020-07-10T02:08:26.794Z"}
  }
]
```

**Voters**
```
[
  {
    "_id": "11111111111",
    "_class": "com.gustavo.pollapi.model.Voter"
  }
]
```

***Votes***
```
[
  {
    "_id": {"$oid": "5f07cdaacedc75f0d8ce34cd"},
    "_class": "com.gustavo.pollapi.model.Vote",
    "date": {"$date": "2020-07-10T02:08:42.713Z"},
    "poll": "{ \"$ref\" : \"polls\", \"$id\" : \"5f07cd9acedc75f0d8ce34cc\" }",
    "voter": "{ \"$ref\" : \"voters\", \"$id\" : \"84692766034\" }"
  }
]
```


## Melhorias para próximas versões

- Adicionar MDC para rastrear com maior facilidade os logs de uma operação
- Inclusão de um módulo de autenticação
- Desenvolver mais testes unitários de forma a atingir no mínimo 80% de cobertura
- Desenvolver testes integrados nos endpoints, realizando persistência em um banco em memória
- Buscar alguma tecnologia semelhante ao liquibase para mongoDB, de forma que seja possível a ter um versionamento da estrutura de BD
- Verificar se agrega valor a emissão de um código após a votação, que possa ser utilizado como comprovante de voto realizado
- Acrescentar a possibilidade de criar respostas customizadas no endpoint de criação de votações (algo que o código já suporta em sua modelagem)
- Utilizar alguma tecnologia como Spring Cloud Config + Vault para armazenamento de credenciais de banco, fila SQS e arquivos de configuração, que hoje estão contidos na aplicação
- Inclusão de Dockerfile para rodar a aplicação local
- Inclusão das annotations do Swagger para gerar automaticamente a documentação dos endpoints disponíveis
- Realizar testes de performance
- Endpoint que permita finalizar uma sessão aberta antes do prazo expirar
- Utilizar o padrão strategy para suporte a diversos tipos de respostas, podendo estas serem de múltiplas escolhas, escrita, preenchimento de formulários, etc.