<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:strip-space elements="*"/>
<xsl:output omit-xml-declaration="yes" indent="yes"/>

<xsl:template match="topic">
    <xsl:if test="number = $id">
        <xsl:value-of select="title"/>
    </xsl:if>
</xsl:template>

</xsl:stylesheet>
