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

package org.eclipse.birt.report.designer.internal.ui.views.attributes.page;

import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.ColorPropertyDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.ComboPropertyDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.ElementIdDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.FontSizePropertyDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.FontStylePropertyDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.IDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.PropertyDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.SimpleComboPropertyDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.TextPropertyDescriptorProvider;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.section.ColorSection;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.section.ComboSection;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.section.FontSizeSection;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.section.FontStyleSection;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.section.Section;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.section.SeperatorSection;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.section.SimpleComboSection;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.section.TextSection;
import org.eclipse.birt.report.model.api.ReportItemHandle;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.TextItemHandle;
import org.eclipse.birt.report.model.api.elements.ReportDesignConstants;
import org.eclipse.swt.SWT;

/**
 * The general attribute page of Text element.
 */
public class TextPage extends GeneralPage
{

	void buildContent( )
	{
		// Defines providers.

		IDescriptorProvider nameProvider = new TextPropertyDescriptorProvider( ReportItemHandle.NAME_PROP,
				ReportDesignConstants.TEXT_ITEM );

		IDescriptorProvider contentTypeProvider = new ComboPropertyDescriptorProvider( TextItemHandle.CONTENT_TYPE_PROP,
				ReportDesignConstants.TEXT_ITEM );

		IDescriptorProvider styleProvider = new SimpleComboPropertyDescriptorProvider( ReportItemHandle.STYLE_PROP,
				ReportDesignConstants.REPORT_ITEM );

		IDescriptorProvider fontFamilyProvider = new ComboPropertyDescriptorProvider( StyleHandle.FONT_FAMILY_PROP,
				ReportDesignConstants.STYLE_ELEMENT );

		IDescriptorProvider fontSizeProvider = new FontSizePropertyDescriptorProvider( StyleHandle.FONT_SIZE_PROP,
				ReportDesignConstants.STYLE_ELEMENT );

		IDescriptorProvider colorProvider = new ColorPropertyDescriptorProvider( StyleHandle.COLOR_PROP,
				ReportDesignConstants.STYLE_ELEMENT );

		IDescriptorProvider bgColorProvider = new ColorPropertyDescriptorProvider( StyleHandle.BACKGROUND_COLOR_PROP,
				ReportDesignConstants.STYLE_ELEMENT );

		IDescriptorProvider[] fontStyleProviders = createFontStyleProviders( );

		// Defines sections.

		TextSection nameSection = new TextSection( nameProvider.getDisplayName( ),
				container,
				true );

		Section seperator1Section = new SeperatorSection( container,
				SWT.HORIZONTAL );

		ComboSection contentTypeSection = new ComboSection( contentTypeProvider.getDisplayName( ),
				container,
				true );

		SimpleComboSection styleSection = new SimpleComboSection( styleProvider.getDisplayName( ),
				container,
				true );

		ComboSection fontFamilySection = new ComboSection( fontFamilyProvider.getDisplayName( ),
				container,
				true );

		FontSizeSection fontSizeSection = new FontSizeSection( fontSizeProvider.getDisplayName( ),
				container,
				true );

		ColorSection colorSection = new ColorSection( colorProvider.getDisplayName( ),
				container,
				true );

		ColorSection bgColorSection = new ColorSection( bgColorProvider.getDisplayName( ),
				container,
				true );

		FontStyleSection fontStyleSection = new FontStyleSection( container,
				true,
				true );

		// Sets providers.

		nameSection.setProvider( nameProvider );
		contentTypeSection.setProvider( contentTypeProvider );
		styleSection.setProvider( styleProvider );
		fontFamilySection.setProvider( fontFamilyProvider );
		fontSizeSection.setProvider( fontSizeProvider );
		colorSection.setProvider( colorProvider );
		bgColorSection.setProvider( bgColorProvider );
		fontStyleSection.setProviders( fontStyleProviders );

		// Sets widths.

		nameSection.setWidth( 200 );
		contentTypeSection.setWidth( 200 );
		styleSection.setWidth( 200 );
		fontFamilySection.setWidth( 200 );
		fontSizeSection.setWidth( 200 );
		colorSection.setWidth( 200 );
		bgColorSection.setWidth( 200 );
		fontStyleSection.setWidth( 200 );

		// Sets layout num.

		nameSection.setLayoutNum( 2 );
		contentTypeSection.setLayoutNum( 2 );
		styleSection.setLayoutNum( 4 );
		fontFamilySection.setLayoutNum( 2 );
		fontSizeSection.setLayoutNum( 4 );
		colorSection.setLayoutNum( 2 );
		bgColorSection.setLayoutNum( 4 );
		fontStyleSection.setLayoutNum( 6 );

		// Sets fill grid num.

		nameSection.setGridPlaceholder( 0, true );
		contentTypeSection.setGridPlaceholder( 0, true );
		styleSection.setGridPlaceholder( 2, true );
		fontFamilySection.setGridPlaceholder( 0, true );
		fontSizeSection.setGridPlaceholder( 2, true );
		colorSection.setGridPlaceholder( 0, true );
		bgColorSection.setGridPlaceholder( 2, true );
		fontStyleSection.setGridPlaceholder( 3, true );

		// Adds sections into container page.

		addSection( PageSectionId.TEXT_NAME, nameSection ); //$NON-NLS-1$

		ElementIdDescriptorProvider elementIdProvider = new ElementIdDescriptorProvider( );
		TextSection elementIdSection = new TextSection( elementIdProvider.getDisplayName( ),
				container,
				true );
		elementIdSection.setProvider( elementIdProvider );
		elementIdSection.setWidth( 200 );
		elementIdSection.setLayoutNum( 4 );
		elementIdSection.setGridPlaceholder( 2, true );
		addSection( PageSectionId.TEXT_ELEMENT_ID, elementIdSection );

		addSection( PageSectionId.TEXT_SEPERATOR_1, seperator1Section ); //$NON-NLS-1$
		addSection( PageSectionId.TEXT_CONTENT_TYPE, contentTypeSection ); //$NON-NLS-1$
		addSection( PageSectionId.TEXT_STYLE, styleSection ); //$NON-NLS-1$
		addSection( PageSectionId.TEXT_FONT_FAMILY, fontFamilySection ); //$NON-NLS-1$
		addSection( PageSectionId.TEXT_FONT_SIZE, fontSizeSection ); //$NON-NLS-1$
		addSection( PageSectionId.TEXT_COLOR, colorSection ); //$NON-NLS-1$
		addSection( PageSectionId.TEXT_BACKGROUND_COLOR, bgColorSection ); //$NON-NLS-1$
		addSection( PageSectionId.TEXT_FONT_STYLE, fontStyleSection ); //$NON-NLS-1$

		createSections( );
		layoutSections( );
	}

	/**
	 * Creates provider's array for font style controls.
	 * 
	 * @return the provider's array(elements are instances of
	 *         <code>IDescriptorProvider</code>).
	 */
	private IDescriptorProvider[] createFontStyleProviders( )
	{
		return new IDescriptorProvider[]{
				// Creates providers with StyleHandle.FONT_WEIGHT_PROP,
				// StyleHandle.FONT_STYLE_PROP, StyleHandle.TEXT_UNDERLINE_PROP,
				// StyleHandle.TEXT_LINE_THROUGH_PROP and
				// StyleHandle.TEXT_ALIGN_PROP.

				new FontStylePropertyDescriptorProvider( StyleHandle.FONT_WEIGHT_PROP,
						ReportDesignConstants.STYLE_ELEMENT ),

				new FontStylePropertyDescriptorProvider( StyleHandle.FONT_STYLE_PROP,
						ReportDesignConstants.STYLE_ELEMENT ),

				new FontStylePropertyDescriptorProvider( StyleHandle.TEXT_UNDERLINE_PROP,
						ReportDesignConstants.STYLE_ELEMENT ),

				new FontStylePropertyDescriptorProvider( StyleHandle.TEXT_LINE_THROUGH_PROP,
						ReportDesignConstants.STYLE_ELEMENT ),

				new PropertyDescriptorProvider( StyleHandle.TEXT_ALIGN_PROP,
						ReportDesignConstants.STYLE_ELEMENT )
		// bidi_hcg
		};
	}
}
