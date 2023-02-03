<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	
	<xsl:import href="htmlshares.xslf"/>
	<xsl:import href="htmlheader-footer.xslf"/>
	<xsl:import href="htmlsorting.xslf"/>

    <xsl:output method="xml" indent="yes" encoding="utf-8" omit-xml-declaration="yes"/>     

	<xsl:template match="alert">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                <title><xsl:call-template name="title"/></title>
				<xsl:call-template name="shares-style"/>
            </head>
            <body style="font-family: Arial,sans-serif; font-size: 10pt">
				<xsl:call-template name="header"/>
				<xsl:call-template name="view-sections"/>
				<xsl:apply-templates select="shares"/>
                <xsl:apply-templates select="sorting"/>
				<xsl:call-template name="footer"/>
            </body>
        </html>
    </xsl:template>
    
	<!-- Overridden to output a View Section link -->
	<xsl:template match="sort-section">
		<h3><xsl:value-of select="@name"/> (<xsl:value-of select="@count"/>)</h3>
		<p>
		<xsl:call-template name="view-section-link">
			<xsl:with-param name="id"><xsl:value-of select="@id"/></xsl:with-param>
			<xsl:with-param name="name">View Section</xsl:with-param>
		</xsl:call-template>
		</p>
		<xsl:apply-templates select="hit"/>
	</xsl:template>		
</xsl:stylesheet>