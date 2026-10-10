package com.example.recipeapp.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.contentOrNull

/**
 * v2 recipe data model.
 *
 * Additive port of the model to the v2 format described by the Simmer design export
 * (`Design/Recipe app with cooking steps/simmer-data.js`). Written alongside the existing v1 model so
 * the app keeps compiling while the UI/resolver port issues (03+) are worked through.
 *
 * v2 differs from v1 at a structural level:
 *  - root has `methods` (a set of preparation questions), `ingredients` (flat, with conditions and
 *    per-condition quantity overrides) and `steps` (phased, with prerequisites and inline ingredient
 *    references) instead of v1's nested `preparationMethods`.
 *  - ingredient quantities are numeric (`q`/`u`) instead of free-form strings like `"200 g"`.
 *
 * Deserialization is tolerant of unknown v2 fields (ignoreUnknownKeys) and rejects legacy v1 samples
 * with a clear [RecipeSchemaException] instead of silently producing a half-built object.
 */

/** Clear, catchable error raised when a sample is in a format this model does not read. */
class RecipeSchemaException(message: String, cause: Throwable? = null) :
    SerializationException(message, cause)

@Serializable
data class RecipeV2(
    val id: String,
    val title: String,
    /** Default serving count; used as the base for ingredient scaling. JSON key is `base` in the design data. */
    @SerialName("base")
    val servings: Int = 4,
    val category: String? = null,
    val tags: List<String> = emptyList(),
    val icon: String? = null,
    val summary: String? = null,
    /** Total time in minutes as declared by the recipe (design `time`). Optional. */
    @SerialName("time")
    val timeMinutes: Int? = null,
    val difficulty: String? = null,
    val variations: List<Variation> = emptyList(),
    val methods: List<Method> = emptyList(),
    val ingredients: List<IngredientVariant> = emptyList(),
    val steps: List<StepV2> = emptyList(),
)

@Serializable
enum class VariationKind { swap, add }

/** A swap variation lets the user pick exactly one option; an add variation toggles an optional ingredient. */
@Serializable
data class Variation(
    val id: String,
    val kind: VariationKind,
    val label: String,
    val options: List<VariationOption> = emptyList(),
    /** Default selected option for a swap variation. Null for add variations. */
    val def: String? = null,
)

@Serializable
data class VariationOption(
    @SerialName("v")
    val value: String = "",
    @SerialName("l")
    val label: String = "",
    val icon: String? = null,
    @SerialName("d")
    val description: String? = null,
)

/** A preparation question (for example "How do you want to simmer the chilli?"). */
@Serializable
data class Method(
    val id: String,
    val label: String,
    @SerialName("q")
    val question: String = "",
    val options: List<MethodOption> = emptyList(),
)

@Serializable
data class MethodOption(
    @SerialName("v")
    val value: String = "",
    @SerialName("l")
    val label: String = "",
    val icon: String? = null,
    /** Display time for this option, e.g. "1 h", "18 min". Formatting/parsing is downstream. */
    @SerialName("time")
    val time: String = "",
    @SerialName("d")
    val description: String? = null,
)

/**
 * A single quantity override applied when [condition] holds (design `alt` as a `[condition, value]` pair).
 *
 * Decoded from the design's positional `[condition, value]` JSON array rather than an object, since
 * kotlinx.serialization has no built-in support for that shape.
 */
@Serializable(AltConditionSerializer::class)
data class AltCondition(val condition: String, val value: Double)

object AltConditionSerializer : KSerializer<AltCondition> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("com.example.recipeapp.model.AltCondition") {
            element("condition", PrimitiveSerialDescriptor("condition", PrimitiveKind.STRING))
            element("value", PrimitiveSerialDescriptor("value", PrimitiveKind.DOUBLE))
        }

    override fun serialize(encoder: Encoder, value: AltCondition) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: throw SerializationException("AltCondition can only be encoded to JSON")
        jsonEncoder.encodeJsonElement(
            JsonArray(listOf(JsonPrimitive(value.condition), JsonPrimitive(value.value))),
        )
    }

    override fun deserialize(decoder: Decoder): AltCondition {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw SerializationException("AltCondition can only be decoded from JSON")
        val arr = jsonDecoder.decodeJsonElement().jsonArray
        if (arr.size != 2) {
            throw SerializationException("alt condition must be a [condition, value] pair, got ${arr.size} elements")
        }
        return AltCondition(
            condition = arr[0].jsonPrimitive.content,
            value = arr[1].jsonPrimitive.content.toDoubleOrNull()
                ?: throw SerializationException("alt condition value must be a number"),
        )
    }
}

@Serializable
data class IngredientVariant(
    val id: String,
    val name: String,
    @SerialName("q")
    val qty: Double? = null,
    @SerialName("u")
    val unit: String? = null,
    val note: String? = null,
    val aisle: String? = null,
    val tips: List<String>? = null,
    /** Condition that gates this ingredient (design `if`). `if` is a Kotlin keyword, so the property is `condition`. */
    @SerialName("if")
    val condition: String? = null,
    /** Per-condition quantity overrides (design `alt`). */
    val alt: List<AltCondition> = emptyList(),
)

@Serializable
enum class StepPhase { prep, cook }

/**
 * A cooking step. The body holds inline ingredient references as raw text (for example `{on:onions}`);
 * parsing those references into tappable chips is downstream.
 */
@Serializable
data class StepV2(
    val id: String,
    // Design key is "ph" (phase); "phase" is a Kotlin-friendly name.
    @SerialName("ph")
    val phase: StepPhase,
    @SerialName("t")
    val title: String = "",
    @SerialName("d")
    val durationSeconds: Int = 0,
    @SerialName("b")
    val body: String = "",
    /** Ids of prerequisite steps that must run first (design `needs`). */
    val needs: List<String> = emptyList(),
    /** Condition that gates this step (design `if`). */
    @SerialName("if")
    val condition: String? = null,
)

/** Entry point for decoding v2 recipes. */
object RecipeV2Codec {
    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    /**
     * Decode a v2 recipe from JSON text.
     *
     * @throws RecipeSchemaException if the root is not an object, or the sample is in the legacy v1
     *   format (it uses `preparationMethods` and lacks v2 `methods`/`ingredients`/`steps`).
     */
    fun decodeFromString(text: String): RecipeV2 {
        val element = json.parseToJsonElement(text)
        val obj = element as? JsonObject
            ?: throw RecipeSchemaException("Recipe JSON must be an object at the root.")

        val isLegacy = obj.containsKey("preparationMethods") && !obj.containsKey("methods")
        if (isLegacy) {
            val id = obj["id"]?.jsonPrimitive?.contentOrNull ?: "<unknown>"
            throw RecipeSchemaException(
                "Recipe '$id' is in legacy v1 format (preparationMethods). " +
                    "This model reads the v2 format (methods, ingredients, steps); convert it first.",
            )
        }
        return json.decodeFromJsonElement(element)
    }
}
