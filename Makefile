sap:	SAP.java
	checkstyle-algs4 SAP.java
	javac-algs4 SAP.java
zip:	Outcast.java WordNet.java SAP.java CachedBFS.java
	if [ -e wordnet.zip ]; then rm wordnet.zip; fi
	zip -r wordnet.zip Outcast.java WordNet.java SAP.java CachedBFS.java
out:	Outcast.java
	checkstyle-algs4 Outcast.java
	javac-algs4 Outcast.java
net:	WordNet.java
	checkstyle-algs4 WordNet.java
	javac-algs4 WordNet.java
bfs:	CachedBFS.java
	checkstyle-algs4 CachedBFS.java
	javac-algs4 CachedBFS.java
