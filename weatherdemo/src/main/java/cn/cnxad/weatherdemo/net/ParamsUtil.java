package cn.cnxad.weatherdemo.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 作者: Louis on 2016/5/11 18:49
 */
public class ParamsUtil {
    private static final String DEFAULT_ENCODING = "utf-8";

    /**
     * 可提取掉BaseRequest
     * 根据参数拼接url
     *
     * @param url    原本url
     * @param params get当中的参数
     * @return 拼接后的url
     */
    public static String createGetUrlWithParams(String url, Map<String, String> params) {
        if (params != null) {
            StringBuffer stringBuffer = new StringBuffer(url);
            if (!url.contains("?")) {
                stringBuffer.append('?');
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey().toString();
                String value;
                if (entry.getValue() == null) {
                    value = "";
                } else {
                    value = entry.getValue().toString();
                }
                stringBuffer.append(key);
                stringBuffer.append("=");
                try {
                    value = URLEncoder.encode(value, DEFAULT_ENCODING);
                    stringBuffer.append(value);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                stringBuffer.append('&');
            }
            //删除最后一个'&'
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            url = stringBuffer.toString();
        }
        return url;
    }
}
