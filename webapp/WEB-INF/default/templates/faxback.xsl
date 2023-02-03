<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	
	<xsl:import href="htmlsorting.xslf"/>
	
	<xsl:output method="xml" indent="yes" encoding="utf-8" omit-xml-declaration="yes"/>     

	<xsl:template match="tool">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                <title>Faxback Request</title>
            </head>
            <body>
                <xsl:apply-templates select="recipient"/>
				<xsl:apply-templates select="sorting"/>
            </body>
        </html>
    </xsl:template>

	<xsl:template match="recipient">
		<p><xsl:value-of select="from-name"/> has requested the following articles to be faxed to them on <xsl:value-of select="to-address"/></p>
	</xsl:template>
</xsl:stylesheet>