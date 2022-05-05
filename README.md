<p align="center"><a href="https://laravel.com" target="_blank"><img src="https://raw.githubusercontent.com/laravel/art/master/logo-lockup/5%20SVG/2%20CMYK/1%20Full%20Color/laravel-logolockup-cmyk-red.svg" width="400"></a></p>

<p align="center">
<a href="https://travis-ci.org/laravel/framework"><img src="https://travis-ci.org/laravel/framework.svg" alt="Build Status"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/dt/laravel/framework" alt="Total Downloads"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/v/laravel/framework" alt="Latest Stable Version"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/l/laravel/framework" alt="License"></a>
</p>

## Sistema Chamados IFPR

Aos que tem acesso ao Projeto, Bem Vindos! Fiz Esse Sistema com muito esforço e Dedicação!!!

Para instalar o Projeto é Muito Simples

- [Página de Download](https://getcomposer.org/download/).
- [Instalar Composer - Instalador GUI Windows](https://getcomposer.org/Composer-Setup.exe).
- [Instalar MySqlServer](https://dev.mysql.com/downloads/installer/).
- [Documentação Laravel 8](https://laravel.com/).

## Configuração

● Após Instalar programas necessários

- Na Pasta Raiz do Projeto

        composer install

Que carrega o processo de auto-load e as bibliotecas/dependencias do projeto.

● ATENÇÃO - Observa que no conteúdo dos arquivos terá um chamado ".env.example", crie uma cópia do mesmo e renomei-o como .env

● Ainda na Raiz do Projeto digite o comando:

        php artisan key:generate

Esse vai gerar uma chave para sua aplicação. Sem isso o Laravel não vai funcionar

● No arquivo .env configurar conforme seu Banco de Dados na sua máquina

            DB_CONNECTION=mysql
            DB_HOST=127.0.0.1 (endereço)
            DB_PORT=3306 (porta ultilizada)
            DB_DATABASE=banco (nome do Banco )
            DB_USERNAME=root (Usuário)
            DB_PASSWORD=    (senha)

● Depois de instalar as dependências basta rodar o comando para rodar as migrations

        php artisan migrate

● Com isso, já temos as dependências e a base de dados disponíveis então basta subir a aplicação:

        php artisan serve


