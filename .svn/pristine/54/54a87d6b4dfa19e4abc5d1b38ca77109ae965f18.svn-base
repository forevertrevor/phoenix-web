<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	
  <xsl:output method="xml" indent="yes" encoding="utf-8" omit-xml-declaration="yes"/>     

	<xsl:template match="tool">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                <title>Summary of selected cuttings sent by <xsl:value-of select="recipient/from-name"/></title>
            </head>
            <body>
                <xsl:apply-templates select="recipient"/>
				<xsl:apply-templates select="sorting"/>
            </body>
        </html>
    </xsl:template>

	<xsl:template match="recipient">
		<p>Summary of selected cuttings sent by <xsl:value-of select="from-name"/></p>
        <p><xsl:value-of select="message"/></p>
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
            <xsl:value-of select="//article[@id=$id]/headline"/>
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
			<i>
                <xsl:choose>
                    <xsl:when test="@nlaid or file != ''">
						<xsl:variable name="id" select="@id"/>
						<xsl:variable name="hit-id" select="//sort-section//*[@article-id=$id]/@hit-id"/>
						<xsl:variable name="hash-value">
							<xsl:call-template name="tobase">
								<xsl:with-param name="number">
									<xsl:call-template name="hash">
										<xsl:with-param name="hash">0</xsl:with-param>
										<xsl:with-param name="string"><xsl:value-of select="/tool/user/login-name"/><xsl:value-of select="/tool/user/password"/>/action/clip?id=<xsl:value-of select="$hit-id"/></xsl:with-param>
									</xsl:call-template>
								</xsl:with-param>
								<xsl:with-param name="base">16</xsl:with-param>
							</xsl:call-template>
				 		</xsl:variable>
						<xsl:element name="a"><xsl:attribute name="href">http://xy.media/phoenix/action/auto-login?user=<xsl:value-of select="/tool/user/login-name"/>&amp;hash=<xsl:value-of select="$hash-value"/>&amp;url=/action/clip?id=<xsl:value-of select="$hit-id"/></xsl:attribute><xsl:value-of select="source/name"/><xsl:if test="section != 'Main'"> (<xsl:value-of select="section"/>)</xsl:if>, <xsl:value-of select="article-date/default-date"/>, page <xsl:value-of select="page"/><xsl:if test="edition != 'First'">, <xsl:value-of select="edition"/></xsl:if></xsl:element>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="source/name"/><xsl:if test="section != 'Main'"> (<xsl:value-of select="section"/>)</xsl:if>, <xsl:value-of select="article-date/default-date"/>, page <xsl:value-of select="page"/><xsl:if test="edition != 'First'">, <xsl:value-of select="edition"/></xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </i>
	</xsl:template>

	<xsl:template match="article[@class='Webpage']">
			<i>
                <xsl:element name="a"><xsl:attribute name="href"><xsl:value-of select="url"/></xsl:attribute><xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/></xsl:element>
            </i>
	</xsl:template>

	<xsl:template match="article">
			<i>
	            <xsl:value-of select="source/name"/>, <xsl:value-of select="article-date/default-date"/>
            </i>
	</xsl:template>

	<!-- Converts a base-10 number to the requested base -->
 	<xsl:template name="tobase">
		<xsl:param name="number"/>
		<xsl:param name="base"/>
		<xsl:param name="result"/>

		<xsl:variable name="digit" select="substring($base-digits, $number mod $base + 1, 1)"/>
		<xsl:choose>
			<xsl:when test="$number >= $base">
				<xsl:call-template name="tobase">
					<xsl:with-param name="number" select="floor($number div $base)"/>
					<xsl:with-param name="base" select="$base"/>
					<xsl:with-param name="result" select="concat($digit, $result)"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat($digit, $result)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- List of numeric digits in ascending order for bases up to base-36 -->
	<xsl:variable name="base-digits" select="'0123456789abcdefghijklmnopqrstuvwxyz'"/>

	<!-- Calculates the hash value of a String using the Java algorithm -->
 	<xsl:template name="hash">
		<xsl:param name="hash"/>
		<xsl:param name="string"/>
		<xsl:choose>
			<xsl:when test="$string">
				<xsl:variable name="char">
					<xsl:call-template name="ascii">
						<xsl:with-param name="char" select="substring($string, 1,1)"/>
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="newhash" select="($hash * 31 + $char) mod 4294967296"/>
				<xsl:call-template name="hash">
					<xsl:with-param name="hash" select="$newhash"/>
					<xsl:with-param name="string" select="substring($string, 2)"/>
		   	 	</xsl:call-template>
	   	 	</xsl:when>
	   	 	<xsl:otherwise>
	   	 		<xsl:value-of select="$hash"/>
	   	 	</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- Looks up the ascii value of a character -->
	<xsl:template name="ascii">
		<xsl:param name="char"/>
		<xsl:choose>
			<xsl:when test="$char=' '">32</xsl:when>
			<xsl:when test="$char='!'">33</xsl:when>
			<xsl:when test="$char='#'">35</xsl:when>
			<xsl:when test="$char='$'">36</xsl:when>
			<xsl:when test="$char='%'">37</xsl:when>
			<xsl:when test="$char='&amp;'">38</xsl:when>
			<xsl:when test="$char='('">40</xsl:when>
			<xsl:when test="$char=')'">41</xsl:when>
			<xsl:when test="$char='*'">42</xsl:when>
			<xsl:when test="$char='+'">43</xsl:when>
			<xsl:when test="$char=','">44</xsl:when>
			<xsl:when test="$char='-'">45</xsl:when>
			<xsl:when test="$char='.'">46</xsl:when>
			<xsl:when test="$char='/'">47</xsl:when>
			<xsl:when test="$char='0'">48</xsl:when>
			<xsl:when test="$char='1'">49</xsl:when>
			<xsl:when test="$char='2'">50</xsl:when>
			<xsl:when test="$char='3'">51</xsl:when>
			<xsl:when test="$char='4'">52</xsl:when>
			<xsl:when test="$char='5'">53</xsl:when>
			<xsl:when test="$char='6'">54</xsl:when>
			<xsl:when test="$char='7'">55</xsl:when>
			<xsl:when test="$char='8'">56</xsl:when>
			<xsl:when test="$char='9'">57</xsl:when>
			<xsl:when test="$char=':'">58</xsl:when>
			<xsl:when test="$char=';'">59</xsl:when>
			<xsl:when test="$char='&lt;'">60</xsl:when>
			<xsl:when test="$char='='">61</xsl:when>
			<xsl:when test="$char='&gt;'">62</xsl:when>
			<xsl:when test="$char='?'">63</xsl:when>
			<xsl:when test="$char='@'">64</xsl:when>
			<xsl:when test="$char='A'">65</xsl:when>
			<xsl:when test="$char='B'">66</xsl:when>
			<xsl:when test="$char='C'">67</xsl:when>
			<xsl:when test="$char='D'">68</xsl:when>
			<xsl:when test="$char='E'">69</xsl:when>
			<xsl:when test="$char='F'">70</xsl:when>
			<xsl:when test="$char='G'">71</xsl:when>
			<xsl:when test="$char='H'">72</xsl:when>
			<xsl:when test="$char='I'">73</xsl:when>
			<xsl:when test="$char='J'">74</xsl:when>
			<xsl:when test="$char='K'">75</xsl:when>
			<xsl:when test="$char='L'">76</xsl:when>
			<xsl:when test="$char='M'">77</xsl:when>
			<xsl:when test="$char='N'">78</xsl:when>
			<xsl:when test="$char='O'">79</xsl:when>
			<xsl:when test="$char='P'">80</xsl:when>
			<xsl:when test="$char='Q'">81</xsl:when>
			<xsl:when test="$char='R'">82</xsl:when>
			<xsl:when test="$char='S'">83</xsl:when>
			<xsl:when test="$char='T'">84</xsl:when>
			<xsl:when test="$char='U'">85</xsl:when>
			<xsl:when test="$char='V'">86</xsl:when>
			<xsl:when test="$char='W'">87</xsl:when>
			<xsl:when test="$char='X'">88</xsl:when>
			<xsl:when test="$char='Y'">89</xsl:when>
			<xsl:when test="$char='Z'">90</xsl:when>
			<xsl:when test="$char='['">91</xsl:when>
			<xsl:when test="$char='\'">92</xsl:when>
			<xsl:when test="$char=']'">93</xsl:when>
			<xsl:when test="$char='^'">94</xsl:when>
			<xsl:when test="$char='_'">95</xsl:when>
			<xsl:when test="$char='`'">96</xsl:when>
			<xsl:when test="$char='a'">97</xsl:when>
			<xsl:when test="$char='b'">98</xsl:when>
			<xsl:when test="$char='c'">99</xsl:when>
			<xsl:when test="$char='d'">100</xsl:when>
			<xsl:when test="$char='e'">101</xsl:when>
			<xsl:when test="$char='f'">102</xsl:when>
			<xsl:when test="$char='g'">103</xsl:when>
			<xsl:when test="$char='h'">104</xsl:when>
			<xsl:when test="$char='i'">105</xsl:when>
			<xsl:when test="$char='j'">106</xsl:when>
			<xsl:when test="$char='k'">107</xsl:when>
			<xsl:when test="$char='l'">108</xsl:when>
			<xsl:when test="$char='m'">109</xsl:when>
			<xsl:when test="$char='n'">110</xsl:when>
			<xsl:when test="$char='o'">111</xsl:when>
			<xsl:when test="$char='p'">112</xsl:when>
			<xsl:when test="$char='q'">113</xsl:when>
			<xsl:when test="$char='r'">114</xsl:when>
			<xsl:when test="$char='s'">115</xsl:when>
			<xsl:when test="$char='t'">116</xsl:when>
			<xsl:when test="$char='u'">117</xsl:when>
			<xsl:when test="$char='v'">118</xsl:when>
			<xsl:when test="$char='w'">119</xsl:when>
			<xsl:when test="$char='x'">120</xsl:when>
			<xsl:when test="$char='y'">121</xsl:when>
			<xsl:when test="$char='z'">122</xsl:when>
			<xsl:when test="$char='{'">123</xsl:when>
			<xsl:when test="$char='|'">124</xsl:when>
			<xsl:when test="$char='}'">125</xsl:when>
			<xsl:when test="$char='~'">126</xsl:when>
			<xsl:otherwise>0</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>