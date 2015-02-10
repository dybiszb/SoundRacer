package com.example.mini.game.shapes.complex;


import com.example.mini.game.GameRenderer;
import com.example.mini.game.logic.GlobalState;
import com.example.mini.game.util.mathematics.Vector3;

/**
 * Class represents objects concerning player's movement,
 * road he is traveling and obstacles to omit.
 * <p></p>
 * In addition it holds all constants for each object type,
 * schedules rendering process and adapts scene to animation
 * changes in time.
 * <p></p>
 * Created by dybisz on 2014-12-01.
 */
public class GameBoard {
    /**
     * Tells {@link #render(float[]) render method} whether draw
     * {@link #road road} or not.
     */
    private static final boolean ROAD_RENDERING = true;
    /**
     * Represents a need for animation of the {@link #road}or not.
     * See {@link #switchFrame()} method's body.
     */
    private static final boolean ROAD_ANIMATION = true;
    /**
     * Tells {@link #render(float[]) render method} whether draw
     * obstacles objects produced {@link #obstaclesWorkshop obstacle workshop}
     * or not.
     */
    private static final boolean OBSTACLES_WORKSHOP_RENDERING = false;
    /**
     * Represents a need for animation of the {@link #obstaclesWorkshop} or not.
     * See {@link #switchFrame()} method's body.
     */
    private static final boolean OBSTACLES_WORKSHOP_ANIMATION = false;
    /**
     * TODO
     */
    private static final boolean HORIZON_RIBBON_RENDERING = true;
    /**
     * TODO
     */
    private static final boolean HORIZON_RIBBON_ANIMATION = true;
    /**
     * TODO
     */
    private static final boolean PLAYER_RENDERING = true;
    /**
     * TODO
     */
    private static final boolean PLAYER_ANIMATION = true;
    /**
     * Tells when to call {@link #switchFrame()} method to
     * proceed with animation.
     * Bigger this constant slower the animation of objects.
     */
    private static final float ANIMATION_FREQUENCY = 1.0f;
    /**
     * Specifies width of the generated road.
     */
    public static final float ROAD_WIDTH = 20.0f;
    /**
     * Color of t{@link #road road} in RGBA format.
     */
    private static float[] ROAD_COLOR = new float[]{0.5f, 0.5f, 0.5f, 0.8f};
    /**
     * Number of vertices used for GL_STRIPS to generate road.
     * More vertices  and lower {@link #TIME_UNIT_LENGTH}, higher the accuracy.
     * Also with {@link #TIME_UNIT_LENGTH} sets {@link #road} length.
     */
    public static final int ROAD_VERTICES_PER_BORDER = 150;
    /**
     * Standard angle of a turn curve for turning right.
     */
    public static final float ROAD_DEFAULT_TURN_RIGHT_ANGLE = 90.0f;
    /**
     * Standard angle of a turn curve for turning left.
     */
    public static final float ROAD_DEFAULT_TURN_LEFT_ANGLE = 90.0f;
    /**
     * Very important constant. It tells how far should
     * object move at each animation step.
     * Bigger the number, bigger "steps" of the animation.
     * E.g. sinus function with this constant = 1, will be more
     * flat than with constant = 0.5;
     * <p></p>
     * Can also be interpreted as space "between two vertices" of the road border.
     */
    public static final float TIME_UNIT_LENGTH = GlobalState.FLUX_LENGTH/5;
    /**
     * To save memory, class generates finite amount of obstacle
     * objects and then rearrange them on the scene using transformation
     * matrices.
     */
    public static final int NUMBER_OF_OBSTACLES = 16;
    /**
     * Color of whole lattice in RGBA format.
     */
    public static final float[] LATTICE_COLOR = new float[]{0.5f, 0.5f, 0.5f, 0.5f};
    /**
     * States what are the coordinates of the bottom right corner of the lattice.
     */
    public static final Vector3 LATTICE_BOTTOM_RIGHT_CORNER = new Vector3(0.0f, 0.0f, 0.0f);
    /**
     * We assume that lattice consists of squares and this constant tells how long
     * is the side of each of them.
     */
    public static final float LATTICE_GAP_LENGTH = 2.0f;
    /**
     * Specifies color of each obstacle generated by
     * {@link com.example.mini.game.shapes.complex.ObstaclesWorkshop}.
     */
    public static final float[] OBSTACLES_COLOR = new float[]{0.1f, 0.2f, 0.3f, 1.0f};
    /**
     * Obviously not every frame of the animation will produce new obstacle on the road,
     * so we need to specify kind of delay to this process.
     */
    public static final float OBSTACLE_GENERATION_TIME = 20f;
    /**
     * Pi constant.
     */
    public static final float PI = 3.14159265359f;
    /**
     * Hard coded length of texture loaded to {@link #horizonRibbon}.
     */
    public static final float HORIZON_RIBBON_TEXTURE_WIDTH = 500.0f;
    /**
     * Hard coded length of texture loaded to {@link #horizonRibbon}.
     */
    public static final float HORIZON_RIBBON_TEXTURE_HEIGHT = 312.0f;
    /**
     * Specifies how many vertices we want to use per border in
     * our {@link #horizonRibbon}. More vertices, more precise it shapes semi-ellipse.
     */
    public static final int HORIZON_RIBBON_VERTICES_PER_BORDER = 250;
    /**
     * Radius of {@link #horizonRibbon}.
     */
    public static final float HORIZON_RIBBON_RADIUS = 150.0f;
    /**
     * How much of 360 degrees or ribbon takes. Higher the angle "more closed"
     * is a circle of view.
     */
    public static final float HORIZON_RIBBON_ANGLE = 220;
    /**
     * How height is {@link #horizonRibbon}. it is automatically calculated from
     * simple formula which base strictly on ratio between elements.
     */
    public static final float HORIZON_RIBBON_HEIGHT = ((2 * PI * HORIZON_RIBBON_RADIUS /
            (360 / HORIZON_RIBBON_ANGLE)) * HORIZON_RIBBON_TEXTURE_HEIGHT) / HORIZON_RIBBON_TEXTURE_WIDTH;
    /**
     * At the beginning center of the horizon semi-sphere is positioned at the origin
     * and we want to translate it. This vector represents this translation.
     */
    public static final Vector3 HORIZON_RIBBON_DEFAULT_TRANSLATION = new Vector3(ROAD_WIDTH / 2,
            -HORIZON_RIBBON_HEIGHT * 0.4f,
            ROAD_VERTICES_PER_BORDER * TIME_UNIT_LENGTH * 0.1f);
    /**
     * If {@link #HORIZON_RIBBON_ANGLE} is different than 180(particularly, when it is > 180),
     * we are interested in slight rotation around Y axis.
     * It is caused by the definition of  {@link com.example.mini.game.shapes.complex.HorizonRibbon},
     * which tells to build up vertices of this object starting from angle 0 and following
     * sphere coordinates.
     */
    public static final float[] HORIZON_RIBBON_DEFAULT_ROTATION = new float[]{
            (HORIZON_RIBBON_ANGLE - 180f > 0) ? (HORIZON_RIBBON_ANGLE - 180f) / 2f : 0.0f
            , 0.0f, 1.0f, 0.0f,
    };
    /**
     * To make code more transparent I'll use this constant by calling this float.
     */
    public static final float DEGREES_TO_RADIAN_COEFFICIENT = 0.0174532925f;
    /**
     * Constant determines how often we generate road which turns right.
     */
    public static final float TURNING_RIGHT_ANIMATION_FREQUENCY = 120.0f;
    /**
     * Constant determines how often we generate road which turns right.
     */
    public static final float TURNING_LEFT_ANIMATION_FREQUENCY = 120.0f;
    /**
     * Width of player cube
     */
    public static final float PLAYER_WIDTH = 2.0f;
    /**
     * Encapsulation of all methods concerning road.
     * See {@link  com.example.mini.game.shapes.complex.Road Road class}
     * for more details.
     */
    private Road road;
    /**
     * Encapsulation of all methods concerning road.
     * See {@link  com.example.mini.game.shapes.complex.ObstaclesWorkshop ObstacleWorkshop class}
     * for more details.
     */
    private ObstaclesWorkshop obstaclesWorkshop;
    /**
     * TODO
     */
    private HorizonRibbon horizonRibbon;
    /**
     * TODO
     */
    private Player player;
    /**
     * Counts to next animation frame switch.
     */
    public static int animationCounter = 1;


    public GameBoard() {
        // do poprawki road
        road = new Road(ROAD_VERTICES_PER_BORDER, TIME_UNIT_LENGTH, ROAD_WIDTH, ROAD_COLOR);
        obstaclesWorkshop = new ObstaclesWorkshop();
        horizonRibbon = new HorizonRibbon();
        player = new Player();
    }

    /**
     * Handles drawing of object specified by appropriate constants.
     *
     * @param mvpMatrix Matrix to apply to objects before rendering.
     *                  Usually camera's matrix.
     */
    public void render(float[] mvpMatrix) {
        if (itIsTimeForAnimation()) {
            switchFrame();
            animationCounter = 0;
        }
        if (HORIZON_RIBBON_RENDERING) {
            horizonRibbon.draw(mvpMatrix);
        }
        if (ROAD_RENDERING) {
            //road.draw(mvpMatrix);
            road.lightsFogDraw(mvpMatrix);
        }
        if (OBSTACLES_WORKSHOP_RENDERING) {
            //obstaclesWorkshop.draw(mvpMatrix);
        }
        if(PLAYER_RENDERING) {
            player.draw(mvpMatrix);
        }
        incrementAnimationCounter();
    }

    /**
     * Proceed with animation of objects with appropriate constants value.
     */
    public void switchFrame() {
        if (HORIZON_RIBBON_ANIMATION) {
            horizonRibbon.switchFrame();
        }
        if (ROAD_ANIMATION) {
            road.switchFrame();
        }
        if (OBSTACLES_WORKSHOP_ANIMATION) {
            //obstaclesWorkshop.switchFrame();
        }
        if(PLAYER_ANIMATION) {
            player.switchFrame();
        }

    }

    /**
     * Increments {@link #animationCounter animation counter} by 1.
     */
    public void incrementAnimationCounter() {
        animationCounter++;
    }

    /**
     * Checks frame switch condition and returns result.
     *
     * @return True or false, depending on the answer to the question:
     * "is {@link #animationCounter animation counter} the same as
     * {@link #ANIMATION_FREQUENCY animation frequency constant}".
     */
    private boolean itIsTimeForAnimation() {
        return (animationCounter == ANIMATION_FREQUENCY) ? true : false;
    }

}
