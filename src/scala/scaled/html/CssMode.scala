//
// Scaled HTML Mode - a Scaled major mode for editing HTML files
// http://github.com/scaled/html-mode/blob/master/LICENSE

package scaled.html

import scaled._
import scaled.grammar._
import scaled.code.{CodeConfig, Commenter}
import java.nio.file.Path

object CssConfig extends Config.Defs {
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

    effacer("entity.name", typeStyle),
    effacer("entity.other.attribute-name", typeStyle),
    effacer("support.type.property-name", variableStyle),
    effacer("support.function", functionStyle),
    effacer("support.constant.font-name", stringStyle),
    effacer("support.constant", constantStyle)
  )

  val grammars = resource(Seq("CSS.ndf"))(Grammar.parseNDFs)
}

@Major(name="css",
       tags=Array("code", "project", "css"),
       pats=Array(".*\\.css", ".*\\.scss"),
       desc="A major mode for editing CSS files.")
class CssMode (env :Env) extends GrammarCodeMode(env) {

  override def dispose () {} // nada for now

  override def configDefs = CssConfig :: super.configDefs
  override def grammars = CssConfig.grammars.get
  override def effacers = CssConfig.effacers

  override val commenter = new Commenter()
  override def createIndenter() = new CssIndenter(config)
}
