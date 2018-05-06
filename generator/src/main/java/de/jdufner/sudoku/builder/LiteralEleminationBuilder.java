// $Id$

/*
 * Gudoku (http://sourceforge.net/projects/gudoku)
 * Sudoku-Implementierung auf Basis des Google Webtoolkit
 * (http://code.google.com/webtoolkit/). Die Lösungsalgorithmen in Java laufen
 * parallel. Die Sudoku-Rätsel werden mittels JDBC in einer Datenbank
 * gespeichert.
 *
 * Copyright (C) 2008 Jürgen Dufner
 *
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der
 * GNU General Public License, wie von der Free Software Foundation
 * veröffentlicht, weitergeben und/oder modifizieren, entweder gemäß Version 3
 * der Lizenz oder (nach Ihrer Option) jeder späteren Version.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen
 * von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die
 * implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem
 * Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 *
 */
package de.jdufner.sudoku.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.factory.SudokuFactory;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class LiteralEleminationBuilder extends EleminationBuilder {

  private static final Logger LOG = Logger.getLogger(LiteralEleminationBuilder.class);

  public LiteralEleminationBuilder() {
    super();
  }

  @Override
  protected void executeBuilder() {
    for (Literal literal : generateRandomListOfCandidates()) {
      Collection<Cell> cells = sudoku.getCellsByValue(literal);
      for (Cell cell : randomizeCells(cells)) {
        eleminateIfPossible(cell);
      }
    }
  }

  private List<Cell> randomizeCells(Collection<Cell> cells) {
    LOG.debug(cells);
    Object[] objs = SudokuFactory.INSTANCE.getRandomData().nextSample(cells, cells.size());
    List<Cell> arbitraryPermutation = new ArrayList<Cell>();
    for (int i = 0; i < objs.length; i++) {
      arbitraryPermutation.add((Cell) objs[i]);
    }
    LOG.debug(arbitraryPermutation);
    return arbitraryPermutation;
  }

  private List<Literal> generateRandomListOfCandidates() {
    List<Literal> list = new ArrayList<Literal>();
    list.addAll(sudoku.getSize().initializeCandidates());
    Object[] objs = SudokuFactory.INSTANCE.getRandomData().nextSample(list, list.size());
    List<Literal> arbitraryPermutation = new ArrayList<Literal>();
    for (int i = 0; i < objs.length; i++) {
      arbitraryPermutation.add((Literal) objs[i]);
    }
    LOG.debug(arbitraryPermutation);
    return arbitraryPermutation;
  }

}
