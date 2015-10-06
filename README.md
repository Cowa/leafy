# leafy
> Temporary name... and kind of a Proof Of Concept for now :)

An UIMA-like... but lightweight, concurrent and distributed by nature

## Why leafy?

It lets you build simple (but powerful) **flows** to leverage your unstructured data.  

A flow can combine multiple **analysis engines** and each flow can be branched with another flow.  

**Branches** are run concurrently, making flows efficient and smart.  

Branches can also be merged and go to another flow... Unlimited power!  

Oh and by the way, flows and analysis engines are **distributed-ready** by design. Nice right?   

### Example illustrations

In Natural Language Processing, you could make something like this:  

![simpleflowleafy](https://cloud.githubusercontent.com/assets/1422403/10304375/ff86ed7e-6c19-11e5-95b2-aa8f9a9de81c.png)

## Usage

### Bucket

// @todo

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

### Data type

To create your own data type that can be put in a bucket, extends `Data`:  

```scala
import leafy.models.Data

case class NamedEntity(text: String) extends Data
```

### Flow (pipeline)

You can chain several analysis engines through a flow: 

```scala
import leafy._
import leafy.flow.Flow

Flow("My source text #data", AE[WhitespaceTokenizer], AE[NamedEntityRecognition], ...)
```

The first parameter is the text data which will be processed.  
The others are all the engines you want to use.  

It returns a **future** bucket containing all the data set by the engines.

// @todo Branches and merges  
// + examples

## Architecture

// @todo

## Powered by

**Akka** <3
