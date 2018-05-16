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
package de.jdufner.sudoku.generator.service;

import de.jdufner.sudoku.builder.LiteralEleminationBuilder;
import de.jdufner.sudoku.builder.RandomEleminationBuilder;
import de.jdufner.sudoku.builder.SymetricRandomEleminationBuilder;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.solver.service.Solution;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Erzeugt mindestens ein Sudoku und speichert es in der Datenbank ab.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuGeneratorService {

  private static final Logger LOG = Logger.getLogger(SudokuGeneratorService.class);
  private static final Logger SUDOKU = Logger.getLogger("sudoku");

  public void generate() {
    generateLiteralEleminationBuilder();
    generateRandomEleminationBuilder();
    generateSymetricRandomEleminationBuilder();
  }

  public void generateLiteralEleminationBuilder() {
    Map<Level, Solution> map = getLiteralEleminationBuilder().buildSudokus();
    for (Level l : map.keySet()) {
      if (map.get(l).getQuest().getNumberOfFixed() <= 30) {
//        sudokuDao.saveSolution(map.get(l));
        SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
      }
    }
  }

  public void generateRandomEleminationBuilder() {
    Map<Level, Solution> map = getRandomEleminationBuilder().buildSudokus();
    for (Level l : map.keySet()) {
//      sudokuDao.saveSolution(map.get(l));
      SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
    }
  }

  public void generateSymetricRandomEleminationBuilder() {
    Map<Level, Solution> map = getSymetricRandomEleminationBuilder().buildSudokus();
    for (Level l : map.keySet()) {
//      sudokuDao.saveSolution(map.get(l));
      SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
    }
  }

  private LiteralEleminationBuilder getLiteralEleminationBuilder() {
    return (LiteralEleminationBuilder) GeneratorServiceFactory.INSTANCE.getBean(LiteralEleminationBuilder.class);
  }

  private RandomEleminationBuilder getRandomEleminationBuilder() {
    return (RandomEleminationBuilder) GeneratorServiceFactory.INSTANCE.getBean(RandomEleminationBuilder.class);
  }

  private SymetricRandomEleminationBuilder getSymetricRandomEleminationBuilder() {
    return (SymetricRandomEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(SymetricRandomEleminationBuilder.class);
  }

}
