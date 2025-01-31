<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
</head>

<body>
    <h1>Forissimo</h1>
    <p>Forissimo é uma aplicação desenvolvida para gerenciar tópicos, respostas e usuários, com autenticação segura e organização eficiente.</p>

<h2>Funcionalidades Principais</h2>
    <h3>Usuários</h3>
    <ul>
        <li>Cadastrar novos usuários com atribuição de perfis.</li>
        <li>Login com autenticação via token JWT.</li>
        <li>Atualizar informações de usuários.</li>
        <li>Listar todos os usuários ou um usuário específico.</li>
        <li>Desativar usuários, impedindo acesso ao sistema.</li>
    </ul>

<h3>Tópicos</h3>
    <ul>
        <li>Criar novos tópicos associados a cursos e autores.</li>
        <li>Listar tópicos com paginação e ordenação por data.</li>
        <li>Visualizar detalhes de um tópico específico.</li>
        <li>Editar informações de um tópico existente.</li>
        <li>Excluir tópicos e suas respostas associadas.</li>
        <li>Marcar uma resposta como solução para um tópico.</li>
    </ul>

<h3>Respostas</h3>
    <ul>
        <li>Criar respostas para tópicos existentes.</li>
        <li>Editar conteúdo de respostas.</li>
        <li>Excluir respostas específicas.</li>
        <li>Identificar qual resposta é a solução de um tópico.</li>
    </ul>

<h2>Futuras Melhorias</h2>
    <ul>
        <li>Estruturar melhor a aplicação utilizando o padrão de Services para separar responsabilidades.</li>
        <li>Adicionar o Spring Doc para gerar documentação automática da API.</li>
        <li>Implementar testes automatizados para garantir maior robustez.</li>
        <li>Otimizar consultas ao banco de dados para melhorar a performance.</li>
    </ul>

<h2>Configuração</h2>
    <ol>
        <li>Clone o repositório: <code>git clone &lt;URL-do-repositório&gt;</code></li>
        <li>Configure o banco de dados no arquivo <code>application.properties</code>.</li>
        <li>Execute o comando <code>mvn spring-boot:run</code> para iniciar a aplicação.</li>
    </ol>

</body>

</html>
