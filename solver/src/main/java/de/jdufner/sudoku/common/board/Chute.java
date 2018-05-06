// $Id$
package de.jdufner.sudoku.common.board;

import java.util.List;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 29.05.2011
 * @see <a
 *      href="http://sudopedia.org/wiki/Chute">http://sudopedia.org/wiki/Chute</a>
 */
public abstract class Chute {

  private List<House> houses;

  protected abstract boolean isUnique();

}
