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

  /**
   * Returns all URLs and their statuses for the given app for all events. Currently, only one
   * webhook URL can be registered to an application. We mark a URL as invalid if it fails the daily
   * validation check. In order to re-enable the URL, call the update endpoint.
   *
   * <p>指定されたすべてのイベントのURLとそのステータスを返します。 現在、Webhook URLはアプリケーションに1つしか登録できません。
   * 毎日の妥当性チェックに失敗した場合は、URLを無効とマークします。 URLを再度有効にするには、更新エンドポイントを呼び出します。
   */
  public void returnsAllUrls() {
    mTwitterClient.returnsAllUrls();
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
