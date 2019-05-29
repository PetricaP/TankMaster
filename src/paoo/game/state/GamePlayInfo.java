package paoo.game.state;

import paoo.core.Camera;
import paoo.core.Entity;
import paoo.core.collisions.CollisionEngine;
import paoo.game.entities.tankentities.Player;

import java.util.ArrayList;

class GamePlayInfo {
    Camera camera;
    ArrayList<Entity> entities;
    CollisionEngine collisionEngine;
    Player player;
    int nEnemies;
    int score;
    int levelNumber;
}
