// $Id$
package de.jdufner.sudoku.common.board;

import java.util.List;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 28.05.2011
 * @see <a
 *      href="http://sudopedia.org/wiki/Line">http://sudopedia.org/wiki/Line</a>
 */
public abstract class Line extends House {

  public Line(SudokuSize sudokuSize, int index, List<Cell> cells) {
    super(sudokuSize, index, cells);
  }

}
