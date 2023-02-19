package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.head.role.Triggers.Trigger_WhileNight;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Vitani extends Role implements Trigger_WhileNight, Trigger_WhileAnyTime {
    public UUID target =null;
    int advance=0;
    public Vitani(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Vitani";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Rejected;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet résistance pendant la nuit.",
                "Vous pouvez avec la commande '/rlu infect', infecté un joueur. Une fois le joueur désigné il faudra atteindre 1200pts en restant près du joueur.",
                "Entre 10-15blocs: 1pt. Entre 5-10blocs: 5pts. A moins de 1 blocs: 10pts",
                "Scar, Simba, Kovu, Rani et Kion ne peuvent pas être infecté."
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
    public void WhileNight(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*5));
    }

    @Override
    public void WhileAnyTime(Player player) {
        final Player tar = Bukkit.getPlayer(target);
        if(tar!=null&&RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(tar.getUniqueId())){
            double distance = RoiLionUHC.api.getCoordinateHelper().Distance(player,tar);
            if(advance>1200){
                advance=-1;
                Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(tar);
                if(!(role instanceof Scar||role instanceof Simba||role instanceof Kovu||role instanceof Kion||role instanceof Rani)){
                    role.setCamp(RLTeams.Rejected);
                    tar.sendMessage("§cVous avez été infecté. Vous devez maintenant gagner avec Les rejetés de la terre des lions.");
                }
                player.sendMessage("§aL'infection est terminée");
            }
            if(advance!=-1&&advance<1200){
                RoiLionUHC.api.getPlayerHelper().sendActionBarMessage(player,"§bInfection: §r"+advance+"/1200");
                if(distance<15){
                    advance+=1;
                    if(distance<10){
                        advance+=4;
                        if(distance<1.1){
                            advance+=5;
                        }
                    }
                }
            }

        }

    }
}