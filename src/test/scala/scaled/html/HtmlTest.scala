//
// Scaled HTML Mode - a Scaled major mode for editing HTML files
// http://github.com/scaled/html-mode/blob/master/LICENSE

package scaled.html

import org.junit.Assert._
import org.junit._
import scaled.TextStore
import scaled.grammar._
import scaled.impl.BufferImpl

class HtmlTest {

  val testHTMLCode = Seq(
    //                1         2         3
    //      0123456789012345678901234567890
    /* 0*/ "<html>",
    /* 1*/ "",
    /* 2*/ "<foo>",
    /* 3*/ " <!- a comment, how lovely -->",
    /* 4*/ " <bar>baz</bar>",
    /* 5*/ " <dingle a=\"b\" />",
    /* 6*/ "</foo>",
    /* 7*/ "}").mkString("\n")

  val html = Grammar.parseNDF(getClass.getClassLoader.getResourceAsStream("HTML.ndf"))
  val grammars = List(html)

  @Test def debugGrammar () {
    // html.print(System.out)
    // html.scopeNames foreach println

    val buffer = BufferImpl(new TextStore("Test.html", "", testHTMLCode))
    val scoper = new Scoper(grammars, buffer, Nil)
    println(scoper.showMatchers(Set("#tag-stuff", "#entity")))
  }
}