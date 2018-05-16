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
package de.jdufner.sudoku.solver.strategy.simple;

import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;

public abstract class AbstractSimpleStrategyTestCase extends AbstractStrategyTestCase {

  //private static final Logger LOG = Logger.getLogger(AbstractSimpleStrategyTestCase.class);

  protected String getSudokuAsString() {
    return "(1378),(13468),(348),(134578),(3457),2,(13456),(13456),9,(139),(13469),2,(1345),(3459),(149),7,(13456),8,(13789),(13489),5,(13478),(3479),6,(1234),(134),(12),6,(1589),(89),(24578),(24579),(4789),(12589),(1589),3,4,2,(89),(568),(569),3,(15689),(15689),7,(3589),(3589),7,(2568),1,(89),(25689),(5689),4,(23589),7,1,(2346),(2346),4,(345689),(345689),(56),(2359),(3459),(349),(123467),8,(147),(134569),(1345679),(156),(38),(348),6,9,(347),5,(1348),2,1";
  }

}
