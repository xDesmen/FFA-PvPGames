package cc.pvpgames.ffa.perk;

import lombok.Data;

@Data
public class Perk {

    private String id;
    private String displayName;
    private String description;

    private double cost;


    public Perk(String id) {
        this.id = id;
    }


}
