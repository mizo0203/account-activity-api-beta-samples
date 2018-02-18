package com.mizo0203.twitter.account.activity.api.beta.samples.domain;

import com.mizo0203.twitter.account.activity.api.beta.samples.repo.TwitterClient;

import java.util.logging.Logger;

public class UseCase implements AutoCloseable {
  private static final Logger LOG = Logger.getLogger(UseCase.class.getName());
  private final TwitterClient mTwitterClient;

  public UseCase() {
    mTwitterClient = new TwitterClient();
  }

  @Override
  public void close() {}

  public void registersWebhookURL() {
    mTwitterClient.registersWebhookURL();
  }

  public void subscriptions() {
    mTwitterClient.subscriptions();
  }

  /**
   * Deactivates subscription(s) for the provided user context and app for all activities. After
   * deactivation, all All events for the requesting user will no longer be sent to the webhook URL.
   *
   * <p>提供されたユーザーコンテキストのサブスクリプションとすべてのアクティビティのアプリケーションを非アクティブ化します。
   * 非アクティブ化後、要求元ユーザのすべてのすべてのイベントはWebHook URLに送信されなくなります。
   */
  public void deactivatesSubscriptions() {
    mTwitterClient.deactivatesSubscriptions();
  }
}
