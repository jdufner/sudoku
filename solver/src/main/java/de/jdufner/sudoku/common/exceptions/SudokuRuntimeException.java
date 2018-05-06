// $Id$
package de.jdufner.sudoku.common.exceptions;

public class SudokuRuntimeException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 8083222351578824111L;

  public SudokuRuntimeException() {
    super();
  }

  public SudokuRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public SudokuRuntimeException(final String message) {
    super(message);
  }

  public SudokuRuntimeException(final Throwable cause) {
    super(cause);
  }

}
