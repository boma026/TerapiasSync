# Registro de Uso de Inteligência Artificial — Sprint 1

**Projeto:** MassoSync  
**Disciplina:** DIM0524 — Desenvolvimento de Sistemas para Dispositivos Móveis  
**Equipe:** boma026  

---

## 1. Ferramentas Utilizadas
- **Antigravity CLI (Google DeepMind / Gemini)**: Utilizado como assistente de desenvolvimento em modo pair programming para aceleração de escrita de código, configuração de build e boas práticas da plataforma.

---

## 2. Escopo de Uso na Sprint 1

Na Sprint 1, o foco esteve na interface, navegação, adaptatividade e acessibilidade do MVP:

1. **Arquitetura de Telas e Componentes Reutilizáveis:**
   - Apoio na estruturação dos modelos de dados (`Cliente`, `Sessao`, `Tecnica`) com serialização Kotlin.
   - Criação de componentes próprios reutilizáveis (`ClienteCard`, `SessaoCard`, `TecnicaSelector`, `MassoTopBar`, `EmptyStateView`).
   - Implementação das telas do MVP com estado elevado (`ClientesScreen`, `DetalheClienteScreen`, `CadastroClienteScreen`, `NovaSessaoScreen`).
   - Inclusão de `@Preview` com dados simulados e cobertura de casos vazios (lista vazia e histórico sem atendimentos).

2. **Navegação com Rotas Tipadas e Deep Links:**
   - Configuração do **Navigation Compose** no `commonMain` com destinos tipados `@Serializable`.
   - Isolamento do `NavController` no `NavHost`, fornecendo callbacks para as telas.
   - Configuração de Deep Link tipado (`massosync://cliente/{clienteId}`) com suporte no manifesto Android (`AndroidManifest.xml`).

3. **Tema, Responsividade e Adaptatividade:**
   - Definição do tema Material 3 claro e escuro (`MassoSyncTheme`), utilizando estritamente os papéis de cores (`primary`, `surface`, `onSurface`, etc.).
   - Suporte adaptativo com base na largura da janela (`currentWindowAdaptiveInfo().windowSizeClass`):
     - Largura Compacta (< 600 dp): navegação em pilha padrão.
     - Largura Média/Expandida (≥ 600 dp): layout Master-Detail (dois painéis: lista de clientes à esquerda e detalhes/atendimentos à direita).
   - Prevenção de overflow com rolagem e `safeDrawingPadding()`.

4. **Acessibilidade:**
   - Adição de `contentDescription` em todos os elementos interativos sem rótulo visível e `contentDescription = null` em elementos decorativos.
   - Marcação de cabeçalhos semânticos com `semantics { heading() }`.
   - Garantia de alvos de toque mínimos de 48 × 48 dp.
   - Uso de `Modifier.selectable` com `Role.RadioButton` no seletor de técnicas.

5. **Ajustes de Infraestrutura de CI e Linters:**
   - Configuração dos filtros de exclusão do `ktlint` e `.editorconfig` para ignorar código gerado pelo gerador de recursos do Compose Multiplatform, mantendo o pipeline local e o CI totalmente verdes.

---

## 3. Validação e Controle Humano
- Todas as sugestões e códigos gerados foram inspecionados quanto à compatibilidade de dependências com Kotlin Multiplatform 2.x e Compose Multiplatform.
- O código foi validado com as tarefas do Gradle:
  - `./gradlew ktlintCheck detekt` (aprovado sem violações)
  - `./gradlew :androidApp:assembleDebug` (APK gerado com sucesso)
