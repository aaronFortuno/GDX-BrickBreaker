package net.estemon.studio.util.entity;

import com.badlogic.gdx.math.Rectangle;

import net.estemon.studio.util.entity.script.EntityScript;
import net.estemon.studio.util.entity.script.ScriptController;

public abstract class EntityBase {

    // attributes
    protected float x;
    protected float y;

    protected float width = 1f;
    protected float height = 1f;

    protected Rectangle bounds;

    protected ScriptController scriptController;

    // constructors

    public EntityBase() {
        init();
    }

    private void init() {
        bounds = new Rectangle(x, y, width, height);
        scriptController = new ScriptController(this);
    }

    // public methods
    public void update(float delta) {
        scriptController.update(delta);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public void setWidth(float width) {
        this.width = width;
        updateBounds();
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void updateBounds() {
        bounds.setPosition(x, y);
        bounds.setSize(width, height);
    }

    public void addScript(EntityScript toAdd) {
        scriptController.addScript(toAdd);
    }

    public void removeScript(EntityScript toRemove) {
        scriptController.removeScript(toRemove);
    }
}
