JAVAC = javac
JAVA = java
BIN_DIR = bin

CP = jaylib-ffm-5.5.0-2.jar

JAVA_FILES = src/App.java \
            src/com/game/Core.java \
            src/com/objects/SpriteSheet.java \
            src/com/player/Player.java \
            src/com/player/InitPlayer.java \
            src/com/player/PlayerMovement.java \
            src/com/enums/PlayerType.java \
            src/com/enums/SpriteMovement.java \
            src/com/game/Cameras.java \
            src/com/Environement/GameMap.java \
            src/com/Environement/CollisionMap.java  \
            src/com/Physic/Collisions.java \
            src/com/MapsBuild/Cyty_01.java \
            src/com/MapsBuild/Donjon_outside.java \
            src/com/Environement/InitAllMaps.java \
            src/com/Utility/ForBuildGame.java \

all: compile

compile:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) -cp $(CP) $(JAVA_FILES)

run: compile
	$(JAVA) --enable-native-access=ALL-UNNAMED -cp "$(CP);$(BIN_DIR)" App

clean:
	@if exist $(BIN_DIR) rd /s /q $(BIN_DIR) 

.PHONY: all compile run clean