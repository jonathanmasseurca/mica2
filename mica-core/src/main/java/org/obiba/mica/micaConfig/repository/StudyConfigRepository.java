/*
 * Copyright (c) 2016 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.micaConfig.repository;

import org.obiba.mica.micaConfig.domain.StudyConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudyConfigRepository extends MongoRepository<StudyConfig, String> {}
