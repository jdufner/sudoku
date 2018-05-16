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


package de.jdufner.sudoku.file;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * TODO Singleton draus machen, nein besser in Spring integrieren
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * 
 */
public final class PropertiesLoader {
  private static final Logger LOG = Logger.getLogger(PropertiesLoader.class);
  private static final String RESOURCE_NAME = "sudokus.properties";

  private final transient Properties sudokus = new Properties();

  public PropertiesLoader() {
    try {
      sudokus.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE_NAME));
    } catch (IOException ioe) {
      LOG.fatal(ioe.getMessage());
    }
  }

  public String getSudokuAsString(final int sudokuId) {
    int myId = sudokuId;
    if (myId == 0) {
      myId = (int) System.currentTimeMillis();
    }
    if (myId < 0) {
      myId *= -1;
    }
    if (myId > sudokus.size()) {
      myId = myId % sudokus.size();
    }
    return sudokus.getProperty(String.valueOf(myId));
  }

}
