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
package org.xwiki.contrib.changerequest.discussions.references;

import org.xwiki.stability.Unstable;

/**
 * Reference used to attach a discussion to a global comment of a change request.
 * Those comments are not attached to a particular review, or to a diff element, they are global to the change request.
 *
 * @version $Id$
 * @since 0.6
 */
@Unstable
public class ChangeRequestCommentReference extends AbstractChangeRequestDiscussionContextReference
{
    /**
     * Default constructor.
     *
     * @param changeRequestId the identifier of the related change request
     */
    public ChangeRequestCommentReference(String changeRequestId)
    {
        super(changeRequestId, ChangeRequestDiscussionReferenceType.CHANGE_REQUEST_COMMENT, "");
    }
}
