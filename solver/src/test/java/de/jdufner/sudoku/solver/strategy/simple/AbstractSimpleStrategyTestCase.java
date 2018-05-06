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
package de.jdufner.sudoku.solver.strategy.simple;

import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;

public abstract class AbstractSimpleStrategyTestCase extends AbstractStrategyTestCase {

  //private static final Logger LOG = Logger.getLogger(AbstractSimpleStrategyTestCase.class);

  protected String getSudokuAsString() {
    return "(1378),(13468),(348),(134578),(3457),2,(13456),(13456),9,(139),(13469),2,(1345),(3459),(149),7,(13456),8,(13789),(13489),5,(13478),(3479),6,(1234),(134),(12),6,(1589),(89),(24578),(24579),(4789),(12589),(1589),3,4,2,(89),(568),(569),3,(15689),(15689),7,(3589),(3589),7,(2568),1,(89),(25689),(5689),4,(23589),7,1,(2346),(2346),4,(345689),(345689),(56),(2359),(3459),(349),(123467),8,(147),(134569),(1345679),(156),(38),(348),6,9,(347),5,(1348),2,1";
  }

}
