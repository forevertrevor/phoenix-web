<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	
  <xsl:output method="text" indent="no" encoding="utf-8" omit-xml-declaration="yes"/>     

<xsl:variable name="doubleline">
  <xsl:text>================================================================================================================</xsl:text>
</xsl:variable>    
<xsl:variable name="underline">
  <xsl:text>----------------------------------------------------------------------------------------------------------------</xsl:text>
</xsl:variable>    
<xsl:variable name="n">
    <xsl:text>
</xsl:text>
</xsl:variable>
            
    <xsl:template match="alert">
<xsl:value-of select="user/user-name"/> - Email Alert - <xsl:value-of select="alert-date/dd"/>-<xsl:value-of select="alert-date/mmm"/>-<xsl:value-of select="alert-date/yyyy"/>, <xsl:value-of select="alert-date/hh"/>:<xsl:value-of select="alert-date/nn"/><xsl:value-of select="$n"/>
<xsl:value-of select="substring($doubleline, 1, string-length(user/user-name) + 35)"/>
<xsl:value-of select="$n"/>
<xsl:apply-templates select="sorting"/>

Prepared by xymedia - mail@mediagen.co.uk
    </xsl:template>
    
	<xsl:template match="sorting">
		<xsl:apply-templates select="sort-brief"/>
	</xsl:template>

    <xsl:template match="sort-brief">
<xsl:value-of select="$n"/>
<xsl:value-of select="@name"/><xsl:value-of select="$n"/>
<xsl:value-of select="substring($doubleline, 1, string-length(@name))"/>
        
		<xsl:apply-templates select="sort-section"/>
<xsl:value-of select="$n"/>
    </xsl:template>
    
	<xsl:template match="sort-section">
<xsl:value-of select="$n"/>
<xsl:value-of select="@name"/> (<xsl:value-of select="@count"/>)
<xsl:value-of select="substring($underline, 1, string-length(@name) + string-length(@count) + 3)"/>
    <xsl:apply-templates select="hit"/>
	</xsl:template>		

	<xsl:template match="hit">
			<xsl:variable name="id" select="@article-id"/>
<xsl:value-of select="$n"/>
- <xsl:value-of select="//article[@id=$id]/headline"/><xsl:value-of select="$n"/>
<xsl:value-of select="summary"/><xsl:value-of select="$n"/>
        			<xsl:apply-templates select="//article[@id=$id]"/>
			<xsl:apply-templates select="also"/>
	</xsl:template>
	
	<xsl:template match="also">
		<xsl:if test="position() = 1"><xsl:text>
 </xsl:text>also mentioned in:
</xsl:if>
		<xsl:variable name="id" select="@article-id"/>
		<xsl:apply-templates select="//article[@id=$id]"/>
		<xsl:if test="position() != last()"><xsl:text>
</xsl:text>
</xsl:if>
	</xsl:template>

	<xsl:template match="article[@class='Cutting']">
<xsl:value-of select="source/name"/><xsl:if test="section != 'Main'"> (<xsl:value-of select="section"/>)</xsl:if>, <xsl:value-of select="article-date/default-date"/>, page <xsl:value-of select="page"/><xsl:if test="edition != 'First'">, <xsl:value-of select="edition"/></xsl:if>
</xsl:template>

	<xsl:template match="article[@class='Webpage']">
<xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/>
</xsl:template>

	<xsl:template match="article">
<xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/>
	</xsl:template>

</xsl:stylesheet>
