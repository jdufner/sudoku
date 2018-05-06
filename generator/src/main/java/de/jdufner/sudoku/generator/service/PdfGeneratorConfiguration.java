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
package de.jdufner.sudoku.generator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-03-04
 * @version $Revision$
 * 
 */
public final class PdfGeneratorConfiguration {

  private List<Page> pages;
  private SudokuSize size;

  private PdfGeneratorConfiguration(Builder builder) {
    this.pages = new ArrayList<Page>();
    this.pages.addAll(builder.pages);
    Collections.sort(this.pages);
    this.size = builder.size;
  }

  public List<Page> getPages() {
    return pages;
  }

  public SudokuSize getSize() {
    return size;
  }

  public static class Page implements Comparable<Page> {
    private Level level;
    private int number;

    private Page(Level level, int number) {
      this.level = level;
      this.number = number;
    }

    public Level getLevel() {
      return level;
    }

    public int getNumber() {
      return number;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (obj instanceof Page) {
        Page that = (Page) obj;
        if (this.level == that.level) {
          return true;
        }
      }
      return false;
    }

    @Override
    public int hashCode() {
      return level.getValue();
    }

    @Override
    public int compareTo(Page that) {
      return this.level.getValue() - that.level.getValue();
    }

    @Override
    public String toString() {
      return this.level + "-" + number + " Seiten";
    }

  }

  public static class Builder {
    private SudokuSize size = SudokuSize.DEFAULT;
    private Set<Page> pages = new HashSet<Page>();

    public Builder() {
    }

    public Builder size(SudokuSize size) {
      this.size = size;
      return this;
    }

    public Builder numberPerLevel(Level level, int number) {
      this.pages.add(new Page(level, number));
      return this;
    }

    public PdfGeneratorConfiguration build() {
      if (pages.isEmpty()) {
        for (Level level : Level.values()) {
          if (level.getValue() > 0) {
            pages.add(new Page(level, 6));
          }
        }
      }
      return new PdfGeneratorConfiguration(this);
    }
  }

}
