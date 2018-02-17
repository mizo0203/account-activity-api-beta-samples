package com.mizo0203.twitter.timeline.talker.repo;

import com.mizo0203.twitter.timeline.talker.domain.difine.KeysAndAccessTokens;
import twitter4j.HttpResponse;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwitterClient {

  private static final Logger LOG = Logger.getLogger(TwitterClient.class.getName());
  private static final String TWITTER_API_ACCOUNT_ACTIVITY_WEBHOOKS_ENV_NAME_URL_STR =
      "https://api.twitter.com/1.1/account_activity/all/env-beta/webhooks.json?url=https%3A%2F%2Fapi-project-93144643231.appspot.com%2Ftwitter_hook";
  private static final String TWITTER_API_ACCOUNT_ACTIVITY_SUBSCRIPTIONS_ENV_NAME_URL_STR =
      "https://api.twitter.com/1.1/account_activity/all/env-beta/subscriptions.json";
  private final Twitter mTwitter;

  public TwitterClient() {
    mTwitter = createTwitterInstance();
  }

  private static Twitter createTwitterInstance() {
    Twitter twitter = new TwitterFactory().getInstance();
    twitter.setOAuthConsumer(KeysAndAccessTokens.CONSUMER_KEY, KeysAndAccessTokens.CONSUMER_SECRET);
    twitter.setOAuthAccessToken(
        new AccessToken(KeysAndAccessTokens.TOKEN, KeysAndAccessTokens.TOKEN_SECRET));
    return twitter;
  }

  public void registersWebhookURL() {
    try {
      Method method = mTwitter.getClass().getDeclaredMethod("post", String.class);
      method.setAccessible(true);
      HttpResponse ret =
          (HttpResponse)
              method.invoke(mTwitter, TWITTER_API_ACCOUNT_ACTIVITY_WEBHOOKS_ENV_NAME_URL_STR);
      LOG.log(Level.INFO, "registersWebhookURL ret: " + ret);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      LOG.log(Level.SEVERE, "registersWebhookURL", e);
    }
  }

  public void subscriptions() {
    try {
      Method method = mTwitter.getClass().getDeclaredMethod("post", String.class);
      method.setAccessible(true);
      HttpResponse ret =
          (HttpResponse)
              method.invoke(mTwitter, TWITTER_API_ACCOUNT_ACTIVITY_SUBSCRIPTIONS_ENV_NAME_URL_STR);
      LOG.log(Level.INFO, "subscriptions ret: " + ret);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      LOG.log(Level.SEVERE, "subscriptions", e);
    }
  }
}
