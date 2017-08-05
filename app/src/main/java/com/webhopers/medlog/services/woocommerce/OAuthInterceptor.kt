package com.webhopers.medlog.services.woocommerce

import okhttp3.Interceptor
import okhttp3.Response
import org.scribe.model.ParameterList
import org.scribe.services.HMACSha1SignatureService
import org.scribe.services.TimestampServiceImpl
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class OAuthInterceptor: Interceptor {

    private val OAUTH_CONSUMER_KEY  = "oauth_consumer_key"
    private val OAUTH_NONCE = "oauth_nonce"
    private val OAUTH_SIGNATURE = "oauth_signature"
    private val OAUTH_SIGNATURE_METHOD = "oauth_signature_method"
    private val OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1"
    private val OAUTH_TIMESTAMP = "oauth_timestamp"
    private val OAUTH_VERSION = "oauth_version"
    private val OAUTH_VERSION_VALUE = "1.0"


    private val consumerKey = "ck_fa5927666cbce67ef3615fe4f9503e3ba5224408"
    private val consumerSecret = "cs_b257b0eda7f21ace5373ef1552e6b9f7f044291e"




    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val nonce = TimestampServiceImpl().nonce
        val timestamp = TimestampServiceImpl().timestampInSeconds
        val dynamicStructureUrl = "${original.url().scheme()}://${original.url().host()}${original.url().encodedPath()}"
        val firstBaseString = "${original.method()}&${urlEncoded(dynamicStructureUrl)}"
        var generatedBaseString = ""

        if (original.url().encodedQuery() != null)
            generatedBaseString = original.url().encodedQuery() + "&oauth_consumer_key=" + consumerKey + "&oauth_nonce=" + nonce + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0";
        else
            generatedBaseString = "oauth_consumer_key=" + consumerKey + "&oauth_nonce=" + nonce + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0";

        val result = ParameterList()
        result.addQuerystring(generatedBaseString)
        generatedBaseString = result.sort().asOauthBaseString()

        var secondBaseString = "&$generatedBaseString"
        if (firstBaseString.contains("%3F"))
            secondBaseString = "%26${urlEncoded(generatedBaseString)}"

        val baseString = firstBaseString + secondBaseString
        val signature = HMACSha1SignatureService().getSignature(baseString, consumerSecret, "")

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter(OAUTH_SIGNATURE_METHOD, OAUTH_SIGNATURE_METHOD_VALUE)
                .addQueryParameter(OAUTH_CONSUMER_KEY, consumerKey)
                .addQueryParameter(OAUTH_VERSION, OAUTH_VERSION_VALUE)
                .addQueryParameter(OAUTH_TIMESTAMP, timestamp)
                .addQueryParameter(OAUTH_NONCE, nonce)
                .addQueryParameter(OAUTH_SIGNATURE, signature)
                .build()

        val requestBuilder = original.newBuilder()
                .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)

    }

    fun urlEncoded(url: String): String {
        var encodedUrl = ""
        try {
            encodedUrl = URLEncoder.encode(url, "UTF-8")
        } catch (e: UnsupportedEncodingException) { e.printStackTrace() }
        return encodedUrl
    }
}
