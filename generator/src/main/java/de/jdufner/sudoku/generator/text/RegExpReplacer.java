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
package de.jdufner.sudoku.generator.text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public final class RegExpReplacer {

  private static final Logger LOG = Logger.getLogger(RegExpReplacer.class);

  private RegExpReplacer() {
  }

  public static void replace(String newFileName, String name, String sudokusAsJavascript) throws IOException {
    String fileContent = readStringFromRessources();
    fileContent = replaceGroup("(<title>)(.*?)(</title>)", fileContent, name);
    fileContent = replaceGroup("(<h1>)(.*?)(</h1>)", fileContent, name);
    fileContent = replaceGroup("(var sudokus = \\[)(.*?)(\\];)", fileContent, sudokusAsJavascript);
    LOG.debug(fileContent);
    writeStringToFile(newFileName, fileContent);
  }

  private static String replaceGroup(String regExp, String inputSequence, String replacement) {
    final Pattern pattern = Pattern.compile(regExp, Pattern.DOTALL);
    final Matcher matcher = pattern.matcher(inputSequence);
    if (matcher.find()) {
      return matcher.replaceFirst(matcher.group(1) + replacement + matcher.group(3));
    }
    throw new IllegalStateException(
        "Suchen und Ersetzen mittels eines regulären Ausdrucks konnte nicht ausgeführt werden.");
  }

  private static String readStringFromRessources() throws IOException {
    final int bufferSize = 2 << 11; // ~ 2 ^ (11 + 1) = 4096
    ClassLoader.getSystemResourceAsStream("sudoku.html");
    InputStreamReader isr = new InputStreamReader(ClassLoader.getSystemResourceAsStream("sudoku.html"));
    try {
      int chars = 0;
      StringBuilder sb = new StringBuilder();
      do {
        char[] buffer = new char[bufferSize];
        chars = isr.read(buffer);
        if (chars == bufferSize) {
          sb.append(buffer);
        } else {
          for (int i = 0; i < chars; i++) {
            sb.append(buffer[i]);
          }
        }
      } while (chars > 0);
      return sb.toString();
    } finally {
      isr.close();
    }
  }

  private static void writeStringToFile(String fileName, String fileContent) throws FileNotFoundException {
    PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
    try {
      pw.print(fileContent);
    } finally {
      pw.close();
    }
  }
}
