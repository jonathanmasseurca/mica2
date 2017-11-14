/*
 * Copyright (c) 2017 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.web.model;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.obiba.mica.dataset.domain.Dataset;
import org.obiba.mica.dataset.domain.DatasetVariable;
import org.obiba.mica.network.domain.Network;
import org.obiba.mica.study.domain.BaseStudy;
import org.obiba.mica.study.domain.Study;
import org.springframework.stereotype.Component;

import static org.obiba.mica.web.model.Mica.DocumentDigestDto;

@Component
@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
public class DocumentDigestDtos {

  @Inject
  private LocalizedStringDtos localizedStringDtos;

  @NotNull
  public DocumentDigestDto.Builder asDtoBuilder(@NotNull Dataset dataset) {
    return DocumentDigestDto.newBuilder().setId(dataset.getId()) //
        .addAllName(localizedStringDtos.asDto(dataset.getName()));
  }

  @NotNull
  public DocumentDigestDto asDto(@NotNull Dataset dataset) {
    return asDtoBuilder(dataset).build();
  }

  @NotNull
  public DocumentDigestDto.Builder asDtoBuilder(@NotNull DatasetVariable variable) {
    DocumentDigestDto.Builder builder = DocumentDigestDto.newBuilder().setId(variable.getId());
    if (variable.getAttributes().hasAttribute("label", null))
      builder.addAllName(localizedStringDtos.asDto(variable.getAttributes().getAttribute("label", null).getValues()));
    return builder;
  }

  @NotNull
  public DocumentDigestDto asDto(@NotNull DatasetVariable variable) {
    return asDtoBuilder(variable).build();
  }

  @NotNull
  public DocumentDigestDto.Builder asDtoBuilder(@NotNull BaseStudy study) {
    return DocumentDigestDto.newBuilder().setId(study.getId()) //
        .addAllName(localizedStringDtos.asDto(study.getName()));
  }

  @NotNull
  public DocumentDigestDto asDto(@NotNull BaseStudy study) {
    return asDtoBuilder(study).build();
  }

  @NotNull
  public DocumentDigestDto.Builder asDtoBuilder(@NotNull Network network) {
    DocumentDigestDto.Builder builder = DocumentDigestDto.newBuilder().setId(network.getId()) //
        .addAllName(localizedStringDtos.asDto(network.getName()));

    List<String> studyIds = network.getStudyIds();
    if (studyIds.size() > 0) {
      builder.setExtension(Mica.NetworkDigestDto.studies,
          Mica.NetworkDigestDto.newBuilder().addAllIds(studyIds).build());
    }

    return builder;
  }

  @NotNull
  public DocumentDigestDto asDto(@NotNull Network network) {
    return asDtoBuilder(network).build();
  }
}