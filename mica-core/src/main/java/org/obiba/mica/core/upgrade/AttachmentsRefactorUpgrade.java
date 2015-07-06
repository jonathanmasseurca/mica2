package org.obiba.mica.core.upgrade;

import javax.inject.Inject;

import org.obiba.mica.access.DataAccessRequestRepository;
import org.obiba.mica.study.StudyRepository;
import org.obiba.runtime.Version;
import org.obiba.runtime.upgrade.UpgradeStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AttachmentsRefactorUpgrade implements UpgradeStep {
  private static final Logger log = LoggerFactory.getLogger(AttachmentsRefactorUpgrade.class);

  @Inject
  private StudyRepository studyRepository;

  @Inject
  private DataAccessRequestRepository dataAccessRequestRepository;

  @Override
  public String getDescription() {
    return "Refactored attachments";
  }

  @Override
  public Version getAppliesTo() {
    return new Version("0.9");
  }

  @Override
  public void execute(Version version) {
    log.info("Executing attachments upgrade");
    studyRepository.findAll().forEach(s -> studyRepository.saveWithAttachments(s, false));
    dataAccessRequestRepository.findAll().forEach(d -> dataAccessRequestRepository.saveWithAttachments(d, false));
  }
}