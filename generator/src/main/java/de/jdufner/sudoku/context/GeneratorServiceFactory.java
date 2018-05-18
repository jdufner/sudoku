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


package de.jdufner.sudoku.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * 
 */
public enum GeneratorServiceFactory {

  INSTANCE;

  //  private static final Logger LOG = Logger.getLogger(GeneratorServiceFactory.class);

  private ApplicationContext applicationContext;

  private GeneratorServiceFactory() {
    applicationContext = new ClassPathXmlApplicationContext("generator-context.xml");
//    try {
//      Log4jConfigurer.initLogging(Log4jConfigurer.CLASSPATH_URL_PREFIX + "log4j.properties", 10000);
//    } catch (FileNotFoundException fnfe) {
//      throw new RuntimeException(fnfe); //NOPMD
//    }
  }

  public Properties getPdfStyle() {
    return (Properties) applicationContext.getBean("pdfStyle");
  }

  public Object getBean(Class<?> clazz) {
    String[] beanNames = applicationContext.getBeanNamesForType(clazz);
    if (beanNames.length == 0) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " nicht.");
    }
    if (beanNames.length > 1) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " mehrfach und ist nicht eindeutig.");
    }
    return applicationContext.getBean(beanNames[0]);
  }

}
