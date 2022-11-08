package api.serializers

import api.models.AbstractLabel
import api.models.Label
import api.models.SimpleLabel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object AbstractLabelSerializer : JsonContentPolymorphicSerializer<AbstractLabel>(AbstractLabel::class) {
    override fun selectDeserializer(element: JsonElement) = when (element) {
        is JsonPrimitive -> SimpleLabel.serializer()
        else -> Label.serializer()
    }
}

object SimpleLabelSerializer : KSerializer<SimpleLabel> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("SimpleLabel")

    override fun deserialize(decoder: Decoder): SimpleLabel {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        return SimpleLabel(element.jsonPrimitive.content)
    }

    override fun serialize(encoder: Encoder, value: SimpleLabel) {
        encoder.encodeString(value.name)
    }
}