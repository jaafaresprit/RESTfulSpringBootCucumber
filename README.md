# RESTfulSpringBootCucumber

<b>Implemantation de cas de test</b> :
<pre>
@adresse @modification
Fonctionnalité: Modifier l'adresse d'un abonné 
@TestsRecevabilité
@scenarioTest
Plan du scénario: Modification de l'adresse d'un abonné résidant en France sans ou avec date d'effet
Etant donné un abonné avec une adresse principale &lt;active&gt; en &lt;pays&gt;
Lorsque le conseiller connecté à <canal> modifie l'adresse de l'abonné &lt;condition&gt;
Alors l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné
Et un mouvement de modification d'adresse est créé
</pre>

<b>Languge</b> : JAVA<br>
<b>Framework & Libs</b> : Spring & Spring Boot, Cucumber, TestNG<br>
<b>Exécution de test</b> : <br>
<pre>
cd RESTfulSpringBoot/
mvn verify
</pre>
