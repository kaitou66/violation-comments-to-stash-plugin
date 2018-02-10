package org.jenkinsci.plugins.jvctb;

import static org.jenkinsci.plugins.jvctb.config.CredentialsHelper.migrateCredentials;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.plugins.jvctb.config.CredentialsHelper;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import com.google.common.base.Optional;

import hudson.Extension;
import hudson.util.ListBoxModel;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import se.bjurr.violations.lib.model.SEVERITY;

/** Created by magnayn on 07/04/2016. */
@Extension
public class ViolationsToBitbucketServerGlobalConfiguration extends GlobalConfiguration
    implements Serializable {

  private static final long serialVersionUID = -5458709657500220354L;

  /**
   * Returns this singleton instance.
   *
   * @return the singleton.
   */
  public static Optional<ViolationsToBitbucketServerGlobalConfiguration> get() {
    return Optional.fromNullable(
        GlobalConfiguration.all().get(ViolationsToBitbucketServerGlobalConfiguration.class));
  }

  public String bitbucketServerUrl;
  @Deprecated private transient String password;
  public String projectKey;
  public String repoSlug;
  @Deprecated private transient String username;
  private String usernamePasswordCredentialsId;
  private SEVERITY minSeverity = SEVERITY.INFO;
  private String personalAccessTokenId;

  public ViolationsToBitbucketServerGlobalConfiguration() {
    load();
  }

  @Override
  public boolean configure(final StaplerRequest req, final JSONObject json) throws FormException {
    req.bindJSON(this, json);
    save();
    return true;
  }

  @Restricted(NoExternalUse.class)
  public ListBoxModel doFillMinSeverityItems() {
    final ListBoxModel items = new ListBoxModel();
    for (final SEVERITY severity : SEVERITY.values()) {
      items.add(severity.name());
    }
    return items;
  }

  public ListBoxModel doFillUsernamePasswordCredentialsIdItems() {
    return CredentialsHelper.doFillUsernamePasswordCredentialsIdItems();
  }

  public ListBoxModel doFillPersonalAccessTokenIdItems() {
    return CredentialsHelper.doFillPersonalAccessTokenIdItems();
  }

  public String getBitbucketServerUrl() {
    return this.bitbucketServerUrl;
  }

  public String getProjectKey() {
    return this.projectKey;
  }

  public String getRepoSlug() {
    return this.repoSlug;
  }

  public String getUsernamePasswordCredentialsId() {
    return this.usernamePasswordCredentialsId;
  }

  @DataBoundSetter
  public void setMinSeverity(final SEVERITY minSeverity) {
    this.minSeverity = minSeverity;
  }

  public SEVERITY getMinSeverity() {
    return minSeverity;
  }

  @DataBoundSetter
  public void setBitbucketServerUrl(final String bitbucketServerUrl) {
    this.bitbucketServerUrl = bitbucketServerUrl;
  }

  @DataBoundSetter
  public void setProjectKey(final String projectKey) {
    this.projectKey = projectKey;
  }

  @DataBoundSetter
  public void setRepoSlug(final String repoSlug) {
    this.repoSlug = repoSlug;
  }

  @DataBoundSetter
  public void setUsernamePasswordCredentialsId(final String usernamePasswordCredentialsId) {
    this.usernamePasswordCredentialsId = usernamePasswordCredentialsId;
  }

  @DataBoundSetter
  public void setPersonalAccessTokenId(final String personalAccessTokenId) {
    this.personalAccessTokenId = personalAccessTokenId;
  }

  public String getPersonalAccessTokenId() {
    return personalAccessTokenId;
  }

  private Object readResolve() {
    if (StringUtils.isBlank(usernamePasswordCredentialsId)
        && username != null
        && password != null) {
      usernamePasswordCredentialsId = migrateCredentials(username, password);
    }
    return this;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (bitbucketServerUrl == null ? 0 : bitbucketServerUrl.hashCode());
    result = prime * result + (minSeverity == null ? 0 : minSeverity.hashCode());
    result =
        prime * result + (personalAccessTokenId == null ? 0 : personalAccessTokenId.hashCode());
    result = prime * result + (projectKey == null ? 0 : projectKey.hashCode());
    result = prime * result + (repoSlug == null ? 0 : repoSlug.hashCode());
    result =
        prime * result
            + (usernamePasswordCredentialsId == null
                ? 0
                : usernamePasswordCredentialsId.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ViolationsToBitbucketServerGlobalConfiguration other =
        (ViolationsToBitbucketServerGlobalConfiguration) obj;
    if (bitbucketServerUrl == null) {
      if (other.bitbucketServerUrl != null) {
        return false;
      }
    } else if (!bitbucketServerUrl.equals(other.bitbucketServerUrl)) {
      return false;
    }
    if (minSeverity != other.minSeverity) {
      return false;
    }
    if (personalAccessTokenId == null) {
      if (other.personalAccessTokenId != null) {
        return false;
      }
    } else if (!personalAccessTokenId.equals(other.personalAccessTokenId)) {
      return false;
    }
    if (projectKey == null) {
      if (other.projectKey != null) {
        return false;
      }
    } else if (!projectKey.equals(other.projectKey)) {
      return false;
    }
    if (repoSlug == null) {
      if (other.repoSlug != null) {
        return false;
      }
    } else if (!repoSlug.equals(other.repoSlug)) {
      return false;
    }
    if (usernamePasswordCredentialsId == null) {
      if (other.usernamePasswordCredentialsId != null) {
        return false;
      }
    } else if (!usernamePasswordCredentialsId.equals(other.usernamePasswordCredentialsId)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ViolationsToBitbucketServerGlobalConfiguration [bitbucketServerUrl="
        + bitbucketServerUrl
        + ", projectKey="
        + projectKey
        + ", repoSlug="
        + repoSlug
        + ", usernamePasswordCredentialsId="
        + usernamePasswordCredentialsId
        + ", minSeverity="
        + minSeverity
        + ", personalAccessTokenId="
        + personalAccessTokenId
        + "]";
  }
}
