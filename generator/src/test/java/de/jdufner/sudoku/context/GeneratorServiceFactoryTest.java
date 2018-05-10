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
