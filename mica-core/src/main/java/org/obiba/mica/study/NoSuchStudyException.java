/*
 * Copyright (c) 2016 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.study;

import java.util.NoSuchElementException;

public class NoSuchStudyException extends NoSuchElementException {

  private static final long serialVersionUID = 5931123739037832740L;

  private NoSuchStudyException(String s) {
    super(s);
  }

  public static NoSuchStudyException withId(String id) {
    return new NoSuchStudyException("Study with id '" + id + "' does not exist");
  }

}
