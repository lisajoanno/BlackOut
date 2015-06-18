rem set PATH=../../lib/;../../jre/bin/
rem javac -cp .;../../VocalyzeSIVOX/bin/SI_VOX.jar -Djava.library.path=../ressources/lib/ -d ../bin devintAPI/*.java jeu/*.java

javac -encoding UTF-8 -cp .;../../VocalyzeSIVOX/bin/SI_VOX.jar -d ../bin devintAPI/*.java jeu/*.java jeu/base/*.java jeu/blackOut/graphics/*.java jeu/blackOut/graphics/grid/*.java jeu/blackOut/utils/*.java jeu/blackOut/utils/instructions/*.java jeu/blackOut/utils/helps/*.java jeu/blackOut/utils/scores/*.java jeu/blackOut/utils/settings/*.java
pause
