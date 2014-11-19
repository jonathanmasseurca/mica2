/*
 * Copyright (c) 2014 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.dataset.rest.harmonization;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.obiba.magma.NoSuchValueTableException;
import org.obiba.magma.NoSuchVariableException;
import org.obiba.mica.dataset.DatasetVariableResource;
import org.obiba.mica.dataset.domain.HarmonizationDataset;
import org.obiba.mica.dataset.service.HarmonizationDatasetService;
import org.obiba.mica.web.model.Dtos;
import org.obiba.mica.web.model.Mica;
import org.obiba.opal.web.model.Math;
import org.obiba.opal.web.model.Search;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

@Component
@Scope("request")
@RequiresAuthentication
public class DraftDataschemaDatasetVariableResource implements DatasetVariableResource {

  private String datasetId;

  private String variableName;

  @Inject
  private HarmonizationDatasetService datasetService;

  @Inject
  private Dtos dtos;

  @GET
  public Mica.DatasetVariableDto getVariable() {
    return dtos.asDto(datasetService.getDatasetVariable(getDataset(), variableName));
  }

  @GET
  @Path("/summary")
  public List<Math.SummaryStatisticsDto> getVariableSummaries() {
    ImmutableList.Builder<Math.SummaryStatisticsDto> builder = ImmutableList.builder();
    HarmonizationDataset dataset = getDataset();
    dataset.getStudyTables().forEach(table -> {
      try {
        builder.add(datasetService
          .getVariableSummary(dataset, variableName, table.getStudyId(), table.getProject(), table.getTable()));
      } catch(NoSuchVariableException | NoSuchValueTableException e) {
        // ignore (case the study has not implemented this dataschema variable)
      }
    });
    return builder.build();
  }

  @GET
  @Path("/facet")
  public List<Search.QueryResultDto> getVariableFacets() {
    ImmutableList.Builder<Search.QueryResultDto> builder = ImmutableList.builder();
    HarmonizationDataset dataset = getDataset();
    dataset.getStudyTables().forEach(table -> {
      try {
        builder.add(datasetService
          .getVariableFacet(variableName, table));
      } catch(NoSuchVariableException | NoSuchValueTableException e) {
        // ignore (case the study has not implemented this dataschema variable)
      }
    });
    return builder.build();
  }

  @GET
  @Path("/harmonizations")
  public List<Mica.DatasetVariableDto> getHarmonizedVariables() {
    ImmutableList.Builder<Mica.DatasetVariableDto> builder = ImmutableList.builder();
    HarmonizationDataset dataset = getDataset();
    dataset.getStudyTables().forEach(table -> {
      try {
        builder.add(dtos.asDto(datasetService.getDatasetVariable(dataset, variableName, table)));
      } catch(NoSuchVariableException | NoSuchValueTableException e) {
        // ignore (case the study has not implemented this dataschema variable)
      }
    });
    return builder.build();
  }

  private HarmonizationDataset getDataset() {
    return datasetService.findById(datasetId);
  }

  @Override
  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  @Override
  public void setVariableName(String variableName) {
    this.variableName = variableName;
  }
}
