package appcloud.iaas.sdk.billing;

import appcloud.core.sdk.common.RpcYhaiRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by lizhenhao on 2017/9/23.
 */
public abstract class BillingRequest extends RpcYhaiRequest {

    public BillingRequest(String product, String version, String actionName) {
        super(product,version,actionName);
    }

    //构造请求URL(endpoint更换哟！)
    public String composerUrl(String endPoint, Map<String, String> queries) throws UnsupportedEncodingException {
        Map<String, String> mapQueries = (queries == null) ? this.getQueryParameters() : queries;
        StringBuilder urlBuilder = new StringBuilder("");
//        endPoint = appcloud.core.sdk.common.Constants.EXPENSE_BILLING;
        logger.info("endpoint = " + endPoint);
        urlBuilder.append(this.getProtocol().toString());
        urlBuilder.append("://").append(endPoint);
        if( -1 == urlBuilder.indexOf("?")) {
            urlBuilder.append("/?");
        }
        String query = concatQueryString(mapQueries);
        return urlBuilder.append(query).toString();
    }
}
