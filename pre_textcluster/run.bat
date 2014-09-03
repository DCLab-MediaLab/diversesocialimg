javac -cp .;%~dp0*; Main.java
java -classpath ".;%~dp0*" Main %*