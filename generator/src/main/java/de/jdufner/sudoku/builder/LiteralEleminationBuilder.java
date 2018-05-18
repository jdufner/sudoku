/*
 * Sudoku is a puzzle game. It solves and generates puzzles in different
 * formats.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * 
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
