<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    
	<xsl:output method="xml" indent="yes" encoding="utf-8" omit-xml-declaration="yes"/>     

	<xsl:template match="rss">
		<table class="events">
			<xsl:apply-templates select="channel"/>
		</table>
	</xsl:template>

	<xsl:template match="channel">
		<tr class="header"><td><b>Headlines from <a href="http://news.bbc.co.uk">BBC News</a></b></td></tr>
		<xsl:apply-templates select="item[position() &lt;= 4]"/>
	</xsl:template>

	<xsl:template match="item">
		<tr class="title"><td><hr /><b>
		<xsl:element name="a">
			<xsl:attribute name="href">
				<xsl:value-of select="link"/>
			</xsl:attribute>
			<xsl:value-of select="title"/>
		</xsl:element>
		</b></td></tr>
			
		<tr class="desc"><td><xsl:value-of select="description" disable-output-escaping="yes" /></td></tr>
		<tr class="date"><td><i><xsl:value-of select="pubDate"/></i></td></tr>
	</xsl:template>

	<xsl:template name="formatdate">
		<xsl:param name="date"/>
		<xsl:variable name="day" select="substring($date,9,2)"/>
		<xsl:variable name="month" select="substring($date,6,2)"/>
		<xsl:variable name="year" select="substring($date,1,4)"/>
		<xsl:variable name="dow">
			<xsl:call-template name="dow">
				<xsl:with-param name="month" select="$month"/>
				<xsl:with-param name="year" select="$year"/>
				<xsl:with-param name="day" select="$day"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:call-template name="dayname">
			<xsl:with-param name="dow" select="$dow"/>
			<xsl:with-param name="abbr" select="'false'"/>
		</xsl:call-template>
		<xsl:text> </xsl:text>
		<xsl:value-of select="$day"/>
		<xsl:text> </xsl:text>
		<xsl:call-template name="monthname">
			<xsl:with-param name="month" select="$month"/>
			<xsl:with-param name="abbr" select="'true'"/>
		</xsl:call-template>
		<xsl:text> </xsl:text>
		<xsl:value-of select="$year"/>
	</xsl:template>

	<xsl:template name="dow">
		<xsl:param name="year"/>
		<xsl:param name="month"/>
		<xsl:param name="day"/>

		<xsl:variable name="a" select="floor((14 - $month) div 12)"/>
		<xsl:variable name="y" select="$year - $a"/>
		<xsl:variable name="m" select="$month + 12 * $a - 2"/>
		<xsl:value-of select="($day + $y + floor($y div 4) - floor($y div 100) + floor($y div 400) + floor((31 * $m) div 12)) mod 7"/>
	</xsl:template>

	<xsl:template name="monthname">
		<xsl:param name="month"/>
		<xsl:param name="abbr"/>
		<xsl:choose>
		<xsl:when test="$abbr = 'true'">
			<xsl:choose>
				<xsl:when test="$month=1">Jan</xsl:when>
				<xsl:when test="$month=2">Feb</xsl:when>
				<xsl:when test="$month=3">Mar</xsl:when>
				<xsl:when test="$month=4">Apr</xsl:when>
				<xsl:when test="$month=5">May</xsl:when>
				<xsl:when test="$month=6">Jun</xsl:when>
				<xsl:when test="$month=7">Jul</xsl:when>
				<xsl:when test="$month=8">Aug</xsl:when>
				<xsl:when test="$month=9">Sep</xsl:when>
				<xsl:when test="$month=10">Oct</xsl:when>
				<xsl:when test="$month=11">Nov</xsl:when>
				<xsl:when test="$month=12">Dec</xsl:when>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:choose>
				<xsl:when test="$month=1">January</xsl:when>
				<xsl:when test="$month=2">February</xsl:when>
				<xsl:when test="$month=3">March</xsl:when>
				<xsl:when test="$month=4">April</xsl:when>
				<xsl:when test="$month=5">May</xsl:when>
				<xsl:when test="$month=6">June</xsl:when>
				<xsl:when test="$month=7">July</xsl:when>
				<xsl:when test="$month=8">August</xsl:when>
				<xsl:when test="$month=9">September</xsl:when>
				<xsl:when test="$month=10">October</xsl:when>
				<xsl:when test="$month=11">November</xsl:when>
				<xsl:when test="$month=12">December</xsl:when>
			</xsl:choose>
		</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="dayname">
		<xsl:param name="dow"/>
		<xsl:param name="abbr"/>
		<xsl:choose>
		<xsl:when test="$abbr = 'true'">
			<xsl:choose>
				<xsl:when test="$dow = 0">Sun</xsl:when>
				<xsl:when test="$dow = 1">Mon</xsl:when>
				<xsl:when test="$dow = 2">Tue</xsl:when>
				<xsl:when test="$dow = 3">Wed</xsl:when>
				<xsl:when test="$dow = 4">Thu</xsl:when>
				<xsl:when test="$dow = 5">Fri</xsl:when>
				<xsl:when test="$dow = 6">Sat</xsl:when>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:choose>
				<xsl:when test="$dow = 0">Sunday</xsl:when>
				<xsl:when test="$dow = 1">Monday</xsl:when>
				<xsl:when test="$dow = 2">Tuesday</xsl:when>
				<xsl:when test="$dow = 3">Wednesday</xsl:when>
				<xsl:when test="$dow = 4">Thursday</xsl:when>
				<xsl:when test="$dow = 5">Friday</xsl:when>
				<xsl:when test="$dow = 6">Saturday</xsl:when>
			</xsl:choose>
		</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>