# Quarkus Application Operator

This project is an example Openshift operator powered by Quarkus Operator SDK.

## Description

The operator downloads Quarkus application artifacts from Maven repositories and deploys the to OpenShift.

## The custom resource

The custom resource introduced by this operator is: `QuarkusApplication` with apiVersion: `acme.org/v1alpha1`.
The sample below is a working sample.

```yml
apiVersion: acme.org/v1alpha1
kind: QuarkusApplication
metadata:
  name: example
  namespace: iocanel
spec:
  coordinates: com.github.iocanel:quarkus-helloworld:1.2
  repositoryUrl: https://jitpack.io
```
