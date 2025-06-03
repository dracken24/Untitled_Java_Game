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

all: compile

compile:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) -cp $(CP) $(JAVA_FILES)

run: compile
	$(JAVA) --enable-native-access=ALL-UNNAMED -cp "$(CP);$(BIN_DIR)" App

clean:
	@if exist $(BIN_DIR) rd /s /q $(BIN_DIR) 

.PHONY: all compile run clean