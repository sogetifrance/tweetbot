<%@page import="org.sogeti.bo.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.datastore.*"%>
<%@ page import="java.util.List"%>
<%@ page import="org.sogeti.bo.UserBean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TweetSender</title>
</head>

<body>
	<c:out
		value="Cette page permet de modifier la configuration des applications utilisant Twitter" />
	<br />
	<p>${erreurMessage}</p>
	<form name="configurationForm" method="post">
criterian1=Tomcat ,Glassfish ,JBoss ,Weblogic ,Websphere, TomEE, PHP, Groovy, Ruby, Python, Scala, Open Source, HTML, JavaScript
#Liste de criteres avec condition, conditions s�par�es par "#" , listes de mots s�par�es par des ";" et  mots spar�s par ",", la fin de ligne ce termine sans rien
#exemple: Software; Developer, Architect, Engineer# Java, J2EE; Spring, Struts, JSF, JSP
criterian1conditions=
criterian2=JDBC, REST, SQL, Programmer, MDE, MDD, MDA, Model-Driven, MyBatis, JavaFX, GWT 
criterian2conditions=Software; Developer, Architect, Engineer
criterian3=Java, JavaEE, J2EE, JEE, Hibernate, JPA, Spring, Struts, Wicket, JSF, JSP, Eclipse
criterian3conditions=
scoreOk=3
		<table border=0>
			<tr>
				<td><label>Oauth Consumer Key</label></td>
				<td colspan="2"><input type="text" id="oauthConsumerKey" name="oauthConsumerKey"
					placeholder="Api key" required /></td>
			</tr>
			<tr>
				<td><label>Oauth Consumer Secret</label></td>
				<td colspan="2"><input type="password" id="oauthConsumerSecret" name="oauthConsumerSecret"
					placeholder="API key secret" required /></td>
			</tr>
			<tr>
				<td><label>oauth Acces Token</label></td>
				<td colspan="2"><input type="text" id="oauthAccesToken" name="oauthAccesToken"
					placeholder="Access Token secret"  required/></td>
			</tr>
			<tr>
				<td><label>oauth Acces Token Secret</label></td>
				<td colspan="2"><input type="password" id="oauthAccesTokenSecret" name="oauthAccesTokenSecret" required
					/></td>
			</tr>
			<tr>
				<td><label>criteres niveau 1</label></td>
				<td colspan="2"><input type="text" id="criterian1" name="criterian1"
					placeholder="criterian1" required /></td>
			</tr>
			<tr>
				<td><label>Liste de criteres avec condition, conditions s�par�es par "#" , listes de mots s�par�es par des ";" et  mots spar�s par ",", la fin de ligne ce termine sans rien</label></td>
				<td colspan="2"><input type="text" id="criterian1conditions" name="criterian1conditions" required
					placeholder="exemple: Software; Developer, Architect, Engineer# Java, J2EE; Spring, Struts, JSF, JSP" /></td>
			</tr>
			<tr>
				<td><label>criteres niveau 2</label></td>
				<td colspan="2"><input type="text" id="criterian2" name="criterian2" required
					placeholder="criterian2" /></td>
			</tr>
			<tr>
				<td><label>Liste de criteres avec condition, conditions s�par�es par "#" , listes de mots s�par�es par des ";" et  mots spar�s par ",", la fin de ligne ce termine sans rien</label></td>
				<td colspan="2"><input type="text" id="criterian2conditions" name="criterian2conditions" required
					placeholder="exemple: Software; Developer, Architect, Engineer" /></td>
			</tr>
				<tr>
				<td><label>criteres niveau 2</label></td>
				<td colspan="2"><input type="text" id="criterian2" name="criterian2" required
					placeholder="criterian2" /></td>
			</tr>
			<tr>
				<td><label>Liste de criteres avec condition, conditions s�par�es par "#" , listes de mots s�par�es par des ";" et  mots spar�s par ",", la fin de ligne ce termine sans rien</label></td>
				<td colspan="2"><input type="text" id="criterian2conditions" name="criterian2conditions" required
					placeholder="exemple: Software; Developer, Architect, Engineer" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="send" class="button"
					style="width: 200px;" value="enregistrer" /></td>
			</tr>
		</table>
	</form>
</body>
</html>