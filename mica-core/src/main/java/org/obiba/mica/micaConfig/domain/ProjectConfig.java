/*
 * Copyright (c) 2016 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.micaConfig.domain;

import com.google.common.collect.Maps;
import org.obiba.mica.core.domain.LocalizedString;

import java.util.Map;

public class ProjectConfig extends EntityConfig {

  private Map<String, LocalizedString> properties;

  public ProjectConfig() {
    setId(DEFAULT_ID);
  }

  public Map<String, LocalizedString> getProperties() {
    return properties == null ? properties = Maps.newHashMap() : properties;
  }

  public void setProperties(Map<String, LocalizedString> properties) {
    this.properties = properties;
  }

  @Override
  public String pathPrefix() {
    return "project-forms";
  }
}
