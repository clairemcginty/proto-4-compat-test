package com.spotify.test

import com.google.protobuf.Message
import org.apache.beam.sdk.extensions.protobuf.ProtoCoder
import org.scalatest.flatspec.AnyFlatSpec
import com.spotify.test.protobuf3.proto3.{RecordProto3Syntax => protobuf3_proto2syntax}
import com.spotify.test.protobuf3.proto3.{RecordProto3Syntax => protobuf3_proto3syntax}
import com.spotify.test.protobuf4.proto3.{RecordProto3Syntax => protobuf4_proto3syntax}
import org.apache.beam.sdk.coders.Coder
import org.apache.beam.sdk.util.CoderUtils

class TestSerialization extends AnyFlatSpec {

  def roundtrip[T <: Message](t: T, coder: Coder[T]): T =
    CoderUtils.decodeFromByteArray(coder, CoderUtils.encodeToByteArray(coder, t))

  // Result: SUCCEEDS
  "protobuf-java 4.x" should "be able to serialize and deserialize Protobuf classes generated with Protoc 3 using proto3 syntax" in {
    val protobufCoder = ProtoCoder.of(classOf[protobuf3_proto3syntax.TestRecord])

    val message = protobuf3_proto3syntax.TestRecord
      .newBuilder()
      .setId(1L)
      .build()

    assert(roundtrip(message, protobufCoder) == message)
  }

  // Result: SUCCEEDS
  it should "be able to serialize and deserialize Protobuf classes generated with Protoc 3 using proto2 syntax" in {
    val protobufCoder = ProtoCoder.of(classOf[protobuf3_proto2syntax.TestRecord])

    val message = protobuf3_proto2syntax.TestRecord
      .newBuilder()
      .setId(1L)
      .build()

    assert(roundtrip(message, protobufCoder) == message)
  }

  it should "be able to serialize and deserialize Protobuf classes generated generated with Protoc 4 using proto3 syntax" in {
    val protobufCoder = ProtoCoder.of(classOf[protobuf4_proto3syntax.TestRecord])

    val message = protobuf4_proto3syntax.TestRecord
      .newBuilder()
      .setId(1L)
      .build()

    assert(roundtrip(message, protobufCoder) == message)
  }

  // Result: FAILS
  it should "be able to serialize and deserialize Tensorflow-core protobuf classes (generated with protoc 2 maybe?)" in {
    // Hypothesis: generated with protoc 2?
    import org.tensorflow.proto.example.{Example, Feature, Features, Int64List}

    val protobufCoder = ProtoCoder.of(classOf[Example])

    val features = Features
      .newBuilder()
      .putFeature(
        "i",
        Feature
          .newBuilder()
          .setInt64List(Int64List.newBuilder().addValue(1))
          .build()
      )

    val message = Example
      .newBuilder()
      .setFeatures(features)
      .build()

    assert(roundtrip(message, protobufCoder) == message)
  }
}