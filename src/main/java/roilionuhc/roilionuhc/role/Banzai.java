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

public class Banzai extends Role implements Trigger_WhileAnyTime, Trigger_WhileNight {
    public boolean passed = false;
    public boolean used=false;
    public Banzai(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Banzai";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Hyennes;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Lorsque vous passez a coté de Shenzi pour la première fois vous obtenez l'effet résistance permanent.",
                "Vous avez la commande /rlu tp qui vous permet de téléporter tout les joueurs dans un rayon de 50blocs dans votre cimetière.",
                "Au bout de 2min 30 tous les joueurs vivants dans votre cimetière seront retéléporter dans sur la map.",
                "Vous avez l'effet §cforce§7 durant la nuit."
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
        if(!passed) {
            for (final Player other : Bukkit.getOnlinePlayers()) {
                if (player.getUniqueId() != other.getUniqueId() && RoiLionUHC.api.getGameProvider().getPlayerList().contains(other.getUniqueId())) {
                    if (other.getLocation().distance(player.getLocation()) < 5) {
                        Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(other);
                        if (role instanceof Shenzi) {
                            passed = true;
                        }
                    }
                }
            }

        }else{
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*4));
        }
    }

    @Override
    public void WhileNight(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,20*3));
    }
}
