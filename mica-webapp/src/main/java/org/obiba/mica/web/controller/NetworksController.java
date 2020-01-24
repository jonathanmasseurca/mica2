package org.obiba.mica.web.controller;

import org.obiba.mica.network.NoSuchNetworkException;
import org.obiba.mica.network.domain.Network;
import org.obiba.mica.network.service.PublishedNetworkService;
import org.obiba.mica.security.service.SubjectAclService;
import org.obiba.mica.study.service.PublishedStudyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class NetworksController extends EntityController {

  @Inject
  private SubjectAclService subjectAclService;

  @Inject
  private PublishedNetworkService publishedNetworkService;

  @Inject
  private PublishedStudyService publishedStudyService;

  @GetMapping("/networks")
  public ModelAndView list() {

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("networks", publishedNetworkService.findAll().stream()
      .filter(n -> subjectAclService.isAccessible("/network", n.getId()))
      .collect(Collectors.toList()));

    return new ModelAndView("networks", params);
  }

  private Network getNetwork(String id) {
    Network network = publishedNetworkService.findById(id);
    if (network == null) throw NoSuchNetworkException.withId(id);
    return network;
  }

}
