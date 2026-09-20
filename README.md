# lovechat

> A Jetpack Compose chat UI for Android — * love means "ai" 爱 in Chinese.*

A streaming-style AI-chat interface built with Jetpack Compose and Material 3, packaged as a small reusable library (`:chatui`) plus two demo apps that show how the same library can power very different products.

The chat experience is intentionally locally driven — there's no real backend — so the focus is on the interaction itself: how the streaming text feels, how a sent message pins to the top while the keyboard slides away, how messages scroll, how a top bar and composer come together at the screen edges.

## Demos

**`:demo-myai`** — sunset palette, serif typography, custom hamburger top bar and pill composer:

https://github.com/user-attachments/assets/8ce44009-717f-47ce-831c-851876271260

**`:app`** — clean light theme, default top bar and composer:

https://github.com/user-attachments/assets/2d2718a7-dcf6-442c-bb7a-3f556dad7fc9

## Modules

```
lovechat/
├── chatui/       ← reusable chat UI library, no backend / no LLM dependency
├── app/          ← first demo — clean light theme, default top bar / composer
└── demo-myai/    ← second demo — sunset palette, serif typography,
                    custom top bar (hamburger + “MyAI”) and pill composer
```

Both demo apps install side-by-side (different `applicationId`s) and consume the same `:chatui` library.

## What the library does

`:chatui` exposes a small set of types and one entry composable:

| Type | Purpose |
|---|---|
| `ChatScreen(...)` | Public entry composable. Drives the whole chat surface. |
| `ChatService` (fun interface) + `ChatChunk` | LLM-agnostic streaming contract — `streamReply(history): Flow<ChatChunk>`. Drop in any backend (mock / OpenAI / Anthropic / Gemini) by implementing this. |
| `DefaultChatViewModel(service)` | Drop-in `ViewModel` that owns `messages`, `draft`, `isStreaming` and runs the streaming coroutine. Apps either extend it or roll their own. |
| `ChatColors` + `ChatDefaults.colors()` | Themable color slots, Material-derived defaults. |
| `ChatConfig` | Tuning knobs: typewriter speed, fade-wave length, paragraph spacing, FAB visibility threshold, etc. |
| `ChatTopBarSlot`, `ChatComposerSlot` | Slot signatures so hosts can fully replace the top bar and composer (positioning, backdrop, chrome) while the library still tracks their measured heights. |
| `DefaultChatTopBar`, `DefaultComposerBar` | Drop-in stock implementations of the slots. |

## Key UX details

- **SSE-style streaming.** Replies arrive as variable-sized chunks at randomized 15–60ms intervals. Splits ignore word/markdown boundaries — same shape as a real upstream byte stream — and the renderer handles partial-token text gracefully.
- **Per-paragraph typewriter.** Each paragraph reveals character-by-character on its own animation, with a soft fading-wave at the leading edge that hides any lag between the network rate and the visible reveal rate.
- **Markdown without regex.** A hand-rolled character-scanning parser drives the renderer; the supported syntax covers what an LLM reply typically emits:

  | Syntax | Renders as |
  |---|---|
  | `**bold**`, `*italic*` | bold / italic inline spans |
  | `` `inline code` `` | monospace inline span with code-tint background |
  | `# H1` &nbsp;·&nbsp; `## H2` &nbsp;·&nbsp; `### H3` | three heading levels |
  | `- item` | bullet list (one level) |
  | `> quote` | left-rule blockquote |
  | ` ```lang ` … ` ``` ` | fenced code block, language label, light Kotlin keyword highlighting |
  | `[text](https://…)` | themed clickable link |
  | `\| col \| col \|` + `\| :--- \| ---: \|` | real grid table — per-column alignment, content-sized columns, horizontal scroll for wide tables, inline markdown inside cells |

  Unclosed fences and partial table rows mid-stream render gracefully — code reads as plain monospace until the closing fence arrives, and a half-built table just shows the rows it has so far.
- **Pinned user message.** When you hit send, the user message is hard-cut to the top of the viewport on the very first frame it exists — no scroll animation, no intermediate positions — and stays there while the assistant's reply unfolds below, so the conversation reads as turns, not as an infinite scroll. The previous turn is simply above the fold; the only motion on send is the keyboard and composer sliding away. A trailing spacer, sized from the same-frame viewport height, holds the pin steady while the keyboard closes and persists after the reply completes, so a short answer never slides the conversation down.
- **Follow mode (opt-in auto-scroll).** Off by default; activates when the user scrolls to the bottom mid-stream or taps the jump-to-bottom FAB. Any user scroll-up turns it back off. Pointer-gated so programmatic scrolls can't accidentally toggle it.
- **Edge-to-edge keyboard handling.** Composer rides the keyboard up and down via a critically-damped Compose spring driven by `WindowInsets.imeAnimationTarget` (decoupled from the system's IME curve to avoid overshoot on certain devices), with `windowSoftInputMode="adjustNothing"` so Compose is the single source of truth for inset handling. The message list is top-anchored and only its bottom edge moves with the inset, which is why the pinned message never shifts while the keyboard animates.
- **Glassy bars.** Top bar and composer wrappers paint translucent gradient backdrops that fade into the chat color, so scrolling messages dissolve behind the bars instead of bleeding into the status bar / nav bar areas.

## Build & run

```bash
./gradlew :app:assembleDebug              # First demo (StreamChat)
./gradlew :app:installDebug               # Install on device/emulator
./gradlew :demo-myai:installDebug         # Second demo (MyAI / sunset)

./gradlew :chatui:assembleDebug           # Build the library
./gradlew :app:testDebugUnitTest          # Unit tests
```

## Tech

- **Kotlin** 2.0.21
- **Jetpack Compose** (BOM 2024.09.00) + **Material 3** 1.3
- **AGP** 8.12.1, **Java** 11
- **Min SDK** 24, **Target / Compile SDK** 36

## License

See [`LICENSE`](LICENSE).
