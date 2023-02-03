<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- <xsl:import href="hash.xslf"/> -->
	
  <xsl:output method="xml" indent="yes" encoding="utf-8" omit-xml-declaration="no"/>     

	<xsl:template match="alert">
		<rss version="2.0">
			<channel>
				<title>xymedia RSS Feed for <xsl:value-of select="user/user-name"/></title>
				<link>http://xy.media/</link>
				<description>A Feed of requested news items from xymedia</description>
				<lastBuildDate><xsl:value-of select="alert-date/default-date"/></lastBuildDate>
				<language>en-gb</language>
				<!-- <xsl:apply-templates select="//hit|//also"/>-->
			</channel>
		</rss>
	</xsl:template>
	
<!-- 	<xsl:template match="hit|also">
		<xsl:variable name="id" select="@article-id"/>
		<item>
			<title><xsl:value-of select="//article[@id=$id]/headline"/></title>
			<link><xsl:apply:templates select="//article[@id=$id]" mode="link"/></link>
			<guid><xsl:apply:templates select="//article[@id=$id]" mode="link"/></guid>
			<pubDate>Mon, 12 Sep 2005 18:37:00 GMT</pubDate>
			<description>[CDATA[ <xsl:value-of select="summary"/><br />
			 
	            <xsl:apply-templates select="//article[@id=$id]" mode="attr"/>
			]]</description>        
			
		</item>
	</xsl:template>
	-->
 <!-- 
	<xsl:template name="article[@class='Cutting']" mode="attr">
		<i><xsl:value-of select="source/name"/><xsl:if test="section != 'Main'"> (<xsl:value-of select="section"/>)</xsl:if>, <xsl:value-of select="article-date/default-date"/>, page <xsl:value-of select="page"/><xsl:if test="edition != 'First'">, <xsl:value-of select="edition"/></xsl:if></i>
	</xsl:template>
	
	<xsl:template name="article[@class='Webpage']" mode="attr"> 
		<i><xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/></i>
	</xsl:template>

	<xsl:template name="article[@class='RSS Item']" mode="attr">
		<i><xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/></i>
	</xsl:template>

	<xsl:template name="article" mode="attr">
		<i><xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/></i>
	</xsl:template>
-->
	<!-- Template for Cuttings -->
<!-- 	<xsl:template match="article[@class='Cutting']" mode="link">
                <xsl:choose>
                	<xsl:when test="@withdrawn = 'true'">
                		<xsl:call-template name="cutting-nolink"/>
                	</xsl:when>
                    <xsl:when test="@nlaid and /alert/user/@nlaid and ((source/copyright = 'NI' and /alert/user/@exclude-ni = 'false') or source/copyright != 'NI')">
						<xsl:call-template name="cutting-link"/>
                    </xsl:when>
                    <xsl:when test="source/copyright/@restricted = 'true' or (/alert/user/@nlaid and /alert/user/@exclude-ni = 'true' and source/copyright = 'NI')">
						<xsl:call-template name="cutting-faxback"/>
                    </xsl:when>
                    <xsl:when test="file != ''">
						<xsl:call-template name="cutting-link"/>
                    </xsl:when>
                    <xsl:otherwise>
                		<xsl:call-template name="cutting-nolink"/>
                    </xsl:otherwise>
                </xsl:choose>
	</xsl:template>
-->
	<!-- A Cutting without a link -->
<!-- 	<xsl:template name="cutting-nolink">
	</xsl:template>
-->
	<!-- A Cutting with a link -->
<!-- 	<xsl:template name="cutting-link">
		<xsl:variable name="id" select="@id"/>
		<xsl:variable name="hit-id" select="//sort-section//*[@article-id=$id]/@hit-id"/>
		<xsl:variable name="hash-value">
			<xsl:call-template name="tobase">
				<xsl:with-param name="number">
					<xsl:call-template name="hash">
						<xsl:with-param name="hash">0</xsl:with-param>
						<xsl:with-param name="string"><xsl:value-of select="/alert/user/login-name"/><xsl:value-of select="/alert/user/password"/>/action/clip?id=<xsl:value-of select="$hit-id"/></xsl:with-param>
					</xsl:call-template>
				</xsl:with-param>
				<xsl:with-param name="base">16</xsl:with-param>
			</xsl:call-template>
 		</xsl:variable>
		http://xymedia.co.uk/phoenix/action/auto-login?user=<xsl:value-of select="/alert/user/login-name"/>&amp;hash=<xsl:value-of select="$hash-value"/>&amp;url=/action/clip?id=<xsl:value-of select="$hit-id"/>
	</xsl:template>
	-->
	<!-- A Cutting with a faxback link -->
<!-- 	<xsl:template name="cutting-faxback">
		<xsl:variable name="id" select="@id"/>
		<xsl:variable name="hit-id" select="//sort-section//*[@article-id=$id]/@hit-id"/>
		<xsl:variable name="fax-hash">
			<xsl:call-template name="tobase">
				<xsl:with-param name="number">
					<xsl:call-template name="hash">
						<xsl:with-param name="hash">0</xsl:with-param>
						<xsl:with-param name="string"><xsl:value-of select="/alert/user/login-name"/><xsl:value-of select="/alert/user/password"/>/action/tool?fwd=faxback&amp;selectForm=true&amp;select=<xsl:value-of select="$hit-id"/></xsl:with-param>
					</xsl:call-template>
				</xsl:with-param>
				<xsl:with-param name="base">16</xsl:with-param>
			</xsl:call-template>
 		</xsl:variable>
    	http://xymedia.co.uk/phoenix/action/auto-login?user=<xsl:value-of select="/alert/user/login-name"/>&amp;hash=<xsl:value-of select="$fax-hash"/>&amp;url=/action/tool?fwd=faxback~selectForm=true~select=<xsl:value-of select="$hit-id"/>
	</xsl:template>
-->
	<!-- Template for Webpages -->
<!-- 	<xsl:template match="article[@class='Webpage']" mode="link">
            <xsl:choose>
              	<xsl:when test="@withdrawn = 'true'">
					<xsl:call-template name="webpage-nolink"/>
               	</xsl:when>
               	<xsl:otherwise>
					<xsl:call-template name="webpage-link"/>
				</xsl:otherwise>
			</xsl:choose>
	</xsl:template>
-->	
	<!-- A Webpage with no link -->
<!-- 	<xsl:template name="webpage-nolink">
	</xsl:template>
-->
	<!-- A Webpage with a link -->
<!-- 	<xsl:template name="webpage-link">
		<xsl:value-of select="url"/>
	</xsl:template>
-->
	<!-- Template for RSS Items -->
<!-- 	<xsl:template match="article[@class='RSS Item']" mode="link">
            <xsl:choose>
              	<xsl:when test="@withdrawn = 'true'">
					<xsl:call-template name="rssitem-nolink"/>
               	</xsl:when>
               	<xsl:otherwise>
					<xsl:call-template name="rssitem-link"/>
				</xsl:otherwise>
			</xsl:choose>
		</i>
	</xsl:template>
-->
	<!-- An RSS Item with no link -->
<!-- 	<xsl:template name="rssitem-nolink">
	</xsl:template>
-->
	<!-- An RSS Item with a link -->
<!-- 	<xsl:template name="rssitem-link">
		<xsl:value-of select="link"/>
	</xsl:template>
-->
	<!-- Default template for undefined Article types -->
<!-- 	<xsl:template match="article" mode="link">
            <xsl:choose>
              	<xsl:when test="@withdrawn = 'true'">
					<xsl:call-template name="article-nolink"/>
               	</xsl:when>
               	<xsl:otherwise>
					<xsl:call-template name="article-nolink"/>		            <xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/>
	            </xsl:otherwise>
            </xsl:choose>
	</xsl:template>

	<xsl:template name="article-nolink">
	</xsl:template>
--></xsl:stylesheet>
