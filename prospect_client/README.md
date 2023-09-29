# Challenge Bootcamp Cielo Dev - FullStack

Esse repositório hospeda o código `front-end` desafio para o Bootcamp Cielo Dev - FullStack.

## Notas

O projeto utilizou a versão `v18.12.1` do `node` em seu desenvolvimento e o `pnpm` como gerenciador de pacotes.

## Iniciando o projeto

Esses passos partem do princípio de que o usuario possua um gerenciador de pacotes do node, Ex: `npm`, `pnpm`.
No decorrer dos passos, estaremos utilizando o `pnpm`.

Primeiro é necessario estar instalando as dependências do projeto:

> Para instalar as dependências execute o comando: `pnpm install`

Após a instalação das dependências do projeto será necessario configurar as variáveis de ambiente do projecto.

> Após a instalação das dependências do projeto, copie o arquivo `sample.env` para `.env` no mesmo diretorio
> Agora dentro do arquivo `.env` terá a seguinte variável: 

```env
VITE_API_URL=http://localhost:8080
```
> Caso o projeto da api esteja rodando utilizando outro endereço basta subistituir o valor.

Configurações inicias já feitas, basta agora executar o comando: `pnpm run dev` para iniciar o projeto, que ficará disponível no endereço `http://localhost:5173/`
