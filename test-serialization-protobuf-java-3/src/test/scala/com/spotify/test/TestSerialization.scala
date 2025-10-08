package com.spotify.test

import com.google.protobuf.{ByteString, Message}
import org.apache.beam.sdk.extensions.protobuf.ProtoCoder
import org.scalatest.flatspec.AnyFlatSpec
import com.spotify.test.protobuf2.proto2.{RecordProto2Syntax => protobuf2_proto2syntax}
import com.spotify.test.protobuf3.proto3.{RecordProto3Syntax => protobuf3_proto2syntax}
import com.spotify.test.protobuf3.proto3.{RecordProto3Syntax => protobuf3_proto3syntax}
import org.apache.beam.sdk.coders.Coder
import org.apache.beam.sdk.util.CoderUtils

class TestSerialization extends AnyFlatSpec {

  def roundtrip[T <: Message](t: T, coder: Coder[T]): T =
    CoderUtils.decodeFromByteArray(coder, CoderUtils.encodeToByteArray(coder, t))

  // Result: SUCCEEDS
  "protobuf-java 3.x" should "be able to serialize and deserialize Protobuf classes generated with Protoc 3 using proto3 syntax" in {
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

  // Result: SUCCEEDS
  it should "be able to serialize and deserialize Protobuf classes generated with Protoc 2 using proto2 syntax" in {
    val protobufCoder = ProtoCoder.of(classOf[protobuf2_proto2syntax.TestRecord])

    val message = protobuf2_proto2syntax.TestRecord
      .newBuilder()
      .setId(1L)
      .build()

    assert(roundtrip(message, protobufCoder) == message)
  }

  // Result: SUCCEEDS
  it should "be able to serialize and deserialize Tensorflow-core protobuf classes (generated with protoc 2 maybe?)" in {
    // Hypothesis: generated with protoc 2?
    import org.tensorflow.proto.example.{BytesList, Example, Feature, Features, FloatList, Int64List}

    val protobufCoder = ProtoCoder.of(classOf[Example])

    val features = Features
      .newBuilder()
      .putFeature(
        "i",
        Feature
          .newBuilder()
          .setInt64List(Int64List.newBuilder().addValue(1))
          .setInt64List(Int64List.newBuilder().addValue(1))
          .setBytesList(BytesList.newBuilder().addValue(ByteString.copyFromUtf8("foo")))
          .setFloatList(FloatList.newBuilder().addValue(1F))
          .build()
      )

    val message = Example
      .newBuilder()
      .setFeatures(features)
      .build()

    assert(roundtrip(message, protobufCoder) == message)
  }
}