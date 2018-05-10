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