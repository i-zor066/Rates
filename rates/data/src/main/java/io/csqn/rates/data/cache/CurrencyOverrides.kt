package io.csqn.rates.data.cache

import io.csqn.rates.data.envelopes.CountryEnvelope
import io.csqn.rates.data.envelopes.CurrencyEnvelope

sealed class CurrencyOverrides {

    object Get : CurrencyOverrides() {

       val overrides = mapOf(
            EUR.currencyCode to EUR.countryEnvelope,
            GBP.currencyCode to GBP.countryEnvelope,
            CHF.currencyCode to CHF.countryEnvelope,
            AUD.currencyCode to AUD.countryEnvelope,
            ZAR.currencyCode to ZAR.countryEnvelope,
            USD.currencyCode to USD.countryEnvelope,
            INR.currencyCode to INR.countryEnvelope
       )

        fun isOverriden(currencyCode: String) = overrides.contains(currencyCode)
    }

    object EUR : CurrencyOverrides() {
        const val currencyCode = "EUR"
        const val currencyFlag = "https://flagpedia.net/data/org/w1160/eu.png"
        val countryEnvelope = CountryEnvelope(
            "EU", listOf(
                CurrencyEnvelope(
                    currencyCode, "Euro"
                )
            ),
            ""
        )
    }

    object GBP : CurrencyOverrides() {
        const val currencyCode = "GBP"
        val countryEnvelope =
            CountryEnvelope(
                "GB", listOf(
                    CurrencyEnvelope(
                        currencyCode, "British pound"
                    )
                ),
                ""
            )
    }

    object CHF : CurrencyOverrides() {
        const val currencyCode = "CHF"
        val countryEnvelope =
            CountryEnvelope(
                "CH", listOf(
                    CurrencyEnvelope(
                        currencyCode, "Swiss franc"
                    )
                ),
                ""
            )
    }

    object AUD : CurrencyOverrides() {
        const val currencyCode = "AUD"
        val countryEnvelope =
            CountryEnvelope(
                "AU", listOf(
                    CurrencyEnvelope(
                        currencyCode, "Australian dollar"
                    )
                ),
                ""
            )
    }

    object ZAR : CurrencyOverrides() {
        const val currencyCode = "ZAR"
        val countryEnvelope =
            CountryEnvelope(
                "ZA", listOf(
                    CurrencyEnvelope(
                        currencyCode, "South African rand"
                    )
                ),
                ""
            )
    }

    object USD : CurrencyOverrides() {
        const val currencyCode = "USD"
        val countryEnvelope =
            CountryEnvelope(
                "US", listOf(
                    CurrencyEnvelope(
                        currencyCode, "United States dollar"
                    )
                ),
                ""
            )
    }

    object INR : CurrencyOverrides() {
        const val currencyCode = "INR"
        val countryEnvelope =
            CountryEnvelope(
                "IN", listOf(
                    CurrencyEnvelope(
                        currencyCode, "Indian rupee"
                    )
                ),
                ""
            )
    }
}