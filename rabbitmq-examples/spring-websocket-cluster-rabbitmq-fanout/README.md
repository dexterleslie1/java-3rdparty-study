spring-websocket��������Ⱥ��ʹ��spring-amqp+rabbitmq fanoutʵ��

����
mvn package

���뾵��
docker build -t test-image1 .
���о���hostipΪ������ip������jmx�����;
docker run -d --name=test1 -p8080:8080 -p19000:19000 -e "hostip=192.168.1.158" test-image1