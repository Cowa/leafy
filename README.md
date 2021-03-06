# leafy
> Temporary name and kind of a POC for now

An UIMA-like... but lightweight, modern, concurrent and distributed by nature

## Why leafy?

It lets you build simple (but powerful) **flows** to leverage your unstructured data.  

A flow can combine multiple **analysis engines** and each flow can be branched into another flow.  

**Branches** are run concurrently, making flows efficient and smart.  

Branches can also be merged and go to another flow... Unlimited power!  

Oh and by the way, flows and analysis engines are **distributed-ready** by design. Nice right?   

### Illustration

In Natural Language Processing, you could make something like this:  

![simpleflowleafy](https://cloud.githubusercontent.com/assets/1422403/10304375/ff86ed7e-6c19-11e5-95b2-aa8f9a9de81c.png)

## Usage

### Bucket

The bucket is the way of communication between analysis engines.  
You put everything you want in it. And the next engines in the flow will be able to get it if they need it.

For now this is what buckets look like:
```scala
case class Bucket(source: String, data: List[Data]) {
  def add(d: Data) = Bucket(source, data :+ d)
  def add(d: List[Data]) = Bucket(source, data ++ d)
  def select[T: ClassTag] = data.collect({case t: T => t})
  def merge(b: Bucket) = Bucket(b.source, (data ++ b.data).distinct)
}
```

This is an immutable structure.

### Analysis engine

Create your engines by implementing the `AnalysisEngine` trait and its `process` method : 

```scala
import leafy.analysis.AnalysisEngine
import leafy.models.{Annotation, Bucket}

class WhitespaceTokenizer extends AnalysisEngine {
  
  def process(b: Bucket): Bucket = {
    var cursor = 0
    var tokens = List[Annotation]()

    b.source.split(" ").foreach { x =>
      tokens = tokens :+ Annotation(cursor, cursor + x.length, x)
      cursor = cursor + x.length + 1
    }

    b.add(tokens)
  }
}
```

Annotations and other results must be put inside the bucket.  
And the last expression **must** be a bucket.

### Data type

To create your own data type that can be put in a bucket, extends `Data`:  

```scala
import leafy.models.Data

case class NamedEntity(text: String) extends Data
```

### Flow

#### Simple flows

You can chain several analysis engines through a flow: 

```scala
import leafy._
import leafy.flow.Flow

Flow("My source text #data", AE[WhitespaceTokenizer], AE[NamedEntityRecognition])
```

<p align="center">
  <img src="https://cloud.githubusercontent.com/assets/1422403/10395259/37f5430c-6e9c-11e5-80b8-288f68ece4b5.png" />
</p>

The first parameter is the text data which will be processed.  
The others are all the engines you want to use.  

It returns a **future** bucket containing all the data set by the engines.

#### Branch flows 

```scala
import leafy._
import leafy.flow.Flow

val startFlow = Flow("My source text #data", AE[WhitespaceTokenizer], AE[NamedEntityRecognition])

val branch0 = Flow.branch(startFlow, AE[SomeAE], AE[AnotherAE])
val branch1 = Flow.branch(startFlow, AE[Stuff], AE[OkWhyNot])
```

![branches](https://cloud.githubusercontent.com/assets/1422403/10395262/3f9e605c-6e9c-11e5-88fc-35372e79d3ea.png)

Each branch will run **concurrently**.  

#### Merge flows 

```scala
import leafy._
import leafy.flow.Flow

// ...

val merged = Flow.merge(branch0, branch1, branch2)

// You can branched it to another flows
val continue = Flow.branch(merged, AE[...])
```

![merged](https://cloud.githubusercontent.com/assets/1422403/10395266/47579a2a-6e9c-11e5-85fd-c5cfef152d92.png)

## Architecture

// @todo, explain how works this beauty :lipstick::kissing_heart:

## To do

- Better bucket structure for large processing
- Simple and sexy resources management (avoid the UIMA ugly way)
- Maybe switch to akka-stream... not sure yet

## Powered by

**Scala**, **Akka** <3
