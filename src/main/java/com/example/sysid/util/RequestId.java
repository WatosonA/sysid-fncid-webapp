package com.example.sysid.util;

/**
 * リクエストID保持クラス。
 * HTTPクライアント等で外部連携する際に元のリクエストを識別できるようにヘッダーに設定するなどで利用する想定。
 */
public class RequestId {

    public static final String ATTRIBUTE_KEY = "X-REQUEST-ID";
    public static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    private RequestId() {
    }

}
