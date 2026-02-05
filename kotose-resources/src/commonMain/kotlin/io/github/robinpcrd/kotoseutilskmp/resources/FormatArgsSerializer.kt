/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

package io.github.robinpcrd.kotoseutilskmp.resources

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.double
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

object FormatArgsSerializer : KSerializer<ImmutableList<Any>> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("FormatArgs") {
        element<JsonArray>("formatArgs")
    }

    override fun serialize(encoder: Encoder, value: ImmutableList<Any>) {
        require(encoder is JsonEncoder) { "This serializer only works with JSON" }

        val jsonArray = buildJsonArray {
            value.forEach { arg ->
                when (arg) {
                    is String -> add(
                        JsonObject(
                            mapOf(
                                "type" to JsonPrimitive("string"),
                                "value" to JsonPrimitive(arg)
                            )
                        )
                    )

                    is Int -> add(
                        JsonObject(
                            mapOf(
                                "type" to JsonPrimitive("int"),
                                "value" to JsonPrimitive(arg)
                            )
                        )
                    )

                    is Long -> add(
                        JsonObject(
                            mapOf(
                                "type" to JsonPrimitive("long"),
                                "value" to JsonPrimitive(arg)
                            )
                        )
                    )

                    is Float -> add(
                        JsonObject(
                            mapOf(
                                "type" to JsonPrimitive("float"),
                                "value" to JsonPrimitive(arg)
                            )
                        )
                    )

                    is Double -> add(
                        JsonObject(
                            mapOf(
                                "type" to JsonPrimitive("double"),
                                "value" to JsonPrimitive(arg)
                            )
                        )
                    )

                    is Boolean -> add(
                        JsonObject(
                            mapOf(
                                "type" to JsonPrimitive("boolean"),
                                "value" to JsonPrimitive(arg)
                            )
                        )
                    )

                    is StrRes -> add(
                        JsonObject(
                            mapOf(
                                "type" to JsonPrimitive("strres"),
                                "value" to encoder.json.encodeToJsonElement(arg)
                            )
                        )
                    )

                    is PlatformStrRes -> add(
                        JsonObject(
                            mapOf(
                                "type" to JsonPrimitive("platform"),
                                "value" to encoder.json.encodeToJsonElement(arg)
                            )
                        )
                    )

                    else -> throw SerializationException(
                        "Unsupported format arg type: ${arg::class}. " +
                                "Allowed types: String, Int, Long, Float, Double, Boolean, StrRes, PlatformStrRes"
                    )
                }
            }
        }

        encoder.encodeJsonElement(jsonArray)
    }

    override fun deserialize(decoder: Decoder): ImmutableList<Any> {
        require(decoder is JsonDecoder) { "This serializer only works with JSON" }

        val jsonArray = decoder.decodeJsonElement().jsonArray

        return jsonArray.map { element ->
            val jsonObject = element.jsonObject
            val type = jsonObject["type"]?.jsonPrimitive?.content
                ?: throw SerializationException("Missing 'type' field in format arg")
            val valueElement = jsonObject["value"]
                ?: throw SerializationException("Missing 'value' field in format arg")

            when (type) {
                "string" -> valueElement.jsonPrimitive.content
                "int" -> valueElement.jsonPrimitive.int
                "long" -> valueElement.jsonPrimitive.long
                "float" -> valueElement.jsonPrimitive.float
                "double" -> valueElement.jsonPrimitive.double
                "boolean" -> valueElement.jsonPrimitive.boolean
                "strres" -> decoder.json.decodeFromJsonElement<StrRes>(valueElement)
                "platform" -> decoder.json.decodeFromJsonElement<PlatformStrRes>(valueElement)
                else -> throw SerializationException("Unknown format arg type: $type")
            }
        }.toImmutableList()
    }
}
