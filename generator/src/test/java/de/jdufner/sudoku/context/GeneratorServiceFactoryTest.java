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
package de.jdufner.sudoku.context;

import de.jdufner.sudoku.builder.AbstractBuilder;
import de.jdufner.sudoku.builder.Builder;
import de.jdufner.sudoku.builder.LiteralEleminationBuilder;
import de.jdufner.sudoku.builder.RandomEleminationBuilder;
import de.jdufner.sudoku.builder.SymetricRandomEleminationBuilder;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class GeneratorServiceFactoryTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(GeneratorServiceFactoryTest.class);

  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void _testGetLiteralEleminationBuilder() throws Exception {
    Builder builder = (LiteralEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(LiteralEleminationBuilder.class);
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void _testGetRandomEleminationBuilder() throws Exception {
    Builder builder = (RandomEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(RandomEleminationBuilder.class);
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void _testGetSymetricRandomEleminationBuilder() throws Exception {
    Builder builder = (SymetricRandomEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(SymetricRandomEleminationBuilder.class);
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void _testGetPdfStyle() {
    Object obj = GeneratorServiceFactory.INSTANCE.getPdfStyle();
    LOG.debug(obj);
  }

  public void testDummy() {
    assertTrue(true);
  }

}
