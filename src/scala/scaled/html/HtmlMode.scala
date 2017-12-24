//
// Scaled HTML Mode - a Scaled major mode for editing HTML files
// http://github.com/scaled/html-mode/blob/master/LICENSE

package scaled.html

import scaled._
import scaled.grammar._
import scaled.code.{CodeConfig, Commenter}
import scaled.xml.XmlIndenter
import java.nio.file.Path

@Plugin(tag="textmate-grammar")
class HtmlGrammarPlugin extends GrammarPlugin {
  import EditorConfig._
  import CodeConfig._

  override def grammars = Map("source.html" -> "HTML.ndf")

  override def effacers = List(
    effacer("comment.line", commentStyle),
    effacer("comment.block", docStyle),
    effacer("constant", constantStyle),
    effacer("invalid", warnStyle),
    effacer("keyword", keywordStyle),
    effacer("string", stringStyle),

    effacer("entity.name.tag", functionStyle),
    effacer("entity.other", variableStyle),

    effacer("variable.language.documentroot", preprocessorStyle),
    effacer("variable.language.entity", typeStyle)
  )
}

@Major(name="html",
       tags=Array("code", "project", "html"),
       pats=Array(".*\\.html", ".*\\.shtml"),
       desc="A major mode for editing HTML files.")
class HtmlMode (env :Env) extends GrammarCodeMode(env) {

  override def dispose () {} // nada for now

  override def langScope = "source.html"

  override val commenter = new Commenter()
  override def createIndenter() = new XmlIndenter(config)
}
