package webhook;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WebHookUtil {


    /**
     * WebHook地址xxx
     */
    public static String WEBHOOK_TOKEN =
            "https://oapi.dingtalk.com/robot/send?access_token=1c29a09f72ee1a08bc6272b60f5c749f1ff3b951331d682b85e826312c00b8d6";


    /**
     *  webHook 发送请求
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
        // content 内容
        // atMobiles 手机号
        // @所有人 isAtAll 时为true，上面的atMobiles就失效了
        String textMsg = "{\"msgtype\":\"text\",\"text\":{\"content\":\"@曹源 今天是不是该我取饭了\"},\"at\":{\"atMobiles\":[\"xxx\"],\"isAtAll\":false}}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }

}
