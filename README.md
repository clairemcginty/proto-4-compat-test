# proto-4-compat-test

To run:

1. Install Scala: https://www.scala-lang.org/download/2.13.16.html
2. Install SBT: https://formulae.brew.sh/formula/sbt
3. Run: `sbt test`

# Results

Everything succeeds EXCEPT tensorflow-core-api models on protobuf-java 4.x:

```
[info] TestSerialization:
[info] protobuf-java 3.x
[info] - should be able to serialize and deserialize Protobuf classes generated with Protoc 3 using proto3 syntax
[info] - should be able to serialize and deserialize Protobuf classes generated with Protoc 3 using proto2 syntax
[info] - should be able to serialize and deserialize Tensorflow-core protobuf classes (generated with protoc 2 maybe?)
[info] protobuf-java 4.x
[info] - should be able to serialize and deserialize Protobuf classes generated with Protoc 3 using proto3 syntax
[info] - should be able to serialize and deserialize Protobuf classes generated with Protoc 3 using proto2 syntax
[info] - should be able to serialize and deserialize Protobuf classes generated generated with Protoc 4 using proto3 syntax
[info] com.spotify.test.TestSerialization *** ABORTED ***
[info]   java.lang.NoSuchMethodError: 'com.google.protobuf.Internal$LongList org.tensorflow.proto.example.Int64List.newLongList()'
[info]   at org.tensorflow.proto.example.Int64List.<init>(Int64List.java:65)
[info]   at org.tensorflow.proto.example.Int64List.<init>(Int64List.java:9)
[info]   at org.tensorflow.proto.example.Int64List$1.parsePartialFrom(Int64List.java:563)
[info]   at org.tensorflow.proto.example.Int64List$1.parsePartialFrom(Int64List.java:557)
[info]   at com.google.protobuf.CodedInputStream$StreamDecoder.readMessage(CodedInputStream.java:2370)
[info]   at org.tensorflow.proto.example.Feature.<init>(Feature.java:89)
[info]   at org.tensorflow.proto.example.Feature.<init>(Feature.java:13)
[info]   at org.tensorflow.proto.example.Feature$1.parsePartialFrom(Feature.java:1086)
[info]   at org.tensorflow.proto.example.Feature$1.parsePartialFrom(Feature.java:1080)
[info]   at org.tensorflow.proto.example.Feature$Builder.mergeFrom(Feature.java:619)
```

Detailed stack trace on theh test failure:

```
[error] java.lang.NoSuchMethodError: 'com.google.protobuf.Internal$LongList org.tensorflow.proto.example.Int64List.newLongList()'
[error] 	at org.tensorflow.proto.example.Int64List.<init>(Int64List.java:65)
[error] 	at org.tensorflow.proto.example.Int64List.<init>(Int64List.java:9)
[error] 	at org.tensorflow.proto.example.Int64List$1.parsePartialFrom(Int64List.java:563)
[error] 	at org.tensorflow.proto.example.Int64List$1.parsePartialFrom(Int64List.java:557)
[error] 	at com.google.protobuf.CodedInputStream$StreamDecoder.readMessage(CodedInputStream.java:2370)
[error] 	at org.tensorflow.proto.example.Feature.<init>(Feature.java:89)
[error] 	at org.tensorflow.proto.example.Feature.<init>(Feature.java:13)
[error] 	at org.tensorflow.proto.example.Feature$1.parsePartialFrom(Feature.java:1086)
[error] 	at org.tensorflow.proto.example.Feature$1.parsePartialFrom(Feature.java:1080)
[error] 	at org.tensorflow.proto.example.Feature$Builder.mergeFrom(Feature.java:619)
[error] 	at org.tensorflow.proto.example.Feature$Builder.mergeFrom(Feature.java:452)
[error] 	at com.google.protobuf.CodedInputStream$StreamDecoder.readMessage(CodedInputStream.java:2354)
[error] 	at com.google.protobuf.MapEntryLite.parseField(MapEntryLite.java:105)
[error] 	at com.google.protobuf.MapEntryLite.parseEntry(MapEntryLite.java:161)
[error] 	at com.google.protobuf.MapEntry.<init>(MapEntry.java:84)
[error] 	at com.google.protobuf.MapEntry.<init>(MapEntry.java:27)
[error] 	at com.google.protobuf.MapEntry$Metadata$1.parsePartialFrom(MapEntry.java:48)
[error] 	at com.google.protobuf.MapEntry$Metadata$1.parsePartialFrom(MapEntry.java:42)
[error] 	at com.google.protobuf.CodedInputStream$StreamDecoder.readMessage(CodedInputStream.java:2370)
[error] 	at org.tensorflow.proto.example.Features.<init>(Features.java:59)
[error] 	at org.tensorflow.proto.example.Features.<init>(Features.java:9)
[error] 	at org.tensorflow.proto.example.Features$1.parsePartialFrom(Features.java:720)
[error] 	at org.tensorflow.proto.example.Features$1.parsePartialFrom(Features.java:714)
[error] 	at com.google.protobuf.CodedInputStream$StreamDecoder.readMessage(CodedInputStream.java:2370)
[error] 	at org.tensorflow.proto.example.Example.<init>(Example.java:56)
[error] 	at org.tensorflow.proto.example.Example.<init>(Example.java:9)
[error] 	at org.tensorflow.proto.example.Example$1.parsePartialFrom(Example.java:581)
[error] 	at org.tensorflow.proto.example.Example$1.parsePartialFrom(Example.java:575)
[error] 	at com.google.protobuf.AbstractParser.parsePartialFrom(AbstractParser.java:192)
[error] 	at com.google.protobuf.AbstractParser.parseFrom(AbstractParser.java:209)
[error] 	at com.google.protobuf.AbstractParser.parseFrom(AbstractParser.java:25)
[error] 	at org.apache.beam.sdk.extensions.protobuf.ProtoCoder.decode(ProtoCoder.java:205)
[error] 	at org.apache.beam.sdk.extensions.protobuf.ProtoCoder.decode(ProtoCoder.java:108)
[error] 	at org.apache.beam.sdk.util.CoderUtils.decodeFromSafeStream(CoderUtils.java:142)
[error] 	at org.apache.beam.sdk.util.CoderUtils.decodeFromByteArray(CoderUtils.java:102)
[error] 	at org.apache.beam.sdk.util.CoderUtils.decodeFromByteArray(CoderUtils.java:96)
[error] 	at com.spotify.test.TestSerialization.roundtrip(TestSerialization.scala:15)
[error] 	at com.spotify.test.TestSerialization.$anonfun$new$4(TestSerialization.scala:74)
```