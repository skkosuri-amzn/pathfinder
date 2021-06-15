package com.amazon.opendistroforelasticsearch.alerting.alerts

import org.elasticsearch.common.io.stream.StreamInput
import org.elasticsearch.common.io.stream.StreamOutput
import org.elasticsearch.common.io.stream.Writeable
import org.elasticsearch.common.xcontent.ToXContent
import org.elasticsearch.common.xcontent.XContentBuilder
import org.elasticsearch.common.xcontent.XContentParser
import org.elasticsearch.common.xcontent.XContentParserUtils
import java.io.IOException

data class RelatedResource(val resourceType: ResourceType, val resource: String) : Writeable, ToXContent {

    @Throws(IOException::class)
    constructor(sin: StreamInput): this(
            resourceType = ResourceType.valueOf(sin.readString()),
            resource = sin.readString()
    )

    @Throws(IOException::class)
    override fun writeTo(out: StreamOutput) {
        out.writeString(resourceType.type)
        out.writeString(resource)
    }
    companion object {

        const val RESOURCE_TYPE_FIELD = "resource_type"
        const val RESOURCE_FIELD = "resource"

        @JvmStatic
        @Throws(IOException::class)
        fun parse(xcp: XContentParser): RelatedResource {

            lateinit var resourceType: ResourceType
            lateinit var resource: String

            XContentParserUtils.ensureExpectedToken(XContentParser.Token.START_OBJECT, xcp.currentToken(), xcp)
            while (xcp.nextToken() != XContentParser.Token.END_OBJECT) {
                val fieldName = xcp.currentName()
                xcp.nextToken()

                when (fieldName) {
                    RESOURCE_TYPE_FIELD -> resourceType = ResourceType.valueOf(xcp.text())
                    RESOURCE_FIELD -> resource = xcp.text()
                }
            }
            return RelatedResource(resourceType = resourceType, resource = resource)
        }

        @JvmStatic
        @Throws(IOException::class)
        fun readFrom(sin: StreamInput): AlertError {
            return AlertError(sin)
        }
    }

    override fun toXContent(builder: XContentBuilder, params: ToXContent.Params): XContentBuilder {
        return builder.startObject()
                .field(RESOURCE_TYPE_FIELD, resourceType)
                .field(RESOURCE_FIELD, resource)
                .endObject()
    }
}
