package org.obiba.mica.micaConfig.rest;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.obiba.mica.micaConfig.domain.HarmonizationPopulationConfig;
import org.obiba.mica.micaConfig.service.EntityConfigService;
import org.obiba.mica.micaConfig.service.HarmonizationPopulationConfigService;
import org.obiba.mica.web.model.Dtos;
import org.obiba.mica.web.model.Mica;
import org.springframework.stereotype.Component;

@Component
@Path("/config/" + HarmonizationPopulationConfigResource.TARGET_NAME)
public class HarmonizationPopulationConfigResource
  extends EntityConfigResource<HarmonizationPopulationConfig, Mica.EntityFormDto>
  implements PermissionAwareResource {

  static final String TARGET_NAME = "harmonization-population";

  @Inject
  HarmonizationPopulationConfigService harmonizationPopulationConfigService;

  @Inject
  Dtos dtos;

  @Override
  protected Mica.EntityFormDto asDto(HarmonizationPopulationConfig entityConfig) {
    return dtos.asDto(entityConfig);
  }

  @Override
  protected HarmonizationPopulationConfig fromDto(Mica.EntityFormDto entityConfig) {
    return dtos.fromDto(entityConfig);
  }

  @Override
  protected EntityConfigService<HarmonizationPopulationConfig> getConfigService() {
    return harmonizationPopulationConfigService;
  }

  @Override
  public String getTarget() {
    return TARGET_NAME;
  }
}
