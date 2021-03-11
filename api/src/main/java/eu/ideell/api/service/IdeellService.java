package eu.ideell.api.service;

import java.util.List;

import eu.ideell.api.model.AccountI;
import eu.ideell.api.model.AnalyticsAction;
import eu.ideell.api.model.CommentI;
import eu.ideell.api.model.Feedback;
import eu.ideell.api.model.IdeellAccount;
import eu.ideell.api.model.PostI;
import eu.ideell.api.model.ReplyToAccountRequest;
import eu.ideell.api.model.ReplyToCommentRequest;
import eu.ideell.api.model.ReplyToPostRequest;
import eu.ideell.api.model.ReplyToReportRequest;
import eu.ideell.api.model.ReportI;

public interface IdeellService {

  public long createAccount(final IdeellAccount resource);

  public IdeellAccount getAccount();

  public void save(Feedback resource);

  public void removeIdsAndSave(AnalyticsAction resource);

  public List<AccountI> getNewAccounts();

  public List<AccountI> getUnacceptedAccounts();

  public long createReplyToAccountRequest(ReplyToAccountRequest resource);

  public List<PostI> getNewPosts();

  public List<PostI> getUnacceptedPosts();

  public List<CommentI> getNewComments();

  public List<CommentI> getUnacceptedComments();

  public long createReplyToPostRequest(ReplyToPostRequest resource);

  public long createReplyToCommentRequest(ReplyToCommentRequest resource);

  public List<ReportI> getNewReports();

  public List<ReportI> getUnacceptedReports();

  public long createReplyToReportRequest(ReplyToReportRequest resource);

}
