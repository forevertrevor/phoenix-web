<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	
  <xsl:output method="xml" indent="yes" encoding="utf-8" omit-xml-declaration="yes"/>     

	<xsl:template match="tool">
        <xsl:apply-templates select="recipient"/>
        <xsl:apply-templates select="sorting"/>
    </xsl:template>

	<xsl:template match="recipient">
	</xsl:template>

	<xsl:template match="sorting">
		<hr />
		<xsl:apply-templates select="sort-brief"/>
	</xsl:template>

    <xsl:template match="sort-brief">
        <h2><xsl:value-of select="@name"/></h2>
		<xsl:apply-templates select="sort-section"/>
    </xsl:template>
    
	<xsl:template match="sort-section">
		<h3><xsl:value-of select="@name"/></h3>
		<xsl:apply-templates select="hit"/>
	</xsl:template>		
    
    <xsl:template match="hit">
		<div>
            <br />
			<xsl:variable name="id" select="@article-id"/>
            <b><xsl:value-of select="//article[@id=$id]/headline"/></b>
    		<br />	
			<xsl:value-of select="summary"/><br />
            <xsl:apply-templates select="//article[@id=$id]"/>
			<xsl:apply-templates select="also"/>
		</div>
    </xsl:template>
    
	<xsl:template match="also">
		<xsl:if test="position() = 1">
			, 
		</xsl:if>
		<xsl:variable name="id" select="@article-id"/>
		<xsl:apply-templates select="//article[@id=$id]"/>
		<xsl:if test="position() != last()">
			, 
		</xsl:if>
	</xsl:template>

	<xsl:template match="article[@class='Cutting']">
			<i><xsl:value-of select="source/name"/><xsl:if test="section != 'Main'"> (<xsl:value-of select="section"/>)</xsl:if>, <xsl:value-of select="article-date/default-date"/>, page <xsl:value-of select="page"/><xsl:if test="edition != 'First'">, <xsl:value-of select="edition"/></xsl:if></i>
	</xsl:template>

	<xsl:template match="article[@class='Webpage']">
			<i><xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/>,<br />
			<xsl:value-of select="url"/>
			</i>
	</xsl:template>

	<xsl:template match="article">
			<i><xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/></i>
	</xsl:template>
</xsl:stylesheet>