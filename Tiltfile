LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = 'micropets-dev'

allow_k8s_contexts('aks-eu-tap-6')

k8s_yaml(["config/application-configuration.yaml"])

k8s_custom_deploy(
    'dogs-springboot',
   
    apply_cmd="tanzu apps workload apply -f config/workload.yaml --update-strategy replace --debug --live-update" +
              " --local-path " + LOCAL_PATH +
              " --namespace " + NAMESPACE +
              " --yes --output yaml",

    delete_cmd="tanzu apps workload delete -f config/workload.yaml --namespace " + NAMESPACE + " --yes",
    deps=['pom.xml', './target/classes'],
    container_selector='workload',
    live_update=[
      sync('./target/classes', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('dogs-springboot', port_forwards=["8080:8080"], extra_pod_selectors=[{'serving.knative.dev/service': 'dogs-springboot'}]) 