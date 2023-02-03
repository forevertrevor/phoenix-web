<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	
	<xsl:import href="plainshares.xslf"/>
	<xsl:import href="plainheader-footer.xslf"/>
	<xsl:import href="plainsorting.xslf"/>
	<xsl:output method="text" indent="no" encoding="utf-8" omit-xml-declaration="yes"/>     

    <xsl:template match="alert">
<xsl:call-template name="header"/>
<xsl:apply-templates select="shares"/>
<xsl:apply-templates select="sorting"/>
<xsl:call-template name="footer"/>
    </xsl:template>
</xsl:stylesheet>
