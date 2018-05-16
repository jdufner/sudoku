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


package de.jdufner.sudoku.generator.pdf;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-07
 * @version $Revision$
 */
public final class PdfConstants {

  /**
   * Farbe Linien: Schwarz
   */
  public static final int[] RAHMEN_FARBE = new int[] { 0x00, 0x00, 0x00 };
  /**
   * Keine Linie
   */
  public static final float RAHMEN_KEIN = 0f;
  /**
   * Dicke Linie
   */
  public static final float RAHMEN_DICK = 1.0f;
  /**
   * Dünner Linie
   */
  public static final float RAHMEN_DUENN = 0.5f;
  /**
   * Farbe Hintergrund: Hellgrau
   */
  public static final int[] HINTERGRUND_FARBE = new int[] { 0xCC, 0xCC, 0xCC };

}
