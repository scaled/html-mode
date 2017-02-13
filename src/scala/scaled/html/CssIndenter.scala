//
// Scaled HTML Mode - a Scaled major mode for editing HTML files
// http://github.com/scaled/html-mode/blob/master/LICENSE

package scaled.html

import scaled._
import scaled.code.Indenter

class CssIndenter (cfg :Config) extends Indenter.ByBlock(cfg) {
  import Indenter._

  override protected def createStater = new BlockStater() {
    override def adjustStart (line :LineV, first :Int, last :Int, start :State) :State = {
      if (countComments(line, first) > 0) {
        // if this is a doc comment which is followed by non-whitespace, then indent to match the
        // second star rather than the first
        val col = if (line.matches(firstLineDocM, first)) 2 else 1
        new CommentS(col, start)
      }
      // otherwise leave the start as is
      else start
    }

    override def adjustEnd (line :LineV, first :Int, last :Int, start :State, cur :State) :State = {
      var end = cur
      // if this line closes a doc/block comment, pop our comment state from the stack
      if (countComments(line, 0) < 0) end = end.popIf(_.isInstanceOf[CommentS])
      end
    }
  }

  protected class CommentS (inset :Int, next :State) extends State(next) {
    override def indent (config :Config, top :Boolean) = inset + next.indent(config)
    override def show = s"CommentS($inset)"
  }

  private val firstLineDocM = Matcher.regexp("""/\*\*\s*\S+""")
}
