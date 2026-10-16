# MassoSync 💆‍♀️📱

**MassoSync** é um aplicativo móvel voltado para terapeutas autônomos registrarem seus atendimentos. O aplicativo centraliza os dados dos clientes e os tratamentos realizados de forma rápida, funcionando totalmente **offline** durante os atendimentos.

Este projeto é desenvolvido com **Kotlin Multiplatform (KMP)** e **Compose Multiplatform**, compartilhando 100% da interface e da lógica de negócios entre as plataformas alvo.

## 🎯 Visão e MVP
O MVP (Produto Mínimo Viável) foca no essencial para o terapeuta no momento do atendimento:
* Cadastro simplificado de clientes.
* Registro de sessões com detalhamento da técnica (massagem relaxante, reflexologia, etc).
* Visualização do histórico de evolução.
* Funcionamento offline com banco de dados local.

Para mais detalhes sobre as justificativas e o escopo do projeto, consulte nossa [Proposta de Projeto](docs/proposta.md).

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Kotlin
* **Framework:** Compose Multiplatform (Interface compartilhada) e Kotlin Multiplatform (Lógica compartilhada)
* **Arquitetura:** MVVM com Navigation Compose e StateFlow
* **Injeção de Dependência:** Koin
* **Banco de Dados (Offline):** Room ou SQLDelight (a ser definido)
* **Backend (Futuro):** Supabase (PostgreSQL + Autenticação)
* **Alvos:** Android (Principal) e Desktop (Para desenvolvimento rápido)

## 🚀 Como Rodar o Projeto

### Android
Para rodar a versão Android, você pode abrir o projeto no **Android Studio**, selecionar o módulo `androidApp` na barra superior (Run Configurations) e clicar em *Play* usando um emulador (ex: API 34+).

Ou via linha de comando:
```bash
./gradlew :androidApp:assembleDebug
```

### Desktop (Windows/Mac/Linux)
A versão Desktop é ideal para testar a interface de forma instantânea sem precisar de emulador.
No Android Studio, você pode rodar acessando a aba Gradle > `desktopApp` > `Tasks` > `compose desktop` > `run`.

Ou via linha de comando:
```bash
./gradlew :desktopApp:run
```

---

## 📱 Sprint 1 — Interface, Navegação e Adaptatividade

Nesta sprint foram implementadas as telas do MVP, o fluxo de navegação, a adaptatividade para múltiplas larguras de tela e recursos de acessibilidade:

### 1. Telas do MVP
- **Listagem de Clientes (`ClientesScreen`):** exibe os clientes cadastrados com informações de contato, resumo do último atendimento e botão flutuante para novo cadastro.
- **Detalhes e Histórico (`DetalheClienteScreen`):** dados completos do cliente e histórico de sessões ordenado da mais recente para a mais antiga, além de botão para registrar atendimento.
- **Cadastro de Cliente (`CadastroClienteScreen`):** formulário com validação em tempo real (botão desabilitado enquanto nome ou telefone forem inválidos).
- **Registro de Sessão (`NovaSessaoScreen`):** formulário vinculado ao cliente com seletor acessível das técnicas do MVP (*Massagem Relaxante*, *Reflexologia Podal* e *Termoterapia*), data e observações clínicas.
- Tratamento de estados vazios em todas as telas com mensagens orientadoras e componentes com `@Preview`.

### 2. Navegação e Deep Link
- Implementada com **Navigation Compose** no `commonMain`, com rotas tipadas usando `@Serializable` (`Clientes`, `CadastroCliente`, `DetalheCliente`, `NovaSessao`).
- Suporte a **Deep Link** no esquema `massosync://cliente/{clienteId}`.
- Para testar via ADB em emulador ou dispositivo conectado:
```bash
adb shell am start -a android.intent.action.VIEW -d "massosync://cliente/1"
```

### 3. Tema e Adaptatividade
- Tema Material 3 com suporte a modo claro e escuro (`MassoSyncTheme`), aplicando estritamente os papéis de cores (`primary`, `surface`, `error`, etc.).
- Layout adaptativo baseado em `currentWindowAdaptiveInfo().windowSizeClass`:
  - **Largura Compacta (< 600 dp):** pilha de navegação tradicional (um destino por vez).
  - **Largura Média / Expandida (≥ 600 dp - Desktop / Tablet / Celular deitado):** layout **Master-Detail** com painel duplo (lista à esquerda e detalhes/atendimentos à direita).
- Prevenção de overflow com `safeDrawingPadding()` e áreas de rolagem (`LazyColumn`).

### 4. Acessibilidade
- Descrições de conteúdo (`contentDescription`) em botões de ação e ícones interativos.
- Marcação semântica de cabeçalhos (`heading()`).
- Alvos de toque garantidos com dimensão mínima de 48 × 48 dp.
- Seleção de técnicas com suporte a leitores de tela (`Modifier.selectable` e `Role.RadioButton`).

### 5. Vídeo de Demonstração (5 minutos)
- 🔗 **Link do vídeo:** *(Inserir aqui o link do YouTube/Google Drive gravado pela equipe)*

### 6. Registro de Uso de Inteligência Artificial
- Documentado em detalhes em [`docs/uso-de-ia.md`](docs/uso-de-ia.md).