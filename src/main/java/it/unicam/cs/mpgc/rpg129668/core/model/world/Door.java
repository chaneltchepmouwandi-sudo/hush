package it.unicam.cs.mpgc.rpg129668.core.model.world;

/**
 * Una porta chiusa a chiave, apribile fornendo il codice della chiave
 * corretta. Una volta aperta, resta aperta.
 */
public class Door implements Obstacle {

    private final String requiredKeyId;
    private boolean open;

    public Door(String requiredKeyId) {
        this.requiredKeyId = requiredKeyId;
        this.open = false;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean tryResolve(String code) {
        if (requiredKeyId.equals(code)) {
            open = true;
        }
        return open;
    }
}