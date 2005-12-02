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

package org.eclipse.birt.report.model.command;

import org.eclipse.birt.report.model.activity.AbstractElementCommand;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ThemeException;
import org.eclipse.birt.report.model.api.core.IModuleModel;
import org.eclipse.birt.report.model.api.util.StringUtil;
import org.eclipse.birt.report.model.core.DesignElement;
import org.eclipse.birt.report.model.core.Module;
import org.eclipse.birt.report.model.elements.Theme;
import org.eclipse.birt.report.model.metadata.ElementPropertyDefn;
import org.eclipse.birt.report.model.metadata.ElementRefValue;
import org.eclipse.birt.report.model.util.ReferenceValueUtil;

/**
 * Sets the theme of the report design.
 */

public class ThemeCommand extends AbstractElementCommand
{

	/**
	 * the module that the new theme attaches
	 * 
	 */

	private Module themeRoot = null;

	/**
	 * Constructor.
	 * 
	 * @param module
	 *            the module that holds <code>ActivityStack</code>
	 * @param themeRoot
	 *            the module that the theme attaches
	 * @param element
	 *            the module to set the theme
	 */

	public ThemeCommand( Module module, Module themeRoot, DesignElement element )
	{
		super( module, element );
		this.themeRoot = themeRoot;
		assert element instanceof Module;
	}

	/**
	 * Sets the theme of an element.
	 * 
	 * @param name
	 *            the name of the theme to set.
	 * @throws SemanticException
	 *             if the element can not have theme or the theme is not found.
	 */

	public void setTheme( String name ) throws SemanticException
	{
		name = StringUtil.trimString( name );

		Module currentModule = (Module) element;
		ElementPropertyDefn propDefn = currentModule
				.getPropertyDefn( IModuleModel.THEME_PROP );

		if ( name == null && currentModule.getThemeName( ) == null )
			return;

		Object retValue = propDefn.validateValue( currentModule, name );

		doSetThemeRefValue( (ElementRefValue) retValue );
	}

	/**
	 * Sets the theme of an element given the theme itself.
	 * 
	 * @param theme
	 *            the theme element to set.
	 * @throws SemanticException
	 *             if the element can not have theme or the theme is not found.
	 */

	public void setThemeElement( Theme theme ) throws SemanticException
	{
		// Make the change starting with the name. This will handle the
		// case where the application is trying to set a theme that is
		// not part of the design.

		String name = null;
		if ( theme != null )
			name = ReferenceValueUtil.needTheNamespacePrefix( theme, themeRoot,
					(Module) element );
		setTheme( name );

	}

	/**
	 * Sets the theme with the given element reference value. Call this method
	 * when the theme name or theme element has been validated. Otherwise, uses
	 * {@link #setTheme(String)} or {@link #setThemeElement(Theme)}.
	 * 
	 * @param refValue
	 *            the validated reference value
	 * @throws SemanticException
	 *             if the element can not have theme or the theme is not found.
	 */

	protected void setThemeRefValue( ElementRefValue refValue )
			throws SemanticException
	{
		if ( refValue == null
				&& ( (Module) element ).getThemeName( ) == null )
			return;
		
		doSetThemeRefValue( refValue );
	}

	/**
	 * Do the work to set the new theme with the given
	 * <code>newThemeValue</code>.
	 * 
	 * @param newThemeValue
	 *            the validated <code>ElementRefValue</code>
	 */

	private void doSetThemeRefValue( ElementRefValue newThemeValue )
			throws SemanticException
	{
		if ( newThemeValue != null && !newThemeValue.isResolved( ) )
		{
			String name = ReferenceValueUtil.needTheNamespacePrefix(
					newThemeValue, (Module) element );
			throw new ThemeException( element, name,
					ThemeException.DESIGN_EXCEPTION_NOT_FOUND );
		}

		if ( newThemeValue != null
				&& newThemeValue.isResolved( )
				&& newThemeValue.getElement( ) == ( (Module) element )
						.getTheme( ) )
			return;

		// adjust the back references for styles in the theme

		ThemeRecord themeRecord = new ThemeRecord( (Module) element,
				newThemeValue );
		getModule( ).getActivityStack( ).execute( themeRecord );
	}
}