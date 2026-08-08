# 📋 ClienteApp — Sistema de Cadastro de Clientes

> Sistema CRUD completo em **Java puro** (sem frameworks) para cadastro e gerenciamento de clientes Pessoa Física e Pessoa Jurídica, com persistência em arquivo e interface via terminal.

---

## 📖 Sobre o Projeto

O **ClienteApp** é um sistema de cadastro de clientes desenvolvido inteiramente em Java 17, sem uso de frameworks externos ou dependências de terceiros. O objetivo é demonstrar domínio de fundamentos da linguagem em um projeto com estrutura profissional — separação em camadas, orientação a objetos completa, validações, tratamento de exceções e persistência de dados.

O sistema permite cadastrar dois tipos de clientes — **Pessoa Física** (CPF) e **Pessoa Jurídica** (CNPJ) — com operações completas de criação, consulta, atualização e desativação, além de relatórios e estatísticas gerados com a API de Streams do Java.

A estrutura de pacotes replica intencionalmente a arquitetura de um projeto Spring Boot (`model`, `repository`, `service`).


---

## 🎯 Objetivo de Desenvolvimento

Este projeto foi construído como **marco de aprendizado** de um plano de estudos focado em Java Backend. Foi o primeiro passo, focado em uso das estruturas e pilares básicos do java, sem frameworks de desenvolvimento.

| Pilar | O que este projeto treina |
|---|---|
| **OOP** | Herança, polimorfismo, encapsulamento e abstração |
| **Collections** | `ArrayList`, `Optional`, `Map`, iteração com Streams e Lambda |
| **Exceções** | Hierarquia customizada de exceções, `try/catch`, validações |
| **Java I/O** | Leitura de input com `Scanner`, serialização binária com `ObjectOutputStream` |
| **Organização** | Separação de responsabilidades em camadas (`model`, `repository`, `service`, `ui`) |
| **Streams & Lambda** | Filtros, agrupamentos, ordenação e mapeamentos com a Stream API |

> **Por que Java puro?** Frameworks resolvem problemas que você precisa entender antes de delegar. Resolver na linguagem pura é essencial para entender os fundamentos que compõem um framework de desenvolvimento. Sem atalhos antes de conhecer o caminho!

---

## ✅ Funcionalidades

- Cadastro de Pessoa Física (CPF) e Pessoa Jurídica (CNPJ)
- Validação de campos e unicidade de documentos e e-mail
- Listagem, pesquisa por nome e busca por ID
- Atualização de dados de contato
- Desativação lógica de clientes (soft delete)
- Estatísticas do sistema via Streams
- Persistência entre execuções (arquivo `.dat`)

---

## 🏗️ Arquitetura e Estrutura de Pastas

O projeto segue uma **arquitetura em camadas** que separa claramente as responsabilidades de cada parte do sistema:

```
clienteapp/
│
├── src/
│   └── br/
│       └── com/
│           └── clienteapp/
│               │
│               ├── Main.java                         ← Ponto de entrada da aplicação
│               │
│               ├── model/                            ← CAMADA DE DOMÍNIO
│               │   ├── Cliente.java                  ← Classe abstrata base
│               │   ├── ClientePF.java                ← Pessoa Física (estende Cliente)
│               │   ├── ClientePJ.java                ← Pessoa Jurídica (estende Cliente)
│               │   └── TipoCliente.java              ← Enum com os tipos possíveis (PF e PJ)
│               │
│               ├── repository/                       ← CAMADA DE ACESSO A DADOS
│               │   └── ClienteRepository.java        ← CRUD em memória + persistência em arquivo
│               │
│               ├── service/                          ← CAMADA DE REGRAS DE NEGÓCIO
│               │   └── ClienteService.java           ← Validações, unicidade, relatórios
│               │
│               ├── exception/                        ← EXCEÇÕES CUSTOMIZADAS
│               │   ├── ClienteNaoEncontradoException.java
│               │   ├── CpfCnpjJaCadastradoException.java
│               │   └── ValidacaoException.java
│               │
│               ├── util/                             ← UTILITÁRIOS
│               │   └── Validador.java                ← Métodos estáticos de validação
│               │
│               └── ui/                               ← CAMADA DE INTERFACE
│                   └── Menu.java                     ← Interação com o usuário via terminal
│
├── clientes.dat                                      ← Arquivo de persistência (gerado em runtime)
└── README.md
```

### Fluxo entre camadas

```
Usuário
  │
  ▼
Menu (ui)          ← captura input, exibe output
  │
  ▼
ClienteService (service)   ← valida regras de negócio, usa Validador
  │
  ▼
ClienteRepository (repository)  ← lê e escreve na coleção em memória / arquivo
  │
  ▼
Cliente / ClientePF / ClientePJ (model)  ← objetos de domínio
```

> Cada camada conhece apenas a camada imediatamente abaixo dela. O `Menu` não sabe que os dados ficam num `ArrayList`. O `Repository` não sabe que existe uma regra de unicidade de CPF. Essa separação é o que torna o código manutenível e testável.


---

## 📚 Conceitos Aplicados

`Abstract class` · `Herança` · `Polimorfismo` · `Encapsulamento` · `Enum` · `UUID` · `Optional<T>` · `ArrayList` · `Streams & Lambda` · `Collectors.groupingBy` · `Exceções customizadas` · `Serialização` · `LocalDate/LocalDateTime` · `Scanner`

---

## ▶️ Como Executar

**Pré-requisito:** JDK 17+

```bash
# Clone
git clone https://github.com/seu-usuario/clienteapp.git
cd clienteapp

# Compile
find src -name "*.java" | xargs javac -d out

# Execute
java -cp out br.com.clienteapp.Main
```

Ou abra no **IntelliJ IDEA** e rode o `Main.java` diretamente.

---

## 💻 Demonstração

```
════════════════════════════════════════
     SISTEMA DE CADASTRO DE CLIENTES
════════════════════════════════════════

✅ 3 cliente(s) carregado(s).

┌─────────────────────────────────┐
│         MENU PRINCIPAL          │
├─────────────────────────────────┤
│  1. Cadastrar Cliente PF        │
│  2. Cadastrar Cliente PJ        │
│  3. Listar todos os clientes    │
│  4. Pesquisar por nome          │
│  5. Buscar por ID               │
│  6. Atualizar contato           │
│  7. Desativar cliente           │
│  8. Estatísticas                │
│  0. Sair e salvar               │
└─────────────────────────────────┘
Escolha uma opção: 1

── CADASTRAR PESSOA FÍSICA ──
Nome completo: João Silva
Email: joao@email.com
Telefone (ex: (11) 99999-0000): (11) 98888-0001
CPF (ex: 123.456.789-00): 123.456.789-00
Data de nascimento (dd/MM/yyyy): 15/05/1990

✅ Cliente cadastrado com sucesso!
   ID: f3a2c1d0-8e4b-4f7a-b2c1-9d3e8f6a0b5c
   João Silva                     | CPF: 123.456.789-00  | Idade: 34 anos | Email: joao@email.com
```

```
── ESTATÍSTICAS DO SISTEMA ──
────────────────────────────────────────
  Total de clientes:         4
  Clientes ativos:           3
  Pessoas Físicas:           2
  Pessoas Jurídicas:         2
  Clientes inativos:         1
────────────────────────────────────────
```

---

## 👨‍💻 Autor

**Eduardo Ulisses Pereira da Silva**

Estudante de Engenharia de Software — FIAP

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Eduardo_Ulisses-0A66C2?style=flat&logo=linkedin)](https://linkedin.com/in/eduardo-ulisses)
[![GitHub](https://img.shields.io/badge/GitHub-@UlissesE-181717?style=flat&logo=github)](https://github.com/UlissesE)
[![Email](https://img.shields.io/badge/Email-contato.eduardoulisses@gmail.com-D14836?style=flat&logo=gmail)](mailto:contato.eduardoulisses@gmail.com)

<br>

---

<p align="center">
  Desenvolvido como projeto de aprendizado de Java Core · 2026
</p>