// $Id$
package de.jdufner.sudoku.common.cellhandlerimpls;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.Literal;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class CellByValue implements CellHandler {

  private final transient Literal literal;
  private final transient Collection<Cell> cells = new ArrayList<Cell>();

  public CellByValue(final Literal literal) {
    this.literal = literal;
  }

  @Override
  public void handleCell(final Cell cell) {
    if (cell.getValue().equals(literal)) {
      cells.add(cell);
    }
  }

  @Override
  public void initialize() {
    // Nothing to do
  }

  public Collection<Cell> getCells() {
    return cells;
  }

}
