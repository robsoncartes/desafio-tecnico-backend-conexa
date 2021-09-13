# Desafio Técnico backend Conexa

Veja [project-description](https://gitlab.com/robsonsousa/desafio-tecnico-backend-conexa/project-description/README.md) para obter as descrições de como executar o projeto.

Construir uma API REST para que nossos médicos de plantão consigam se logar na plataforma, consigam manter pacientes e agendar atendimentos com os pacientes cadastrados.

## Autenticação

### Signup
Precisamos de uma rota para que os médicos consigam realizar cadastro na plataforma:
```
POST /api/v1/signup
{
  "email": "medico@email.com",
  "senha": "",
  "confirmacaoSenha": "",
  "specialidade": "Cardiologista",
  "cpf": "101.202.303-11",
  "idade": "33",
  "telefone": "(21) 3232-6565"
}
```

### Login
Precisamos de uma rota para que médicos da clínica Conexa Saúde consigam realizar login na aplicação:
```
POST /api/v1/login
{
  "email": "medico@email.com",
  "senha": "senhamedico"
}
```

### Logoff
Também precisamos de uma rota para o médico conseguir realizar logoff:
```
POST /api/v1/logoff
Authentication: token_jwt
```

### Requisitos para autenticação:
- Utilização de JWT;
- Após logoff, o token precisa ser invalidado, não podendo mais ser permitida a sua utilização;
- Validações de tipos de campo como E-mail, CPF válido, idade apenas números, telefone válido, etc;
- As senhas precisam ser armazenadas de forma criptografada;


## Pacientes

Na API também precisamos de rotas para que o médico logado consiga manter pacientes:
### Criação:
```
POST /api/v1/patients
Authentication: token_jwt
{
  "nome": "Rafael Braga",
  "cpf": "101.202.303-11",
  "idade": "33",
  "email": "rafael.braga@gmail.com",
  "telefone": "(21) 3232-6565"
}
```

### Alteração:
```
PUT /api/v1/patients/{id}
Authentication: token_jwt
{
  "nome": "Rafael Braga",
  "cpf": "101.202.303-11",
  "idade": "33",
  "email": "rafael.braga@gmail.com",
  "telefone": "(21) 3232-6565"
}
```

### Deleção:
```
DELETE /api/v1/patients/{id}
Authentication: token_jwt
```

### Busca por ID do paciente:
```
GET /api/v1/patients/{id}
Authentication: token_jwt
```

### Listagem de todos os pacientes:
```
GET /api/v1/patients/
Authentication: token_jwt
```

### Requisitos para pacientes:
- Todas as rotas de paciente precisam passar pela autenticação;
- Validações de tipos de campo como E-mail, CPF válido, idade apenas números, telefone válido, etc;

## Agendamento

### Criação de atendimento
Precisamos de uma rota onde o médico logado realiza agendamento de consulta para um paciente:
```
POST /api/v1/attendance
Authentication: token_jwt
{
  "dataHora": "2020-08-03 09:00:00",
  "idPaciente": "3u84904",
  "sintomas": [
    {
      "descricao": "Dor de cabeça",
      "detalhes": "Dor na parte de trás da cabeça"
    },
    {
      "descricao": "Febre"
      "detalhes": "40 graus de febre"
    }
  ]
}
```

### Consulta de atendimentos agendados
Precisamos de uma rota onde o médico logado consulte os atendimentos agendados que ainda não aconteceram
```
GET /api/v1/attendance
Authentication: token_jwt
```

### Cancelamento de atendimentos agendados
Precisamos de uma rota onde o médico logado cancele o atendimentos agendado
```
DELETE /api/v1/attendance/{id}
Authentication: token_jwt
```

### Requisitos para atendimentos:
- Todas as rotas de atendimento precisam passar pela autenticação;
- Só pode ser possível criar agendamentos no futuro;
- Só pode ser possível criar agendamentos para pacientes existentes;
- Só pode ser possível cancelar atendimentos criados pelo própio médico;
- Somente o médico que criou o atendimento pode visualizá-lo;


# Requisitos Obrigatórios:
- Utilização de Java com Spring;
- Banco de dados MySQL;
- Realizar fork deste repositório, e após o desenvolvimento, abrir um merge request apontando para repositório original;


# Serão avaliados os seguintes itens:
* Se todos os requisitos descritos acima foram atendidos;
* Clareza do código;
* Estrutura de código organizada;
* Se utilizou corretamente os conceitos do REST;
* Se utilizou o Spring de forma correta;
* Se possui tratamento de erros;
* Se os testes rodam;

**Obs:**
Necessário descrever, ou prover, todas as dependências e ações necessárias (banco, tabelas, versões, etc) para que o avaliador consiga rodar a aplicação na sua máquina local;

**Lembre-se que uma aplicação boa é uma aplicação bem testada;**

Em caso de dúvidas sobre o desafio, entre em contato.
