# Leafy
> LEaFy - Language procEssing Flow

An UIMA-like... but lightweight, concurrent and distributed by nature

## Usage

### Analysis engine

Create your engines by implementing the `AnalysisEngine` trait and its `process` method : 

```scala
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
import leafy.core
import leafy.flow.Flow

Flow.run("My source text #data", Props[WhitespaceTokenizer], Props[NamedEntityRecognition], ...)
```

The first parameter is the text data which will be processed.  
The others are all the engines you want to use.  

It returns a **future** bucket containing all the data set by the engines.

## Architecture

// @todo

## Powered by

**Akka** <3
