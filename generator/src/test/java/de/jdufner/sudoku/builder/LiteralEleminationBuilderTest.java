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

import java.util.Map;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.test.AbstractGeneratorTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class LiteralEleminationBuilderTest extends AbstractGeneratorTestCase {
  private static final Logger LOG = Logger.getLogger(LiteralEleminationBuilderTest.class);

  private Builder builder;
  private Grid sudoku;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    builder = (LiteralEleminationBuilder) GeneratorServiceFactory.INSTANCE.getBean(LiteralEleminationBuilder.class);
    builder.setSize(SudokuSize.DEFAULT);
  }

  public void testBuild() throws Exception {
    sudoku = builder.build();
    LOG.debug("Prüfe Gültigkeit:");
    assertTrue(sudoku.isValid());
    LOG.debug("Prüfe Eindeutigkeit:");
    assertTrue(getStrategySolverWithBacktracking().isUnique(sudoku));
    LOG.debug(sudoku);
  }

  public void testBuildSudokus() {
    Map<Level, Solution> map = builder.buildSudokus();
  }

}
