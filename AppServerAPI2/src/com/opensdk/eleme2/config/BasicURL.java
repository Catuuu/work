package com.opensdk.eleme2.config;

public abstract class BasicURL {

    public static final class OAuth{

        public static final String SANDBOX_AUTHORIZE = "https://open-api-sandbox.shop.ele.me/authorize";

        public static final String PRODUCTION_AUTHORIZE="https://open-api.shop.ele.me/authorize";

        public static final String SANDBOX_TOKEN = "https://open-api-sandbox.shop.ele.me/token";

        public static final String PRODUCTION_TOKEN="https://open-api.shop.ele.me/token";
    }

    public static final class OpenApi{
        public static final String SANDBOX_Api = "https://open-api-sandbox.shop.ele.me/api/v1/";

        public static final String PRODUCTION_Api="https://open-api.shop.ele.me/api/v1/";
    }

    /*public static final class OAuth{

        public static final String SANDBOX_AUTHORIZE = "https://open-api-sandbox-shop.alpha.elenet.me/authorize";

        public static final String PRODUCTION_AUTHORIZE="https://open-api-shop.alpha.elenet.me/authorize";

        public static final String SANDBOX_TOKEN = "https://open-api-sandbox-shop.alpha.elenet.me/token";

        public static final String PRODUCTION_TOKEN="https://open-api-shop.alpha.elenet.me/token";
    }

    public static final class OpenApi{
        public static final String SANDBOX_Api = "https://open-api-sandbox-shop.alpha.elenet.me/api/v1/";

        public static final String PRODUCTION_Api="https://open-api-shop.alpha.elenet.me/api/v1/";
    }*/
}
