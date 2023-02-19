package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Nuka extends Role implements Trigger_WhileAnyTime {
    boolean given= false;
    public Nuka(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Nuka";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Rejected;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "A moins de 30blocs de Vitani vous avez l'effet force.",
                "A moins de 45blocs de Zira vous avez l'effet vitesse.",
                "A la mort de Vitani vous avez l'effet force pendant 20min.",
                "A la mort de Ziza vous avez l'effet vitesse 2 permanent."
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
    public void WhileAnyTime(Player player) {
        UUID vitani = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Vitani.class);
        UUID Zira = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Zira.class);
        if(vitani==null){
            if(!given){
                given=true;
                player.sendMessage("§cVitani est morte, vous avez l'effet force pendant 20min.");
                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,20*20*60));
            }else{
                Player vitaniplayer= Bukkit.getPlayer(Zira);
                if(vitaniplayer!=null && RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(vitaniplayer.getUniqueId())&&vitaniplayer.isOnline()){
                    double distance = RoiLionUHC.api.getCoordinateHelper().Distance(player,vitaniplayer);
                    if(distance<30){
                        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,20*5));
                    }
                }
            }
        }
        if(Zira==null){
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.SPEED,1,20*5));
        }else{
            Player ziraplayer= Bukkit.getPlayer(Zira);
            if(ziraplayer!=null && RoiLionUHC.api.getPlayerHelper().IsPlayerAlive(ziraplayer.getUniqueId())&&ziraplayer.isOnline()){
                double distance = RoiLionUHC.api.getCoordinateHelper().Distance(player,ziraplayer);
                if(distance<45){
                    RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.SPEED,0,20*5));
                }
            }
        }
    }
}
