//
// Scaled HTML Mode - a Scaled major mode for editing HTML files
// http://github.com/scaled/html-mode/blob/master/LICENSE

package scaled.html

import scaled._
import scaled.code.Indenter
import scaled.grammar._
import scaled.code.{CodeConfig, Commenter}
import scaled.xml.XmlIndenter

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

  val cssGrammar = reloadable("CSS.ndf", Grammar.parseNDF)
  val jsGrammar = reloadable("JS.ndf", Grammar.parseNDF)
  val htmlGrammar = reloadable("HTML.ndf", Grammar.parseNDF)
  def grammars = Seq(cssGrammar(), jsGrammar(), htmlGrammar())
}

@Major(name="html",
       tags=Array("code", "project", "html"),
       pats=Array(".*\\.html", ".*\\.shtml"),
       desc="A major mode for editing HTML files.")
class HtmlMode (env :Env) extends GrammarCodeMode(env) {

  override def dispose () {} // nada for now

  override def configDefs = HtmlConfig :: super.configDefs
  override def grammars = HtmlConfig.grammars
  override def effacers = HtmlConfig.effacers

  override def detectIndent = new Indenter.Detecter(4) {
    // if the line starts with '<' then it is meaningful
    def consider (line :LineV, start :Int) :Int = if (line.charAt(start) == '<') 1 else 0
  }.detectIndent(buffer)

  override val indenters = List(
    new XmlIndenter.CloseTag(indentCtx),
    new XmlIndenter.NestedTag(indentCtx)
  )
  override val commenter = new Commenter()
}
