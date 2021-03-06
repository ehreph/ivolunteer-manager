<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
    <head>
		<link rel="stylesheet" href="component.css"/>
	</head>
	<body>
	    <!-- NavBar Top -->
		<div class="topnav">
			<xsl:for-each select="/Document/Masters/Master">
				<xsl:if test="./PackageInfo/Name='headerComponent'"> <!-- Check masters for header -->
				
					<!-- iterate over shapes for header - headline-->
					<xsl:for-each select="./Diagram/Widgets/Shape"> 
						<xsl:sort select="./Rectangle/Rectangle/@X" order="ascending" data-type="number" />
						
						<xsl:if test="string-length(./Text)!=0 and string(number(./Text))='NaN'">
							<a href="#{generate-id()}"><xsl:value-of select="./Text"/></a>
						</xsl:if>
					   
					</xsl:for-each>
					
				</xsl:if>
			</xsl:for-each>
			
			<xsl:for-each select="/Document/Masters/Master">
				<xsl:if test="./PackageInfo/Name='headerComponent'"> <!-- Check masters for header -->
				
					<!-- iterate over shapes for header - number-->
					<xsl:for-each select="./Diagram/Widgets/Shape"> 
						<xsl:sort select="./Rectangle/Rectangle/@X" order="ascending" data-type="number" /> 
						
						<xsl:if test="string-length(./Text)!=0 and string(number(./Text))!='NaN'">
							<a href="#{generate-id()}"><xsl:value-of select="./Text"/></a>
						</xsl:if>
					   
					</xsl:for-each>

				</xsl:if>
			</xsl:for-each>
		</div>
		
		
		<!-- Content-->
		<!-- Headline-->
		<xsl:for-each select="/Document/Pages/Page"> 
			<xsl:if test="./PackageInfo/Name='User Aktivitäten'"> <!-- Check masters for header -->
				<xsl:if test="string-length(./Diagram/Widgets/Shape/Text)!=0">
					<h3><xsl:value-of select="./Diagram/Widgets/Shape/Text"/></h3>
				</xsl:if>
				
			</xsl:if>
		</xsl:for-each>
		
		<div style="overflow-x:auto;">
			<table>
				<!-- activities -->
				<xsl:for-each select="/Document/Masters/Master">
					<xsl:if test="./PackageInfo/Name='userActivity'"> <!-- Check masters for header -->
						<tr>
							<xsl:for-each select="./Diagram/Widgets/Shape"> 
									
								<xsl:if test="string-length(./Text)!=0">
									<td><xsl:value-of select="./Text"/></td>
								</xsl:if>
								   
							</xsl:for-each>
						</tr>
					</xsl:if>
				</xsl:for-each>

				<br/><br/>

				<xsl:for-each select="/Document/Masters/Master">
					<xsl:if test="./PackageInfo/Name='userActivity'"> <!-- Check masters for header -->
						<tr>
							<xsl:for-each select="./Diagram/Widgets/Shape"> 
									
								<xsl:if test="string-length(./Text)!=0">
									<td><xsl:value-of select="./Text"/></td>
								</xsl:if>
								   
							</xsl:for-each>
						</tr>
					</xsl:if>
				</xsl:for-each>
				
				<br/><br/>
				
				<xsl:for-each select="/Document/Masters/Master">
					<xsl:if test="./PackageInfo/Name='userActivity'"> <!-- Check masters for header -->
						<tr>
							<xsl:for-each select="./Diagram/Widgets/Shape"> 
									
								<xsl:if test="string-length(./Text)!=0">
									<td><xsl:value-of select="./Text"/></td>
								</xsl:if>
								   
							</xsl:for-each>
						</tr>
					</xsl:if>
				</xsl:for-each>
			</table>
		</div>
		
		<!-- NavBar Bottom -->
		<div class="navbar">
			<xsl:for-each select="/Document/Masters/Master">
				<xsl:if test="./PackageInfo/Name='footerComponent'"> <!-- Check masters for footer -->
				
					<xsl:for-each select="./Diagram/Widgets/Shape"> <!-- iterate over shapes-->
						<xsl:sort select="./Rectangle/Rectangle/@X" order="ascending" data-type="number" />
						
						<xsl:if test="string-length(./Text)!=0">
							<a href="#{generate-id()}"><xsl:value-of select="./Text"/></a>
						</xsl:if>
					   
					</xsl:for-each>

				</xsl:if>
			</xsl:for-each>
		</div>
		
	</body>
  </html>
</xsl:template>

</xsl:stylesheet>