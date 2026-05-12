package com.pact.demomyai.data

/**
 * Canned markdown replies for the MyAI demo. Distinct from the :app
 * MockChatData so the two demos read as different products even though
 * they share the underlying chat UI library.
 */
object MockChatData {

    fun replyTextFor(prompt: String): String {
        val normalized = prompt.lowercase()
        return when {
            "weather" in normalized || "forecast" in normalized -> """
The afternoon will warm into the **mid-70s**, with a soft southerly breeze and *high cirrus* drifting in from the coast. The light is going to be that particular **late-spring gold** that flattens shadows and makes everything look like a dressed-up postcard.

### Through the rest of today

- **Late morning**: a few wispy clouds, but nothing committed; expect 68°F by 11
- **Early afternoon**: peak warmth, a touch over 75°F, with the breeze keeping it from feeling heavy
- **Golden hour**: roughly 6:30 to 7:15 pm — *long, slanting light* through the buildings
- **Evening**: cools quickly to the low 60s; jacket weather by 9

### Tonight and into tomorrow

The cloud cover should clear out entirely by midnight, leaving a **good viewing window** for the western horizon if you're following the planets. Tomorrow morning starts cooler — *low 50s at sunrise* — but climbs back into the 70s by lunch.

> Pollen levels remain *moderate*. Tree pollen has tapered off but **grass is climbing**; bring an antihistamine if you're sensitive, especially for any extended time on lawns or trail edges.

A few small things worth knowing:

- **UV index** peaks at *7* between 12 and 2; reapply if you're outside through that window
- **Air quality** sits at *good* (AQI 38) — no concerns for sensitive groups
- **Humidity** stays in the comfortable *45–55%* band all afternoon

If you're trying to choose between an outdoor activity now or later — the **3 to 5 pm** window is going to be the most forgiving: warm but not glaring, with the breeze still active.
            """.trimIndent()

            "recipe" in normalized || "cook" in normalized || "dinner" in normalized -> """
## Roasted Tomato & Burrata Pasta

A **summer-leaning** pasta that comes together in one sheet pan plus a pot of water. The whole point is that the *roasted tomato liquid* — sweet, slightly caramelized, savory from the garlic — becomes your sauce, with no need to reach for cream or stock.

### Ingredients

- 1 lb **cherry tomatoes** (a mix of red and yellow if you can find them)
- 4 cloves **garlic**, smashed but not minced — you want them to *infuse* the oil, not disappear
- 3 tbsp **good olive oil** (this is one of those dishes where the oil quality shows)
- *Flaky salt*, freshly cracked **black pepper**, and a pinch of *red chili flakes*
- 12 oz **pasta** — rigatoni or orecchiette work best because the rough surfaces grab the sauce
- 1 ball **burrata**, room temperature (this matters — cold burrata seizes up against hot pasta)
- A generous handful of **fresh basil**, torn at the last moment
- *Optional but excellent*: a few anchovies stirred into the oil before roasting (Rao's quality is worth the upgrade — see [their site](https://www.raos.com))

### Method

```text
1. Heat the oven to 425°F (220°C). Toss tomatoes, smashed garlic,
   and oil on a sheet pan. Salt them well.

2. Roast for 20–25 minutes — you want the tomatoes to BURST and
   start to caramelize at the edges of the pan, not just soften.

3. Meanwhile, bring a large pot of water to a hard boil. Salt it
   so it tastes like the sea, then add the pasta and cook 1 minute
   short of the package's al dente time.

4. Reserve a cup of pasta water before draining.

5. Tip the pasta directly into the sheet pan with the tomatoes.
   Add a splash of the pasta water and toss vigorously — the
   starch will pull everything into a glossy emulsion.

6. Tear the burrata over the top in irregular chunks. Don't slice
   it. The cream inside is part of the sauce.

7. Finish with the basil and an extra grind of pepper.
```

### Notes & variations

- The **bursting** in step 2 is the most important visual cue. If the tomatoes still look like firm spheres, give them another 5 minutes. You want some collapse.
- For a **richer** version, add a strip of *guanciale* or pancetta to the sheet pan in the last 10 minutes of roasting — it'll render and salt the oil.
- For a **brighter** version, finish with a tablespoon of *capers* and the zest of half a lemon.
- If you can't find burrata, **fresh mozzarella torn into chunks** works; you'll lose the cream but keep the soft texture.

> The trick is letting the *roasted tomato liquid* be your sauce — don't reach for cream, don't reach for stock. The pasta water and the rendered tomato juice are doing all the work, and a heavy hand will mute the flavor of the tomatoes themselves.

Serve in *shallow bowls* with the burrata torn fresh on top of each portion, and a glass of something cold and acidic — a **Vermentino** or a chilled **Beaujolais** are both ideal.
            """.trimIndent()

            "travel" in normalized || "trip" in normalized || "vacation" in normalized -> """
A **slow three days in Lisbon** — built around walking, eating, and resisting the urge to over-plan. The city rewards loitering more than checklists.

### Day 1 — Alfama wandering

Start late, around 10am, with a *galão* and a **pastel de nata** at any unassuming café. The good ones are not the famous ones; the famous ones have lines that aren't worth your morning.

Then climb. Alfama is a **medieval district** that survived the 1755 earthquake mostly intact, which means streets that don't make sense, stairs that aren't on any map, and a constant rearrangement of perspective as you turn corners.

- *Mirador de Santa Luzia* — the postcard view, but go at golden hour
- *Mirador de Portas do Sol* — fewer tourists, slightly higher up
- **Castelo de São Jorge** — touristy, but the views and the peacocks make it worth it once

End the day at a **mirador** with a glass of *vinho verde* and watch the river change color. Dinner can wait until 9 pm — that's *normal* here.

### Day 2 — LX Factory and the river

Take the morning to explore **LX Factory**, a converted industrial complex that's now part bookstore, part design shop, part bakery. *Ler Devagar* is worth an hour by itself — old printing presses, suspended bicycles, books from floor to ceiling.

Then walk the **riverfront** west toward Belém. It's about an hour at a leisurely pace and one of the most underrated walks in the city.

> The *25 de Abril* bridge is San Francisco's Golden Gate's slightly more dramatic Iberian cousin. The light hitting it in the late afternoon is something to plan around.

In Belém:

- **Mosteiro dos Jerónimos** — the Manueline architecture is genuinely transporting
- **Pastéis de Belém** — yes, the lines; yes, they're worth it once
- **MAAT** — the contemporary art museum on the riverfront, if you have time and energy

### Day 3 — Sintra day trip

Catch the **early train** from Rossio (the 8:30 is sweet — late enough to feel civil, early enough to beat the crush). Sintra is its own microclimate; bring a **layer**.

The classic itinerary is:

- **Pena Palace** — the maximalist 19th-century fantasy on the hill
- **Quinta da Regaleira** — gardens, grottoes, an *initiation well* you descend in spirals
- *Lunch* in town at a quiet **quinta**

If you have to choose only one, pick *Quinta da Regaleira*. Pena gets the postcards, but the Regaleira gets the imagination.

### Practical notes

- **Skip the trams** during peak hours. They're more about the queue than the ride.
- **Cash is still useful** — many smaller restaurants and miradouro vendors don't take cards.
- **Pack light layers**. The *Atlantic wind* sneaks up on you in the evenings, even in summer.
- **Walk in flat shoes**. Lisbon's *calçada* — the patterned cobblestone — is beautiful and unforgiving.
- **Eat dinner late**. 7 pm is teatime; 9 pm is when restaurants come alive.

> The best thing you can do for a trip to Lisbon is build in *empty afternoons*. The city rewards the unstructured hour — the unplanned café, the wrong turn into a tile-covered alley, the impromptu *fado* coming from a doorway. Don't pack the days too tight.
            """.trimIndent()

            "code" in normalized || "kotlin" in normalized -> """
A few **Kotlin coroutine** patterns worth knowing, in roughly the order I reach for them:

### 1. `withContext` — switch threads, return a value

```kotlin
suspend fun fetchProfile(userId: String): Profile {
    return withContext(Dispatchers.IO) {
        api.getProfile(userId)
    }
}
```

The call to `withContext(Dispatchers.IO)` ensures the network work happens on a *background thread* without blocking the caller. The function is `suspend`, so callers don't need to know it switches threads — that's an implementation detail.

### 2. `launch` and `async` — start work in a scope

```kotlin
viewModelScope.launch {
    val profile = fetchProfile(userId)
    _state.value = State.Loaded(profile)
}
```

`launch` is **fire-and-forget** — you don't wait for the result. For parallel work where you *do* care about the result, use `async`:

```kotlin
viewModelScope.launch {
    val profile = async { api.getProfile(userId) }
    val friends = async { api.getFriends(userId) }
    _state.value = State.Loaded(profile.await(), friends.await())
}
```

Both calls run concurrently; `await()` is where you collect the results.

### 3. `Flow` — for streams of values over time

```kotlin
fun watchUpdates(): Flow<Update> = flow {
    while (currentCoroutineContext().isActive) {
        emit(api.poll())
        delay(1000)
    }
}
```

Each `emit` delivers a value to whoever is collecting downstream. `Flow` is *cold* — nothing runs until something starts collecting:

```kotlin
viewModelScope.launch {
    watchUpdates().collect { update ->
        applyUpdate(update)
    }
}
```

### 4. `StateFlow` — observable state

For UI state, prefer `StateFlow` — it always has a current value and de-duplicates equal emissions:

```kotlin
private val _state = MutableStateFlow<State>(State.Idle)
val state: StateFlow<State> = _state.asStateFlow()

fun load(userId: String) {
    viewModelScope.launch {
        _state.value = State.Loading
        try {
            _state.value = State.Loaded(fetchProfile(userId))
        } catch (e: Exception) {
            _state.value = State.Error(e)
        }
    }
}
```

The UI collects `state` and reacts to each transition.

### A few things to watch for

- **Don't use `GlobalScope`** outside of test code or top-level entry points. Tie work to a real lifecycle (`viewModelScope`, `lifecycleScope`, or your own `CoroutineScope`).
- **Cancellation is cooperative**. If a tight loop doesn't suspend, `cancel()` won't interrupt it — sprinkle in `yield()` or `ensureActive()`.
- **`runBlocking` belongs in tests and `main()`**, not in production code paths.
- **Structured concurrency** is the point: parent scopes wait for child jobs, and exceptions propagate up. Fight that with `SupervisorJob` only when you actually need to.

> The mental model: a coroutine is a *function that can pause and resume*. Once you internalize that, the rest of the API stops feeling like magic and starts feeling like a small set of composable building blocks.
            """.trimIndent()

            "markdown" in normalized || "format" in normalized -> """
A tour of the **markdown** I support, with a quick note on when each one earns its place.

### Inline emphasis

- **Bold** with double asterisks — for the *thing* in a sentence that you'd lean on if you were saying it aloud
- *Italic* with single asterisks — for technical terms, titles, or a softer kind of emphasis
- `Inline code` with backticks — for identifiers, file names, command fragments
- ***Bold italic*** for when both apply, used sparingly

### Headings

Three levels are usually enough:

- `#` — a top-line title for a long answer
- `##` — section breaks within an answer
- `###` — subsections; rarely beyond this

Going deeper than `###` usually means the answer wants to be **two answers**.

### Block quotes

> Block quotes with `>` are useful for asides, warnings, pull-quotes, or *quoting* the prompt back to make sure I understood it. Don't overuse them — a quote in every paragraph stops feeling like emphasis.

> *"Keep your formatting alphabet small,"* the editor said, *"and use each letter deliberately."*

### Lists

Bullet lists with `-` or `*`:

- For unordered ideas of equal weight
- When the order *doesn't* matter
- When you want the reader to scan rather than read linearly

For ordered procedures, use numbers:

- Wait, that's still bullets — but in a real markdown renderer you'd use `1.`, `2.`, `3.` for numbered steps
- Numbered lists are best when **order is part of the meaning** (recipes, install steps, debugging procedures)

### Code blocks

Fenced with triple backticks, optionally with a language tag:

```kotlin
fun main() {
    val message = "Hello, sunset"
    val style = "serif by default"
    println("${'$'}message — rendered in ${'$'}style")
}
```

```text
Plain code — no syntax highlighting,
useful for shell commands or pseudo-code.
```

I do *light* keyword highlighting for Kotlin — `fun`, `val`, `var`, `class`, `if`, `when`, etc. — without trying to be a full IDE. The goal is **readability**, not parity with an editor.

### What I deliberately don't use

- **Tables** — they break in narrow chat layouts and most things that want to be a table want to be a list instead
- **Footnotes** — chat is the wrong medium
- **HTML** — escape hatches are tempting but rarely worth the inconsistency
- **Horizontal rules** — section headings do the same job better

### A note on density

Markdown is a *layout language* in disguise. The structure you choose **shapes how the answer reads** before any individual sentence does its work. A wall of text and a tight list say different things even if they cover the same content. Match the structure to the question.
            """.trimIndent()

            "table" in normalized -> """
A quick demo of *tables* — both the comfortable kind and the kind that needs to swipe.

### Narrow — fits in the bubble

This first one is **four columns** and sits comfortably without scrolling. It's the shape most chat tables want to be: a short comparison the eye can take in at one glance.

| Course | Time | Pairing | Notes |
| :--- | :---: | :--- | :--- |
| **Bread & butter** | 6:30 | *Crémant d'Alsace* | warm the bread |
| Roasted tomato pasta | 7:00 | **Vermentino** | hold the cream |
| Bitter greens salad | 7:45 | (palate cleanser) | dress at the table |
| Olive-oil cake | 8:30 | *Vin santo* | a thin slice |

### Wide — swipe horizontally

This second one has **seven columns** and overflows the bubble. The renderer wraps it in `horizontalScroll`, so you can drag sideways to see the rest. Long cells get capped at the column max (the default is `200dp`) and *wrap* onto another line.

| City | Region | Population | Founded | Famous for | Best month | Vibe |
| :--- | :--- | ---: | ---: | :--- | :---: | :--- |
| **Lisbon** | Portugal | 547,000 | 1255 | *azulejos*, fado, custard tarts | May | slow walks, golden hour |
| **Porto** | Portugal | 231,000 | 300 BC | *port wine*, riverside tiles | September | working-class warmth |
| **Sintra** | Portugal | 377,000 | 1147 | *palaces in the mist*, ferns | October | melancholy and damp |
| **Évora** | Portugal | 56,000 | 60 BC | Roman temple, *megaliths* | April | empty squares at dusk |
| **Aveiro** | Portugal | 78,000 | 950 | canals, *ovos moles* | June | Portuguese Venice (sort of) |

Inline markdown inside cells works too — *italics*, **bold**, and `code` all parse normally. Cell alignment comes from the `:---`/`---:`/`:---:` syntax on the divider row.

> One *limitation* worth knowing: the table has to be its own paragraph (a blank line above and below). A pipe row mid-paragraph parses as ordinary text.
            """.trimIndent()

            else -> """
## Hello — and welcome to MyAI

This is a *gentle* assistant for the kinds of questions that **don't** have a one-line answer. I lean toward longer, more discursive responses — closer to a *short essay* than a quick reply — because most things worth asking benefit from a few paragraphs of context, options, and caveats. If you ever want **brevity instead**, just say so and I'll trim aggressively.

### What I'm good at

- **Recipes & cooking** — try asking about a *recipe*, a specific dish, or what to do with whatever's in your fridge
- **Travel ideas** — ask about a *trip* or *vacation* and I'll lean toward slow, walkable itineraries with room for the unplanned
- **Weather narratives** — ask about the *weather* and you'll get something more readable than a number
- **Code snippets** — ask about *kotlin* (or other languages) and you'll get working examples plus the small footnotes that make the difference between code that compiles and code that's good
- **Markdown demos** — ask about *markdown* or *format* if you want to see the typography in action — and yes, [links work too](https://github.com/chinalwb/lovechat)

### How I respond

I tend toward **longer, more discursive** answers — closer to a *short essay* than a one-liner. The structure tries to earn its keep:

- **Headings** signal that the answer has more than one part
- *Italics* mark technical terms, titles, and the softer kind of emphasis
- **Bold** is for the thing in a sentence I'd lean on if I were saying it aloud
- `Inline code` is for identifiers, paths, and command fragments
- Block quotes are for asides, warnings, and the kind of pull-quote that earns its own line

```kotlin
// Streaming is real — text arrives in waves, not all at once.
val reply: Flow<String> = api.stream(prompt)
    .onEach { token -> render(token) }
    .catch { e -> showError(e) }
```

### A note on style

I read in **serif** because the subject usually deserves it. The serif typeface (`Lora` here) does a quiet thing: it tells your eye that this is *prose*, not *interface*. Bullet lists, code blocks, and *italics* are part of the same vocabulary, so I use them when they help and skip them when they'd just be decoration.

> *"The job of an assistant is not to be brief, but to be useful."*
>
> — paraphrasing nobody in particular, but it scans well

### A few things I'll happily do

- **Brainstorm** — I'm comfortable with half-formed prompts; I'll ask follow-up questions if I need to
- **Edit** — paste text and tell me what you want louder, quieter, or sharper
- **Explain** — code, concepts, news; I'll show my work when the explanation is non-obvious
- **Disagree gently** — I'll push back if your premise seems shaky, but only after I've understood it

### What I try to avoid

- **Vague hedging** ("there are pros and cons") — if you ask me what I'd recommend, I'll recommend
- **Padding** — every paragraph should earn its place
- **Bullet lists where prose would be better** — and vice versa

Try one of the topics above to see streaming in action, or just throw something at me and we'll figure out the shape together.
            """.trimIndent()
        }
    }
}
