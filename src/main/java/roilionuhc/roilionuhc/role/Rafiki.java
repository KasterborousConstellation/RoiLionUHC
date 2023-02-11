package roilionuhc.roilionuhc.role;

import fr.supercomete.head.core.Main;
import fr.supercomete.head.role.CoolDown;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.RoleModifier.PreAnnouncementExecute;
import fr.supercomete.head.role.Triggers.Trigger_OnScoreBoardUpdate;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.head.world.ScoreBoard.SimpleScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Rafiki extends Role implements Trigger_OnScoreBoardUpdate , PreAnnouncementExecute, Trigger_WhileAnyTime {
    public UUID targeted;
    public CoolDown coolDown = new CoolDown(0,300);
    HashMap<UUID,Double> score = new HashMap<>();
    public Rafiki(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Rafiki";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous pouvez avec la commande '/rlu coco' utilisé vos noix. Le colorant orange vous donnera 10% de speed pendant 30s. Le colorant rouge vous donnera 10% de force pendant 30s. Le colorant jaune vous donnera 10% de résistance pendant 30s.",
                "Les effets de peinture ne sont pas cumulables et ils ont 5minutes de cooldown.",
                "Vous pouvez avec la commande '/rlu choix' connaitre le camp d'un joueur. Pour cela après l'avoir choisi il faudra faire monter le pourcentage au dessus de sa tête jusqu'a 100%. Une fois arrivé à 100% vous connaitrez son camp.",
                "Entre 1 et 2 blocs du joueurs vous obtiendez 7% toutes les 15s, entre 3 et 5 5% toutes les 17s, entre 6 et 10 3% toutes les 19s et entre 11 et 15 1% tout les 21s."
        );
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        return new ItemStack[0];
    }

    @Override
    public boolean AskIfUnique() {
        return true;
    }

    @Override
    public String AskHeadTag() {
        return null;
    }

    @Override
    public void onScoreBoardUpdate(Player player, SimpleScoreboard simpleScoreboard, Scoreboard scoreboard) {
        try {
            scoreboard.getObjective("§a%").unregister();
        }catch (Exception ignored) {}
        Objective ob = scoreboard.registerNewObjective("§a%", "score");
        ob.setDisplaySlot(DisplaySlot.BELOW_NAME);
        Score score3;
        for (UUID uu : score.keySet()) {
            Player p = Bukkit.getPlayer(uu);
            if (p == null || player.getUniqueId()==uu)
                continue;
            score3 = ob.getScore(p.getName());
            int i = (int)((double)score.get(uu));
            if(i==-1){
                i=100;
            }
            score3.setScore(i);
        }
    }

    @Override
    public void PreAnnouncement() {
        for(UUID uuid : RoiLionUHC.api.getRoleProvider().getRoleMap().keySet()){
            score.put(uuid,0.0);
        }
    }

    @Override
    public void WhileAnyTime(Player player) {
        if(targeted!=null){
            Player target = Bukkit.getPlayer(targeted);
            if(target!=null && RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(target.getUniqueId())){
                double distance = RoiLionUHC.api.getCoordinateHelper().Distance(player,target);
                if(!(score.get(targeted)==-1.0)){
                    if(distance<2.5){
                        score.put(targeted,score.get(targeted)+(7.0/15.0));
                    }else if(distance<5.5){
                        score.put(targeted,score.get(targeted)+(5.0/17.0));
                    }else if(distance<10.5){
                        score.put(targeted,score.get(targeted)+(3.0/19.0));
                    }else if(distance<15){
                        score.put(targeted,score.get(targeted)+(1.0/21.0));
                    }
                    if(score.get(targeted)>100){
                        score.put(targeted,-1.0);
                        player.sendMessage("§aLe camp de "+target.getName()+" est "+ RoleHandler.getRoleOf(target).getCamp().getColoredName());
                    }
                }

            }
        }

    }
}
