# IoT App Helm Chart

This Helm chart deploys the IoT Data Processing Spring Boot application to Kubernetes.

## Usage
1. Ensure you have [Helm](https://helm.sh/) installed and a Kubernetes cluster running.
2. Package or deploy the chart:
   ```bash
   helm install iot-app ./helm/iot-app
   ```
   Or for upgrades:
   ```bash
   helm upgrade iot-app ./helm/iot-app
   ```
3. Customize `values.yaml` for image, environment variables, resources, and service type.


## Files
- `Chart.yaml`: Chart metadata
- `values.yaml`: Default configuration values
- `templates/deployment.yaml`: Kubernetes Deployment manifest
- `templates/service.yaml`: Kubernetes Service manifest
- `templates/ingress.yaml`: Ingress for HTTP routing
- `templates/configmap.yaml`: ConfigMap for non-sensitive configuration
- `templates/secret.yaml`: Secret for sensitive data (e.g., DB password)
- `templates/hpa.yaml`: Horizontal Pod Autoscaler for autoscaling


## Customization
- Set your Docker image repository and tag in `values.yaml`
- Adjust resource requests/limits for your workload
- Change service type (ClusterIP, NodePort, LoadBalancer) as needed
- Add environment variables under `env` in `values.yaml`
- Set Ingress host in `values.yaml` (`ingress.host`)
- Store sensitive values in `values.yaml` under `secrets` (e.g., `DB_PASSWORD`)
- Configure autoscaling in `values.yaml` under `autoscaling` (min/max replicas, target CPU)


## References
- [Helm Docs](https://helm.sh/docs/)
- [Kubernetes Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)
- [Kubernetes Service](https://kubernetes.io/docs/concepts/services-networking/service/)
- [Kubernetes Ingress](https://kubernetes.io/docs/concepts/services-networking/ingress/)
- [Kubernetes ConfigMap](https://kubernetes.io/docs/concepts/configuration/configmap/)
- [Kubernetes Secret](https://kubernetes.io/docs/concepts/configuration/secret/)
- [Kubernetes Horizontal Pod Autoscaler](https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale/)