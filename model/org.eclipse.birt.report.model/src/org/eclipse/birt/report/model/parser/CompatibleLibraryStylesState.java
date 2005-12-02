/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.model.parser;

import org.eclipse.birt.report.model.api.core.IModuleModel;
import org.eclipse.birt.report.model.core.DesignElement;
import org.eclipse.birt.report.model.elements.Library;
import org.eclipse.birt.report.model.elements.Theme;
import org.eclipse.birt.report.model.i18n.ModelMessages;
import org.eclipse.birt.report.model.metadata.ElementRefValue;
import org.eclipse.birt.report.model.util.AbstractParseState;
import org.xml.sax.SAXException;

/**
 * Parses the compatible of contents of the list of styles in the library.
 * Contents in the style slot are reconstructed to a new Theme element.
 * <p>
 * 
 * <pre>
 *            &lt;styles&gt;
 *            &lt;style name=&quot;label&quot;&gt;
 *            &lt;property name=&quot;color&quot;&gt;red&lt;/property&gt;											
 *            &lt;/style&gt;			
 *            &lt;/styles&gt;
 * </pre>
 * 
 * to
 * 
 * <pre>
 *            &lt;themes&gt;
 *            &lt;theme name=&quot;defaultTheme&quot;&gt;
 *            &lt;styles&gt;
 *            &lt;style name=&quot;label&quot;&gt;
 *            &lt;property name=&quot;color&quot;&gt;red&lt;/property&gt;											
 *            &lt;/style&gt;			
 *            &lt;/styles&gt;
 *            &lt;/theme&gt;
 *            &lt;/themes&gt;
 * </pre>
 */

class CompatibleLibraryStylesState extends ReportElementState
{

	/**
	 * 
	 */

	Theme theme = null;

	/**
	 * Constructs the compatible styles state for the library.
	 * 
	 * @param handler
	 *            the design file parser handler
	 * @param theContainer
	 *            the element that contains this one
	 * @param slot
	 *            the slot in which this element appears
	 */

	public CompatibleLibraryStylesState( ModuleParserHandler handler,
			DesignElement theContainer, int slot )
	{
		super( handler, theContainer, slot );

		theme = new Theme( ModelMessages.getMessage( Theme.DEFAULT_THEME_NAME ) );
		addToSlot( container, slotID, theme );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.util.AbstractParseState#startElement(java.lang.String)
	 */

	public AbstractParseState startElement( String tagName )
	{
		if ( tagName.equalsIgnoreCase( DesignSchemaConstants.STYLE_TAG ) )
			return new StyleState( handler, theme, Theme.STYLES_SLOT );
		return super.startElement( tagName );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.parser.DesignParseState#getElement()
	 */

	public DesignElement getElement( )
	{
		return theme;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.util.AbstractParseState#end()
	 */

	public void end( ) throws SAXException
	{
		// set the default theme to the library.

		DesignElement container = theme.getContainer( );
		assert container instanceof Library;

		( (Library) container ).setProperty( IModuleModel.THEME_PROP,
				new ElementRefValue( null, theme ) );

		super.end( );
	}
}