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

// $Id$
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
