package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.Triggers.Trigger_WhileNight;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Fauve extends Role implements Trigger_WhileNight {
    public Fauve(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Fauve";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Rejected;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet résistance pendant la nuit."
        );
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        return new ItemStack[0];
    }

    @Override
    public boolean AskIfUnique() {
        return false;
    }

    @Override
    public String AskHeadTag() {
        return null;
    }

    @Override
    public void WhileNight(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*5));
    }
}
