package roilionuhc.roilionuhc.role;

import fr.supercomete.head.GameUtils.Time.TimeUtility;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.CoolDown;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleModifier.HasAdditionalInfo;
import fr.supercomete.head.role.RoleModifier.InvisibleRoleWithArmor;
import fr.supercomete.head.role.RoleModifier.PreAnnouncementExecute;
import fr.supercomete.head.role.Triggers.Trigger_OnHitPlayer;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Kion extends Role implements HasAdditionalInfo,Trigger_OnHitPlayer,InvisibleRoleWithArmor, Trigger_WhileAnyTime , PreAnnouncementExecute {
    public CoolDown coolDown = new CoolDown(2,15*60);
    public int remaning_time = 0;
    UUID rani;
    public Kion(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[]{};
    }

    @Override
    public String askName() {
        return "Kion";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Solo;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet résisance à moins de 50 blocs de Rani.",
                "Vous pouvez vous rendre invisible avec votre armure pendant 15min avec la commande '/rlu invi' (2x/partie)",
                "Lorsque vous êtes invisible si vous rentrez en combat alors vous redeviendrez visible tant que vous êtes en combat. Cependant pendant ce temps vous utilisez quand même votre temps d'invisibilité."
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
        if(rani==null){
            return;
        }
        Player kionplayer = Bukkit.getPlayer(rani);
        if(kionplayer!=null && RoiLionUHC.api.getRoleProvider().getRoleOf(kionplayer)instanceof Rani && kionplayer.isOnline()){
            if(RoiLionUHC.api.getCoordinateHelper().Distance(player,kionplayer)<50){
                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*4));
            }
        }
        if(remaning_time>0){
            remaning_time--;
            RoiLionUHC.api.getPlayerHelper().sendActionBarMessage(player,"§7Temps restant: "+ TimeUtility.transform(remaning_time,"§b"));
            if(!RoiLionUHC.api.getFightProvider().isInCombat(player)){
                hide(player);
                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INVISIBILITY,0,10*3));
            }else{
                show(player);
            }
        }else{
            show(player);
        }
    }

    @Override
    public void PreAnnouncement() {
        rani = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Rani.class);
        setCamp(RLTeams.Duo_Rani_Kion);
    }

    @Override
    public boolean OnHitPlayer(Player player, Player player1, double v, EntityDamageEvent.DamageCause damageCause) {
        show(player);
        return false;
    }

    @Override
    public String[] getAdditionnalInfo() {
        return new String[]{"Vous connaissez Rani: "+RoiLionUHC.api.getRoleProvider().FormalizedWhoHaveRole(Rani.class)};
    }
}
