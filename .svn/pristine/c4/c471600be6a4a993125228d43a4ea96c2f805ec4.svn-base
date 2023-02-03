<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output method="xml" indent="yes" encoding="utf-8" omit-xml-declaration="yes"/>     

	<xsl:template match="shares">
		<h1>Share Prices</h1>
		<table>
			<tr>
				<th>Symbol</th>
				<th>Exchange</th>
				<th>Last Trade</th>
				<th>Change</th>
				<th>Volume</th>
			</tr>
			<xsl:apply-templates select="share"/>
		</table>
	</xsl:template>

	<xsl:template match="share">
		<tr>
			<td><xsl:value-of select="name"/> (<xsl:value-of select="symbol"/>)</td>
			<td><xsl:value-of select="exchange"/></td>
			<td><xsl:value-of select="last-trade-time" disable-output-escaping="yes"/></td>
			<td><xsl:value-of select="change-percent"/></td>
			<td><xsl:value-of select="volume"/></td>
		</tr>
	</xsl:template>

</xsl:stylesheet>