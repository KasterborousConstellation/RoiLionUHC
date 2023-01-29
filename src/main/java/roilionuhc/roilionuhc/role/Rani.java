package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleModifier.HasAdditionalInfo;
import fr.supercomete.head.role.RoleModifier.PreAnnouncementExecute;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.head.role.Triggers.Trigger_WhileDay;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Rani extends Role implements HasAdditionalInfo,PreAnnouncementExecute, Trigger_WhileAnyTime, Trigger_WhileDay {
    UUID kion;

    public Rani(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Rani";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Solo;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet force pendant le jour.",
                "Si vous êtes a moins de 50blocs de Kion vous avez l'effet résistance 1"
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
    public void PreAnnouncement() {
        kion = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Kion.class);
        setCamp(RLTeams.Duo_Rani_Kion);
    }

    @Override
    public void WhileAnyTime(Player player) {
        if(kion==null){
            return;
        }
        Player kionplayer = Bukkit.getPlayer(kion);
        if(kionplayer!=null && RoiLionUHC.api.getRoleProvider().getRoleOf(kionplayer)instanceof Kion && kionplayer.isOnline()){
            if(RoiLionUHC.api.getCoordinateHelper().Distance(player,kionplayer)<50){
                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*4));
            }
        }
    }

    @Override
    public void WhileDay(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,20*5));
    }

    @Override
    public String[] getAdditionnalInfo() {
        return new String[]{"Vous connaissez Kion: "+RoiLionUHC.api.getRoleProvider().FormalizedWhoHaveRole(Kion.class)};
    }
}
