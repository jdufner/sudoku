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


package de.jdufner.sudoku.commands;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since
 * @version $Revision$
 */
public final class RemoveCandidatesCommandTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(RemoveCandidatesCommandTest.class);

  private Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.ING_DIBA);

  public void setUp() {
    LOG.debug(sudoku);
  }

  public void testRemoveCandidatesCommand() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Literal l = sudoku.getCell(0, 0).getValue(); // 9
    Command rcc1 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(l).build();
    assertNull(rcc1.getFrozenString());
    rcc1.execute(sudoku);
    assertTrue(rcc1.isSuccessfully());
    assertTrue(cell.isValid());
    assertTrue(rcc1.reversible());
    LOG.debug(rcc1.getFrozenString());
    assertEquals("null: Entferne Kandidaten [9] in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", rcc1
        .getFrozenString());

    assertEquals(8, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(1)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(3)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(4)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(5)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(7)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(8)));
    assertFalse(cell.isFixed());

    // Kandidaten aus der Zeile
    Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(sudoku.getCell(0, 1).getValue()); // 5
    candidates1.add(sudoku.getCell(0, 6).getValue()); // 1
    candidates1.add(sudoku.getCell(0, 7).getValue()); // 8
    Command rcc2 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(candidates1).build();
    assertNull(rcc2.getFrozenString());
    rcc2.execute(sudoku);
    assertTrue(rcc2.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals(5, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(3)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(4)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(7)));
    assertFalse(cell.isFixed());

    // Kandidaten aus der Spalte
    Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(sudoku.getCell(5, 2).getValue()); // 4
    candidates2.add(sudoku.getCell(6, 2).getValue()); // 5
    candidates2.add(sudoku.getCell(8, 2).getValue()); // 3
    Command rcc3 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(candidates2).build();
    assertNull(rcc3.getFrozenString());
    rcc3.execute(sudoku);
    assertTrue(rcc3.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals(3, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(7)));
    assertFalse(cell.isFixed());

    // Kandidaten aus dem Block
    Candidates<Literal> candidates3 = new Candidates<Literal>();
    candidates3.add(sudoku.getCell(0, 0).getValue()); // 9
    candidates3.add(sudoku.getCell(0, 1).getValue()); // 5
    candidates3.add(sudoku.getCell(1, 0).getValue()); // 8
    candidates3.add(sudoku.getCell(2, 0).getValue()); // 4
    candidates3.add(sudoku.getCell(2, 1).getValue()); // 7

    Command rcc4 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(candidates3).build();
    assertNull(rcc4.getFrozenString());
    rcc4.execute(sudoku);
    assertTrue(rcc4.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals(2, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertFalse(cell.isFixed());

    // Entferne einen bereits entfernen Kandidat
    Command rcc6 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(1).build();
    assertNull(rcc6.getFrozenString());
    rcc6.execute(sudoku);
    assertFalse(rcc6.isSuccessfully());
    assertTrue(cell.isValid());

    // Entferne mehrere bereits entfernen Kandidaten
    Candidates<Literal> candidates4 = new Candidates<Literal>();
    candidates4.add(Literal.getInstance(1));
    candidates4.add(Literal.getInstance(3));
    Command rcc7 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(candidates4).build();
    assertNull(rcc7.getFrozenString());
    rcc7.execute(sudoku);
    assertFalse(rcc7.isSuccessfully());
    assertTrue(cell.isValid());

    // Entferne einen weiteren beliebigen Kandidaten und erwarte automatisches Setzen des verbleibenden Kandidaten
    Command rcc5 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(2).build();
    assertNull(rcc5.getFrozenString());
    rcc5.execute(sudoku);
    assertTrue(rcc5.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals(0, cell.getCandidates().size());
    assertTrue(cell.isFixed());
  }

  public void testRemoveDuplicate() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command rcc1 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(1).build();
    rcc1.execute(sudoku);
    assertTrue(rcc1.isSuccessfully());

    Command rcc2 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(1).build();
    rcc2.execute(sudoku);
    assertFalse(rcc2.isSuccessfully());

    Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(Literal.getInstance(8));
    candidates1.add(Literal.getInstance(9));

    Command rcc3 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(candidates1).build();
    rcc3.execute(sudoku);
    assertTrue(rcc3.isSuccessfully());

    Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(Literal.getInstance(8));
    candidates2.add(Literal.getInstance(9));

    Command rcc4 = new RemoveCandidatesCommandBuilder(null, cell).addCandidate(candidates2).build();
    rcc4.execute(sudoku);
    assertFalse(rcc4.isSuccessfully());
  }

  public void testEquals() {
    final Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(Literal.getInstance(1));
    candidates1.add(Literal.getInstance(2));
    Command rcc1 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, new Cell(7, 6, Literal.EMPTY,
        SudokuSize.DEFAULT)).addCandidate(candidates1).build();
    final Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(Literal.getInstance(2));
    candidates2.add(Literal.getInstance(1));
    Command rcc2 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, new Cell(7, 6, Literal.EMPTY,
        SudokuSize.DEFAULT)).addCandidate(candidates1).build();
    assertEquals("Sollen gleich sein.", rcc1, rcc2);
    assertNotSame("Sind nicht dasselbe.", rcc1, rcc2);
  }

}
