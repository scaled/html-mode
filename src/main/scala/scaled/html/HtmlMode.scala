//
// Scaled HTML Mode - a Scaled major mode for editing HTML files
// http://github.com/scaled/html-mode/blob/master/LICENSE

package scaled.html

import scaled._
import scaled.grammar._
import scaled.code.{CodeConfig, Commenter}
import scaled.xml.XmlIndenter
import java.nio.file.Path

object HtmlConfig extends Config.Defs {
  import EditorConfig._
  import CodeConfig._
  import GrammarConfig._

  // maps TextMate grammar scopes to Scaled style definitions
  val effacers = List(
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

  val grammars = reloadable(Seq("CSS.ndf", "JS.ndf", "HTML.ndf"))(Grammar.parseNDFs)
}

@Major(name="html",
       tags=Array("code", "project", "html"),
       pats=Array(".*\\.html", ".*\\.shtml"),
       desc="A major mode for editing HTML files.")
class HtmlMode (env :Env) extends GrammarCodeMode(env) {

  override def dispose () {} // nada for now

  override def configDefs = HtmlConfig :: super.configDefs
  override def grammars = HtmlConfig.grammars.get
  override def effacers = HtmlConfig.effacers

  override val commenter = new Commenter()
  override def createIndenter() = new XmlIndenter(buffer, config)
}
