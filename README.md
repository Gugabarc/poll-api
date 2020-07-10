# poll-api

Poll-api é um microserviço que permite realizar votações, contabilizar votos e validar votantes apenas com CPFs válidos. Cada CPF pode participar apenas uma vez por votação. Após encerrada, o resultado é enviado para uma fila SQS.

O sistema foi desenvolvido utilizando Java 8, Spring boot, Spring data e mongoDB. A validação de campos obrigatórios utiliza as annotations disponíveis na especificação Bean Validation 2.0.

Foram desenvolvidas duas versões, uma inicial utilizando Spring Webflux, que está no branch ***reactive***. Esta primeira versão foi desenvolvida com o intuito de aprofundar o conhecimento em programação reativa, entretanto não foi possível finalizá-la com a qualidade desejada dentro do prazo, sendo disponibilizada separadamente em um branch para posterior aperfeiçoamento. A versão final, já coberta parcialmente com testes unitários, está no branch ***master***.

## Recursos

@POST /v1/poll - permite criar uma sessão de votação com duração conforme especificada no campo **expirationInMinutes**. Caso este campo não seja preenchido, o valor default de duração é 1 minuto. Por default, as respostas são SIM e NÃO, entretanto o sistema foi desenvolvido de forma que isto pode ser flexibilizado em uma futura versão, sendo necessário apenas incluir estes campos na requisição.

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

@GET /v1/poll/:pollId/result - permite obter resultado de sessões abertas ou finalizadas.

```
{
    "responses": [
        {
            "option": "string",
            "votes": 0
        },
        {
            "option": "string",
            "votes": 0
        }
    ]
}
```

@GET /v1/voters - permite cadastrar votantes. O CPF deve ser válido.

```
{
    "cpf": "string"
}
```

@POST /v1/poll/5f07cd9acedc75f0d8ce34cc/vote - permite que seja realizado um voto. O CPF deve estar estar registrado e ser válido. Um mesmo CPF pode realizar apenas um voto por votação. **Option** deve conter o valor **YES** ou **NO**.

```
{
    "cpf": "string",
    "option": "string"
}
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