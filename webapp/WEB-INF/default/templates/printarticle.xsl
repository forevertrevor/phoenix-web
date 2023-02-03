<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">
	
    <xsl:output method="xml" indent="yes" encoding="utf-8" omit-xml-declaration="yes"/>     

	<xsl:template match="tool">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
                    <fo:region-body margin-bottom="1.25cm"/>
                    <fo:region-after extent="1.25cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center" font-size="9pt" padding-top="5mm">
                        <fo:leader leader-length="100%" leader-pattern="rule" rule-style="solid" rule-thickness="1px" />
                        This summary is compiled for <xsl:value-of select="user/user-name"/> by xymedia mail@xy.media
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="20pt" font-weight="bold" text-align="center">
                        Press Index
                    </fo:block>
                    <xsl:apply-templates select="sorting"/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
    <xsl:template match="sorting">
        <xsl:apply-templates select="sort-brief"/>
    </xsl:template>

	<xsl:template match="sort-brief">
		<xsl:apply-templates select="sort-section"/>
	</xsl:template>

	<xsl:template match="sort-section">
		<fo:block font-weight="bold" font-size="12pt" text-decoration="underline" padding-top="1mm">
            <xsl:value-of select="@name"/>
        </fo:block>
        <fo:table table-layout="fixed">
            <fo:table-column column-width="1.25cm"/>
            <fo:table-column column-width="14.75cm"/>
            <fo:table-body>
            	<xsl:apply-templates select="hit"/>
            </fo:table-body>
        </fo:table>
	</xsl:template>		

	<xsl:template match="hit">
		<xsl:variable name="id" select="@article-id"/>
        <fo:table-row>
            <fo:table-cell>
                <fo:block font-size="10pt">
                    <xsl:choose>
                        <xsl:when test="@page = '0'">
                            -
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="@page"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
            		<fo:inline font-size="10pt">
                        <xsl:value-of select="summary"/>
                    </fo:inline>
                    <xsl:apply-templates select="//article[@id=$id]"/>
                    <xsl:apply-templates select="also"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
	</xsl:template>
	
	<xsl:template match="also">
		<xsl:if test="position() = 1">
			<fo:inline font-size="10pt"> also mentioned in: </fo:inline>
		</xsl:if>
		<xsl:variable name="id" select="@article-id"/>
		<xsl:apply-templates select="//article[@id=$id]" mode="also"/>
		<xsl:if test="position() != last()">, </xsl:if>
	</xsl:template>

	<xsl:template match="article[@class='Cutting']">
			<fo:inline font-size="10pt">
                <xsl:text> </xsl:text><xsl:value-of select="source/name"/> p<xsl:value-of select="page"/>
            </fo:inline>
	</xsl:template>

	<xsl:template match="article">
			<fo:inline font-size="10pt">
                <xsl:text> </xsl:text><xsl:value-of select="source/name"/>
            </fo:inline>
	</xsl:template>

	<xsl:template match="article[@class='Cutting']" mode="also">
			<fo:inline font-size="10pt">
                <xsl:value-of select="source/name"/> p<xsl:value-of select="page"/>
            </fo:inline>
	</xsl:template>

	<xsl:template match="article" mode="also">
			<fo:inline font-size="10pt">
                <xsl:value-of select="source/name"/>
            </fo:inline>
	</xsl:template>
    
</xsl:stylesheet>