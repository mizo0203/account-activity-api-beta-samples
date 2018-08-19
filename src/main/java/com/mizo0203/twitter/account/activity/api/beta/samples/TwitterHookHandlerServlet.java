package com.mizo0203.twitter.account.activity.api.beta.samples;

import com.mizo0203.twitter.account.activity.api.beta.samples.domain.difine.KeysAndAccessTokens;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import twitter4j.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwitterHookHandlerServlet extends HttpServlet {

  private static final Logger LOG = Logger.getLogger(TwitterHookHandlerServlet.class.getName());

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String source = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8);
    parse(source);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter out = resp.getWriter();
    String crc_token = req.getParameter("crc_token");
    try {
      String signature = getSignature(crc_token);
      LOG.log(Level.INFO, "doGet signature: " + signature);
      out.print("{\"response_token\": \"sha256=" + signature + "\"}");
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  private String getSignature(String httpRequestBody)
      throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
    SecretKeySpec key =
        new SecretKeySpec(KeysAndAccessTokens.CONSUMER_SECRET.getBytes(), "HmacSHA256");
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(key);
    byte[] source = httpRequestBody.getBytes("UTF-8");
    return Base64.encodeBase64String(mac.doFinal(source));
  }

  private void parse(String source) {
    LOG.log(Level.INFO, "parse source: " + source);
    try {
      JSONObject json = new JSONObject(source);
      if (json.isNull("tweet_create_events")) {
        return;
      }
      long forUserId = json.getLong("for_user_id");
      JSONArray tweet_create_events = json.getJSONArray("tweet_create_events");
      for (int i = 0; i < tweet_create_events.length(); i++) {
        onTweetCreateEvent(
            forUserId, Twitter4JJSONUtil.asStatus(tweet_create_events.getJSONObject(i)));
      }
    } catch (JSONException | TwitterException e) {
      e.printStackTrace();
    }
  }

  /**
   * twitter、Retweets、返信、@mentions、QuoteTweetsのいずれかがサブスクリプションユーザーによって、
   * またはサブスクリプションユーザーに行われたときのステータスペイロードをツイートします
   *
   * <p>Tweet status payload when any of the following actions are taken by or to the subscription
   * user: Tweets, Retweets, Replies, @mentions, QuoteTweets
   *
   * @param status ステータス
   */
  private void onTweetCreateEvent(long forUserId, Status status) {
    LOG.log(Level.INFO, "onTweetCreateEvent forUserId: " + forUserId + " status: " + status);
  }
}
