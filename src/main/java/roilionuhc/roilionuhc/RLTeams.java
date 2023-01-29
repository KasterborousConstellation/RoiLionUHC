package roilionuhc.roilionuhc;

import fr.supercomete.head.role.KasterBorousCamp;
import org.bukkit.ChatColor;

public enum RLTeams implements KasterBorousCamp {
    LionEarth("Terre des Lions",ChatColor.GREEN,false),
    Hyennes("Les Hyènes",ChatColor.RED,false),
    Rejected("Les rejetés de la terre des lions",ChatColor.BLUE,false),
    Solo("Solitaire",ChatColor.GOLD,true),
    Scar("Camp de Scar",ChatColor.GOLD,false),
    Duo_Rani_Kion("Duo Rani/Kion",ChatColor.GOLD,false)
    ;
    private final ChatColor color;
    private final String name;
    private final boolean singleplayervictory;
    RLTeams(String name,ChatColor color,boolean singleplayervictory){
        this.color = color;
        this.name=name;
        this.singleplayervictory=singleplayervictory;
    }
    @Override
    public ChatColor getColor() {
        return color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean singleplayervictory() {
        return singleplayervictory;
    }
}
