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


package de.jdufner.sudoku.dao;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Das ist die persistente Repräsentation eines Sudokus. Diese Klasse enthält das {@link Grid} als {@link String},
 * welches mittels {@link SudokuFactory} erzeugt werden muss. Aus Gründen der Übersichtlichkeit ist die Persistenz von
 * der Klasse {@link Grid} getrennt.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
@Entity
@Table(name = "SUDOKUS")
public final class SudokuData {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "SDKS_ID")
  private int id;
  @Column(name = "SDKS_SUDOKU_STRING", length = 512, nullable = false, unique = true)
  private String sudokuAsString;
  @Column(name = "SDKS_LEVEL", nullable = false)
  private int level;
  @Column(name = "SDKS_FIXED", nullable = false)
  private int fixed;
  @Column(name = "SDKS_SIZE", nullable = false)
  private int size;
  @Column(name = "SDKS_GENERATED_AT", nullable = false)
  private Date generatedAt = new Date();
  @Column(name = "SDKS_PRINTED_AT", nullable = true)
  private Date printedAt;
  @Column(name = "SDKS_X_SDK", nullable = true)
  private String xSudoku = "N";
  @Column(name = "SDKS_STRGY_SIMPLE", nullable = true)
  private int strategySimple;
  @Column(name = "SDKS_STRGY_HIDDEN_SINGLE", nullable = true)
  private int strategyHiddenSingle;
  @Column(name = "SDKS_STRGY_NAKED_PAIR", nullable = true)
  private int strategyNakedPair;
  @Column(name = "SDKS_STRGY_NAKED_TRIPLE", nullable = true)
  private int strategyNakedTriple;
  @Column(name = "SDKS_STRGY_NAKED_QUAD", nullable = true)
  private int strategyNakedQuad;
  @Column(name = "SDKS_STRGY_HIDDEN_PAIR", nullable = true)
  private int strategyHiddenPair;
  @Column(name = "SDKS_STRGY_HIDDEN_TRIPLE", nullable = true)
  private int strategyHiddenTriple;
  @Column(name = "SDKS_STRGY_HIDDEN_QUAD", nullable = true)
  private int strategyHiddenQuad;
  @Column(name = "SDKS_STRGY_INTERSECTION_REMOVAL", nullable = true)
  private int strategyIntersectionRemoval;
  @Column(name = "SDKS_STRGY_YWING", nullable = true)
  private int strategyYwing;
  @Column(name = "SDKS_STRGY_XWING", nullable = true)
  private int strategyXwing;
  @Column(name = "SDKS_STRGY_SWORDFISH", nullable = true)
  private int strategySwordfish;
  @Column(name = "SDKS_STRGY_JELLYFISH", nullable = true)
  private int strategyJellyfish;
  @Column(name = "SDKS_STRGY_BACKTRACKING", nullable = true)
  private int strategyBacktracking;
  @Column(name = "SDKS_DAT_ERST", nullable = false)
  private Date created;
  @Column(name = "SDKS_DAT_AEND", nullable = false)
  private Date modified;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSudokuAsString() {
    return sudokuAsString;
  }

  public void setSudokuAsString(String sudokuAsString) {
    this.sudokuAsString = sudokuAsString;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getFixed() {
    return fixed;
  }

  public void setFixed(int fixed) {
    this.fixed = fixed;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Date getGeneratedAt() {
    return generatedAt;
  }

  public void setGeneratedAt(Date generatedAt) {
    this.generatedAt = generatedAt;
  }

  public Date getPrintedAt() {
    return printedAt;
  }

  public void setPrintedAt(Date printedAt) {
    this.printedAt = printedAt;
  }

  public String getxSudoku() {
    return xSudoku;
  }

  public void setxSudoku(String xSudoku) {
    this.xSudoku = xSudoku;
  }

  public int getStrategySimple() {
    return strategySimple;
  }

  public void setStrategySimple(int strategySimple) {
    this.strategySimple = strategySimple;
  }

  public int getStrategyHiddenSingle() {
    return strategyHiddenSingle;
  }

  public void setStrategyHiddenSingle(int strategyHiddenSingle) {
    this.strategyHiddenSingle = strategyHiddenSingle;
  }

  public int getStrategyNakedPair() {
    return strategyNakedPair;
  }

  public void setStrategyNakedPair(int strategyNakedPair) {
    this.strategyNakedPair = strategyNakedPair;
  }

  public int getStrategyNakedTriple() {
    return strategyNakedTriple;
  }

  public void setStrategyNakedTriple(int strategyNakedTriple) {
    this.strategyNakedTriple = strategyNakedTriple;
  }

  public int getStrategyNakedQuad() {
    return strategyNakedQuad;
  }

  public void setStrategyNakedQuad(int strategyNakedQuad) {
    this.strategyNakedQuad = strategyNakedQuad;
  }

  public int getStrategyHiddenPair() {
    return strategyHiddenPair;
  }

  public void setStrategyHiddenPair(int strategyHiddenPair) {
    this.strategyHiddenPair = strategyHiddenPair;
  }

  public int getStrategyHiddenTriple() {
    return strategyHiddenTriple;
  }

  public void setStrategyHiddenTriple(int strategyHiddenTriple) {
    this.strategyHiddenTriple = strategyHiddenTriple;
  }

  public int getStrategyHiddenQuad() {
    return strategyHiddenQuad;
  }

  public void setStrategyHiddenQuad(int strategyHiddenQuad) {
    this.strategyHiddenQuad = strategyHiddenQuad;
  }

  public int getStrategyIntersectionRemoval() {
    return strategyIntersectionRemoval;
  }

  public void setStrategyIntersectionRemoval(int strategyIntersectionRemoval) {
    this.strategyIntersectionRemoval = strategyIntersectionRemoval;
  }

  public int getStrategyXwing() {
    return strategyXwing;
  }

  public void setStrategyXwing(int strategyXwing) {
    this.strategyXwing = strategyXwing;
  }

  public int getStrategySwordfish() {
    return strategySwordfish;
  }

  public void setStrategySwordfish(int strategySwordfish) {
    this.strategySwordfish = strategySwordfish;
  }

  public int getStrategyYwing() {
    return strategyYwing;
  }

  public void setStrategyYwing(int strategyYwing) {
    this.strategyYwing = strategyYwing;
  }

  public int getStrategyJellyfish() {
    return strategyJellyfish;
  }

  public void setStrategyJellyfish(int strategyJellyfish) {
    this.strategyJellyfish = strategyJellyfish;
  }

  public int getStrategyBacktracking() {
    return strategyBacktracking;
  }

  public void setStrategyBacktracking(int strategyBacktracking) {
    this.strategyBacktracking = strategyBacktracking;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date erstellt) {
    this.created = erstellt;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date geaendert) {
    this.modified = geaendert;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj instanceof SudokuData) {
      SudokuData that = (SudokuData) obj;
      if (this.getId() == that.getId()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31).append(getId()).toHashCode();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
