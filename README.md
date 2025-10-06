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