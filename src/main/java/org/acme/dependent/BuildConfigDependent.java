package org.acme.dependent;

import static org.acme.Util.version;
import static org.acme.dependent.BuilderImageStreamDependent.BUILDER_IMAGE_NAME;
import static org.acme.dependent.BuilderImageStreamDependent.BUILDER_IMAGE_TAG;

import org.acme.QuarkusApplication;
import org.jboss.logging.Logger;

import io.fabric8.openshift.api.model.BuildConfig;
import io.fabric8.openshift.api.model.BuildConfigBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;

public class BuildConfigDependent extends QuarkusApplicationDependent<BuildConfig> {

  private static final Logger LOG = Logger.getLogger(BuildConfigDependent.class);

  public BuildConfigDependent() {
    super(BuildConfig.class);
  }

  @Override
  public BuildConfig desired(QuarkusApplication quarkusApp, Context context) {

    var name = quarkusApp.getMetadata().getName();
    var namespace = quarkusApp.getMetadata().getNamespace();
    var version = version(quarkusApp.getSpec().getCoordinates());

    LOG.debugf("Creating BuildConfig: %s in namespace: %s", name, namespace);
    BuildConfig bc = new BuildConfigBuilder()
        .withNewMetadata()
        .withName(name)
        .withNamespace(namespace)
        .endMetadata()
        .withNewSpec()
        .withNewOutput()
        .withNewTo()
        .withKind("ImageStreamTag")
        .withName(name + ":" + version)
        .endTo()
        .endOutput()
        .withNewSource()
        .withNewBinary()
        .endBinary()
        .endSource()
        .withNewStrategy()
        .withNewSourceStrategy()
        .withEnv()
        .withNewFrom()
        .withKind("ImageStreamTag")
        .withName(BUILDER_IMAGE_NAME + ":" + BUILDER_IMAGE_TAG)
        .endFrom()
        .endSourceStrategy()
        .endStrategy()
        .endSpec()
        .build();
    return bc;
  }
}
