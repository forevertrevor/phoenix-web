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
				<xsl:apply-templates select="shares"/>
                <xsl:apply-templates select="sorting"/>
				<xsl:call-template name="footer"/>
            </body>
        </html>
    </xsl:template>
	
	<!-- Override the article[Cutting] template to force nla articles to be output without a link -->
	<xsl:template match="article[@class='Cutting']">
	    <xsl:choose>
    	    <xsl:when test="@nlaid">
				<i>
					<xsl:call-template name="cutting-nolink"/>
				</i>
            </xsl:when>
            <xsl:otherwise>
            	<xsl:apply-imports/>
            </xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>