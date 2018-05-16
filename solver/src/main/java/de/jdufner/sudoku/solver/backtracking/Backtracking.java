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


package de.jdufner.sudoku.solver.backtracking;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Backtracking {

  private static final Logger LOG = Logger.getLogger(Backtracking.class);
  private static final int MAX_SOLUTION_COUNTER = 10;

  /**
   * Die vorige Instanz aus dem Stapel von {@link Backtracking}, wie das in Backtracking üblich ist, wird ein Stapel von
   * Backtracking-Instanzen aufgebaut.
   */
  private transient Backtracking previousInstance = null;

  /**
   * Das Backtracking wird solange durchgeführt, bis die Grenze erreicht ist. Ist der Wert kleiner oder gleich 0, werden
   * alle Lösungen gesucht. Ist der Wert 1 wird nur die erste Lösung gesucht, etc.
   */
  private int solutionLimit = 1;

  /**
   * Werden im Rahmen des Backtrackings mehrere Lösungen gefunden, werden diese in der untersten Instanz im Stapel
   * gespeichert.
   */
  private transient List<Grid> solutions = null;

  /**
   * Das {@link Grid} der aktuellen Instanz, also auf der obersten Instanz auf dem Stapel.
   */
  private final transient Grid sudoku;

  /**
   * Die Höhe des Stapels.
   */
  private final transient int stackSize;

  private transient Grid solution = null;

  private transient int solutionCounter = 0;

  private transient int startPosition = 0;

  public Backtracking(final Grid sudoku, final int stackSize) {
    assert stackSize <= sudoku.getSize().getTotalSize() : "stackSize is " + stackSize
        + ", but must be smaller or equals " + sudoku.getSize().getTotalSize();
    this.sudoku = sudoku;
    this.sudoku.resetAndClearCandidatesOfNonFixed();
    this.stackSize = stackSize;
    if (!sudoku.isValid()) {
      throw new IllegalStateException("Das übergebene Sudoku ist in keinem gültigen Zustand!");
    }
  }

  public Backtracking(final Backtracking previousInstance, final Grid sudoku, final int stackSize,
      final int solutionCounter, final int startPosition) {
    assert stackSize <= sudoku.getSize().getTotalSize() : "stackSize is " + stackSize
        + ", but must be smaller or equals " + sudoku.getSize().getTotalSize();
    this.previousInstance = previousInstance;
    this.sudoku = sudoku;
    this.stackSize = stackSize;
    this.solutionCounter = solutionCounter;
    this.startPosition = startPosition;
  }

  public int countSolutions() {
    setSolutionLimit(Integer.MAX_VALUE);
    findSolutions2();
    assert solutionCounter == getSolutions().size() : "Der Zähler muss genau soviele Lösungen haben, wie gefunden wurden.";
    return solutionCounter;
  }

  public boolean isUnique() {
    setSolutionLimit(2);
    findSolutions2();
    assert solutionCounter == getSolutions().size() : "Der Zähler muss genau soviele Lösungen haben, wie gefunden wurden.";
    return solutionCounter == 1;
  }

  /**
   * @return First (found) solution via backtracking algorithm, <code>null</code> if no solution exists.
   * @throws CloneNotSupportedException
   */
  public Grid firstSolution() {
    setSolutionLimit(1);
    final Grid mySolution = findSolutions2();
    LOG.debug("mySolution" + mySolution);
    LOG.debug("solutionCounter" + solutionCounter);
    LOG.debug("getSolutions()" + getSolutions());
    assert solutionCounter == getSolutions().size() : "Der Zähler muss genau soviele Lösungen haben, wie gefunden wurden.";
    return mySolution;
  }

  // TODO Dokumentieren und testen.
  public List<Grid> firstSolutions(final int maxNumberOfSolutions) {
    setSolutionLimit(maxNumberOfSolutions);
    findSolutions2();
    return getSolutions();
  }

  private Grid findSolutions2() {
    assert sudoku.isValid() : "Sudoku ist ungültig!";
    // assert !sudoku.isSolved() : "Sudoku ist bereits gelöst!";
    if (LOG.isDebugEnabled()) {
      LOG.debug("Stacksize=" + stackSize);
    }

    // Wenn das Sudoku gelöst ist, dann nicht weitermachen
    if (sudoku.isSolved()) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Wird diese Stelle '1' in Praxis verwendet?");
      }
      // Eine Kopie der Lösung anlegen und zurückgeben
      // Muss die Lösung selbst zurück in den Pool?
      Grid result = new Grid(sudoku);
      // Zur Konsistenz auch in Solutions eintragen und Counter erhöhen
      increaseSolutionCounter();
      addSolutions(result);
      return result;
    }

    // Finde Zelle, über deren Kandidaten iteriert wird
    final Cell cell = getFirstNonFixed(startPosition);
    // assert !cell.isFixed() : "Die Zelle darf nicht besetzt sein!";
    if (cell == null) {
      // Es ist keine weitere Zelle zu besetzen
      if (sudoku.isSolved()) {
        // Entweder ist das Sudoku gelöst
        if (LOG.isInfoEnabled()) {
          LOG.info("Wird diese Stelle '2' in Praxis verwendet?");
        }
        // Eine Kopie der Lösung anlegen und zurückgeben
        // Muss die Lösung selbst zurück in den Pool?
        return new Grid(sudoku);
      } else {
        // Oder alle Zellen sind besetzt und das Sudoku ist ungültig
        LOG.equals("Alle Zellen sind besetzt, aber das Sudoku ist nicht gelöst.");
        throw new IllegalStateException("Alle Zellen sind besetzt, aber das Sudoku ist nicht gelöst.");
      }
    } else {
      // Diese Zelle muss einen Wert aus den Kandidaten haben
      for (Literal candidate : cell.getCandidates()) {
        Grid nextSudoku = null;
        Cell nextCell = null;
        nextSudoku = new Grid(sudoku);
        nextCell = nextSudoku.getCell(cell.getNumber());
        nextCell.setValue(candidate);
        if (removeCandidate(nextSudoku, nextCell, candidate) && nextSudoku.isValid()) {
          // Zelle gesetzt
          // Sudoku gelöst
          if (nextSudoku.isSolvedByCheckSum()) {
            solution = new Grid(nextSudoku);
            if (LOG.isDebugEnabled()) {
              LOG.debug("Found a solution #" + solutionCounter);
              LOG.debug(nextSudoku.toShortString());
            }
            increaseSolutionCounter();
            addSolutions(solution);
            if (getSolutionCounter() >= getSolutionLimit()) {
              return solution;
            }
          } else {
            if (nextSudoku.isValid()) {
              LOG.debug("Valid!");
            } else {
              LOG.debug("Not Valid!");
            }
            final Backtracking backtracking = new Backtracking(this, nextSudoku, stackSize + 1, solutionCounter,
                nextCell.getNumber());
            solution = backtracking.findSolutions2();
            if (solution == null) {
              LOG.debug("Totes Ende.");
              // teste nächsten Kandidat
            } else {
              if (getSolutionCounter() >= getSolutionLimit()) {
                assert solution.isValid() : "Sudoku muss gültig sein!";
                assert solution.isSolved() : "Sudoku muss gelöst sein!";
                assert solution.isSolvedByCheckSum() : "Sudoku muss gelöst sein!";
                LOG.debug("Eine Lösung gefunden.");
                return solution;
              }
            }
          }
        } else {
          // nextSudoku verwerfen, Instanz zurück in Pool legen
          LOG.debug(nextSudoku.isValid());
        }
      }
    }
    return solution;
  }

  /**
   * Entfernt den Kandidat in allen unbesetzten Zellen desselben Blocks, Zeile und Spalte. Wenn der Kandidat erfolgreich
   * entfernt werden konnte, dann gibt die Methode <code>true</code> zurück, sonst <code>false</code>.
   * 
   * @param cell
   *          Die Zelle, die besetzt wurde.
   * @param candidate
   *          Der Kandidat, der entfernt werden soll.
   * @return <code>true</code>, wenn der Kandidat erfolgreich entfernt werden konnte und das Sudoku weiterhin gültig
   *         ist, sonst <code>false</code>.
   */
  private boolean removeCandidate(final Grid sudoku, final Cell cell, final Literal candidate) {
    return removeCandidateSerial(sudoku, cell, candidate);
  }

  private boolean removeCandidateSerial(final Grid sudoku, final Cell cell, final Literal candidate) {
    return removeCandidateAndTestValidityInUnit(sudoku.getBlock(cell.getBlockIndex()), candidate)
        && removeCandidateAndTestValidityInUnit(sudoku.getColumn(cell.getColumnIndex()), candidate)
        && removeCandidateAndTestValidityInUnit(sudoku.getRow(cell.getRowIndex()), candidate);
  }

  /**
   * Entfernt den Kandidat in allen unbesetzten Zellen der Einheit und gibt <code>true</code> zurück, wenn alle Zellen
   * noch gültig sind, sonst <code>false</code>.
   * 
   * @param unit
   *          Die Einheit, dessen Zellen vom Kandidat entfernt werden soll und dessen Gültigkeit geprüft wird.
   * @param candidate
   *          Der Kandidat, der entfernt werden soll.
   * @return <code>true</code>, wenn nach dem Entfernen des Kandidaten die Einheit noch gültig ist, sonst
   *         <code>false</code>.
   */
  private boolean removeCandidateAndTestValidityInUnit(final House unit, final Literal candidate) {
    for (Cell cell : unit.getCells()) {
      if (!cell.isFixed()) {
        cell.getCandidates().remove(candidate);
        if (cell.getCandidates().size() <= 0) {
          return false;
        }
      }
    }
    return unit.isValid();
  }

  /**
   * @param number
   *          Nummer der Zelle mit 0 beginnend.
   * @return Die erste nicht besetzte Zelle ab der (einschließlich) angegebenen Nummer.
   */
  private Cell getFirstNonFixed(final int number) {
    assert number >= 0 : "Zellennummer muss größer oder gleich 0 sein.";
    assert number < sudoku.getSize().getTotalSize() : "Zellennummer muss kleiner als "
        + sudoku.getSize().getTotalSize() + " sein.";
    for (int i = number; i < sudoku.getSize().getTotalSize(); i++) {
      if (!sudoku.getCell(i).isFixed()) {
        return sudoku.getCell(i);
      }
    }
    return null;
  }

  public void increaseSolutionCounter() {
    if (previousInstance == null) {
      solutionCounter++;
      return;
    }
    previousInstance.increaseSolutionCounter();
  }

  public int getSolutionCounter() {
    if (previousInstance == null) {
      return solutionCounter;
    }
    return previousInstance.getSolutionCounter();
  }

  public int getSolutionLimit() {
    if (previousInstance == null) {
      return solutionLimit;
    }
    return previousInstance.getSolutionLimit();
  }

  public void setSolutionLimit(final int solutionLimit) {
    int mySolutionLimit = solutionLimit;
    if (previousInstance == null) {
      if (mySolutionLimit <= 0) {
        mySolutionLimit = MAX_SOLUTION_COUNTER;
      }
      this.solutionLimit = mySolutionLimit;
      return;
    }
    previousInstance.setSolutionLimit(mySolutionLimit);
  }

  public List<Grid> getSolutions() {
    if (previousInstance == null) {
      return solutions;
    }
    return previousInstance.getSolutions();
  }

  public void addSolutions(final Grid mySolution) {
    if (previousInstance == null) {
      if (solutions == null) {
        solutions = new ArrayList<Grid>();
      }
      solutions.add(mySolution);
      return;
    }
    previousInstance.addSolutions(mySolution);
  }

}
