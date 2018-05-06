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
