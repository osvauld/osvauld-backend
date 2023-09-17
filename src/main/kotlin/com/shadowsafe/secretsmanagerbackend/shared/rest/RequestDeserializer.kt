package com.shadowsafe.secretsmanagerbackend.dto.shared.rest

//
// import com.fasterxml.jackson.annotation.JsonProperty
// import com.fasterxml.jackson.core.JsonParser
// import com.fasterxml.jackson.core.type.TypeReference
// import com.fasterxml.jackson.databind.DeserializationContext
// import com.fasterxml.jackson.databind.JsonNode
// import com.fasterxml.jackson.databind.deser.std.StdDeserializer
// import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
// import com.fasterxml.jackson.module.kotlin.readValue
//
// data class Wrapper<T: >(
//  @JsonProperty("value") val value: T
// )
// class RequestDeserializer<T>: StdDeserializer<GenericRequestDTO<T>>(GenericRequestDTO::class.java) {
//  override fun deserialize(jp: JsonParser?, ctxt: DeserializationContext?): GenericRequestDTO<T>? {
//    val jsonNode:JsonNode? = jp?.codec?.readTree(jp)
//    jsonNode?.let{
//      val requestID:String = it.get("requestId").toString()
//
//      val dataJson:String = it.get("data").toString()
//      val type = jacksonObjectMapper().typeFactory.constructParametricType(T::class.java)
//      val dto = jacksonObjectMapper().readValue<T>(dataJson)
//      return GenericRequestDTO(requestID,dto)
//    }
//    return null //Change NULL
//  }
// }
