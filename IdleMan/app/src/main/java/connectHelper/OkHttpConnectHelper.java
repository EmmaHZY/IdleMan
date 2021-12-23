package connectHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpConnectHelper {
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client=new OkHttpClient();

    //转Map为String
    public static String getMapToString(Map<String,Object> map){
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        //因为String拼接效率会很低的，所以转用StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if(i != keyArray.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    //发送post请求
    public static String postTargetData(String  url ,String content )
    {
        String result=null;
        RequestBody body=RequestBody.create(JSON,content);
        Request request=new Request.Builder().url(url).post(body).build();
        Response res=null;
        try {
            res = client.newCall(request).execute();
            result=res.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    //发送get请求
    public static String getTargetData(String url)
    {
        String result=null;
        Request request=new Request.Builder().url(url).build();
        Response res=null;
        try {
            res=client.newCall(request).execute();
            result=res.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
