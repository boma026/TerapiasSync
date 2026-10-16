package br.ufrn.imd.massosync.model

enum class Tecnica(val titulo: String, val descricao: String) {
    MASSAGEM_RELAXANTE(
        titulo = "Massagem Relaxante",
        descricao = "Alívio de tensões musculares, estresse e relaxamento corporal geral."
    ),
    REFLEXOLOGIA_PODAL(
        titulo = "Reflexologia Podal",
        descricao = "Estímulo de pontos reflexos nos pés para equilíbrio físico e energético."
    ),
    TERMOTERAPIA(
        titulo = "Termoterapia",
        descricao = "Aplicação de calor terapêutico para aumento da circulação e alívio de dores."
    );

    companion object {
        fun fromTitulo(titulo: String): Tecnica? {
            return entries.firstOrNull { it.titulo.equals(titulo, ignoreCase = true) }
        }
    }
}
