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
package org.xwiki.contrib.changerequest.replication.internal.listeners;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.changerequest.notifications.events.ChangeRequestDiscussionRecordableEvent;
import org.xwiki.contrib.changerequest.replication.internal.messages.ChangeRequestDiscussionReplicationSenderMessage;

/**
 * Default listener and message sender for {@link ChangeRequestDiscussionRecordableEvent}.
 *
 * @version $Id$
 * @since 0.16
 */
@Component
@Singleton
@Named(ChangeRequestDiscussionListener.NAME)
public class ChangeRequestDiscussionListener extends
    AbstractChangeRequestEventListener<ChangeRequestDiscussionRecordableEvent,
        ChangeRequestDiscussionReplicationSenderMessage>
{
    /**
     * Listener name.
     */
    static final String NAME =
        "org.xwiki.contrib.changerequest.replication.internal.listeners.ChangeRequestDiscussionListener";

    /**
     * Default constructor.
     */
    public ChangeRequestDiscussionListener()
    {
        super(NAME, new ChangeRequestDiscussionRecordableEvent());
    }

    @Override
    public String getMessageHint()
    {
        return ChangeRequestDiscussionRecordableEvent.EVENT_NAME;
    }
}
