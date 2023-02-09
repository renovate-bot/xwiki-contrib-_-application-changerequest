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
package org.xwiki.contrib.changerequest.internal.diff;

import javax.inject.Provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xwiki.contrib.changerequest.ChangeRequestException;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.test.junit5.mockito.ComponentTest;
import org.xwiki.test.junit5.mockito.InjectMockComponents;
import org.xwiki.test.junit5.mockito.MockComponent;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link DefaultChangeRequestDiffRenderContent}.
 *
 * @version $Id$
 * @since 1.4.4
 * @since 1.5
 */
@ComponentTest
class DefaultChangeRequestDiffRenderContentTest
{
    @InjectMockComponents
    private DefaultChangeRequestDiffRenderContent diffRenderContent;

    @MockComponent
    private Provider<XWikiContext> contextProvider;

    private XWikiContext context;

    @BeforeEach
    void setup()
    {
        this.context = mock(XWikiContext.class);
        when(this.contextProvider.get()).thenReturn(this.context);
    }

    @Test
    void getRenderedContent() throws XWikiException, ChangeRequestException
    {
        XWikiDocument document = mock(XWikiDocument.class);
        XWikiDocument currentDoc = mock(XWikiDocument.class);
        when(this.context.getDoc()).thenReturn(currentDoc);
        String expectedResult = "Some html";
        when(document.displayDocument(Syntax.HTML_5_0, true, this.context)).thenReturn(expectedResult);
        assertEquals(expectedResult, this.diffRenderContent.getRenderedContent(document, null));
        verify(this.context).setDoc(document);
        verify(this.context).setDoc(currentDoc);
    }
}