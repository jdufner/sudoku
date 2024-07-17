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


package de.jdufner.sudoku.generator.service;

import com.itextpdf.text.DocumentException;
import de.jdufner.sudoku.common.misc.Level;
import java.io.IOException;
import junit.framework.TestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-20
 * 
 */
public final class PdfGeneratorServiceTest extends TestCase {

  private PdfGeneratorService pdfGenerator;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
//    pdfGenerator = (PdfGeneratorService) GeneratorServiceFactory.INSTANCE.getBean(PdfGeneratorService.class);
  }

  public void _testGenerate() throws DocumentException, IOException {
    pdfGenerator.generate(new PdfGeneratorConfiguration.Builder().numberPerLevel(Level.SEHR_LEICHT, 2).build());
  }

  public void testDummy() {
    assertTrue(true);
  }

}
