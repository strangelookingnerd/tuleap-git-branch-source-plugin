package org.jenkinsci.plugins.tuleap_git_branch_source.trait;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import jenkins.scm.api.SCMHeadCategory;
import jenkins.scm.api.SCMSource;
import jenkins.scm.api.trait.SCMSourceContext;
import jenkins.scm.api.trait.SCMSourceTrait;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.tuleap_git_branch_source.Messages;
import org.jenkinsci.plugins.tuleap_git_branch_source.TuleapSCMSource;
import org.jenkinsci.plugins.tuleap_git_branch_source.TuleapSCMSourceContext;
import org.jetbrains.annotations.NotNull;
import org.kohsuke.stapler.DataBoundConstructor;

public class TuleapCommitNotificationTrait extends SCMSourceTrait {
    @DataBoundConstructor
    public TuleapCommitNotificationTrait() {}

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateContext(SCMSourceContext<?, ?> context) {
        TuleapSCMSourceContext tuleapSourceContext = (TuleapSCMSourceContext) context;
        tuleapSourceContext.notifyPullRequest(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean includeCategory(@NonNull SCMHeadCategory category) {
        return category.isUncategorized();
    }

    @Symbol("tuleapNotifyPullRequest")
    @Extension
    public static class DescriptorImpl extends SCMSourceTraitDescriptor {

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public String getDisplayName() {
            return Messages.TuleapCommitNotificationTrait_displayName();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<? extends SCMSourceContext> getContextClass() {
            return TuleapSCMSourceContext.class;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<? extends SCMSource> getSourceClass() {
            return TuleapSCMSource.class;
        }
    }
}
