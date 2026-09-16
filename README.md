# TerapiasSync 💆‍♀️📱

**TerapiasSync** é um aplicativo móvel voltado para terapeutas autônomos registrarem seus atendimentos. O aplicativo centraliza os dados dos clientes e os tratamentos realizados de forma rápida, funcionando totalmente **offline** durante os atendimentos.

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
