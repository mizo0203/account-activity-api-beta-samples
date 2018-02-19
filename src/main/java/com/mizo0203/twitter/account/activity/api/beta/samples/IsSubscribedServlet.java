package com.mizo0203.twitter.account.activity.api.beta.samples;

import com.mizo0203.twitter.account.activity.api.beta.samples.domain.UseCase;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IsSubscribedServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try (UseCase useCase = new UseCase()) {
      useCase.isSubscribed();
    }
  }
}
