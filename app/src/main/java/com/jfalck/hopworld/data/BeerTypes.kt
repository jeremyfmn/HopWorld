package com.jfalck.hopworld.data

enum class BeerTypes(
    val id: Int,
    private val typeName: String,
    val shortName: String = typeName
) {
    GUARD(1, "Bière de garde", "Garde"),
    ABBEY(2, "Bière d’abbaye", "Abbaye"),
    LAGER(3, "Lager"),
    PALE_ALE(4, "Pale Ale"),
    IPA(5, "India Pale Ale", "IPA"),
    NEIPA(6, "India Pale Ale", "NEIPA"),
    TRIPLE(7, "Triple"),
    STOUT(8, "Stout"),
    WHITE(9, "Blanche"),
    SEASON(10, "Saison"),
    SOUR(11, "Sour"),
    KEG(12, "Vieillie en fût", "Fût");

    companion object {
        fun fromIdsList(ids: List<Int>): List<BeerTypes>? = ids.mapNotNull { fromId(it) }

        fun fromId(id: Int): BeerTypes? = values().find { it.id == id }
    }


}