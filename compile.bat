del /P bin
mkdir bin
cd bin
mkdir game
mkdir pages
mkdir maze

cd maze
mkdir autoplayer
mkdir modification

cd ../..

javac -cp src -d "bin" src/game/*.java 
javac -cp src -d "bin" src/pages/*.java
javac -cp src -d "bin" src/maze/*.java   
javac -cp src -d "bin/maze" src/maze/autoplayer/*.java
javac -cp src -d "bin/maze" src/maze/modification/*.java

cp img/* bin

echo "done"
