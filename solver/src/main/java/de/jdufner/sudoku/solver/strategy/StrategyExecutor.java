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

// $Id$
package de.jdufner.sudoku.solver.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.exceptions.SudokuRuntimeException;
import de.jdufner.sudoku.solver.strategy.backtracking.BacktrackingStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyConfiguration;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyThreadingEnum;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenPairParallelStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenPairSerialStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenQuadParallelStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenQuadSerialStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenSingleStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenTripleParallelStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenTripleSerialStrategy;
import de.jdufner.sudoku.solver.strategy.intersection.removal.IntersectionRemovalParallelStrategy;
import de.jdufner.sudoku.solver.strategy.intersection.removal.IntersectionRemovalSerialStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedPairParallelStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedPairSerialStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedQuadParallelStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedQuadSerialStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedTripleParallelStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedTripleSerialStrategy;
import de.jdufner.sudoku.solver.strategy.simple.SimpleParallelStrategy;
import de.jdufner.sudoku.solver.strategy.swordfish.SwordFishParallelStrategy;
import de.jdufner.sudoku.solver.strategy.xwing.XWingParallelStrategy;
import de.jdufner.sudoku.solver.strategy.xwing.XWingSerialStrategy;
import de.jdufner.sudoku.solver.strategy.ywing.YWingSerialStrategy;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class StrategyExecutor {

  private static final Logger LOG = Logger.getLogger(StrategyExecutor.class);

  final private transient Grid sudoku;
  final private transient StrategyConfiguration configuration;

  final private transient List<StrategyResult> result = new ArrayList<StrategyResult>();

  public StrategyExecutor(final Grid sudoku, final StrategyConfiguration configuration) {
    this.sudoku = sudoku;
    this.configuration = configuration;
    // Die folgende Prüfung ist so wichtig, dass ein assert nicht ausreicht.
    if (!sudoku.isValid()) {
      throw new IllegalStateException("Das übergebene Sudoku ist in keinem gültigen Zustand!");
    }
  }

  public List<StrategyResult> execute() {
    int zaehler = 0;
    boolean doExecute = true;
    while (doExecute) {
      zaehler += 1;
      LOG.info("Starte Iteration " + zaehler);
      LOG.info("Sudoku " + getSudoku() //
          + " (" + getSudoku().getNumberOfFixed() + " Zellen gesetzt, " //
          + getSudoku().getNumberOfCandidates() + " Kandidaten )");
      //LOG.info("Sudoku vor Iteration " + zaehler + ": " + getSudoku());
      final StrategyResult strategyResult = executeStrategy();
      if (strategyResult == null) {
        LOG.info("Keinen weiteren Lösungschrift gefunden. Sudoku nicht gelöst!");
        doExecute = false;
      } else {
        result.add(strategyResult);
      }
      if (getSudoku().isSolved()) {
        if (getSudoku().isValid() && getSudoku().isSolvedByCheckSum()) {
          LOG.debug("Sudoku gelöst! " + getSudoku());
          doExecute = false;
        } else {
          LOG.warn("Sudoku scheint gelöst zu sein, aber irgendwas ist schief gelaufen.");
        }
      } else {
        LOG.debug("Sudoku noch nicht gelöst, weitermachen.");
      }
    }
    return result;
  }

  private StrategyResult executeStrategy() {
    for (Class<? extends AbstractStrategy> strategyClass : getStrategies()) {
      final Strategy strategy = instantiateStrategy(strategyClass, getSudoku());
      final StrategyResult strategyResult = strategy.execute();
      if (LOG.isDebugEnabled()) {
        LOG.debug("Sudoku " + getSudoku());
        if (getSudoku().isValid()) {
          LOG.debug("Sudoku ist gültig.");
        } else {
          LOG.debug("Sudoku ist nicht gültig!");
        }
        if (getSudoku().isSolvedByCheckSum()) {
          LOG.debug("Sudoku ist gelöst und durch Prüfsummen bestätigt.");
        } else {
          LOG.debug("Sudoku ist nicht gelöst.");
        }
      }
      if (strategyResult.getCommands().size() > 0) {
        strategyResult.storeStateBefore(getSudoku());
        executeCommands(strategyResult.getCommands());
        strategyResult.storeStateAfter(getSudoku());
        if (strategyResult.getNumberEleminatedCandidates() > 0) {
          if (LOG.isInfoEnabled()) {
            LOG.info(strategy + " entfernte " + strategyResult.getNumberEleminatedCandidates()
                + " Kandidaten und setzte " + strategyResult.getNumberNewlyFixedCells() + " Zellen.");
          }
          return strategyResult;
        } else {
          if (LOG.isInfoEnabled()) {
            LOG.info(strategy + " hat keinen Kandidat entfernt und keine Zelle gesetzt");
          }
        }
      }
    }
    LOG.debug("Kein Kandidat entfernt!");
    return null;
  }

  /**
   * TODO Performancetest: Ist es sinnvoll den alten Befehl oder den umfassenderen Befehl zu suchen oder ist es billiger
   * das Kommando auszuführen, auch wenn es nutzlos ist.
   * 
   * @param commands
   */
  private void executeCommands(final Collection<Command> commands) {
    for (Command command : commands) {
      command.execute(getSudoku());
    }
  }

  /**
   * Erzeugt eine Instanz einer Strategie ohne eine deklarierte Exception zu werfen, sondern nur eine nicht-deklarierte
   * Exception.
   * 
   * @param clazz
   * @param sudoku
   * @return
   */
  private Strategy instantiateStrategy(final Class<? extends AbstractStrategy> clazz, final Grid sudoku) {
    try {
      return instantiateStrategyThrowingExceptions(clazz, sudoku);
    } catch (Exception e) {
      throw new SudokuRuntimeException(e);
    }
  }

  /**
   * Erzeugt eine Instanz einer Strategie.
   * 
   * @param strategyClass
   * @param sudoku
   * @return
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws IllegalArgumentException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  private AbstractStrategy instantiateStrategyThrowingExceptions(final Class<? extends AbstractStrategy> strategyClass,
      final Grid sudoku) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    final Constructor<? extends AbstractStrategy> strategyConstructor = strategyClass
        .getConstructor(new Class[] { Grid.class });
    final AbstractStrategy strategy = strategyConstructor.newInstance(new Object[] { sudoku });
    if (LOG.isDebugEnabled()) {
      LOG.debug("Strategie " + strategy + " erfolgreicht instanziiert.");
    }
    return strategy;
  }

  public StrategyConfiguration getConfiguration() {
    return configuration;
  }

  public Grid getSudoku() {
    return sudoku;
  }

  private List<Class<? extends AbstractStrategy>> getStrategies() { // NOPMD by Jürgen on 24.11.09 22:10
    final List<Class<? extends AbstractStrategy>> strategies = new ArrayList<Class<? extends AbstractStrategy>>();
    if (getConfiguration().getThreading().equals(StrategyThreadingEnum.SERIAL)) {
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.SIMPLE)) {
        strategies.add(SimpleParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_SINGLE)) {
        strategies.add(HiddenSingleStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_PAIR)) {
        strategies.add(NakedPairSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_TRIPLE)) {
        strategies.add(NakedTripleSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_QUAD)) {
        strategies.add(NakedQuadSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_PAIR)) {
        strategies.add(HiddenPairSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_TRIPLE)) {
        strategies.add(HiddenTripleSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_QUAD)) {
        strategies.add(HiddenQuadSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.INTERSECTION_REMOVAL)) {
        strategies.add(IntersectionRemovalSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.YWING)) {
        strategies.add(YWingSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.XWING)) {
        strategies.add(XWingSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.SWORDFISH)) {
        strategies.add(SwordFishParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.BACKTRACKING)) {
        strategies.add(BacktrackingStrategy.class);
      }
    } else {
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.SIMPLE)) {
        strategies.add(SimpleParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_SINGLE)) {
        strategies.add(HiddenSingleStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_PAIR)) {
        strategies.add(NakedPairParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_TRIPLE)) {
        strategies.add(NakedTripleParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_QUAD)) {
        strategies.add(NakedQuadParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_PAIR)) {
        strategies.add(HiddenPairParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_TRIPLE)) {
        strategies.add(HiddenTripleParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_QUAD)) {
        strategies.add(HiddenQuadParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.INTERSECTION_REMOVAL)) {
        strategies.add(IntersectionRemovalParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.YWING)) {
        strategies.add(YWingSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.XWING)) {
        strategies.add(XWingParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.SWORDFISH)) {
        strategies.add(SwordFishParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.BACKTRACKING)) {
        strategies.add(BacktrackingStrategy.class);
      }
    }
    return strategies;
  }

}
