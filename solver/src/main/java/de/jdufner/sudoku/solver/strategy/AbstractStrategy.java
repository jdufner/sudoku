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
package de.jdufner.sudoku.solver.strategy;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.service.StrategySolver;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Diese Klasse stellt die allgemeine Funktion für eine Lösungsstrategie eines Sudokus zur Verfügung.
 * 
 * Diese Klasse gehört nicht zur Schnittstelle des Pakets. Der Zugriff erfolgt über den {@link StrategySolver}.
 * 
 * TODO Trotzdem muss ein Interface Strategy eingeführt werden, damit klar wird welche Methoden von außen aufgerufen
 * werden und welche für interne Zwecke da sind.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractStrategy implements Strategy {

  private static final Logger LOG = Logger.getLogger(AbstractStrategy.class);

  private final transient Grid sudoku;
  private transient Collection<Command> commands = new HashSet<Command>();

  /**
   * Erzeugt eine konkrete Strategie.
   * 
   * @param sudoku
   */
  protected AbstractStrategy(final Grid sudoku) {
    this.sudoku = sudoku;
    assert sudoku.isValid() : "Das übergebene Sudoku ist in keinem gültigen Zustand!";
  }

  protected Grid getSudoku() {
    return sudoku;
  }

  protected Collection<Command> getCommands() {
    return commands;
  }

  protected void setCommands(final Collection<Command> commands) {
    this.commands = commands;
  }

  /*
   * @see de.jdufner.sudoku.solver.strategy.Strategy#getLevel()
   */
  public abstract Level getLevel();

  /**
   * Diese Methode wird von die Subklasse für die tatsächliche Strategie implemeniert.
   * 
   * @return Den Namen der Strategie.
   */
  public abstract StrategyNameEnum getStrategyName();

  /**
   * Diese Methode wird von die Subklasse für die tatsächliche Strategie implemeniert.
   * 
   * @return
   */
  protected abstract Collection<Command> executeStrategy();

  /**
   * Führt diese Strategie aus und führt einige zusätzliche Dinge durch.
   * 
   * Diese Methode darf nicht überschrieben werden. Hier werden einige Dinge für alle Strategien implementiert, wie
   * bspw. Zeitmessung.
   * 
   * @return Das Ausführungsergebnis.
   */
  public final StrategyResult execute() {
    final StrategyResult strategyResult = new StrategyResult(getStrategyName(), getLevel());
    if (LOG.isInfoEnabled()) {
      LOG.info("Führe " + getStrategyName() + " (" + getClass().getSimpleName() + ") aus.");
    }
    //strategyResult.storeStateBefore(getSudoku());
    strategyResult.start();
    setCommands(executeStrategy());
    strategyResult.stop();
    //strategyResult.storeStateAfter(getSudoku());
    strategyResult.setCommands(getCommands());
    strategyResult.setSudokuUnique(isSudokuUnique());
    if (LOG.isInfoEnabled()) {
      LOG.info(getClass().getSimpleName() + " hat " + getCommands().size() + " Commands erzeugt.");
    }
    if (LOG.isDebugEnabled()) {
      LOG.info("Ausführungsdauer von " + getClass().getSimpleName() + " war " + strategyResult.getDurationInMillis()
          + " ms.");
    }
    return strategyResult;
  }

  /**
   * Liefert den Standardwert <code>true</code> zurück für alle Strategien. Nur in der Backtracking-Strategie existiert
   * eine echte Implementierung.
   * 
   * @return <code>true</code> als Standardwert.
   */
  @Override
  public boolean isSudokuUnique() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Verwende Standardwert.");
    }
    return true;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
