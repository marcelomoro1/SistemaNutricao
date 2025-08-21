## Sistema de Fichas Técnicas de Nutrição

Aplicação web para criação, edição e visualização de fichas técnicas nutricionais de receitas. Voltada para nutricionistas, cozinhas profissionais e estabelecimentos que precisam padronizar preparações, calcular per capita, custos e perfil nutricional por porção.

### Objetivo
Centralizar o cadastro de receitas com seus ingredientes, modo de preparo, equipamentos e cálculos (custo total, custo per capita, FCC e perfil nutricional), oferecendo interface web simples e API REST.

---

## Funcionalidades
- **Autenticação e autorização**: login por formulário, controle de acesso por perfis `ADMIN`, `NUTRICIONISTA`, `USER`.
- **Dashboard autenticado**: acesso às ações principais após login.
- **Gerenciar receitas (fichas técnicas)**:
  - Criar, editar e visualizar fichas técnicas.
  - Informar ingredientes (com autocomplete), medidas, PB/PL, custo e FCC.
  - Calcular custo total, custo per capita e FCC geral com água opcional no preparo.
  - Calcular perfil nutricional por porção (PTN/CHO/LIP, sódio e gordura saturada) e VCT.
- **Banco de ingredientes**:
  - Base de ingredientes do sistema (pré-carregável por SQL).
  - Ingredientes próprios do nutricionista (escopo de usuário).
- **Listagem e detalhes**:
  - Tabela de receitas cadastradas.
  - Página de detalhes de cada receita em modo somente leitura.
- **API REST** para integração com frontend e clientes externos.

---

## Tecnologias
- Java 17, Maven
- Spring Boot 3.4.5 (Web, Validation)
- Spring Security 6 (form login, roles)
- Spring Data JPA (Hibernate)
- Thymeleaf + thymeleaf-extras-springsecurity6
- Lombok
- MySQL 8+

---

## Arquitetura e organização
- `configuration/`: segurança (`SecurityConfig`), autenticação customizada (`CustomUserDetailsService`) e carga inicial de dados (`DataInitializer`).
- `controller/`: controladores de páginas (Thymeleaf) e REST (`/api`).
- `service/`: regras de negócio de ingredientes, receitas e usuários.
- `repository/`: repositórios JPA.
- `model/`: entidades JPA (Receita, Ingrediente, PerfilNutricional, etc.).
- `dto/`: objetos de transferência para requisições e respostas da API.
- `resources/templates`: páginas Thymeleaf (login, dashboard, criar/visualizar ficha, detalhes).
- `resources/static`: CSS e imagens.

Fluxo principal: UI → Controllers (views) → Services → Repositories → BD. A UI usa também endpoints REST para salvar/consultar dados.

---

## Requisitos
- JDK 17
- Maven 3.9+
- MySQL 8+

---

## Configuração
Arquivo `src/main/resources/application.properties` (ajuste conforme seu ambiente):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistemanutricao?createDatabaseIfNotExist=true
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```


## Banco de dados e carga de ingredientes
O schema é criado automaticamente pelo Hibernate. Para popular a base de ingredientes do sistema, importe o arquivo `insert_ingredientes_final.sql` após o schema existir.

Exemplo via MySQL CLI:
```bash
mysql -u root -p nutricao < insert_ingredientes_final.sql
```

Ou via MySQL Workbench (File → Run SQL Script).

---

## Como executar
Clonar e subir a aplicação:

```bash
# Windows PowerShell
./mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

Acesse: `http://localhost:8080`

### Usuários iniciais (criados ao iniciar)
- `admin` / `123456` → ROLE `ADMIN`
- `nutricionista` / `nutricionista` → ROLE `NUTRICIONISTA`

Você também pode se registrar em `/registro` (usuários via registro recebem ROLE `USER`).

---

## Rotas e páginas (UI)
- `GET /` e `GET /login`: página de login.
- `GET /registro`: formulário de cadastro; `POST /registro` efetiva cadastro.
- `GET /dashboard`: dashboard (apenas autenticado).
- `GET /visualizar`: lista todas as receitas.
- `GET /criarFichaTecnica`: formulário para criar/editar ficha técnica.
- `GET /receita/detalhes/{id}`: detalhes da receita em modo leitura.
- `GET /receita/editar/{id}`: abre o formulário preenchido para edição.

Regras de acesso (resumo de `SecurityConfig`):
- Público: `/`, `/home`, `/login`, `/registro`, `/css/**`, `/js/**`, `/images/**`.
- Autenticado: `/dashboard` e demais, exceto as públicas.
- Com role: `/admin/**` (ADMIN) e `/fichatecnica/**` (qualquer role autenticada: `NUTRICIONISTA`, `ADMIN`, `USER`).
- Logout: `POST /logout` (redireciona para `/login?logout`).

---

## API REST
Autenticação baseada em sessão (form login). Para consumir a API fora da UI, autentique-se primeiro para obter o cookie de sessão.

Base path principal: não há prefixo global; recursos estão sob `/api/...`.

### Receitas
- **POST `/api/receitas`** Criar receita (precisa estar autenticado)
```json
{
  "nome": "Arroz integral",
  "categoria": "GUARNICAO",
  "equipamentos": "Panela, colher",
  "modoPreparo": "Refogar e cozinhar...",
  "tempoPreparo": 30,
  "pesoPorcao": 120.0,
  "rendimento": 1200.0,
  "numeroPorcoes": 10,
  "fcc": 1.05,
  "medidaCaseira": "1 concha",
  "perfilNutricional": {
    "perCapita": 120.0,
    "totalGramas": 0.0,
    "totalKcal": 0.0,
    "totalPorcentagem": 0.0,
    "vct": 0.0
  },
  "ingredientes": [
    {
      "ingredienteId": 1,
      "ingredienteNome": "Arroz, integral, cozido",
      "medidaCaseira": "1 xícara",
      "pesoBruto": 120.0,
      "pesoLiquido": 120.0,
      "fatorCorrecao": 1.0,
      "custoCompra": 10.0,
      "pesoCompra": 1000.0,
      "custoUtilizado": 1.2
    }
  ]
}
```

- **PUT `/api/receitas/{id}`** Editar receita (dono ou ADMIN)
  - Body com a mesma estrutura do POST. Ingredientes existentes são substituídos pelos enviados.

- **GET `/api/receitas`** Listar receitas (resumo)
```json
[
  {
    "id": 1,
    "nome": "Arroz integral",
    "categoria": "GUARNICAO",
    "tempoPreparo": 30,
    "numeroPorcoes": 10,
    "totalKcal": 2300.0,
    "nutricionistaUsername": "nutricionista"
  }
]
```

- **GET `/api/receitas/{id}`** Detalhes completos de uma receita
```json
{
  "receita": { "id": 1, "nome": "Arroz integral", "categoria": "GUARNICAO", "equipamentos": "...", "modoPreparo": "...", "tempoPreparo": 30, "pesoPorcao": 120.0, "rendimento": 1200.0, "numeroPorcoes": 10, "fcc": 1.05, "medidaCaseira": "1 concha", "nutricionistaUsername": "nutricionista", "ingredientes": [ { "ingredienteId": 1, "ingredienteNome": "Arroz, integral, cozido", "medidaCaseira": "1 xícara", "pesoBruto": 120.0, "pesoLiquido": 120.0, "fatorCorrecao": 1.0, "custoCompra": 10.0, "pesoCompra": 1000.0, "custoUtilizado": 1.2, "proteina": 2.588, "carboidrato": 25.81, "lipidio": 1.0, "sodio": 1.245, "gorduraSaturada": 0.3 } ] },
  "ingredientes": [ { "id": 1, "nome": "Arroz, integral, cozido", "tipo": "ORIGINAL", "quantidade": 120.0, "medida": "g", "custoPorcao": 0.12, "ingredienteSistema": true, "nutricionistaId": 0 } ],
  "perfilNutricional": { "id": 1, "perCapita": 120.0, "totalGramas": 0.0, "totalKcal": 0.0, "totalPorcentagem": 0.0, "vct": 0.0, "receitaId": 1 }
}
```

### Ingredientes
- **GET `/api/ingredientes`** Listar todos os ingredientes (sistema + de nutricionistas)
- **GET `/api/ingredientes/buscar?nome=arroz`** Busca por nome (case-insensitive)
- **GET `/api/ingredientes/nutricionista/{username}`** Ingredientes próprios de um nutricionista
- **POST `/api/ingredientes/nutricionista/{username}`** Criar ingrediente do nutricionista
```json
{
  "nome": "Arroz parboilizado",
  "carboidrato": 77.0,
  "proteina": 7.0,
  "lipidio": 1.5,
  "sodio": 2.0,
  "gorduraSaturada": 0.3
}
```

### Usuários (Nutricionistas)
- **GET `/api/nutricionistas`** Listar
- **GET `/api/nutricionistas/{id}`** Buscar por id
- **GET `/api/nutricionistas/username/{username}`** Buscar por username
- **POST `/api/nutricionistas`** Criar usuário

---

## Segurança
- Login por formulário em `/login` (mecanismo padrão do Spring Security).
- Sessão HTTP (JSESSIONID). Logout via `POST /logout`.
- Regras por role conforme seção de rotas.
- CSRF desabilitado para desenvolvimento.

---

## Como usar (fluxo sugerido)
1. Inicie a aplicação e faça login como `admin` ou `nutricionista`.
2. (Opcional) Importe `insert_ingredientes_final.sql` para popular a base de ingredientes.
3. Vá ao `Dashboard` e escolha:
   - "Criar Ficha Técnica" para cadastrar uma receita (use o autocomplete de ingredientes).
   - "Visualizar Fichas Técnicas" para listar/abrir detalhes/editar.

---

## Desenvolvimento
- Use `spring-boot-devtools` para hot reload.

---

