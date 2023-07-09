<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:msxsl="urn:schemas-microsoft-com:xslt" exclude-result-prefixes="msxsl">
    <xsl:output method="xml" indent="yes"/>

    <!-- Center align the elements in the ID column -->
    <xsl:template match="fo:table-cell[fo:block = 'ID']">
        <xsl:copy>
            <xsl:attribute name="text-align">center</xsl:attribute>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <!-- Left align the elements in the Name column -->
    <xsl:template match="fo:table-cell[fo:block = 'Name']">
        <xsl:copy>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <!-- Center align the elements in the Unit Price column -->
    <xsl:template match="fo:table-cell[fo:block = 'Unit Price']">
        <xsl:copy>
            <xsl:attribute name="text-align">center</xsl:attribute>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <!-- Copy all other nodes and attributes unchanged -->
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="/">
        <fo:root background-color="#E3F4F4">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-height="29.7cm" page-width="21.0cm" margin-top="1cm" margin-left="2cm" margin-right="2cm" margin-bottom="1cm">
                    <!-- Page template goes here -->
                    <fo:region-body />
                    <fo:region-before region-name="xsl-region-before" extent="3cm"/>
                    <fo:region-after region-name="xsl-region-after" extent="4cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <!-- Page content goes here -->
                <fo:static-content flow-name="xsl-region-before">
<!--                    <fo:block text-align="center">-->
<!--                        <fo:external-graphic src="url('../increff.png')" width="6cm" content-height="3cm"/>-->
<!--                    </fo:block>-->
                    <fo:block >
                        <fo:table>
                            <fo:table-column column-width="16cm"/>
                            <fo:table-body>
                                <fo:table-row font-size="21pt" line-height="30px" background-color="#C4DFDF" color="black">
                                    <fo:table-cell>
                                        <fo:block text-align="center">
                                            INVOICE
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body" line-height="20pt">
                    <xsl:apply-templates />
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="invoice">
        <fo:block></fo:block>
        <fo:block space-before="120pt" width="17cm" >
            <fo:table>
                <fo:table-column column-width="5.5cm"/>
                <fo:table-column column-width="5.5cm"/>
                <fo:table-body>
                    <fo:table-row>
                        <fo:table-cell>
                            <fo:block text-align="left">
                                <fo:inline font-weight="bold">Invoice #</fo:inline>&#x2028;
                                <fo:inline font-weight="bold">Invoice Date</fo:inline>&#x2028;
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell>
                            <fo:block text-align="left">
                                <xsl:value-of select="./order_id"/>
                                <xsl:text>&#x2028;</xsl:text>
                                <xsl:value-of select="./order_date"/>
                                <xsl:text>&#x2028;</xsl:text>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>
        </fo:block>
        <fo:block space-before="35pt">
            <fo:table line-height="30px">
                <fo:table-column column-width="2cm"/>
                <fo:table-column column-width="5.5cm"/>
                <fo:table-column column-width="2cm"/>
                <fo:table-column column-width="3cm"/>
                <fo:table-column column-width="3cm"/>
                <fo:table-header>
                    <fo:table-row background-color="#C4DFDF" text-align="center" font-weight="bold">
                        <fo:table-cell border="1px">
                            <fo:block>ID</fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="1px">
                            <fo:block>Name</fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="1px">
                            <fo:block>QTY</fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="1px">
                            <fo:block>Unit Price</fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="1px">
                            <fo:block>AMOUNT</fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-header>
                <fo:table-body>
                    <xsl:apply-templates select="order_item"/>
                    <fo:table-row font-weight="bold">
                        <fo:table-cell number-columns-spanned="4" text-align="right" padding-right="3pt">
                            <fo:block>Total</fo:block>
                        </fo:table-cell>
                        <fo:table-cell text-align="center" padding-right="3pt" background-color="#F8F6F4" border="1px">
                            <fo:block background-color="#E3F4F4">
                                <xsl:value-of select="amount"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

    <xsl:template match="order_item">
        <fo:table-row  background-color="#E3F4F4">
            <fo:table-cell border="1px" padding-left="3pt" text-align="center">
                <fo:block>
                    <xsl:value-of select="id"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell border="1px" padding-left="3pt" text-align="center">
                <fo:block>
                    <xsl:value-of select="product_name"/>
                </fo:block>
            </fo:table-cell>

            <fo:table-cell border="1px" text-align="center">
                <fo:block>
                    <xsl:value-of select="quantity"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell border="1px" text-align="center" padding-right="3pt">
                <fo:block>
                    <xsl:value-of select="selling_price"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell border="1px" text-align="center" padding-right="3pt">
                <fo:block>
                    <xsl:value-of select="amt"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
</xsl:stylesheet>
