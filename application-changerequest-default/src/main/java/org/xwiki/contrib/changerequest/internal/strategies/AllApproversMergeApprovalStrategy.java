/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.changerequest.internal.strategies;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.changerequest.ApproversManager;
import org.xwiki.contrib.changerequest.ChangeRequest;
import org.xwiki.contrib.changerequest.ChangeRequestException;
import org.xwiki.contrib.changerequest.ChangeRequestReview;
import org.xwiki.contrib.changerequest.MergeApprovalStrategy;
import org.xwiki.user.UserReference;

/**
 * This strategy checks that all explicit approvers have approved the given change request.
 * If the change request does not have an explicit list of approvers, then the strategy fallbacks on
 * {@link OnlyApprovedMergeApprovalStrategy}.
 *
 * @version $Id$
 * @since 0.5
 */
@Component
@Singleton
@Named(AllApproversMergeApprovalStrategy.NAME)
public class AllApproversMergeApprovalStrategy extends AbstractMergeApprovalStrategy
{
    static final String NAME = "allapprovers";

    @Inject
    @Named(OnlyApprovedMergeApprovalStrategy.NAME)
    private MergeApprovalStrategy fallbackStrategy;

    @Inject
    private ApproversManager<ChangeRequest> changeRequestApproversManager;

    @Inject
    private Logger logger;

    /**
     * Default constructor.
     */
    public AllApproversMergeApprovalStrategy()
    {
        super(NAME);
    }

    private Optional<Set<UserReference>> getApprovers(ChangeRequest changeRequest)
    {
        Optional<Set<UserReference>> result = Optional.empty();
        try {
            Set<UserReference> allApprovers =
                new HashSet<>(this.changeRequestApproversManager.getAllApprovers(changeRequest, true));
            if (!allApprovers.isEmpty()) {
                result = Optional.of(allApprovers);
            }
        } catch (ChangeRequestException e) {
            logger.warn("Error while getting the list of approvers for [{}], we'll use the fallback strategy: [{}]",
                changeRequest, ExceptionUtils.getRootCauseMessage(e));
        }
        return result;
    }

    @Override
    public boolean canBeMerged(ChangeRequest changeRequest)
    {
        boolean result;
        Optional<Set<UserReference>> allApproversOpt = this.getApprovers(changeRequest);
        if (allApproversOpt.isPresent()) {
            Set<UserReference> allApprovers = allApproversOpt.get();
            List<ChangeRequestReview> reviews = changeRequest.getReviews();
            for (ChangeRequestReview review : reviews) {
                if (review.isApproved() && review.isValid()) {
                    allApprovers.remove(review.getAuthor());
                }
            }
            result = allApprovers.isEmpty();
        } else {
            result = this.fallbackStrategy.canBeMerged(changeRequest);
        }
        return result;
    }

    @Override
    public String getStatus(ChangeRequest changeRequest)
    {
        Optional<Set<UserReference>> approversOpt = this.getApprovers(changeRequest);
        if (approversOpt.isPresent()) {
            if (this.canBeMerged(changeRequest)) {
                return this.contextualLocalizationManager.getTranslationPlain(getTranslationPrefix() + "success");
            } else {
                return this.contextualLocalizationManager.getTranslationPlain(getTranslationPrefix() + "failure");
            }
        } else {
            return this.fallbackStrategy.getStatus(changeRequest);
        }
    }
}